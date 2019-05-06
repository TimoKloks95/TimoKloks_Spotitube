package nl.han.oose.timokloks.persistence;

import nl.han.oose.timokloks.dto.UserDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();

    public UserDTO getUser(String user, String password) {
        UserDTO foundUser = null;
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM user WHERE user=? AND password=?")
        ) {
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foundUser = new UserDTO();
                foundUser.setName(resultSet.getString("username"));
                foundUser.setUser(user);
                foundUser.setPassword(password);
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return foundUser;
    }


}