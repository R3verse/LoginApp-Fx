package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    private Stage secondStage;

    @FXML
    public Label isConnected;

    @FXML
    public TextField txtUsername;

    @FXML
    public TextField txtPassword;

    @FXML
    public Button signOutBtn;

    public void initialize(URL location, ResourceBundle resources)
    {

        if(loginModel.isDbConnected())
        {
            try {
                isConnected.setStyle("-fx-font-weight: bold;");
                isConnected.setText("Connected to: "+ InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }else{
            isConnected.setText("Not connected..");
            System.exit(1);
        }
    }

    public void login(ActionEvent event)
    {
        try {

            if(loginModel.isLoggedIn(txtUsername.getText(), txtPassword.getText())){
                isConnected.setText("Logged in as: " + txtUsername.getText());

                ((Node)event.getSource()).getScene().getWindow().hide(); // hiding stage
                secondStage = new Stage();
                FXMLLoader loader = new FXMLLoader();
                Pane root = loader.load(getClass().getResource("User.fxml").openStream());
                UserController userController = (UserController)loader.getController();
                userController.getUser(txtUsername.getText());
                Scene scene = new Scene(root);
                secondStage.setTitle("User panel");
                secondStage.setScene(scene);
                secondStage.show();

            }else{
                isConnected.setText("username/password combination is invalid");
            }
        } catch (SQLException e) {
            isConnected.setText("username/password combination is invalid");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
