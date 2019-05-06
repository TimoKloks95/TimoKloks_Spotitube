package nl.han.oose.timokloks.dto;

import java.util.ArrayList;

public class PlaylistDTO {
    private int id;
    private String name;
    private boolean owner;
    private ArrayList<TrackDTO> tracks;

    private String ownerName;

    public PlaylistDTO(int id, String name, boolean owner, ArrayList<TrackDTO> tracks) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.tracks = tracks;
    }

    public PlaylistDTO() {
        tracks = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getOwner() {
        return owner;
    }

    public void setOwner(boolean isOwner) {
        owner = isOwner;
    }

    public void addTrack(TrackDTO track) {
        tracks.add(track);
    }

    public void removeTrack(int trackId) {
        for (TrackDTO track : tracks) {
            if (track.getId() == trackId) {
                tracks.remove(track);
            }
        }
    }

    public ArrayList<TrackDTO> getTracks() {
        return tracks;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String name) {
        this.ownerName = name;
    }
}
