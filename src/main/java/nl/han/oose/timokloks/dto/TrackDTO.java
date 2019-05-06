package nl.han.oose.timokloks.dto;

import java.util.Date;

public class TrackDTO {
    private int id, duration, playcount;
    private String title, performer, album, description, publicationDate;
    private boolean offlineAvailable;

    public TrackDTO(int id, int duration, int playcount, String title, String performer, String album, String description,
                    String publicationDate, boolean offlineAvailable) {
        this.id = id;
        this.duration = duration;
        this.playcount = playcount;
        this.title = title;
        this.performer = performer;
        this.album = album;
        this.description = description;
        this.publicationDate = publicationDate;
        this.offlineAvailable = offlineAvailable;
    }

    public TrackDTO() {

    }

    public boolean getOfflineAvailable() {
        return offlineAvailable;
    }

    public void setOfflineAvailable(boolean offlineAvailable) {
        this.offlineAvailable = offlineAvailable;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public int getPlaycount() {
        return playcount;
    }

    public void setPlaycount(int playcount) {
        this.playcount = playcount;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPerformer() {
        return performer;
    }

    public void setPerformer(String performer) {
        this.performer = performer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
