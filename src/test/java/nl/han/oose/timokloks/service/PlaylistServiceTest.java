package nl.han.oose.timokloks.service;


import nl.han.oose.timokloks.persistence.PlaylistDAO;
import nl.han.oose.timokloks.persistence.TracksInPlaylistDAO;
import nl.han.oose.timokloks.util.PlaylistLengthCalculator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PlaylistServiceTest {

    @Mock
    private PlaylistDAO playlistDAO;

    @Mock
    private PlaylistLengthCalculator playlistLengthCalculator;

    @Mock
    private TracksInPlaylistDAO tracksInPlaylistDAO;

    @InjectMocks
    private PlaylistService sut;


}