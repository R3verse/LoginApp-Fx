package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by oelsner on 25/11/15.
 */
public class SqlConnection
{
    private String hostname;
    private String username;
    private String password;
    private Connection myConn;

    private static SqlConnection instance;

    // URL string har formatet "jdbc:mysql://borg.network:3306/cleanify2"
    private SqlConnection(String hostname, String username, String password)
    {
        this.hostname = hostname;
        this.username = username;
        this.password = password;
    }

    public static synchronized SqlConnection getInstance()
    {
        if(instance == null) {

            instance = new SqlConnection("jdbc:mysql://127.0.0.1:3306/employeedb?useSSL=true", "root", "");
        }
        return instance;
    }

    public Connection connect() throws SQLException
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        }
        catch(ClassNotFoundException cnfe)
        {
            System.err.println("x Error: "+cnfe.getMessage());
        }
        catch(InstantiationException ie)
        {
            System.err.println("x Error: "+ie.getMessage());
        }
        catch(IllegalAccessException iae)
        {
            System.err.println("x Error: "+iae.getMessage());
        }
        catch (Exception e)
        {
            System.out.println("x Error: " + e.getMessage());
        }

        myConn = DriverManager.getConnection(hostname, username, password);

        return myConn;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if (myConn != null && !myConn.isClosed()) {
            return myConn;
        } else {
            connect();
            return myConn;
        }
    }


}