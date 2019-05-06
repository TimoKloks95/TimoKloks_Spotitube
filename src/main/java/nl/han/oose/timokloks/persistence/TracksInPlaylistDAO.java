package nl.han.oose.timokloks.persistence;

import nl.han.oose.timokloks.dto.TrackDTO;
import nl.han.oose.timokloks.util.AvailabilityConverter;

import javax.inject.Inject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TracksInPlaylistDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private AvailabilityConverter availabilityConverter;
    private TrackDAO trackDAO;

    public TracksInPlaylistDAO() {

    }

    @Inject
    public TracksInPlaylistDAO(AvailabilityConverter availabilityConverter, TrackDAO trackDAO) {
        this.availabilityConverter = availabilityConverter;
        this.trackDAO = trackDAO;
    }

    public ArrayList<TrackDTO> getTracksInPlaylist(int playlistId) {
        ArrayList<TrackDTO> tracksInPlaylist = new ArrayList<>();
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT track_id FROM track_in_playlist WHERE playlist_id=?")
        ) {
            preparedStatement.setInt(1, playlistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TrackDTO trackInPlaylist = trackDAO.getTrack(resultSet.getInt("track_id"));
                trackInPlaylist.setOfflineAvailable(availabilityConverter.convertToClient(resultSet.getInt("state")));
                tracksInPlaylist.add(trackInPlaylist);
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return tracksInPlaylist;
    }

    public ArrayList<TrackDTO> getTracksNotInPlaylist(int playlistId) {
        ArrayList<TrackDTO> tracksNotInPlaylist = new ArrayList<>();
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT id FROM track WHERE id NOT IN (SELECT track_id FROM track_in_playlist WHERE playlist_id = ?)")
        ) {
            preparedStatement.setInt(1, playlistId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                TrackDTO trackInPlaylist = trackDAO.getTrack(resultSet.getInt("id"));
                trackInPlaylist.setOfflineAvailable(availabilityConverter.convertToClient(resultSet.getInt("state")));
                tracksNotInPlaylist.add(trackInPlaylist);
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return tracksNotInPlaylist;
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "DELETE FROM track_in_playlist WHERE playlist_id=? AND track_id=?")
        ) {
            preparedStatement.setInt(1, playlistId);
            preparedStatement.setInt(2, trackId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }

    public void addTrackToPlaylist(int playlistId, TrackDTO toAddTrack) {
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "INSERT INTO track_in_playlist VALUES (?,?,?)")
        ) {
            int offlineAvailable = availabilityConverter.convertToDatabase(toAddTrack.getOfflineAvailable());
            preparedStatement.setInt(1, offlineAvailable);
            preparedStatement.setInt(2, playlistId);
            preparedStatement.setInt(3, toAddTrack.getId());
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
    }
}
