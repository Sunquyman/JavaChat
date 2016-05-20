package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Server {

	private ServerSocket servSocket;
	private Scanner sc;

	private ArrayList<ClientHandler> servList;

	public Server(){

		servList = new ArrayList<ClientHandler>();
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

	public void writeUsername(String username){

		PrintWriter clientWriter;

		for(ClientHandler ch : servList){

			if(!ch.getNickname().equals(username)){ //If the newly logged in username is different from the one you're thinking of writing it to, write it! Cleaner solution is perhaps make it so duplicates don't happen by tweaking logic behind what is sent/not sent, but meh
				clientWriter = ch.getWriter();
				clientWriter.println("%(/U+" + username);
				clientWriter.flush();
			}
		}
	}

	public String usernamesToString(){

		String str = "%(/L";

		if(servList.size() == 1){
			str = str + servList.get(0).getNickname();

		} else{
			for(int i = 0; i < servList.size() - 1; i++){
				str = str + servList.get(i).getNickname() + "p!8s@p";
			}
			str = str + servList.get(servList.size() - 1).getNickname();
		}

		return str;
	}








	class ClientHandler implements Runnable {

		private Socket s;
		private BufferedReader reader;
		private PrintWriter writer;
		private Scanner sc;
		private String clientNickname;

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
					String timestamp = String.format("[%tT]", Calendar.getInstance());
					writeChatMessages(timestamp + " " + clientNickname + ": " + msg);
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

		public String getNickname(){
			return clientNickname;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			loadNames();
			readMessages();

		}

		public void loadNames() {
			System.out.println(usernamesToString());
			writer.println(usernamesToString());
			writer.flush();
			writeUsername(clientNickname); //write this new ClientHandler, under Server's, nickname to all other Clients
		}
	}


}


