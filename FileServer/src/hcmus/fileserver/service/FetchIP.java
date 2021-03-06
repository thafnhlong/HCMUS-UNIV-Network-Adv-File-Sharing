package hcmus.fileserver.service;

import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class FetchIP {
    public static List<String> getAllIp() {
        List<String> ls = new LinkedList<>();
        String ip;
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iface = interfaces.nextElement();
                // filters out 127.0.0.1 and inactive interfaces
                // if (iface.isLoopback() || !iface.isUp())
                if (!iface.isUp())
                    continue;

                Enumeration<InetAddress> addresses = iface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    InetAddress addr = addresses.nextElement();

                    // *EDIT*
                    if (addr instanceof Inet6Address)
                        continue;

                    ip = addr.getHostAddress();
                    ls.add(ip);
                }
            }
        } catch (SocketException e) {
        }
        return ls;
    }
}
