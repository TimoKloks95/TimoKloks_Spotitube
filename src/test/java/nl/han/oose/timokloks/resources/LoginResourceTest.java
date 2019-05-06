package nl.han.oose.timokloks.resources;

import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.dto.UserDTO;
import nl.han.oose.timokloks.service.AuthenticationServiceImpl;
import nl.han.oose.timokloks.service.SpotitubeLoginException;
import nl.han.oose.timokloks.service.TokenService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class LoginResourceTest {

    @Mock
    private AuthenticationServiceImpl authenticationService;

    @Mock
    private TokenService tokenService;

    @InjectMocks
    private LoginResource sut;

    @Test
    void loginSuccess() {
        when(authenticationService.login("timo", "timopass"))
                .thenReturn(new TokenDTO("1234", "testuser"));

        UserDTO userDTO = new UserDTO("timo", "timopass");

        Response actualResult = sut.login(userDTO);
        assertEquals(Status.OK.getStatusCode(), actualResult.getStatus());

        TokenDTO actualToken = (TokenDTO) actualResult.getEntity();
        assertEquals("testuser", actualToken.getUser());
        assertEquals("1234", actualToken.getToken());
    }


    @Test
    void loginFailure() {
        when(authenticationService.login(anyString(), anyString()))
                .thenThrow(new SpotitubeLoginException("Login failed for user."));

        SpotitubeLoginException spotitubeLoginException = assertThrows(SpotitubeLoginException.class, () -> {
            Response actualResult = sut.login(new UserDTO("uwe", "uwepass"));
        });

        assertEquals("Login failed for user.", spotitubeLoginException.getMessage());
    }
}
