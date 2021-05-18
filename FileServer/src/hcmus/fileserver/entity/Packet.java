package hcmus.fileserver.entity;

import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Packet {

    private Lock lock = new Lock();

    public void test() {
        sendHello();
        try {
            lock.lock();
        } catch (InterruptedException e) {
        }
        lock.unlock();
        sendHello();
        try {
            lock.lock();
        } catch (InterruptedException e) {
        }
        lock.unlock();

        System.out.println("Bat dong bo");
    }

    public void sendHello() {
        try {
            lock.lock();
        } catch (InterruptedException e) {
        }
        new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000 * 5);
                } catch (InterruptedException e) {
                } finally {
                    lock.unlock();
                }
            }
        }.start();
    }

    public static class PKUDP {
        // public int ssid;
        // public int index;
        // 60 bytes - 4-4 = 52 byte data

        public static List getPacket(int ssid, String fileName) {
            List<Byte> data = Arrays.asList(SObject.getBytes(fileName));
            int bysize = data.size();
            List<Byte> bssid = Arrays.asList(SObject.getBytesFromInt(ssid));

            List<byte[]> allBytes = new ArrayList<>();
            List<Integer> ret = new LinkedList<>();

            for (int i = 0; i < bysize; i += FileReader.BUFFER) {
                ret.add(i);

                LinkedList<Byte> fragmentList = new LinkedList<>();
                fragmentList.addAll(bssid);
                fragmentList.addAll(Arrays.asList(SObject.getBytesFromInt(i)));

                for (int j = i * FileReader.BUFFER; j < bysize; j++) {
                    fragmentList.add(data.get(i + j));
                }

                Byte[] fragmentBytes = fragmentList.toArray(new Byte[0]);
                allBytes.add(SObject.convertByteTobyte(fragmentBytes));
            }
            Integer[] listFragment = ret.toArray(new Integer[0]);
            return new ArrayList() {
                {
                    add(listFragment);
                    add(allBytes);
                }
            };
        }

    }

    private static int PORT = 12021;
    private static String SVNAME = "localhost";

    public void createServer() {
        try {
            DatagramSocket utpSocket = new DatagramSocket(PORT);
            byte[] buff = new byte[FileReader.BUFFER];
            DatagramPacket dpRead = new DatagramPacket(buff, FileReader.BUFFER);

            for (int i = 0; i < 2; i++) {
                utpSocket.receive(dpRead);
                String sentence = new String(dpRead.getData());
                System.out.println("Receive From Client: " + sentence);
            }

            dpRead.setData(new String("21").getBytes());
            utpSocket.send(dpRead);
            utpSocket.send(dpRead);
            utpSocket.send(dpRead);

        } catch (Exception e) {
        }
    }

    public void createClient() {
        try {
            DatagramSocket utpSocket = new DatagramSocket();
            InetAddress address = InetAddress.getByName(SVNAME);
            byte[] buff = new byte[FileReader.BUFFER];
            DatagramPacket dpSend = new DatagramPacket(buff, FileReader.BUFFER, address, PORT);

            dpSend.setData(new String("12").getBytes());
            utpSocket.send(dpSend);
            utpSocket.send(dpSend);
            utpSocket.send(dpSend);

            for (int i = 0; i < 2; i++) {
                utpSocket.receive(dpSend);
                String sentence = new String(dpSend.getData());
                System.out.println("Receive From Server: " + sentence);
            }

        } catch (Exception e) {
        }
    }
}
