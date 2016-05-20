package client;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class ClientGUIController implements Initializable {

	Client c;

	@FXML
	private TextArea chatArea;

	@FXML
	private TextArea userArea;

	@FXML
	private Label usernameLabel;

	@FXML
	private TextField messageField;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		c = new Client(this);
		try {
			c.makeConnection();
			usernameLabel.setText(c.getUsername() + ":");
			Thread t = new Thread(c);
			t.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void sendMessage(KeyEvent e){

		if(e.getCode() == KeyCode.ENTER){
			String msg = messageField.getText();
			messageField.setText("");
			c.sendMessage(msg);
		}

	}

	public void writeToChat(String msg){
		chatArea.appendText(msg + "\n");
	}

	public void writeToUser(String msg){
		userArea.appendText(msg + "\n");
	}


}
