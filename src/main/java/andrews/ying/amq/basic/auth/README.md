## How to Secure ActiveMQ Classic with Basic Authentication ##

### Secure AMQ with basic authentication (username/password) ###

#### Broker changes ####
- Create a user 'michelle' with 'password' as password
    -  Modify conf/users.properties file: Add a new user entry: michelle=password
    - Modify conf/groups.properties file: Add user 'michelle' to the 'admins' group: admins=admin,michelle
    - Modify conf/activemq.xml file: Add the basic auth plugin inside <broker> tag:
  ```
  <plugins>
      <simpleAuthenticationPlugin>
          <users>
              <authenticationUser username="admin" password="admin" groups="admins"/>
              <authenticationUser username="michelle" password="password" groups="admins"/>
          </users>
      </simpleAuthenticationPlugin>
  </plugins>
  ```
- Restart AMQ

#### Client changes ####
- Modify Java client code to set username and password on ActiveMQConnectionFactory object: 
    - example:
    connectionFactory.setUserName(MICHELLE_USERNAME);
  connectionFactory.setPassword(MICHELLE_PASSWORD);

### Reference: https://activemq.apache.org/components/classic/documentation/security###