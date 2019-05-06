package nl.han.oose.timokloks.service;

import nl.han.oose.timokloks.dto.AllPlaylistsDTO;
import nl.han.oose.timokloks.dto.PlaylistDTO;
import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.dto.TrackDTO;
import nl.han.oose.timokloks.persistence.PlaylistDAO;
import nl.han.oose.timokloks.persistence.TracksInPlaylistDAO;
import nl.han.oose.timokloks.util.PlaylistLengthCalculator;

import javax.inject.Inject;
import java.util.ArrayList;

public class PlaylistService {
    private PlaylistDAO playlistDAO;
    private AllPlaylistsDTO allPlaylistsDTO;
    private PlaylistLengthCalculator playlistLengthCalculator;
    private TracksInPlaylistDAO tracksInPlaylistDAO;

    public PlaylistService() {

    }

    @Inject
    public PlaylistService(PlaylistDAO playlistDAO, AllPlaylistsDTO allPlaylistsDTO, PlaylistLengthCalculator playlistLengthCalculator,
                           TracksInPlaylistDAO tracksInPlaylistDAO) {
        this.playlistDAO = playlistDAO;
        this.allPlaylistsDTO = allPlaylistsDTO;
        this.playlistLengthCalculator = playlistLengthCalculator;
        this.tracksInPlaylistDAO = tracksInPlaylistDAO;
    }

    private ArrayList<PlaylistDTO> getPlaylists(TokenDTO token) {
        return playlistDAO.getAllPlaylists(token.getUser());
    }

    public AllPlaylistsDTO assemblePlaylists(TokenDTO token) {
        ArrayList<PlaylistDTO> playlists = getPlaylists(token);
        allPlaylistsDTO.setPlaylists(playlists);
        allPlaylistsDTO.setLength(playlistLengthCalculator.calculateLength(playlists));
        return allPlaylistsDTO;
    }

    public void addPlaylist(PlaylistDTO playlistDTO, String username) {
        playlistDAO.addPlaylist(playlistDTO, username);
    }

    public void editPlaylistName(int playlistId, String playlistNewName) {
        if (playlistDAO.existsPlaylist(playlistId)) {
            playlistDAO.editPlaylistName(playlistId, playlistNewName);
        } else {
            throw new SpotitubeEndpointException("Playlist with id: " + playlistId + " was not found.");
        }
    }

    public void deletePlaylist(int playlistId, String username) {
        if (playlistDAO.checkOwnerPlaylist(playlistId, username)) {
            playlistDAO.deletePlaylist(playlistId);
        } else {
            throw new SpotitubeAuthorizationException("User with name: " + username + " is not authorized to delete playlist: "
                    + playlistId);
        }
    }

    public void deleteTrackFromPlaylist(int playlistId, int trackId, String username) {
        if (playlistDAO.checkOwnerPlaylist(playlistId, username)) {
            tracksInPlaylistDAO.deleteTrackFromPlaylist(playlistId, trackId);
        } else {
            throw new SpotitubeAuthorizationException("User with name: " + username + " is not authorized to delete track: " + trackId + " from playlist: "
                    + playlistId);
        }
    }

    public void addTrackToPlaylist(int playlistId, TrackDTO newTrack, String username) {
        if (playlistDAO.checkOwnerPlaylist(playlistId, username)) {
            tracksInPlaylistDAO.addTrackToPlaylist(playlistId, newTrack);
        } else {
            throw new SpotitubeAuthorizationException("User with name: " + username + " is not authorized to add track: " + newTrack.getId() + " to playlist: "
                    + playlistId);
        }
    }
}
