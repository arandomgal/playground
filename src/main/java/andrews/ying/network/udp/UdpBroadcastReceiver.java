package andrews.ying.network.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Scanner;

public class UdpBroadcastReceiver {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        byte[] buffer = new byte[256];
        DatagramSocket socket = new DatagramSocket(4445);
        System.out.println("Start listening for broadcast message on port 4445");
        char continueFlag = 'Y';
        while (continueFlag == 'Y' || continueFlag == 'y' ) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Just received a broadcast message: [" + message + "].");
            if (message.equalsIgnoreCase("bye")) {
                System.out.println("Stop listening since 'bye' is received from the sender.");
                break;
            } else {
                System.out.println("Continue to listen? (Enter 'Y' to continue");
                continueFlag = scanner.next().charAt(0);
            }
        }
        socket.close();
        System.out.println("Stopped listening for broadcast messages.");
    }
}
