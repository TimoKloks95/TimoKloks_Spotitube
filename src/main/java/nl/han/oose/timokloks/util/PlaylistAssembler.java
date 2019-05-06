package nl.han.oose.timokloks.util;

import nl.han.oose.timokloks.dto.PlaylistDTO;
import nl.han.oose.timokloks.dto.TrackDTO;
import nl.han.oose.timokloks.persistence.TracksInPlaylistDAO;

import javax.inject.Inject;
import java.util.ArrayList;

public class PlaylistAssembler {

    private TracksInPlaylistDAO tracksInPlaylistDAO;

    public PlaylistAssembler() {

    }

    @Inject
    public PlaylistAssembler(TracksInPlaylistDAO tracksInPlaylistDAO) {
        this.tracksInPlaylistDAO = tracksInPlaylistDAO;
    }

    public PlaylistDTO assemblePlaylist(PlaylistDTO playlistDTO) {
        ArrayList<TrackDTO> tracks = tracksInPlaylistDAO.getTracksInPlaylist(playlistDTO.getId());
        for (TrackDTO track : tracks) {
            playlistDTO.addTrack(track);
        }
        return playlistDTO;
    }

}
