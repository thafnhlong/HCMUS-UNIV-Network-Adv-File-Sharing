package hcmus.fileserver.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;

public class TcpSocket {
    private Socket clientSocket;
    private OutputStream os;
    private static TcpSocket sing;

    private TcpSocket() {
    }

    public static TcpSocket getInstance() {
        if (sing == null) {
            sing = new TcpSocket();
        }
        return sing;
    }

    public boolean connect(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            os = clientSocket.getOutputStream();
        } catch (IOException e) {
            disconnect();
            return false;
        }
        return true;
    }

    public boolean send(StringBuilder msg) {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        try {
            bw.append(msg);
            bw.flush();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void disconnect() {
        try {
            if (os != null)
                os.close();
            if (clientSocket != null)
                clientSocket.close();
        } catch (IOException e) {
        }
    }

}
