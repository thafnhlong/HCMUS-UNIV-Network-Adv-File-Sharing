package entity;

import service.MasterServerService;

import java.io.*;
import java.net.Socket;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Communication {

    public final String STOP_MSG = "stop";

    protected Socket socket;
    protected BufferedReader reader;
    protected BufferedWriter writer;

    public Communication(Socket socket) throws IOException {
        this.socket = socket;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void send(String msg) throws IOException {
        writer.write(msg);
        writer.flush();
    }

    public String receive() throws IOException {
        return reader.readLine();
    }

    public String GetIPClient() {
        var octets = new String[4];
        var byteOctets = socket.getInetAddress().getAddress();
        for (var i = 0; i < byteOctets.length; i++) {
            octets[i] = String.valueOf(byteOctets[i]);
        }

        return String.join(".", octets);
    }

    public boolean IsLive() throws IOException, InterruptedException {
        final int TIME_OUT = 2000;
        return socket.getInetAddress().isReachable(TIME_OUT);
    }

    public boolean IsConnected() {
        return socket.isConnected();
    }
    
    public void Stop() throws IOException {
        if (null != reader)
            reader.close();

        if (null != writer)
            writer.close();

        if (null != socket)
            socket.close();
    }
}


