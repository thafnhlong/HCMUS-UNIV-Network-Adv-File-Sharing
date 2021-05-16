package service;

import entity.Communication;

import java.io.IOException;

public class ClientService extends Thread {

    protected final Communication communication;

    public ClientService(Communication communication) {
        this.communication = communication;
    }

    public void Accept() throws IOException, InterruptedException {
        communication.send(FileServerService.GetContent());
    }

    @Override
    public void run() {
        try {
            Accept();
            communication.Stop();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
