package nl.han.oose.timokloks.resources;

import nl.han.oose.timokloks.dto.AllPlaylistsDTO;
import nl.han.oose.timokloks.dto.PlaylistDTO;
import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.dto.TrackDTO;
import nl.han.oose.timokloks.service.PlaylistService;
import nl.han.oose.timokloks.service.TokenService;
import nl.han.oose.timokloks.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaylistResourceTest {

    @Mock
    private PlaylistService playlistService;

    @Mock
    private TokenService tokenService;

    @Mock
    private TrackService trackService;

    @InjectMocks
    private PlaylistResource sut;

    @Test
    void getAllPlaylists_succes() {
        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);


        when(playlistService.assemblePlaylists(expectedToken))
                .thenReturn(new AllPlaylistsDTO());

        Response actualResult = sut.getAllPlaylists("1234");
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        AllPlaylistsDTO actualPlaylists = (AllPlaylistsDTO) actualResult.getEntity();

        assertEquals(0, actualPlaylists.getTotalTrackLength());
        assertEquals(new ArrayList<PlaylistDTO>(), actualPlaylists.getPlaylists());
    }

    @Test
    void addPlaylist_succes() {

        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);

        PlaylistDTO toAddPlaylist = new PlaylistDTO(1, "testplaylist", true, new ArrayList<>());
        ArrayList<PlaylistDTO> allExpectedPlaylists = new ArrayList<>();
        allExpectedPlaylists.add(toAddPlaylist);
        AllPlaylistsDTO expectedPlaylists = new AllPlaylistsDTO();
        expectedPlaylists.setPlaylists(allExpectedPlaylists);

        when(playlistService.assemblePlaylists(expectedToken))
                .thenReturn(expectedPlaylists);

        Response actualResult = sut.addPlaylist(toAddPlaylist, "1234");
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        AllPlaylistsDTO actualPlaylists = (AllPlaylistsDTO) actualResult.getEntity();

        verify(playlistService).addPlaylist(toAddPlaylist, "testuser");
        assertEquals(0, actualPlaylists.getTotalTrackLength());
        assertEquals(allExpectedPlaylists, actualPlaylists.getPlaylists());
        assertEquals(1, allExpectedPlaylists.size());
        assertEquals("testplaylist", actualPlaylists.getPlaylists().get(0).getName());
        assertEquals(1, actualPlaylists.getPlaylists().get(0).getId());
        assertTrue(actualPlaylists.getPlaylists().get(0).getOwner());
        assertEquals(new ArrayList<TrackDTO>(), actualPlaylists.getPlaylists().get(0).getTracks());
    }

    @Test
    void editPlaylistName_succes() {
        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);

        PlaylistDTO toChangePlaylist = new PlaylistDTO(1, "newplaylist", true, new ArrayList<>());
        ArrayList<PlaylistDTO> allExpectedPlaylists = new ArrayList<>();
        allExpectedPlaylists.add(toChangePlaylist);
        AllPlaylistsDTO expectedPlaylists = new AllPlaylistsDTO();
        expectedPlaylists.setPlaylists(allExpectedPlaylists);

        when(playlistService.assemblePlaylists(expectedToken))
                .thenReturn(expectedPlaylists);

        Response actualResult = sut.editPlaylistName(1, toChangePlaylist, "1234");
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        AllPlaylistsDTO actualPlaylists = (AllPlaylistsDTO) actualResult.getEntity();

        verify(playlistService).editPlaylistName(1, "newplaylist");
        assertEquals(0, actualPlaylists.getTotalTrackLength());
        assertEquals(allExpectedPlaylists, actualPlaylists.getPlaylists());
        assertEquals(1, allExpectedPlaylists.size());
        assertEquals("newplaylist", actualPlaylists.getPlaylists().get(0).getName());
        assertEquals(1, actualPlaylists.getPlaylists().get(0).getId());
        assertTrue(actualPlaylists.getPlaylists().get(0).getOwner());
        assertEquals(new ArrayList<TrackDTO>(), actualPlaylists.getPlaylists().get(0).getTracks());
    }

    @Test
    void deletePlayist_succes() {
        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);

        AllPlaylistsDTO expectedPlaylists = new AllPlaylistsDTO();

        when(playlistService.assemblePlaylists(expectedToken))
                .thenReturn(expectedPlaylists);

        Response actualResult = sut.deletePlaylist(1, "1234");
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        AllPlaylistsDTO actualPlaylists = (AllPlaylistsDTO) actualResult.getEntity();

        verify(playlistService).deletePlaylist(1, "testuser");
        assertEquals(0, actualPlaylists.getTotalTrackLength());
        assertEquals(new ArrayList<PlaylistDTO>(), actualPlaylists.getPlaylists());
        assertEquals(0, actualPlaylists.getPlaylists().size());
    }

    @Test
    void getTracksFromPlaylist_succes() {
        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);

        ArrayList<TrackDTO> expectedTracksInPlaylist = new ArrayList<>();
        TrackDTO expectedTrackInPlaylist = new TrackDTO(1, 120, 0, "testtrack", "testperformer"
                , "testalbum", "testdescription", "01-01-2019", true);
        expectedTracksInPlaylist.add(expectedTrackInPlaylist);

        when(trackService.getTracksInPlaylist(1))
                .thenReturn(expectedTracksInPlaylist);

        Response actualResult = sut.getTracksFromPlaylist(1, "1234");
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        ArrayList<TrackDTO> actualTracksInPlaylist = (ArrayList<TrackDTO>) actualResult.getEntity();

        assertEquals(1, actualTracksInPlaylist.size());
        assertEquals(1, actualTracksInPlaylist.get(0).getId());
        assertEquals(120, actualTracksInPlaylist.get(0).getDuration());
        assertEquals(0, actualTracksInPlaylist.get(0).getPlaycount());
        assertEquals("testtrack", actualTracksInPlaylist.get(0).getTitle());
        assertEquals("testperformer", actualTracksInPlaylist.get(0).getPerformer());
        assertEquals("testalbum", actualTracksInPlaylist.get(0).getAlbum());
        assertEquals("testdescription", actualTracksInPlaylist.get(0).getDescription());
        assertEquals("01-01-2019", actualTracksInPlaylist.get(0).getPublicationDate());
        assertTrue(actualTracksInPlaylist.get(0).getOfflineAvailable());
    }

    @Test
    void deleteTrackFromPlaylist_succes() {
        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);

        ArrayList<TrackDTO> expectedTracksInPlaylist = new ArrayList<>();

        when(trackService.getTracksInPlaylist(1))
                .thenReturn(expectedTracksInPlaylist);

        Response actualResult = sut.deleteTrackFromPlaylist(1, 1, "1234");
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        ArrayList<TrackDTO> actualTracksInPlaylist = (ArrayList<TrackDTO>) actualResult.getEntity();

        verify(playlistService).deleteTrackFromPlaylist(1, 1, "testuser");
        assertEquals(0, actualTracksInPlaylist.size());
    }

    @Test
    void addTrackToPlaylist_succes() {
        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);

        ArrayList<TrackDTO> expectedTracksInPlaylist = new ArrayList<>();
        TrackDTO expectedTrackToAdd = new TrackDTO(1, 120, 0, "testtrack", "testperformer"
                , "testalbum", "testdescription", "01-01-2019", true);
        expectedTracksInPlaylist.add(expectedTrackToAdd);

        when(trackService.assembleTrack(expectedTrackToAdd))
                .thenReturn(expectedTrackToAdd);

        when(trackService.getTracksInPlaylist(1))
                .thenReturn(expectedTracksInPlaylist);

        Response actualResult = sut.addTrackToPlaylist("1234", 1, expectedTrackToAdd);
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        ArrayList<TrackDTO> actualTracksInPlaylist = (ArrayList<TrackDTO>) actualResult.getEntity();

        verify(playlistService).addTrackToPlaylist(1, expectedTrackToAdd, "testuser");
        assertEquals(1, actualTracksInPlaylist.size());
        assertEquals(1, actualTracksInPlaylist.get(0).getId());
        assertEquals(120, actualTracksInPlaylist.get(0).getDuration());
        assertEquals(0, actualTracksInPlaylist.get(0).getPlaycount());
        assertEquals("testtrack", actualTracksInPlaylist.get(0).getTitle());
        assertEquals("testperformer", actualTracksInPlaylist.get(0).getPerformer());
        assertEquals("testalbum", actualTracksInPlaylist.get(0).getAlbum());
        assertEquals("testdescription", actualTracksInPlaylist.get(0).getDescription());
        assertEquals("01-01-2019", actualTracksInPlaylist.get(0).getPublicationDate());
        assertTrue(actualTracksInPlaylist.get(0).getOfflineAvailable());
    }
}