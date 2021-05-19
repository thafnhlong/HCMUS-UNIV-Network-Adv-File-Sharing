package hcmus.fileserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import hcmus.fileserver.entity.FileReader;
import hcmus.fileserver.entity.Packet;
import hcmus.fileserver.entity.SObject;
import hcmus.fileserver.entity.ShareFile;
import hcmus.fileserver.service.Manager;

public class Main {

    public static void main(String[] args) {
        System.out.println("This is File Server");
        new Manager().run();
    }

    public static void main2(String[] args) {
        System.out.println("This is File Server");
        // new Manager().run();

        // test();

        if (false) {
            sv();
            cl();
        } else {
            if (args.length > 0) {
                sv();
            } else {
                cl();
            }
        }
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

        var ee2 = new LinkedList<ShareFile>() {
            {
                add(ee.get(0));
            }
        };

        new Packet().createClient(ee, "localhost", 12021);
    }
}
