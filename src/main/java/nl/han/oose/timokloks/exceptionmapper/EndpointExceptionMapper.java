package nl.han.oose.timokloks.exceptionmapper;

import nl.han.oose.timokloks.dto.ErrorDTO;
import nl.han.oose.timokloks.persistence.SpotitubePersistenceException;
import nl.han.oose.timokloks.service.SpotitubeEndpointException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class EndpointExceptionMapper implements ExceptionMapper<SpotitubeEndpointException> {

    @Override
    public Response toResponse(SpotitubeEndpointException e) {
        return Response.status(Response.Status.NOT_FOUND)
                .entity(new ErrorDTO("Requested endpoint was not found. Try again."))
                .build();
    }
}
