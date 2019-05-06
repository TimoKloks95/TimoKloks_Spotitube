package nl.han.oose.timokloks.util;

import nl.han.oose.timokloks.dto.PlaylistDTO;
import nl.han.oose.timokloks.dto.TrackDTO;

import java.util.ArrayList;

public class PlaylistLengthCalculator {

    public int calculateLength(ArrayList<PlaylistDTO> playlists) {
        int length = 0;
        for (PlaylistDTO playlist : playlists) {
            ArrayList<TrackDTO> tracks = playlist.getTracks();
            for (TrackDTO track : tracks) {
                length += track.getDuration();
            }
        }
        return length;
    }
}
