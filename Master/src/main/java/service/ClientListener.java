package service;

import entity.Communication;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.List;

public class ClientListener extends Listener {

    public ClientListener() throws IOException {
        PORT = 12021;
        socket = new ServerSocket(PORT);
    }

    @Override
    public void run() {
        super.run();
    }
}
