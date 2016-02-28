package sample;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Maxjensendk on 28-02-2016.
 */
public class LoginModel {

    public Connection connection;


    public LoginModel()
    {
        SqlConnection dbConnector = SqlConnection.getInstance();
        try {
            connection = dbConnector.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public boolean isDbConnected()
    {
        try {
            return !connection.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isLoggedIn(String user, String pass) throws SQLException
    {

        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM employee WHERE username = ? and password = ?"; // search for user
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user); // replace this in query
            preparedStatement.setString(2, pass);

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                loggedInAs();
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            return false;

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }


    public void loggedInAs() throws SQLException
    {
        LoginController loginController = new LoginController();
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String user = "";

        String query = "SELECT * FROM employee WHERE username = ?"; // search for user
        try {

            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, user); // replace this in query

            resultSet = preparedStatement.executeQuery();

            if(resultSet.next())
            {
                loginController.txtUsername.setText(user);

            }else{

            }
        } catch (Exception e) {
            e.printStackTrace();

        }finally {
            preparedStatement.close();
            resultSet.close();
        }

    }
}
