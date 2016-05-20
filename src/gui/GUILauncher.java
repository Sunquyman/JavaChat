package gui;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GUILauncher {

	private Stage primaryStage;
	//In theory, I could make this class more generic, like a utility static, by just making Stage an argument for the rest of the methods, but this design struck me as interesting

	public GUILauncher(Stage inS){
		primaryStage = inS;
	}

	public void showGUI(String FXMLpath, String csspath, String title){

		try {
			Parent root = FXMLLoader.load(getClass().getResource(FXMLpath));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource(csspath).toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle(title);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void showClientGUI(){
		showGUI("../view/ClientGUI.fxml", "../view/application.css", "JavaChat Client");
	}

	public void showServerGUI(){
		showGUI("../view/ServerGUI.fxml", "../view/application.css", "JavaChat Server");
	}

}
