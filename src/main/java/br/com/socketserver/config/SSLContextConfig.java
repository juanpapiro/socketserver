package br.com.socketserver.config;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SSLContextConfig {
	
	@Bean
	@Qualifier("sslContextServer")
	public SSLContext sslContextConfigJKS() {
		
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(new FileInputStream(getClass().getResource("/keystoreserver").getPath()), null);
			ks.setCertificateEntry("juanpapiroclientcert", generateCertificate());
//			ks.setEntry("certclient", new KeyStore.TrustedCertificateEntry(generateCertificate()), null);
			
//			KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
//			truststore.load(new FileInputStream(getClass().getResource("/servertruststore").getPath()), null);
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, "123456".toCharArray());
			
//			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ks);

			SSLContext contextoSSL = SSLContext.getInstance("TLS");
//			contextoSSL.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);		
			contextoSSL.init(kmf.getKeyManagers(), null, null);		
			
			return contextoSSL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
	public Certificate generateCertificate() {
		Certificate ca = null;
		try (InputStream caInput = new BufferedInputStream(
				new FileInputStream(getClass().getResource("/certclient.cer").getPath()))){
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			ca = cf.generateCertificate(caInput);
			System.out.println("client=" + ((X509Certificate) ca).getSubjectDN());				
		} catch(Exception e) {
			e.printStackTrace();
		}
		return ca;
	}

}
