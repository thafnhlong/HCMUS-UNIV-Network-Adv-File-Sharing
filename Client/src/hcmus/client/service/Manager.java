/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcmus.client.service;

import hcmus.client.entity.FileReader;
import hcmus.client.entity.PKUDP;
import hcmus.client.entity.SObject;
import hcmus.client.entity.ShareFile;
import hcmus.client.entity.Timer;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeMap;

/**
 *
 * @author long
 */
public class Manager {

    private String ipMaster;
    private int portMaster;
    private static Manager sing;

    private Manager() {
    }

    public static Manager getInstance() {
        if (sing == null) {
            sing = new Manager();
        }
        return sing;
    }

    public boolean getConnection(String ip, int port) {
        this.ipMaster = ip;
        this.portMaster = port;

        TcpSocket ts = TcpSocket.getInstance();
        boolean ret = ts.connect(ip, port);
        ts.disconnect();
        return ret;
    }

    public String getFileServer() {

        TcpSocket ts = TcpSocket.getInstance();
        if (ts.connect(ipMaster, portMaster)) {
            return ts.receive();
        }
        return null;
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
            try {
                DatagramSocket utpSocket = new DatagramSocket();
                InetAddress address = InetAddress.getByName(server);
                byte[] buff = new byte[PKUDP.BUFFERDATA];
                byte[] byteFileName = data.getFileName().getBytes();

                LinkedList<Long> ll = new LinkedList();
                for (long i = 0; i < data.getFileSize(); i += FileReader.BUFFER) {
                    ll.add(i);
                    DatagramPacket dpSend = new DatagramPacket(buff, PKUDP.BUFFERDATA, address, port);
                    sendPacket(dpSend, utpSocket, i, byteFileName);

                    if ((i / FileReader.BUFFER) % 10 == 0) // 10 * buffer pause
                    {
                        Thread.sleep(100);
                    }
                }

                utpSocket.setSoTimeout(100);

                long now = Timer.timenow();

                String uniqueFile = Timer.getUniqueNumber();

                TreeMap<Long, String> tempFiles = new TreeMap();

                while (true) {
                    try {
                        DatagramPacket dpRead = new DatagramPacket(buff, PKUDP.BUFFERDATA, address, port);
                        utpSocket.receive(dpRead);
                        byte[] recie = dpRead.getData();
                        long index = SObject.getLong(recie, 0);
                        if (ll.contains(index)) {
                            ll.remove(Long.valueOf(index));
                            String fileName = String.valueOf(uniqueFile) + "_" + String.valueOf(index);
                            tempFiles.put(index, fileName);
                            List<Byte> fileByteData = SObject.getSubBytes(recie, 8, dpRead.getLength() - 1);
                            ShareFile.writeFileByBytes(fileName, fileByteData);
                            System.out.println(fileByteData.size());
                        }
                        System.out.println("Receive From Server: -" + index + "-" + data.getFileName());
                    } catch (SocketTimeoutException e) {
                    }

                    if (ll.size() == 0) {
                        break;
                    }
                    if (Timer.timenow() - now > 1) {
                        DatagramPacket dpSend = new DatagramPacket(buff, PKUDP.BUFFERDATA, address, port);
                        sendPacket(dpSend, utpSocket, ll.getFirst(), byteFileName);
                    }
                }
                ShareFile.mergeFile(data.getFileName(), tempFiles);
                System.out.println("Client ok");
            } catch (IOException | InterruptedException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }

    }

    public void createClient(List<ShareFile> ShareFiles, String SERVER, int PORT) {
        for (ShareFile shareFile : ShareFiles) {
            new ClientRequest(SERVER, PORT, shareFile).start();
        }
    }

}
