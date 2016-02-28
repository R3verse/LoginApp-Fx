package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();

    @FXML
    public Label isConnected;

    @FXML
    public TextField txtUsername;

    @FXML
    public TextField txtPassword;

    public void initialize(URL location, ResourceBundle resources)
    {

        if(loginModel.isDbConnected())
        {
            isConnected.setText("Connected!");
        }else{
            isConnected.setText("Not connected..");
            System.exit(1);
        }


        txtUsername.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (key == KeyCode.ENTER)
            {
                isConnected.setText("Please enter a username..");
            }
        });

        txtPassword.setOnKeyPressed(e -> {
            KeyCode key = e.getCode();
            if (key == KeyCode.ENTER)
            {
                login();
            }
        });
    }

    public void login()
    {
        try {

            if(loginModel.isLoggedIn(txtUsername.getText(), txtPassword.getText())){
                isConnected.setText("Logged in as: " + txtUsername.getText());
            }else{
                isConnected.setText("username/password combination is invalid");
            }
        } catch (SQLException e) {
            isConnected.setText("username/password combination is invalid");
            e.printStackTrace();
        }
    }
}
