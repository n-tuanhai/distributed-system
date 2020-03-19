package com.hust.soict.tuan_hai.client_server;

import java.io.BufferedReader;
import java.util.Random;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import com.hust.soict.tuan_hai.helper.*;
import java.util.Arrays;

public class server {
	private static class Sorter extends Thread {
		private Socket socket;
		private int clientNumber;

		public Sorter(Socket socket, int clientNumber) {
			this.socket = socket;
			this.clientNumber = clientNumber;

			System.out.println("New client #" + clientNumber + " connected at " + socket);
		}

		public void run() {
			try {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				// Send a welcome message to the client.
				out.println("Hello, you are client #" + clientNumber);
				// Get messages from the client, line by line; Each line has several numbers
				// separated by a space character
				while (true) {
					String input = in.readLine();
					if (input == null || input.isEmpty()) {
						break;
					}
					// Put it in a string array
					String[] nums = input.split(" ");
					// Convert this string array to an int array
					int[] intarr = new int[nums.length];
					int i = 0;
					for (String textValue : nums) {
						intarr[i] = Integer.parseInt(textValue);
						i++;
					}
					// Sort the numbers in this int array
				    Random rand = new Random();
				    String str = new String("");
				    int randomNum = rand.nextInt((3 - 0) + 1) + 0;
				    if (randomNum == 0) {
						new SelectionSort().sort(intarr);
						str = "Selection Sort";
					}
				    else if (randomNum == 1) {
						new BubbleSort().sort(intarr);
						str = "Bubble Sort";
				    }
				    else if (randomNum == 2) {
						new ShellSort().sort(intarr);
						str = "Shell Sort";
				    }
				    else if (randomNum == 3) {
						new InsertionSort().sort(intarr);
						str = "Insertion Sort";
				    }
					// Convert the integer array to String
					String strArray[] = Arrays.stream(intarr).mapToObj(String::valueOf).toArray(String[]::new);
					// Send the result to Client
					out.println("Sorted using " + str + ": " + Arrays.toString(strArray));
				}
			} catch (IOException e) {
				System.out.println("Error handling client #" + clientNumber);
			} finally {
				try {
					socket.close();
				} catch (IOException e) {
				}
				System.out.println("Connection with client # " + clientNumber + " closed");
			}
		}
	}

	public static void main(String[] args) throws IOException {
		System.out.println("The Sorter Server is running!");
		int clientNumber = 0;
		try (ServerSocket listener = new ServerSocket(9898)) {
			while (true) {
				new Sorter(listener.accept(), clientNumber++).start();
			}
		}
	}
}
