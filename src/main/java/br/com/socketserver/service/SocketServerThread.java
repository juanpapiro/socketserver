package br.com.socketserver.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class SocketServerThread extends Thread {
	
	private Socket socket;
	
	public SocketServerThread() {}
	
	public SocketServerThread(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		
		try {
		
			//RECEBE DADOS DO CLIENTE
			String messageIn = readerWithBr(this.socket.getInputStream());
			
			//RETORNA DADOS PARA O CLIENTE
			PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
			writer.println("Mensagem recebida: ".concat(messageIn != null ? messageIn : ""));			
		
		} catch(IOException e) {
			e.printStackTrace();
		}

	}
	
	public String readerWithBr(InputStream is) {
		try {
			InputStreamReader reader = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(reader);
			return br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public String readerWithBaos(InputStream is) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int length;
			byte[] buffer = new byte[1024];	
			while((length = is.read(buffer)) != -1) {
				baos.write(buffer, 0, length);
			}
			return baos.toString("UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

}
