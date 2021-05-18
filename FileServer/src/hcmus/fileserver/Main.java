package hcmus.fileserver;

import java.util.ArrayList;
import java.util.List;

import hcmus.fileserver.entity.FileReader;
import hcmus.fileserver.entity.Packet;
import hcmus.fileserver.entity.SObject;
import hcmus.fileserver.service.Manager;

public class Main {

    static void test(){
        // for (var q : FileReader.readFileByIndex(11, "README.md")) {

        // System.out.println(SObject.getBytes(new String("TEST")).length);
        // var e = Packet.PKUDP.getPacket(123, "TEST");

        // Integer[] list1 = (Integer[]) e.get(0);
        // for (Integer integer : list1) {
        //     System.out.println(integer);
        // }
        // List<byte[]> list2 = (List<byte[]>) e.get(1);
        // for (byte[] bs : list2) {
        //     System.out.println(bs.length);
        // }
    }
    
    public static void main(String[] args) {
        System.out.println("This is File Server");
        // new Manager().run();

        new Thread(){
            public void run() {
                System.out.println("Create Server");
                new Packet().createServer();
            };
        }.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Create Client");

        new Packet().createClient();

    }
}
