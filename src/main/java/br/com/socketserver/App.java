package br.com.socketserver;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.socketserver.service.SSLSocketServerService;
import br.com.socketserver.service.SocketServerService;

@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
//		start();
		startSSL();
	}
	
	public static void start() {
		
		SocketServerService socketServerService = new SocketServerService();
		try {
			socketServerService.socketServerInit();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public static void startSSL() {
		SSLSocketServerService sslSocketServerService = new SSLSocketServerService();
		
		sslSocketServerService.start();
		
	}
}
