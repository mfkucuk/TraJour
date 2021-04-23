package trajour.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection() {
        String dbName;
        String dbUser;
        String dbPassword;
        String dbUrl;

        dbName = "trajour";
        dbUser = "root";
        dbPassword = "";
        dbUrl = "jdbc:mysql://localhost/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
