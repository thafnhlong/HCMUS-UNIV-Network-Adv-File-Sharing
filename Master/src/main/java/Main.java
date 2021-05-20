import service.FileServerService;
import service.MasterServerService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        new MasterServerService();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Master server running!");
        System.out.println("Listen File Servers at 12022");
        System.out.println("Listen Clients at 12021");
    }
}
