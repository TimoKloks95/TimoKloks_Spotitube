package nl.han.oose.timokloks.persistence;

import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.util.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.inject.Inject;

public class TokenDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private final int DURATION_OF_TOKEN_IN_MINUTES = 45;
    private DateConverter dateConverter;

    public TokenDAO() {

    }

    @Inject
    public TokenDAO(DateConverter dateConverter) {
        this.dateConverter = dateConverter;
    }

    public boolean checkIfExists(TokenDTO token) {
        boolean alreadyExists = false;
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM user_token WHERE username=?")
        ) {
            preparedStatement.setString(1, token.getUser());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                alreadyExists = true;
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return alreadyExists;
    }

    public void updateToken(TokenDTO token) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE user_token SET token=?, expiration_date=? WHERE username=?")
        ) {
            preparedStatement.setString(1, token.getToken());
            preparedStatement.setString(2, dateConverter.getConvertedDateToDatabase(DURATION_OF_TOKEN_IN_MINUTES));
            preparedStatement.setString(3, token.getUser());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }

    public void insertToken(TokenDTO token) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO user_token(token, username, expiration_date) VALUES (?, ?, ?)")
        ) {
            preparedStatement.setString(1, token.getToken());
            preparedStatement.setString(2, token.getUser());
            preparedStatement.setString(3, dateConverter.getConvertedDateToDatabase(DURATION_OF_TOKEN_IN_MINUTES));
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }

    public String getUsername(String token) {
        String username = null;
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT username from user_token where token=? AND expiration_date < NOW()")
        ) {
            preparedStatement.setString(1, token);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                username = resultSet.getString("username");
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return username;
    }
}
