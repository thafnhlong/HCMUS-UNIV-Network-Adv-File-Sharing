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
}
