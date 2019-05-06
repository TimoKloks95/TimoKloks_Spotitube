package nl.han.oose.timokloks.service;

import nl.han.oose.timokloks.dto.TokenDTO;

public interface AuthenticationService {
    TokenDTO login(String username, String password);
}