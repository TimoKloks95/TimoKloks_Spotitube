package nl.han.oose.timokloks.service;

import nl.han.oose.timokloks.dto.TrackDTO;
import nl.han.oose.timokloks.persistence.TrackDAO;
import nl.han.oose.timokloks.persistence.TracksInPlaylistDAO;

import javax.inject.Inject;
import java.util.ArrayList;

public class TrackService {
    private TrackDAO trackDAO;
    private TracksInPlaylistDAO tracksInPlaylistDAO;

    public TrackService() {

    }

    @Inject
    public TrackService(TrackDAO trackDAO, TracksInPlaylistDAO tracksInPlaylistDAO) {
        this.trackDAO = trackDAO;
        this.tracksInPlaylistDAO = tracksInPlaylistDAO;
    }

    public ArrayList<TrackDTO> getTracksNotInPlaylist(int playlistId) {
        return tracksInPlaylistDAO.getTracksNotInPlaylist(playlistId);
    }

    public ArrayList<TrackDTO> getTracksInPlaylist(int playlistId) {
        return tracksInPlaylistDAO.getTracksInPlaylist(playlistId);
    }

    public TrackDTO assembleTrack(TrackDTO toAddTrack) {
        TrackDTO track = trackDAO.getTrack(toAddTrack.getId());
        if (track != null) {
            track.setOfflineAvailable(toAddTrack.getOfflineAvailable());
            return track;
        } else {
            throw new SpotitubeEndpointException("Track with id: " + toAddTrack.getId() + " was not found.");
        }
    }
}
