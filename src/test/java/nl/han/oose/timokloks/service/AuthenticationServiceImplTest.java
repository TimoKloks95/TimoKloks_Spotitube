package nl.han.oose.timokloks.service;

import static org.junit.jupiter.api.Assertions.*;

import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.dto.UserDTO;
import nl.han.oose.timokloks.persistence.UserDAO;
import nl.han.oose.timokloks.util.TokenGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private UserDAO userDAO;

    @Mock
    private TokenGenerator tokenGenerator;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private AuthenticationServiceImpl sut;

    @Test
    void userExistsInDatabase_soSucces() {
        UserDTO expectedUser = new UserDTO("testuser", "testpass");
        expectedUser.setName("TestUser");
        when(userDAO.getUser("testuser", "testpass"))
                .thenReturn(expectedUser);

        when(tokenGenerator.generateToken())
                .thenReturn("1234");

        TokenDTO actualResult = sut.login("testuser", "testpass");
        verify(tokenService).saveToken(any(TokenDTO.class));
        assertEquals("1234", actualResult.getToken());
        assertEquals("TestUser", actualResult.getUser());
    }

    @Test
    void userDoesNotExistInDatabase_soFails() {

        when(userDAO.getUser(anyString(), anyString()))
                .thenReturn(new UserDTO());

        SpotitubeLoginException spotitubeLoginException = assertThrows(SpotitubeLoginException.class, () -> {
            TokenDTO tokenDTO = sut.login("testuser", "testpass");
        });

        assertEquals("Login failed for user testuser", spotitubeLoginException.getMessage());

    }
}