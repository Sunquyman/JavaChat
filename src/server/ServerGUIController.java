package server;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

public class ServerGUIController implements Initializable {

	private Server s;

	@FXML
	private TextArea outputArea;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		s = new Server();
		s.checkForConnections();
	}

	public void writeToOutput(String str){
		outputArea.appendText(str + "\n");
	}

}