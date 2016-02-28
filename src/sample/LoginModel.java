package sample;

import java.sql.Connection;
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

}
