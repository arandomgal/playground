package andrews.ying.amq.stompssl;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

public class Producer {
    public static final String BROKER_ENDPOINT_STOMP_SSL = "ssl://gxpd527568.devlnk.net:61612";
    public static final String TOPIC_NAME = "michelle";
    public static int MESSAGE_ID = 0;

    public static void main(String[] args) throws JMSException, KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, KeyManagementException {
        // Configure SSLContext for trusting broker's certificate
        KeyStore trustStore = KeyStore.getInstance("JKS");
        InputStream inputStream = new FileInputStream("C:\\TrustedApps\\apache-activemq-5.16.3\\conf\\client.ts");
        trustStore.load(inputStream, "wfnp1234".toCharArray());

        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(trustStore);

        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(null, trustManagerFactory.getTrustManagers(), null); // Client authentication (optional) can be configured here

        StompJmsConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            // Create a ConnectionFactory
            connectionFactory = new StompJmsConnectionFactory();
            connectionFactory.setBrokerURI(BROKER_ENDPOINT_STOMP_SSL);
            connectionFactory.setSslContext(sslContext);

            // Create a Connection
            connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createTopic(TOPIC_NAME);

            // Create a MessageProducer from the Session to the Topic or Queue
            producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Tell the producer to send the message X number of times
            while (true) {
                // Create a messages
                String text = "MESSAGE " + MESSAGE_ID++;
                TextMessage message = session.createTextMessage(text);

                System.out.println("Sent message: " + text);
                producer.send(message);
                Thread.sleep(3000); // sleep 3 seconds
            }
        }
        catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        } finally {
            // Clean up
            if (producer != null)
                producer.close();
            if (session != null)
                session.close();
            if (connection != null)
                connection.close();
        }

    }
}
