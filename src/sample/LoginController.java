package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public LoginModel loginModel = new LoginModel();
    private Connection connection;

    @FXML
    private BorderPane createUsersPane;

    private Stage stage2;

    private Scene scene2;

    @FXML
    public TextField username;

    private AnchorPane root;

    @FXML
    public Label isConnected;

    @FXML
    public TextField txtUsername;

    @FXML
    public TextField txtPassword;

    @FXML
    public Button signOutBtn;



    public LoginController()
    {

        try
        {
            SqlConnection dbConnector = SqlConnection.getInstance();
            connection = dbConnector.getConnection();

        }
        catch (ClassNotFoundException eCNF)
        {
            eCNF.printStackTrace();
        }
        catch (SQLException eSQL)
        {
            eSQL.printStackTrace();
        }
    }


    public void initialize(URL location, ResourceBundle resources)
    {

        if(loginModel.isDbConnected())
        {
            try {
                isConnected.setStyle("-fx-font-weight: bold;");
                isConnected.setText("Connected to: "+ InetAddress.getLocalHost().getHostName());

                txtUsername.setOnKeyPressed(e ->{
                    if(e.getCode() == KeyCode.ENTER){
                        login();
                    }
                });


                txtPassword.setOnKeyPressed(e ->{
                    if(e.getCode() == KeyCode.ENTER){
                        login();
                    }

                });

            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }else{
            isConnected.setText("Not connected..");
            System.exit(1);
        }
    }

    public void login()
    {
        try {


            PreparedStatement preparedStatement = null;
            ResultSet resultSet = null;
            String query = "SELECT * FROM employee WHERE username = ? and password = ?"; // search for user
            try {



                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, txtUsername.getText()); // replace this in query
                preparedStatement.setString(2, txtPassword.getText());

                resultSet = preparedStatement.executeQuery();

                if(resultSet.next())
                {

                    isConnected.setText("Logged in as: " + txtUsername.getText());

                    stage2 = new Stage();
                    FXMLLoader loader = new FXMLLoader();

                    ActionEvent event = null;
                    //  ((Node)event.getSource()).getScene().getWindow().hide(); // hiding stage
                    setScene2();
                    stage2.show();

                }else{
                    isConnected.setText("username/password combination is invalid");
                    showAlert("ERROR", "Invalid username/password combination!", "Please enter a valid username/password\nRemember the system is case sensitive.");
                }
            } catch (Exception e) {
                e.printStackTrace();

            }finally {
                preparedStatement.close();
                resultSet.close();
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    public Scene setScene2()
    {

        stage2.setTitle("User panel");




        VBox fields = new VBox(6);
        fields.setPadding(new Insets(150));
        fields.setMaxWidth(Double.MAX_VALUE);

        Label loggedInLabel = new Label();
        loggedInLabel.setText("Logged in as: "+ txtUsername.getText());
        loggedInLabel.setAlignment(Pos.TOP_LEFT);

        Label label1 = new Label("Create new Users");
        label1.setFont(new Font("Sanserif", 20));
        label1.setMaxWidth(Double.MAX_VALUE);

        TextField id = new TextField();
        id.setFont(Font.font("Sanserif", 20));
        id.setPromptText("ID");
        id.setMaxWidth(Double.MAX_VALUE);

        TextField fName = new TextField();
        fName.setFont(Font.font("Sanserif", 20));
        fName.setPromptText("First Name");
        fName.setMaxWidth(Double.MAX_VALUE);

        TextField lName = new TextField();
        lName.setFont(Font.font("Sanserif", 20));
        lName.setPromptText("Last Name");
        lName.setMaxWidth(Double.MAX_VALUE);

        TextField age = new TextField();
        age.setFont(Font.font("Sanserif", 20));
        age.setPromptText("Age");
        age.setMaxWidth(Double.MAX_VALUE);

        Button logoutBtn = new Button("Logout");
        logoutBtn.setFont(Font.font("Sanserif", 16));
        logoutBtn.setOnAction(event ->{
            //TODO: Logout functionality
        });


        username = new TextField();
        username.setFont(Font.font("Sanserif", 20));
        username.setPromptText("Username");
        username.setMaxWidth(Double.MAX_VALUE);

        PasswordField password = new PasswordField();
        password.setFont(Font.font("Sanserif", 20));
        password.setPromptText("Password");
        password.setMaxWidth(Double.MAX_VALUE);

        Button insertUserBtn = new Button("Save");
        insertUserBtn.setFont(Font.font("Sanserif", 15));
        insertUserBtn.setMaxWidth(Double.MAX_VALUE);

        insertUserBtn.setOnAction(e -> {

            insertUsers(id, fName, lName, age, password);
        });


        fields.getChildren().addAll(loggedInLabel, label1, id, fName, lName, age, username, password, insertUserBtn, logoutBtn);


        scene2 = new Scene(fields);
        stage2.setScene(scene2);
        return scene2;
    }

    private void showAlert(String title, String headerText, String contentText)
    {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();

    }

    private void insertUsers(TextField id, TextField fName, TextField lName, TextField age, PasswordField password) {
        int thisId = Integer.parseInt(id.getText());
        String firstname = fName.getText();
        String lastname = lName.getText();
        int thisAge = Integer.parseInt(age.getText());
        String user = username.getText();
        String pass = password.getText();

        try
        {
            Statement st = connection.createStatement();

                String query = "INSERT INTO employee(id, firstname, lastname, age, username, password) " +
                        "VALUES (?,?,?,?,?,?)";

                PreparedStatement pst = connection.prepareStatement(query);

                pst.setInt(1, thisId);
                pst.setString(2, firstname);
                pst.setString(3, lastname);
                pst.setInt(4, thisAge);
                pst.setString(5, user);
                pst.setString(6, pass);

                System.out.println("Inserted user into database: "+ this.username.getText());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Info");
                alert.setHeaderText(null);
                alert.setContentText("User has been inserted into database.");
                alert.showAndWait();
                pst.execute();
                pst.close();

        } catch (SQLException ex)
        {
            ex.printStackTrace();
        }
    }

}
