package andrews.ying.amq.insecure;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class Producer {
    public static final String BROKER_ENDPOINT_NO_SSL = "tcp://localhost:61616";
    public static final String TOPIC_NAME = "michelle";
    public static int MESSAGE_ID = 0;

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory connectionFactory = null;
        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            // Create a ConnectionFactory
            connectionFactory = new ActiveMQConnectionFactory(BROKER_ENDPOINT_NO_SSL);

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
