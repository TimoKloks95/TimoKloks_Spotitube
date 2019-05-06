package nl.han.oose.timokloks.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

class ConnectionFactory {

    private final static String CONNECTION_URL = "jdbc:mysql://localhost:3306/spotitube?useSSL=false";
    private final static String DB_USER = "spotitubeUser";
    private final static String DB_PASS = "8mMN72Iw";
    private final static String MYSQL_JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";


    static {
        try {
            Class.forName(MYSQL_JDBC_DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    Connection getConnection() {
        try {
            return DriverManager.getConnection(CONNECTION_URL,
                    DB_USER,
                    DB_PASS);
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }

}