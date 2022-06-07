# SOCKETSETVER

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
LINKS DE REFERÊNCIA
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

https://cic.unb.br/~pedro/trabs/ssl_jsse.html#apendiceA

https://www.baeldung.com/java-ssl-handshake-failures

https://docs.oracle.com/javase/10/security/java-secure-socket-extension-jsse-reference-guide.htm#JSSEC-GUID-3D26386B-BC7A-41BB-AC70-80E6CD147D6F

https://docs.oracle.com/javase/10/security/java-secure-socket-extension-jsse-reference-guide.htm#JSSEC-GUID-0ACD9274-607C-49BE-AED9-BEE2B4F2BEF2

https://docs.oracle.com/javase/10/security/sample-code-illustrating-secure-socket-connection-client-and-server.htm#JSSEC-GUID-B9103D0C-3E6A-4301-B558-461E4CB23DC9

https://docs.oracle.com/javase/10/security/java-secure-socket-extension-jsse-reference-guide.htm#JSSEC-GUID-3D26386B-BC7A-41BB-AC70-80E6CD147D6F

https://www.programcreek.com/java-api-examples/?api=javax.net.ssl.SSLSocket

https://stackoverflow.com/questions/13874387/create-app-with-sslsocket-java

http://www.java2s.com/Tutorial/Java/0490__Security/SSLSimpleServer.htm

http://www.java2s.com/Tutorial/Java/0490__Security/SSLContextandKeymanager.htm

https://www.it-swarm-es.com/es/java/como-deshabilitar-la-verificacion-de-certificados-ssl-con-spring-resttemplate/1045584571/

https://pergunte.org/question/stack/58674628/java-ssl-client-handshake-failure-and-server-no-cipher-suites-in-common

https://www.youtube.com/watch?v=S2Vxr3RoRgI

https://www.ti-enxame.com/pt/java/usando-certificados-de-cliente-servidor-para-soquete-ssl-de-autenticacao-bidirecional-em-android/970747586/

https://www.ti-enxame.com/pt/ssl/como-gerar-o-arquivo-.key-e-.crt-do-arquivo-jks-para-o-servidor-httpd-apache/959877371/

https://stackoverflow.com/questions/42370904/javax-net-ssl-sslhandshakeexception-null-cert-chain-null-cert-chain

https://docs.oracle.com/javase/6/docs/technotes/tools/windows/keytool.html

https://www.ibm.com/docs/pt-br/host-on-demand/12.0?topic=SSS9FA_12.0.0/com.ibm.hod.doc/tutorials/ssh/ssh-pk03.html

https://www.certisign.com.br/duvidas-suporte/certificado-servidor/gerar-csr/keytool

https://codigofonte.org/converter-uma-cadeia-de-certificados-e-uma-chave-em-um-java-keystore-para-ssl-em-puma-java/

https://marceloprogramador.com/2015/07/28/guia-certificados-java/

https://www.certisign.com.br/duvidas-suporte/certificado-servidor/gerar-csr

https://www.certisign.com.br/duvidas-suporte/certificado-servidor/gerar-csr/keytool

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

TUTORIAIS
------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

Tutorial SSLSocket

https://www.youtube.com/watch?v=l4_JIIrMhIQ

https://www.ti-enxame.com/pt/ssl/como-gerar-o-arquivo-.key-e-.crt-do-arquivo-jks-para-o-servidor-httpd-apache/959877371/

------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GERAR CERTIFICADO E KEYSTORE
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

keytool –genkey –alias <keystore_alias> -keyalg <encryption_algorithm> –keystore <path_to_the_keystore_being_created> –keysize <size_of_encryption_key>

keytool -genkey -alias jpstore -keystore jp.store 

keytool -genkey -alias <meualias> -keystore <meustore>

keytool -genkey -keystore kservidor -keyalg rsa -alias servidorsimples

keytool -genkey -keystore kcliente -keyalg rsa -alias simpleclient

keytool -importkeystore -srckeystore jp.store -destkeystore jp.store -deststoretype pkcs12

1 - Crie um novo keystore e certificado autoassinado com as chaves públicas e privadas correspondentes.
keytool -genkeypair -alias juanpapiro -keyalg RSA -validity 7 -keystore juanpapirokeystore

2 - Examine o armazenamento de chaves. Observe que o tipo de entrada é PrivatekeyEntry, o que significa que esta entrada possui uma chave privada associada).
keytool -list -v -keystore juanpapirokeystore

3 - Exporte e examine o certificado autoassinado.
keytool -export -alias juanpapiro -keystore juanpapirokeystore -rfc -file juanpapiro.cer 

4 - Importe o certificado para um novo armazenamento confiável.
keytool -import -alias juanpapirocert -file juanpapiro.cer -keystore juanpapirotruststore


------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
GERAR KEYSTORE E CERTIFICADO AUTOASSINADO COM CHAVES PÚBLICAS E PRIVADAS
-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

1 - Crie um novo keystore e certificado autoassinado com as chaves públicas e privadas correspondentes.

keytool -genkey -keypass 123456 -storepass 123456 -keystore serverkeystore

obs.: para definir algorítmo e validade pode-se adicionar(keyalg RSA -validity 7)  

2 - Examine o armazenamento de chaves. Observe que o tipo de entrada é PrivatekeyEntry, o que significa que esta entrada possui uma chave privada associada).

keytool -list -v -keystore serverkeystore

3 - Exporte e examine o certificado autoassinado.

keytool -export -storepass 123456 -file certclient.cer -keystore clientkeystore

4 - Importe o certificado para um novo armazenamento confiável.

keytool -import -v -trustcacerts -file certserver.cer -keypass 123456 -storepass 123456 -keystore clienttruststore


Quando setNeedClientAuth(true) do SSLServerSocket, é necessário que o cliente tbm envie certificado e o servidor deve contê-lo em seu keystore

keytool -genkey -keypass 123456 -storepass 123456 -keystore clientkeystore

keytool -export -storepass 123456 -file certclient.cer -keystore clientkeystore

keytool -import -v -trustcacerts -file certclient.cer -keypass 123456 -storepass 123456 -keystore servertruststore

keytool -genkey -keypass <senha> -storepass <senha> -keystore <nome_keystore> //gerar com java 8 no caminho do path em variáveis de ambiente
