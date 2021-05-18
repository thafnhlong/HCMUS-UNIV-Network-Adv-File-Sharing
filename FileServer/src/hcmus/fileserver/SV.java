// package hcmus.fileserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;

public class SV {
    public static void main(String argv[]) throws Exception {
        String sentence_from_client;
        String sentence_to_client;

        // Tạo socket server, chờ tại cổng '6543'
        ServerSocket welcomeSocket = new ServerSocket(6543);

        while (true) {
            // chờ yêu cầu từ client
            Socket connectionSocket = welcomeSocket.accept();

            // Tạo input stream, nối tới Socket
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));

            // Tạo outputStream, nối tới socket
            DataOutputStream outToClient = new DataOutputStream(connectionSocket.getOutputStream());

            // Đọc thông tin từ socket
            String inputLine;
            System.out.println("PRINT1");
            
            // sb1.deleteCharAt(sb1.length()-1);
            System.out.println(inFromClient.readLine());
            System.out.println("PRINT2");
            System.out.flush();

            
            return;
        }

    }
}
