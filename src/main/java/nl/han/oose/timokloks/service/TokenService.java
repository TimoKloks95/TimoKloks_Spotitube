package nl.han.oose.timokloks.service;

import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.persistence.TokenDAO;

import javax.inject.Inject;

public class TokenService {

    private TokenDAO tokenDAO;
    private TokenDTO tokenDTO;

    public TokenService() {

    }

    @Inject
    public TokenService(TokenDAO tokenDAO, TokenDTO tokenDTO) {
        this.tokenDAO = tokenDAO;
        this.tokenDTO = tokenDTO;
    }

    void saveToken(TokenDTO token) {
        if (tokenDAO.checkIfExists(token)) {
            tokenDAO.updateToken(token);
        } else {
            tokenDAO.insertToken(token);
        }
    }

    public TokenDTO getToken(String tokenStr) {
        String username = tokenDAO.getUsername(tokenStr);
        if (username != null) {
            tokenDTO.setToken(tokenStr);
            tokenDTO.setUser(username);
            return tokenDTO;
        } else {
            throw new SpotitubeLoginException("Given token: " + tokenStr + " is invalid or has expired.");
        }
    }

}
