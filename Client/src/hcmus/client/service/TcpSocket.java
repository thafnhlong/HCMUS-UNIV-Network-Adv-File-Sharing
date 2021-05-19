/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hcmus.client.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;

public class TcpSocket {
    private Socket clientSocket;
    private InputStream is;
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
            is = clientSocket.getInputStream();
        } catch (IOException e) {
            disconnect();
            return false;
        }
        return true;
    }

    public String receive() {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        try {
            return br.readLine();
        } catch (IOException e) {
            return null;
        }
    }

    public void disconnect() {
        try {
            if (is != null)
                is.close();
            if (clientSocket != null)
                clientSocket.close();
        } catch (IOException e) {
        }
    }

}
