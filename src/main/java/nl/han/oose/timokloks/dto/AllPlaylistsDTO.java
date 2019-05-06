package nl.han.oose.timokloks.dto;

import java.util.ArrayList;

public class AllPlaylistsDTO {
    private ArrayList<PlaylistDTO> playlists;
    private int length;

    public AllPlaylistsDTO() {
        playlists = new ArrayList<>();
        length = 0;
    }

    public AllPlaylistsDTO(ArrayList<PlaylistDTO> playlists, int length) {
        this.playlists = playlists;
        this.length = length;
    }

    public ArrayList<PlaylistDTO> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(ArrayList<PlaylistDTO> playlists) {
        this.playlists = playlists;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getTotalTrackLength() {
        return length;
    }
}
