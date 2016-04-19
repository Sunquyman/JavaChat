package application;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client implements Runnable {

	Socket s;
	BufferedReader reader;
	PrintWriter writer;
	Scanner sc;
	String nickname;

	ClientGUIController cc;

	public Client() {
		sc = new Scanner(System.in);
	}

	public Client(ClientGUIController inCC) {

		sc = new Scanner(System.in);
		cc = inCC;
	}

	public void makeConnection() throws UnknownHostException, IOException{

		System.out.println("Type in a nickname!");
		nickname = new Scanner(System.in).nextLine();

		System.out.println("Connecting to server...");
		System.out.println("Use default options? (localhost:5000) (Y/N)");
		String in = sc.nextLine();
		if(in.equals("yes") || in.equals("Y") || in.equals("y")){
			s = new Socket("127.0.0.1", 5000);
		} else {
			System.out.println("IP Address?");
			String IP = sc.nextLine().trim();
			System.out.println("Port number?");
			int portNum = Integer.valueOf(sc.nextLine().trim());
			s = new Socket(IP, portNum);
		}

		reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		writer = new PrintWriter(s.getOutputStream());
		writer.println(nickname);
		writer.flush();
		System.out.println("Connected successfully!");
		String userList = reader.readLine();
		System.out.println(userList);
		try {
			String[] tempList = userList.substring(4).split("p!8s@p");
			System.out.println(tempList);
			for(String str : tempList){
				cc.writeToUser(str);
			}
		} catch(Exception e){
			cc.writeToUser(userList.substring(4));
		}


	}

	public void sendMessage(String msg){

		writer.println(msg);
		writer.flush();
	}

	public void checkServerMessages(){

		//For some reason, when this thread starts, JVM just won't let it go, so to let the initilization process go through and display the GUI...
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} //initial sleep of thread
		boolean isFinished = false;
		while(!isFinished){
			try {
				String msg = reader.readLine();
				if(msg.indexOf("%(/U+") == 1){
					msg = msg.substring(5);
					cc.writeToUser(msg);
				} else{
					cc.writeToChat(msg);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		checkServerMessages();
	}

	public String getUsername(){
		return nickname;
	}

}
