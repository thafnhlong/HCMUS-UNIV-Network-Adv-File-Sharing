package hcmus.fileserver.entity;

import java.io.IOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class Packet {

    private Lock lock = new Lock();

    public void test() {
        sendHello();
        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
        sendHello();
        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();

        System.out.println("Bat dong bo");
    }

    public void sendHello() {
        try {
            lock.lock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }.start();
    }

    public static class PKUDP {
        // public int index;
        // 63 max bytes
        public static final int BUFFERDATA = 8 + FileReader.BUFFER;

        public static byte[] getPacket(long index, List<Byte> bytes) {
            List<Byte> bindex = Arrays.asList(SObject.getBytesFromLong(index));
            LinkedList<Byte> fragmentList = new LinkedList<>() {
                {
                    addAll(bindex);
                    addAll(bytes);
                }
            };
            return SObject.convertByteTobyte(fragmentList.toArray(new Byte[0]));
        }

        public static byte[] getPacket(long index, byte[] bytes) {
            return getPacket(index, Arrays.asList(SObject.convertbyteToByte(bytes)));
        }

    }

    public void createServer(int PORT) {
        try {
            DatagramSocket utpSocket = new DatagramSocket(PORT);
            while (true) {
                byte[] buff = new byte[PKUDP.BUFFERDATA];
                DatagramPacket dpRead = new DatagramPacket(buff, PKUDP.BUFFERDATA);
                utpSocket.receive(dpRead);
                byte[] recie = dpRead.getData();
                long index = SObject.getLong(recie, 0);

                String fileName = new String(SObject
                        .convertByteTobyte(SObject.getSubBytes(recie, 8, dpRead.getLength() - 1).toArray(new Byte[0])));
                System.out.println("Receive From Client: -" + index + "-" + fileName + " - "
                        + dpRead.getAddress().toString() + "-" + dpRead.getPort());

                byte[] data = FileReader.readFileByIndex(index, fileName);

                if (new Random().nextInt(10) > 8) {
                    System.out.println("Failed send");
                    continue;
                }
                sendPacket(dpRead, utpSocket, index, data);
                System.out.println("Send To Client: " + dpRead.getAddress().toString() + "-" + dpRead.getPort());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendPacket(DatagramPacket dpSend, DatagramSocket utpSocket, long index, byte[] byteSend)
            throws IOException {
        byte[] ret = PKUDP.getPacket(index, byteSend);
        dpSend.setData(ret, 0, ret.length);
        utpSocket.send(dpSend);
    }

    class ClientRequest extends Thread {
        private String server;
        private int port;
        private ShareFile data;

        public ClientRequest(String server, int port, ShareFile data) {
            this.server = server;
            this.port = port;
            this.data = data;
        }

        @Override
        public void run() {
            DatagramSocket utpSocket;
            try {
                utpSocket = new DatagramSocket();

                InetAddress address = InetAddress.getByName(server);

                byte[] buff = new byte[PKUDP.BUFFERDATA];
                DatagramPacket dpSend = new DatagramPacket(buff, PKUDP.BUFFERDATA, address, port);

                byte[] byteFileName = data.getFileName().getBytes();

                LinkedList<Long> ll = new LinkedList<>();

                for (long i = 0; i < data.getFileSize(); i += FileReader.BUFFER) {
                    ll.add(i);
                    sendPacket(dpSend, utpSocket, i, byteFileName);

                    if ((i / FileReader.BUFFER) % 10 == 0) // 10 * buffer pause
                        Thread.sleep(100);
                }

                utpSocket.setSoTimeout(100);

                long now = Timer.timenow();

                String uniqueFile = Timer.getUniqueNumber();
                List<String> tempFiles = new LinkedList<>();

                while (true) {
                    try {
                        utpSocket.receive(dpSend);
                        byte[] recie = dpSend.getData();
                        long index = SObject.getLong(recie, 0);
                        if (ll.contains(index)) {
                            ll.remove(Long.valueOf(index));
                            String fileName = String.valueOf(uniqueFile) + "_" + String.valueOf(index);
                            tempFiles.add(fileName);
                            List<Byte> fileByteData = SObject.getSubBytes(recie, 8, dpSend.getLength() - 1);
                            ShareFile.writeFileByBytes(fileName, fileByteData);
                        }
                        System.out.println("Receive From Server: -" + index + "-" + data.getFileName());
                    } catch (SocketTimeoutException e) {
                    }

                    if (ll.size() == 0) {
                        break;
                    }
                    if (Timer.timenow() - now > 1) {
                        sendPacket(dpSend, utpSocket, ll.getFirst(), byteFileName);
                    }
                    ShareFile.mergeFile(data.getFileName(), tempFiles);
                    System.out.println("Client ok");
                }
            } catch (IOException | InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }

    public void createClient(List<ShareFile> ShareFiles, String SERVER, int PORT) {
        System.out.println(ShareFiles.size());
        for (ShareFile shareFile : ShareFiles) {
            new ClientRequest(SERVER,PORT,shareFile).start();
        }
    }
}
