package andrews.ying.amq.basic.auth;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Consumer {
    public static final String BROKER_ENDPOINT_NO_SSL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "michelle";
    public static final String MICHELLE_USERNAME = "michelle";
    public static final String MICHELLE_PASSWORD = "password";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageConsumer consumer = null;
        try {

            // Create a ConnectionFactory
            connectionFactory = new ActiveMQConnectionFactory(BROKER_ENDPOINT_NO_SSL);

            // Use basic authentication
            connectionFactory.setUserName(MICHELLE_USERNAME);
            connectionFactory.setPassword(MICHELLE_PASSWORD);

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
