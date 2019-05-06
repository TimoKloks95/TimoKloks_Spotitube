package nl.han.oose.timokloks.resources;

import nl.han.oose.timokloks.dto.*;
import nl.han.oose.timokloks.service.TokenService;
import nl.han.oose.timokloks.service.TrackService;


import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;
import java.util.ArrayList;

@Path("/tracks")
public class TrackResource {

    private TokenService tokenService;
    private TrackService trackService;

    public TrackResource() {

    }

    @Inject
    public TrackResource(TokenService tokenService, TrackService trackService) {
        this.tokenService = tokenService;
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllToAddTracksForPlaylist(@QueryParam("token") String tokenStr, @QueryParam("forPlaylist") int playlistId) {
        TokenDTO token = tokenService.getToken(tokenStr);
        ArrayList<TrackDTO> tracks = trackService.getTracksNotInPlaylist(playlistId);
        return Response.ok(tracks).build();
    }

}
