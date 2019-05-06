package nl.han.oose.timokloks.persistence;

import nl.han.oose.timokloks.dto.TrackDTO;
import nl.han.oose.timokloks.util.AvailabilityConverter;
import nl.han.oose.timokloks.util.DateConverter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TrackDAO {

    private final ConnectionFactory connectionFactory = new ConnectionFactory();
    private DateConverter dateConverter;
    private AvailabilityConverter availabilityConverter;

    public TrackDAO() {

    }

    public TrackDAO(DateConverter dateConverter, AvailabilityConverter availabilityConverter) {
        this.dateConverter = dateConverter;
        this.availabilityConverter = availabilityConverter;
    }

    public TrackDTO getTrack(int trackId) {
        TrackDTO foundTrack = null;
        try (
                Connection connection = connectionFactory.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(
                        "SELECT * FROM track WHERE id=?")
        ) {
            preparedStatement.setInt(1, trackId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                foundTrack = new TrackDTO();
                foundTrack.setId(resultSet.getInt("id"));
                foundTrack.setTitle(resultSet.getString("title"));
                foundTrack.setPerformer(resultSet.getString("performer"));
                foundTrack.setDuration(resultSet.getInt("duration"));
                foundTrack.setAlbum(resultSet.getString("album"));
                foundTrack.setPlaycount(resultSet.getInt("playcount"));

                Date publication_date = resultSet.getDate("publication_date");
                foundTrack.setPublicationDate(dateConverter.getConvertedDateToClient(publication_date));

                foundTrack.setDescription(resultSet.getString("description"));
                int offlineAvailable = resultSet.getInt("offline_available");
                foundTrack.setOfflineAvailable(availabilityConverter.convertToClient(offlineAvailable));
            }
        } catch (SQLException e) {
            throw new SpotitubePersistenceException(e);
        }
        return foundTrack;
    }
}
