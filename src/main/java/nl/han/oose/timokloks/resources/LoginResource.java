package nl.han.oose.timokloks.resources;


import nl.han.oose.timokloks.dto.TokenDTO;
import nl.han.oose.timokloks.dto.UserDTO;
import nl.han.oose.timokloks.service.AuthenticationServiceImpl;


import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    private AuthenticationServiceImpl authenticationService;

    public LoginResource() {
    }

    @Inject
    public LoginResource(AuthenticationServiceImpl authenticationService) {
        this.authenticationService = authenticationService;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(UserDTO user) {
        TokenDTO token = authenticationService.login(user.getUser(), user.getPassword());
        return Response.ok(token).build();
    }

}