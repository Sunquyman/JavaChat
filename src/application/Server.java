package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

	ServerSocket servSocket;
	Scanner sc;

	ArrayList<ClientHandler> servList = new ArrayList<ClientHandler>();

	public Server(){

		sc = new Scanner(System.in);
		System.out.println("Use default options? (5000) (Y/N)");
		String in = sc.nextLine();
		if(in.equals("yes") || in.equals("Y") || in.equals("y")){
			try {
				servSocket = new ServerSocket(5000);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out.println("Port number?");
			int portNum = Integer.valueOf(sc.nextLine().trim());
			try {
				servSocket = new ServerSocket(portNum);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void checkForConnections(){

		boolean isFinished = false;
		while(!isFinished){
			try {
				Socket s = servSocket.accept();
				System.out.println("Connection initiated...");
				System.out.println(s.getPort());
				ClientHandler ch = new ClientHandler(s);
				servList.add(ch);
				System.out.println("Chat client added to server!");
				Thread t = new Thread(ch);
				t.start();

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void writeChatMessages(String msg){

		PrintWriter clientWriter;

		for(ClientHandler ch : servList){

			clientWriter = ch.getWriter();
			clientWriter.println(msg);
			clientWriter.flush();

		}
	}








	class ClientHandler implements Runnable {

		Socket s;
		BufferedReader reader;
		PrintWriter writer;
		Scanner sc;
		String clientNickname;

		public ClientHandler(Socket inS){

			s = inS;
			try {
				reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
				writer = new PrintWriter(s.getOutputStream());
				System.out.println("Getting nickname...");
				clientNickname = reader.readLine();
				System.out.println(clientNickname + " is now connected at port " + s.getPort());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		public void readMessages(){

			boolean isFinished = false;
			while(!isFinished){
				try {
					String msg = reader.readLine();
					writeChatMessages(clientNickname + ": " + msg);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		public Socket getSocket(){
			return s;
		}

		public PrintWriter getWriter(){
			return writer;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			readMessages();

		}
	}


}


