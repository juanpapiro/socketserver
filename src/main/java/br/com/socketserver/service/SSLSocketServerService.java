package br.com.socketserver.service;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Service;

@Service
public class SSLSocketServerService {
	
	public void start() {
		
		try(SSLServerSocket sslServerSocket = (SSLServerSocket) sslContextConfigJKS().getServerSocketFactory().createServerSocket(9001)) {
			
			while(true) new SocketServerThread(sslServerSocket.accept()).start();
			
		} catch (IOException e) {
			System.out.println(e);
		}
		
	}
	
	public SSLContext sslContextConfigJKS() {
		
		try {
			KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
			ks.load(new FileInputStream(getClass().getResource("/keystoreserver").getPath()), null);
//			ks.setCertificateEntry("juanpapiroclientcert", generateCertificate());
			ks.setEntry("certclient", new KeyStore.TrustedCertificateEntry(generateCertificate()), null);
			
//			KeyStore truststore = KeyStore.getInstance(KeyStore.getDefaultType());
//			truststore.load(new FileInputStream(getClass().getResource("/servertruststore").getPath()), null);
			
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
			kmf.init(ks, "123456".toCharArray());
			
//			TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
			tmf.init(ks);

			SSLContext contextoSSL = SSLContext.getInstance("TLS");
			contextoSSL.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);		
			
			return contextoSSL;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public Certificate generateCertificate() {
		Certificate ca = null;
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			InputStream caInput = new BufferedInputStream(new FileInputStream(getClass().getResource("/certclient.cer").getPath()));
			try {
			    ca = cf.generateCertificate(caInput);
			    System.out.println("certclient=" + ((X509Certificate) ca).getSubjectDN());
			} finally {
			    caInput.close();
			}
			
		} catch(Exception e) {
		}
		return ca;
	}

}
