package hcmus.fileserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import hcmus.fileserver.entity.FileReader;
import hcmus.fileserver.entity.Packet;
import hcmus.fileserver.entity.SObject;
import hcmus.fileserver.entity.ShareFile;
import hcmus.fileserver.service.Manager;

public class Main {

    static void test() {
        // System.out.println(FileReader.readFileByIndex(0, "README.md").length);
        // System.out.println(FileReader.readFileByIndex(16, "README.md").length);

        // for (var q : FileReader.readFileByIndex(11, "README.md")) {

        // System.out.println(SObject.getBytes("README.md").length); // 16bytes
        // var e = Packet.PKUDP.getPacket(123, "TEST");

        // Integer[] list1 = (Integer[]) e.get(0);
        // for (Integer integer : list1) {
        // System.out.println(integer);
        // }
        // List<byte[]> list2 = (List<byte[]>) e.get(1);
        // for (byte[] bs : list2) {
        // System.out.println(bs.length);
        // }
    }

    public static void main(String[] args) {
        System.out.println("This is File Server");
        // new Manager().run();

        // test();

        sv();
        cl();

        // if(args.length>0){
        // sv();
        // } else {
        // cl();
        // }

    }

    static void sv() {
        new Thread() {
            public void run() {
                System.out.println("Create Server");
                new Packet().createServer(12021);
            };
        }.start();
    }

    static void cl() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("Create Client");

        var ee = ShareFile.listFilesForFolder("./SharedFile");

        var ee2 = new LinkedList<ShareFile>(){{
            add(ee.get(0));
        }};

        new Packet().createClient(ee2,"127.0.0.1",12021);
    }
}
