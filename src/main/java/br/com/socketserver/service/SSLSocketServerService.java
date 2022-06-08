package br.com.socketserver.service;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLServerSocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class SSLSocketServerService {
	
	@Autowired
	@Qualifier("sslContextServer")
	private SSLContext sslContext;
	
	public SSLSocketServerService() {}
	
	public SSLSocketServerService(SSLContext sslContext) {
		this.sslContext = sslContext;
	}
	
	public void start() {
		
		CompletableFuture.runAsync(() -> {
			
			try(SSLServerSocket sslServerSocket = (SSLServerSocket) sslContext.getServerSocketFactory().createServerSocket(9001)) {
				
				sslServerSocket.setEnabledCipherSuites(sslServerSocket.getSupportedCipherSuites());
				sslServerSocket.setEnabledProtocols(sslServerSocket.getEnabledProtocols());
			
//				sslServerSocket.setNeedClientAuth(true);
				
				while(true) new SocketServerThread(sslServerSocket.accept()).start();
				
			} catch (IOException e) {
				log.error(e);
			}
		});
		
	}

}
