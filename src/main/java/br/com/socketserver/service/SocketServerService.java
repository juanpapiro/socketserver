package br.com.socketserver.service;

import java.io.IOException;
import java.net.ServerSocket;

import org.springframework.stereotype.Service;

@Service
public class SocketServerService extends Thread {
	
	
	public void socketServerInit() throws IOException {
//		ServerSocket socketServer = SSLServerSocketFactory.getDefault().createServerSocket(9000);
		
		try (ServerSocket serverSocket = new ServerSocket(9000)) {

			while(true) new SocketServerThread(serverSocket.accept()).start();

		} catch(Exception e) {
			System.out.println(e);
		}
	}
	

}
