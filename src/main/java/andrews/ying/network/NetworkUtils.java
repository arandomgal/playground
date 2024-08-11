package andrews.ying.network;

import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.stream.Collectors;

public class NetworkUtils {
    public static void main(String[] args) throws SocketException {
        List<InetAddress> broadcastAddresses = listAllBroadcastAddresses();
        System.out.println(broadcastAddresses.stream().map(Object::toString).collect(Collectors.joining("\n")));
    }

    /**
     * list all the IP addresses that can broadcast to on the network
     * @return all the IP addresses that can broadcast to on the network.
     * @throws SocketException
     */
    public static List<InetAddress> listAllBroadcastAddresses() throws SocketException {
        List<InetAddress> broadcastIpAddressList = new ArrayList<>();
        Enumeration<NetworkInterface> interfaceEnumeration = NetworkInterface.getNetworkInterfaces();
        while (interfaceEnumeration.hasMoreElements()) {
            NetworkInterface networkInterface = interfaceEnumeration.nextElement();
            System.out.println(networkInterface.getInterfaceAddresses() + "=>" + networkInterface.getDisplayName() + "(hardware address=" + networkInterface.getHardwareAddress() + ")");
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }
            List<InterfaceAddress> inetAddressList = networkInterface.getInterfaceAddresses();
            for (InterfaceAddress interfaceAddress: inetAddressList) {
                InetAddress address = interfaceAddress.getBroadcast();
                if (address != null) {
                    broadcastIpAddressList.add(address);
                }
            }
        }
        return broadcastIpAddressList;
    }
}
