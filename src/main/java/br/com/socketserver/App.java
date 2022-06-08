package br.com.socketserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.com.socketserver.service.SSLSocketServerService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@SpringBootApplication
public class App {
	
	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}
		
	@Bean
	public CommandLineRunner startSocketServer(SSLSocketServerService sslSocketServerService) {
		return args -> {
			log.info("CommandLineRunner - iniciando socket server...");
			sslSocketServerService.start();
			log.info("CommandLineRunner - socket server iniciado");
		};
	}

	
}
