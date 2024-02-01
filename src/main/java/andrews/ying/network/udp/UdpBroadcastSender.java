package andrews.ying.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpBroadcastSender {
    public static void main(String[] args) throws IOException {
        final String message = "bye";
        InetAddress address = InetAddress.getByName("255.255.255.255");
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 4445);
        socket.send(packet);
        socket.close();
        System.out.println("Just broadcast a message: [" + message + "]");
    }
}
