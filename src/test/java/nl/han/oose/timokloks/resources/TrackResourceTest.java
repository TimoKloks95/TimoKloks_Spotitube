package nl.han.oose.timokloks.resources;

import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.dto.TrackDTO;
import nl.han.oose.timokloks.service.TokenService;
import nl.han.oose.timokloks.service.TrackService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TrackResourceTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private TrackService trackService;

    @InjectMocks
    private TrackResource sut;

    @Test
    void getAllToAddTracksForPlaylist_succes() {
        TokenDTO expectedToken = new TokenDTO("1234", "testuser");
        when(tokenService.getToken("1234"))
                .thenReturn(expectedToken);

        ArrayList<TrackDTO> tracksNotInPlaylist = new ArrayList<>();
        TrackDTO trackNotInPlaylist = new TrackDTO(1, 120, 0, "testtrack", "testperformer"
                , "testalbum", "testdescription", "01-01-2019", true);
        tracksNotInPlaylist.add(trackNotInPlaylist);

        when(trackService.getTracksNotInPlaylist(1))
                .thenReturn(tracksNotInPlaylist);

        Response actualResult = sut.getAllToAddTracksForPlaylist("1234", 1);
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        ArrayList<TrackDTO> actualTracksNotInPlaylist = (ArrayList<TrackDTO>) actualResult.getEntity();
        assertEquals(1, actualTracksNotInPlaylist.size());
        assertEquals(1, actualTracksNotInPlaylist.get(0).getId());
        assertEquals(120, actualTracksNotInPlaylist.get(0).getDuration());
        assertEquals(0, actualTracksNotInPlaylist.get(0).getPlaycount());
        assertEquals("testtrack", actualTracksNotInPlaylist.get(0).getTitle());
        assertEquals("testperformer", actualTracksNotInPlaylist.get(0).getPerformer());
        assertEquals("testalbum", actualTracksNotInPlaylist.get(0).getAlbum());
        assertEquals("testdescription", actualTracksNotInPlaylist.get(0).getDescription());
        assertEquals("01-01-2019", actualTracksNotInPlaylist.get(0).getPublicationDate());
        assertTrue(actualTracksNotInPlaylist.get(0).getOfflineAvailable());
    }

}