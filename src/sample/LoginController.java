package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();

    @FXML
    private Label isConnected;

    public void initialize(URL location, ResourceBundle resources) {

        if(loginModel.isDbConnected())
        {
            isConnected.setText("Connected!");
        }else{
            isConnected.setText("Not connected..");
            System.exit(1);
        }
    }
}
