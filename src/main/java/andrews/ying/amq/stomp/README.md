## How to set AMQ to use stomp protocol ##

### Broker changes ###
  - Configure AMQ broker settings
      - Modify activemq.xml file to add a new transport connector for stomp protocol over tcp
      ```
        <transportConnector name="stomp" uri="stomp://gxpd527568.devlnk.net:61613"/> 
      ```
        Note connector name is "stomp", uri is prefixed with "stomp" instead of "tcp"; hostname needs to match the CN of the broker certificate; the port is 61613 insteadof 61616.You can use any available port.
- Restart AMQ broker

### Java client changes ###
- Add stomp jms client library to POM file
  ```
  <!-- https://mvnrepository.com/artifact/org.fusesource.stompjms/stompjms-client -->
  <dependency>
     <groupId>org.fusesource.stompjms</groupId>
     <artifactId>stompjms-client</artifactId>
     <version>1.19</version>
  </dependency>
  ```
- Modify broker url endpoint
    ```
    public static final String BROKER_ENDPOINT_STOMP = "tcp://gxpd527568.devlnk.net:61613";
    ```
- Create a stomp jsm connection factory
    ```
    connectionFactory = new StompJmsConnectionFactory();
    connectionFactory.setBrokerURI(BROKER_ENDPOINT_STOMP);
    ```
    You may see error message: java.lang.reflect.InaccessibleObjectException: Unable to make public void sun.security.ssl.SSLSocketImpl.setHost(java.lang.String) accessible: module java.base does not "exports sun.security.ssl" to unnamed module @52aa2946. It's OK. 

### References ###
https://activemq.apache.org/components/classic/documentation/stomp

