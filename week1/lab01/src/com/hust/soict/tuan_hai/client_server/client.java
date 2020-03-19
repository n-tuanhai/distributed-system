package com.hust.soict.tuan_hai.client_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class client {
	public static void main(String[] args) throws IOException {
		Socket socket = new Socket("127.0.0.1", 9898);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		System.out.println(in.readLine());
		Scanner scanner = new Scanner(System.in);
		
		String clientMsg = new String("");
		while (true) {
			clientMsg = scanner.nextLine();
			if (clientMsg.isEmpty()) break;
			System.out.println("Client input: " + clientMsg);
			out.println(clientMsg);
			System.out.println(in.readLine());
		}
			
		socket.close();
		scanner.close();
	}
}
