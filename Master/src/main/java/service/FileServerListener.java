package service;

import entity.Communication;

import java.io.IOException;
import java.net.ServerSocket;

public class FileServerListener extends Listener {

    public FileServerListener() throws IOException {
        PORT = 12022;
        socket = new ServerSocket(PORT);
    }

    @Override
    protected ClientService MakeService(Communication communication) {
        System.out.println("Accept a file server " + communication.GetIPClient());
        return new FileServerService(communication);
    }

    @Override
    public void run() {
        super.run();
    }
}
