package andrews.ying;

public class BreakDownUrl {
    public static void main(String[] args) {
        String brokerUrl = "tcp://localhost:61616";
        int firstColonPlusSlashesPos = brokerUrl.indexOf("://");
        int lastColonPos = brokerUrl.lastIndexOf(":");
        String protocol = brokerUrl.substring(0, firstColonPlusSlashesPos);
        String host = brokerUrl.substring(firstColonPlusSlashesPos + 3, lastColonPos);
        String port = brokerUrl.substring(lastColonPos + 1);
        System.out.println(protocol);
        System.out.println(host);
        System.out.println(port);
    }
}
