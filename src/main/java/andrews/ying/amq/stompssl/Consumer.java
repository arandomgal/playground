package andrews.ying.amq.stompssl;

import org.fusesource.stomp.jms.StompJmsConnectionFactory;
import org.fusesource.stomp.jms.message.StompJmsMessage;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
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

public class Consumer {
    public static final String BROKER_ENDPOINT_STOMP_SSL = "ssl://gxpd527568.devlnk.net:61612";
    public static final String TOPIC_NAME = "michelle";

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
        MessageConsumer consumer = null;

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

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createConsumer(destination);

            while (true) {
                // Wait for a message
                Message message = consumer.receive(1000);

                if (message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    String text = textMessage.getText();
                    System.out.println("Received: " + text);
                } else if (message instanceof StompJmsMessage) {
                    StompJmsMessage stompJmsMessage = (StompJmsMessage) message;
                    String text = stompJmsMessage.getFrame().contentAsString();
                    System.out.println("Received: " + text);
                }

                Thread.sleep(3000); //sleep 3 seconds
            }
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }  finally {
            // Clean up
            if (consumer != null)
                consumer.close();
            if (session != null)
                session.close();
            if (connection != null)
                connection.close();
        }
    }

}
