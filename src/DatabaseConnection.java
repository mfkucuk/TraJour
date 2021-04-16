import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class DatabaseConnection {
    public Connection databaseLink;
    private ResourceBundle reader = null;

    public Connection getConnection() {
        try {
            reader = ResourceBundle.getBundle("dbconfig.properties");
            databaseLink = DriverManager.getConnection(reader.getString("db.url"), reader.getString("db.username"), reader.getString("db.password"));
        } catch (Exception e) {
            e.printStackTrace();
            e.getCause();
        }

        return databaseLink;
    }
}
