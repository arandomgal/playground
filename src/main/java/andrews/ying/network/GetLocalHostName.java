package andrews.ying.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This program is to test how to find the canonical hostname of a machine.
 * Many apps will use 'localhost' as the hostname and will not be understood
 * over the network. I see the following example of using the Java built-in
 * InetAddress class to achieve the purpose.
 *
 * While testing on a machine with Docker Desktop installed, I got an unexpected
 * result:
 *  localhostIP => GXPD527568/10.93.33.63
 *  canonicalHostName => host.docker.internal
 *  hostname => GXPD527568
 *
 * Root cause: Docker Desktop and/or WSL installed on this machine changed the
 * Windows hosts file with the following entries added:
 *
 *   # Added by Docker Desktop
 *   10.93.33.63 host.docker.internal
 *   10.93.33.63 gateway.docker.internal
 *   # To allow the same kube context to work on the host and the container:
 *   127.0.0.1 kubernetes.docker.internal
 *   # End of section
 *
 *  Once I commented out the two entries of 10.93.33.63 (the machine's real IP on the network),
 *  The canonicalHostName => GXPD527568.devlnk.net
 *
 *  When a Java application running inside a Docker container calls
 *  InetAddress.getLocalHost().getCanonicalHostname(), it might return host.docker.internal
 *  instead of the actual hostname. This behavior is due to how Docker Desktop configures
 *  networking, especially on Windows and macOS.
 *  Docker Desktop adds an entry to the host's hosts file that maps host.docker.internal
 *  to the internal IP address used by the host. This is intended to allow containers to
 *  easily access services running on the host machine. However, it can interfere with
 *  the standard hostname resolution process within the container.
 *
 *  The key differences are that getCanonicalHostName() always does a DNS lookup
 *  and aims to return the FQDN, while getHostName() might return the stored
 *  hostname or perform a DNS lookup depending on how the InetAddress was
 *  created and security settings.
 *
 *  References:
 *  https://youtrack.jetbrains.com/issue/IJPL-36689/Docker-Desktop-causes-wrong-host-name-to-be-sent-while-obtaining-floating-license
 *  https://stackoverflow.com/questions/71032544/docker-desktop-installation-on-windows-causes-inetaddress-getlocalhost-getcano
 *
 */
public class GetLocalHostName {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress localhostIP = InetAddress.getLocalHost();
        String canonicalHostName = localhostIP.getCanonicalHostName();
        String hostname = localhostIP.getHostName();
        System.out.println("localhostIP = " + localhostIP);
        System.out.println("canonicalHostName = " + canonicalHostName);
        System.out.println("hostname = " + hostname);
    }
}
