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
import java.util.TreeMap;

public class Packet {
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
                // System.out.println("Receive From Client: -" + index + "-" + fileName + " - "
                //         + dpRead.getAddress().toString() + "-" + dpRead.getPort());

                byte[] data = FileReader.readFileByIndex(index, fileName);

                // fake drop packet
                // if (new Random().nextInt(10) > 8) {
                //     System.out.println("Failed send");
                //     continue;
                // }

                DatagramPacket dpSend = new DatagramPacket(buff, PKUDP.BUFFERDATA,dpRead.getAddress(),dpRead.getPort());
                sendPacket(dpSend, utpSocket, index, data);
                // System.out.println("Send To Client: " + dpRead.getAddress().toString() + "-" + dpRead.getPort());
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


    // class ClientRequest extends Thread {
    //     private String server;
    //     private int port;
    //     private ShareFile data;

    //     public ClientRequest(String server, int port, ShareFile data) {
    //         this.server = server;
    //         this.port = port;
    //         this.data = data;
    //     }

    //     @Override
    //     public void run() {
    //         try {
    //             DatagramSocket utpSocket = new DatagramSocket();
    //             InetAddress address = InetAddress.getByName(server);
    //             byte[] buff = new byte[PKUDP.BUFFERDATA];
    //             byte[] byteFileName = data.getFileName().getBytes();

    //             LinkedList<Long> ll = new LinkedList<>();
    //             for (long i = 0; i < data.getFileSize(); i += FileReader.BUFFER) {
    //                 ll.add(i);
    //                 DatagramPacket dpSend = new DatagramPacket(buff, PKUDP.BUFFERDATA, address, port);
    //                 sendPacket(dpSend, utpSocket, i, byteFileName);

    //                 if ((i / FileReader.BUFFER) % 10 == 0) // 10 * buffer pause
    //                     Thread.sleep(100);
    //             }

    //             utpSocket.setSoTimeout(100);

    //             long now = Timer.timenow();

    //             String uniqueFile = Timer.getUniqueNumber();
    //             TreeMap<Long,String> tempFiles = new TreeMap<>();

    //             while (true) {
    //                 try {
    //                     DatagramPacket dpRead = new DatagramPacket(buff, PKUDP.BUFFERDATA, address, port);
    //                     utpSocket.receive(dpRead);
    //                     byte[] recie = dpRead.getData();
    //                     long index = SObject.getLong(recie, 0);
    //                     if (ll.contains(index)) {
    //                         ll.remove(Long.valueOf(index));
    //                         String fileName = String.valueOf(uniqueFile) + "_" + String.valueOf(index);
    //                         tempFiles.put(index, fileName);
    //                         List<Byte> fileByteData = SObject.getSubBytes(recie, 8, dpRead.getLength() - 1);
    //                         ShareFile.writeFileByBytes(fileName, fileByteData);
    //                         System.out.println(fileByteData.size());
    //                     }
    //                     System.out.println("Receive From Server: -" + index + "-" + data.getFileName());
    //                 } catch (SocketTimeoutException e) {
    //                 }

    //                 if (ll.size() == 0) {
    //                     break;
    //                 }
    //                 if (Timer.timenow() - now > 1) {
    //                     DatagramPacket dpSend = new DatagramPacket(buff, PKUDP.BUFFERDATA, address, port);
    //                     sendPacket(dpSend, utpSocket, ll.getFirst(), byteFileName);
    //                 }
    //             }
    //             ShareFile.mergeFile(data.getFileName(), tempFiles);
    //             System.out.println("Client ok");
    //         } catch (IOException | InterruptedException e1) {
    //             // TODO Auto-generated catch block
    //             e1.printStackTrace();
    //         }
    //     }

    // }

    // public void createClient(List<ShareFile> ShareFiles, String SERVER, int PORT) {
    //     for (ShareFile shareFile : ShareFiles) {
    //         new ClientRequest(SERVER, PORT, shareFile).start();
    //     }
    // }

}
