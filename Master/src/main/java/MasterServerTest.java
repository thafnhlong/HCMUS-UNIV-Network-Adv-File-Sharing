import service.FileServerService;
import service.MasterServerService;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MasterServerTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        var master = new MasterServerService();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Master server running!");
        System.out.println("Listen File Servers at 12022");
        System.out.println("Listen Clients at 12021");

        // FileServer("127.0.0.1`32107`hello.txt");
        TimeUnit.SECONDS.sleep(1);

        System.out.println("Content Values:" + FileServerService.GetContent());
        TimeUnit.SECONDS.sleep(2);
    }

    private static void FileServer(String msg) throws IOException {
        var server = new Socket("127.0.0.1", 12022);
        var writer = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
        writer.write(msg + "\n");
        writer.flush();
    }

    private static void Client() throws IOException {
        var client = new Socket("127.0.0.1", 12021);
        var reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        var content = reader.readLine();
        System.out.println("Received Data: " + content);
    }
}
