package andrews.ying.amq.ssl;

import org.apache.activemq.ActiveMQSslConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Consumer {
    public static final String BROKER_ENDPOINT_SSL = "ssl://gxpd527568.devlnk.net:61617";
    public static final String TOPIC_NAME = "michelle";

    public static void main(String[] args) throws JMSException {
        ActiveMQSslConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {

            // Create a ConnectionFactory
            connectionFactory = new ActiveMQSslConnectionFactory (BROKER_ENDPOINT_SSL);

            // Set the TrustStore details
            connectionFactory.setTrustStore("C:\\TrustedApps\\apache-activemq-5.16.3\\conf\\client.ts");
            connectionFactory.setTrustStorePassword("wfnp1234");

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
                } else {
                    System.out.println("Received: " + message);
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
