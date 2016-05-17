package application;

import java.io.IOException;
import java.util.Scanner;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {

		startProgram(primaryStage);
	}

	public static void main(String[] args) {
		launch(args);
	}

	public void startProgram(Stage primaryStage){

		Scanner sc = new Scanner(System.in);

		System.out.println("Welcome to JavaChat(TM/Copyright/NSA/Ibasicallyownallthemessagesyoutype)");
		System.out.println("Server or Client mode? (Type \"1\" for Server, \"2\" for Client)");
		boolean isGoodInput = false;
		while(!isGoodInput){
			String in = sc.nextLine();
			if(in.equals("1")){
				isGoodInput = true;
				Server s = new Server();
				s.checkForConnections();
			}
			if(in.equals("2")){
				isGoodInput = true;
//				Client c = new Client();
//				try {
//					c.makeConnection();
//					c.checkServerMessages();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				showClientGUI(primaryStage);
			}
			if(!(in.equals("1")||(in.equals("2")))){
				System.out.println("Please try again... must be a number between 1 and 2 inclusively.");
			}
		}


	}

	public void showServerGUI(Stage primaryStage){

		try {
			Parent root = FXMLLoader.load(getClass().getResource("ServerGUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("JavaChat Server");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void showClientGUI(Stage primaryStage){

		try {
			Parent root = FXMLLoader.load(getClass().getResource("ClientGUI.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("JavaChat Client");
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
