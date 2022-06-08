package br.com.socketserver.config;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Configuration
public class SSLContextConfig {
	
	public static final String KEYSTORE = "/keystoreserver";
	public static final String STOREPASS = "123456";
	public static final String CLIENTCERT = "/certclient.cer";
	
	@Bean
	@Qualifier("sslContextServer")
	public SSLContext sslContextConfigJKS() {
		
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(new FileInputStream(getClass().getResource(KEYSTORE).getPath()), null);
						
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, STOREPASS.toCharArray());
			
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ks);

			SSLContext contextoSSL = SSLContext.getInstance("TLS");
			contextoSSL.init(kmf.getKeyManagers(), null, null);		
			
			return contextoSSL;
		} catch (Exception e) {
			log.error(e);
			return null;
		}
	}
	

}
