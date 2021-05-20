package hcmus.fileserver.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import hcmus.fileserver.entity.Packet;
import hcmus.fileserver.entity.ShareFile;

public class Manager {

    private static final String SHAREFOLDER = "./SharedFile";

    private List<ShareFile> db;
    private TcpSocket ts;

    public void run() {
        Console cn = Console.getInstace();
        cn.write("Nhap ip Master Server: ");
        String ip = cn.readString();
        cn.write("Nhap port Master Server: ");
        int port = Integer.parseInt(cn.readString());

        connectToMaster(ip, port);

        cn.write("Danh sach IP cua FileServer:\n");
        int i = 1;
        List<String> ips = FetchIP.getAllIp();
        for (String ipsv : ips) {
            cn.write(i++ + ", " + ipsv + "\n");
        }
        cn.write("Ban chon: ");
        int ipsvIndex = Integer.parseInt(cn.readString());
        String ipsv = ips.get(ipsvIndex-1);
        int portsv = 0;

        while (true) {
            cn.write("Nhap port: ");
            portsv = Integer.parseInt(cn.readString());

            if (createServer(portsv)) {
                break;
            }
            cn.write("Khong the su dung port nay\n");
        }

        sendToMaster(ipsv, portsv);

        waitClient(portsv);
    }

    public void connectToMaster(String ip, int port) {
        ts = TcpSocket.getInstance();

        boolean result = ts.connect(ip, port);
        if (!result) {
            Console.getInstace().write("Khong the ket noi toi server");
            System.exit(1);
        }
    }

    public void sendToMaster(String ip, int port) {
        db = ShareFile.listFilesForFolder(SHAREFOLDER);
        StringBuilder sb = new StringBuilder(ip + "`" + port + "`");
        for (ShareFile shareFile : db) {
            sb.append(shareFile.getFileName()).append("\t").append(shareFile.getFileSize()).append(":");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        ts.send(sb.append("\n"));
    }

    public boolean createServer(int port) {
        try {
            DatagramSocket utpSocket = new DatagramSocket(port);
            utpSocket.close();
        } catch (SocketException e) {
            return false;
        }
        return true;
    }

    public void waitClient(int port) {
        new Packet().createServer(port);
    }
}
