package hcmus.fileserver.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;

public class UdpServerSocket {
    private DatagramSocket serverSocket;
    private static UdpServerSocket sing;

    private UdpServerSocket() {
    }

    public static UdpServerSocket getInstance() {
        if (sing == null) {
            sing = new UdpServerSocket();
        }
        return sing;
    }

    public boolean create(int port){
        try {
            serverSocket = new DatagramSocket(port);
        } catch (SocketException e) {
            return false;
        }
        return true;
    }

    public void disconnect() {
        serverSocket.close();
    }

}
