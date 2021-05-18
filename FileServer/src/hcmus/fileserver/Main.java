package hcmus.fileserver;

import hcmus.fileserver.entity.FileReader;
import hcmus.fileserver.entity.SObject;
import hcmus.fileserver.service.Manager;

public class Main {

    public static void main(String[] args) {
        System.out.println("This is File Server");
        // new Manager().run();
        
        // for (var q : FileReader.readFileByIndex(11, "README.md")) {
        //     System.out.format("0x%x ", q);
        // }


        System.out.println(SObject.getBytes(new String("README.md")).length);
    }
}
