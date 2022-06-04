package br.com.socketserver;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.socketserver.config.SSLContextConfig;
import br.com.socketserver.service.SSLSocketServerService;
import br.com.socketserver.service.SocketServerService;

@SpringBootApplication
public class App {
	
//	@Autowired
//	private SSLSocketServerService sslSocketServerService;
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
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
		SSLContextConfig sslContexConfig = new SSLContextConfig();
		SSLSocketServerService sslSocketServerService = new SSLSocketServerService(sslContexConfig.sslContextConfigJKS());
		sslSocketServerService.start();
		
	}


	

	@Bean
	public CommandLineRunner startSocketServer(SSLSocketServerService sslSocketServerService) {
		return args -> {
			sslSocketServerService.start();
		};
	}

	
}
