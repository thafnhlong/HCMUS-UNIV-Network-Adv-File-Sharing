package service;

import entity.Communication;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class MasterServerService {

    private ClientListener clientListener;
    private FileServerListener serverListener;

    public MasterServerService() throws IOException {
        clientListener = new ClientListener();
        clientListener.start();

        serverListener = new FileServerListener();
        serverListener.start();
    }
}
