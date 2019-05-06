package nl.han.oose.timokloks.exceptionmapper;

import nl.han.oose.timokloks.dto.ErrorDTO;
import nl.han.oose.timokloks.service.SpotitubeAuthorizationException;
import nl.han.oose.timokloks.service.SpotitubeEndpointException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class AuthorizationExceptionMapper implements ExceptionMapper<SpotitubeAuthorizationException> {

    @Override
    public Response toResponse(SpotitubeAuthorizationException e) {
        return Response.status(Response.Status.FORBIDDEN)
                .entity(new ErrorDTO("Requested token was invalid. Try again."))
                .build();
    }
}
