## How to Secure ActiveMQ Classic with SSL over default protocol (openwire) ##

### Broker changes ###
- Create certificates, keystores and truststores
  - Create the broker certificate and its enclosing keystore. This command will generate 1 file: broker.ks. This is a keystore file and this keystore contains the broker certificate in its raw format, in other words, not *.crt format or the *.pem format. You can open this file in KeyStore Explorer app to view the details.
      ```
      keytool -genkey -alias broker -keyalg RSA -keystore broker.ks
      ```
      It will ask you a bunch of questions:
      ```
        Enter keystore password:
        Re-enter new password:
        What is your first and last name?
        [Unknown]:  gxpd527568.devlnk.net
        What is the name of your organizational unit?
        [Unknown]:  GXP
        What is the name of your organization?
        [Unknown]:  BAE Systems
        What is the name of your City or Locality?
        [Unknown]:  Durham
        What is the name of your State or Province?
        [Unknown]:  NC
        What is the two-letter country code for this unit?
        [Unknown]:  US
        Is CN=gxpd527568.devlnk.net, OU=GXP, O=BAE Systems, L=Durham, ST=NC, C=US correct?
        [no]:  yes
      ```
      Don't be fooled by the first question (What is your first and last name?). This will be the CN value as seen in a certificate signing request (CSR).
      You can use and IP address for this question as well.
  
  - Export the broker's certificate file from the above broker's keystore file so that the AMQ client can add it to its trust store for SSL verification.
    ```
    keytool -export -alias broker -keystore broker.ks -file broker_cert
    ```
      It generates a new file called "broker_cert" in its raw format.  This file works for Java client, but not for Ben's C++ code. You can open this file in KeyStore Explorer app and export it to .509x format. The exported file can be viewed in a text editor and file extension: *.cer. Its content will look like below:
      ```
        -----BEGIN CERTIFICATE-----
        MIIDfTCCAmWgAwIBAgIEI4nbwDANBgkqhkiG9w0BAQsFADBvMQswCQYDVQQGEwJV
        UzELMAkGA1UECBMCTkMxDzANBgNVBAcTBkR1cmhhbTEUMBIGA1UEChMLQkFFIFN5
        c3RlbXMxDDAKBgNVBAsTA0dYUDEeMBwGA1UEAxMVZ3hwZDUyNzU2OC5kZXZsbmsu
        bmV0MB4XDTI1MDcxMjE4MjQzNloXDTI1MTAxMDE4MjQzNlowbzELMAkGA1UEBhMC
        VVMxCzAJBgNVBAgTAk5DMQ8wDQYDVQQHEwZEdXJoYW0xFDASBgNVBAoTC0JBRSBT
        eXN0ZW1zMQwwCgYDVQQLEwNHWFAxHjAcBgNVBAMTFWd4cGQ1Mjc1NjguZGV2bG5r
        Lm5ldDCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJU+q9tIavuLe3og
        4pTHFxtdtC5HYc/s6maY6NxBVnvZhoqjhkzrfb2TukM3LrYOSCagp8DodDX1/dgF
        WcuYtu1BHS5GTLCfZ2l9nJZw4MrXZDRDOrc7ZkTECVsFmI7AtFx+HbmUnGkBn6fn
        3jeWKDvcwEmBfy+/jBirK0fq8x7kTIHIsuuRs8WrNS851pcu4nnkpglfhwDvRELR
        hvRPOisgE6cWpJ+MLY6quft2Xi9+W7Wtvwxc6l/vJ7DX9Bfi8AUJpVAqVPQ25isa
        gAqVm/5ij8I/Ets5oiM7Z8ihKrDJUVd0rCuH4tQlAbbpUg0w0jrW0BPhPRxqVgF7
        K4Kam4ECAwEAAaMhMB8wHQYDVR0OBBYEFNUoYeutbN8R94y/HPqK/d3p5SbcMA0G
        CSqGSIb3DQEBCwUAA4IBAQAHgDIgUh4LYStSQoTMvTFGOLl8jfFFOnh48MePr9e0
        0tCglvfPyHKRqc/u71nkubVOV/ybHXnyKfKdBuPYj6qHBkNS27y4RM/wCY4oiLO6
        ZWDVWACIbtMuVv2GiTIcFSx8l1vYuQFJNcYXawNtxFsTBq4wpgF8t+tTarmM9HX1
        cbu6N0Wua85d//V4PiJM2VCTOone0MTdNC5mUN3XMQl4ZuQEQnuUFEfTzCFdhgTx
        zIizEsaoFgcb29Bhc4dq8v9hdIUkfIGXyabQ9wRbmIueortzmgxQOARefmveZOm1
        mDBJR/c7pHHKB5UJduFawiGT3KReU2JBIhGjJh7l4QAI
        -----END CERTIFICATE-----

      ```
  - Create the client certificate and its enclosing keystore. This step in only needed if you want to have mutual authentication.
      ```
      keytool -genkey -alias client -keyalg RSA -keystore client.ks
      ```
    Like with broker process, be careful with the first question.
  - Create a truststore for the client, and import the broker’s certificate. This establishes that the client “trusts” the broker
    ```
    keytool -import -alias broker -keystore client.ts -file broker_cert
    ```
    Note the broker certificate file is the exported file earlier.
  
    - Install certificates, keystores and truststores

        By now we have 5 newly generated files and we can put them under AMQ/conf folder:
      - broker.ks (broker keystore which contains broker's certificate)
      - broker_cert and broker.cer (broker certificat in raw format and x509 format)
      - client.ks (client keystore which contains client's certificate although we are not using client certificate yet)
      - client.ts (client truststore which contains broker's certificate)
        We are not doing mutual TLS, so we do not need to export client certificate from the client keystore and then import it to the broker truststore. We haven't created a broker truststore at all.
  - Configure AMQ broker settings
      - Modify activemq.xml file to add broker's keystore to sslContext element under <broker> tag. Since we are not doing mutual authentication, we don't need to specify broker's truststore path or its password.
      ```
      <sslContext>
          <sslContext keyStore="file:${activemq.conf}/broker.ks" keyStorePassword="wfnp1234"/>
      </sslContext>

      ```
      - Modify activemq.xml file to add a new transport connector for SSL over TCP using default openwire protocol
      ```
        <transportConnector name="ssl" uri="ssl://gxpd527568.devlnk.net:61617"/>
      ```
        Note connector name is "ssl", uri is prefixed with "ssl" instead of "tcp"; hostname needs to match the CN of the broker certificate; the port is 61617 insteadof 61616.You can use any available port.
- Restart AMQ broker

### Java client changes ###

- Modify broker url endpoint
    ```
    public static final String BROKER_ENDPOINT_SSL = "ssl://gxpd527568.devlnk.net:61617";
    ```
- Create SSL connection factory with client truststore path and password
    ```
    connectionFactory = new ActiveMQSslConnectionFactory(BROKER_ENDPOINT_SSL);
    connectionFactory.setTrustStore("C:\\TrustedApps\\apache-activemq-5.16.3\\conf\\client.ts");
    connectionFactory.setTrustStorePassword("wfnp1234");
    ```
    You may see error message: java.lang.reflect.InaccessibleObjectException: Unable to make public void sun.security.ssl.SSLSocketImpl.setHost(java.lang.String) accessible: module java.base does not "exports sun.security.ssl" to unnamed module @52aa2946. It's OK. 

### References ###
https://activemq.apache.org/components/classic/documentation/how-do-i-use-ssl
https://issues.apache.org/jira/browse/AMQ-8275

