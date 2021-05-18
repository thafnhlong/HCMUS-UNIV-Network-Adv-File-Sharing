package hcmus.fileserver.entity;

import java.io.Serializable;
import java.net.DatagramSocket;
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

    static class PKUDP {
        // public int ssid;
        // public int index;
        // 60 bytes - 4-4 = 52 byte data

        public static List<byte[]> getPacket(int ssid, String fileName, Integer[] listFragment) {
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
                fragmentList.addAll(data);
                Byte[] fragmentBytes = (Byte[]) fragmentList.toArray();
                allBytes.add(SObject.convertByteTobyte(fragmentBytes));
            }
            listFragment = (Integer[]) ret.toArray();
            return allBytes;
        }

    }

    public void createServer() {
        try {
            DatagramSocket utpSocket = new DatagramSocket(12021);
            // DatagramPacket receivedPacket = new DatagramPacket(receivedBuf,
            // receivedBuf.length);

            // utpSocket.receive(receivedPacket);

        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        System.out.println("Args: ");
        for (String string : args) {
            System.out.println(string);
        }

    }
}
