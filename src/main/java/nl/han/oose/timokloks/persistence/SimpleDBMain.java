package nl.han.oose.timokloks.persistence;

import java.sql.*;

public class SimpleDBMain {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/spotitube?useSSL=false",
                        "spotitubeUser",
                        "8mMN72Iw");
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM account WHERE user=?")
        ) {
            preparedStatement.setString(1, "timo");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.print(resultSet.getString("user") + ": ");
                System.out.println(resultSet.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}