package nl.han.oose.timokloks.service;

import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.dto.UserDTO;
import nl.han.oose.timokloks.persistence.UserDAO;
import nl.han.oose.timokloks.util.TokenGenerator;

import javax.enterprise.inject.Default;
import javax.inject.Inject;

@Default
public class AuthenticationServiceImpl implements AuthenticationService {

    private UserDAO userDAO;
    private TokenService tokenService;
    private TokenGenerator tokenGenerator;

    public AuthenticationServiceImpl() {
    }

    @Inject
    public AuthenticationServiceImpl(UserDAO userDAO, TokenService tokenService, TokenGenerator tokenGenerator) {
        this.userDAO = userDAO;
        this.tokenGenerator = tokenGenerator;
        this.tokenService = tokenService;
    }

    @Override
    public TokenDTO login(String username, String password) {
        UserDTO user = userDAO.getUser(username, password);
        if (user.getName() != null) {
            TokenDTO tokenDTO = new TokenDTO(tokenGenerator.generateToken(), user.getName());
            tokenService.saveToken(tokenDTO);
            return tokenDTO;
        } else {
            throw new SpotitubeLoginException("Login failed for user " + username);
        }
    }
}