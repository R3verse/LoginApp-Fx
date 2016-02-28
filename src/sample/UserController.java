package sample;

/**
 * Created by Maxjensendk on 28-02-2016.
 */

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class UserController implements Initializable{

    private LoginModel loginModel;

    public Connection conn = null;

    @FXML
    private Label usrLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void signOut(ActionEvent event){
        try {
            ((Node)event.getSource()).getScene().getWindow().hide(); // hiding stage
            Stage secondStage = new Stage();
            FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("Login.fxml").openStream());
            Scene scene = new Scene(root);
            secondStage.setTitle("Hello World 2");
            secondStage.setScene(scene);
            secondStage.show();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUser(String user)
    {
        usrLabel.setText("Logged in as: " + user);
    }
}
