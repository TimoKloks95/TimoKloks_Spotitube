package nl.han.oose.timokloks.persistence;

import nl.han.oose.timokloks.dto.PlaylistDTO;
import nl.han.oose.timokloks.util.PlaylistAssembler;

import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;


public class PlaylistDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private PlaylistAssembler playlistAssembler;
    private int test = 0;

    public PlaylistDAO() {

    }

    @Inject
    public PlaylistDAO(PlaylistAssembler playlistAssembler) {
        this.playlistAssembler = playlistAssembler;
    }

    public ArrayList<PlaylistDTO> getAllPlaylists(String username) {
        ArrayList<PlaylistDTO> playlists = new ArrayList<>();
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM playlist")
        ) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                PlaylistDTO playlist = new PlaylistDTO();
                int playlistId = resultSet.getInt("id");

                playlist.setId(playlistId);
                playlist.setName(resultSet.getString("playlist_name"));
                if (username.equals(resultSet.getString("username"))) {
                    playlist.setOwner(true);
                } else {
                    playlist.setOwner(false);
                }
                playlists.add(playlistAssembler.assemblePlaylist(playlist));
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return playlists;
    }

    public boolean existsPlaylist(int id) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM playlist WHERE id=?")
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return false;
    }

    public boolean checkOwnerPlaylist(int playlistId, String username) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM playlist WHERE id=? AND username=?")
        ) {
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setString(2, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return false;
    }

    public void deletePlaylist(int id) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM playlist WHERE id=?")
        ) {
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }

    public void addPlaylist(PlaylistDTO toAddPlaylist, String nameUser) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO playlist VALUES (?, ?)")
        ) {
            preparedStatement.setString(1, nameUser);
            preparedStatement.setString(2, toAddPlaylist.getName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }

    public void editPlaylistName(int playlistId, String newPlaylistName) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "UPDATE playlist SET playlist_name=? WHERE id=?")
        ) {
            preparedStatement.setString(1, newPlaylistName);
            preparedStatement.setInt(2, playlistId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }
}
