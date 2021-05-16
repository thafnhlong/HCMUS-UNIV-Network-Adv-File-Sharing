package service;

import entity.Communication;

import java.io.IOException;
import java.net.ServerSocket;

public abstract class Listener extends Thread {
    protected int PORT;
    protected int MAX_CLIENTS = 1000;

    protected int curClients;
    protected ServerSocket socket;

    public Listener() throws IOException {
        this.socket = new ServerSocket(PORT);
    }

    private void Listen() throws IOException {
        while (curClients < MAX_CLIENTS) {
            var client = socket.accept();
            var communication = new Communication(client);
            var communicationThread = MakeService(communication);
            communicationThread.start();
        }
    }

    protected ClientService MakeService(Communication communication) {
        System.out.println("Accept a client " + communication.GetIPClient());
        return new ClientService(communication);
    }

    @Override
    public void run() {
        try {
            Listen();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
