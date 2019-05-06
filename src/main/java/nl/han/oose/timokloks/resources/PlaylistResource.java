package nl.han.oose.timokloks.resources;

import nl.han.oose.timokloks.dto.*;
import nl.han.oose.timokloks.service.PlaylistService;
import nl.han.oose.timokloks.service.TokenService;
import nl.han.oose.timokloks.service.TrackService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.inject.Inject;
import java.util.ArrayList;

@Path("/playlists")
public class PlaylistResource {

    private TokenService tokenService;
    private PlaylistService playlistService;
    private TrackService trackService;

    public PlaylistResource() {

    }

    @Inject
    public PlaylistResource(TokenService tokenService, PlaylistService playlistService, TrackService trackService) {
        this.tokenService = tokenService;
        this.playlistService = playlistService;
        this.trackService = trackService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllPlaylists(@QueryParam("token") String tokenStr) {
        TokenDTO token = tokenService.getToken(tokenStr);
        AllPlaylistsDTO allPlaylistsDTO = playlistService.assemblePlaylists(token);
        return Response.ok(allPlaylistsDTO).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addPlaylist(PlaylistDTO toAddPlaylist, @QueryParam("token") String tokenStr) {
        TokenDTO token = tokenService.getToken(tokenStr);
        playlistService.addPlaylist(toAddPlaylist, token.getUser());
        AllPlaylistsDTO allPlaylistsDTO = playlistService.assemblePlaylists(token);
        return Response.ok(allPlaylistsDTO).build();
    }

    @Path("/{id}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPlaylistName(@PathParam("id") int toEditId, PlaylistDTO editedPlaylist, @QueryParam("token") String tokenStr) {
        TokenDTO token = tokenService.getToken(tokenStr);
        playlistService.editPlaylistName(toEditId, editedPlaylist.getName());
        AllPlaylistsDTO allPlaylistsDTO = playlistService.assemblePlaylists(token);
        return Response.ok(allPlaylistsDTO).build();
    }


    @Path("/{id}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePlaylist(@PathParam("id") int playlistId, @QueryParam("token") String tokenStr) {
        TokenDTO token = tokenService.getToken(tokenStr);
        playlistService.deletePlaylist(playlistId, token.getUser());
        AllPlaylistsDTO allPlaylistsDTO = playlistService.assemblePlaylists(token);
        return Response.ok(allPlaylistsDTO).build();
    }

    @Path("/{id}/tracks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTracksFromPlaylist(@PathParam("id") int playlistId, @QueryParam("token") String tokenStr) {
        TokenDTO token = tokenService.getToken(tokenStr);
        ArrayList<TrackDTO> tracks = trackService.getTracksInPlaylist(playlistId);
        return Response.ok(tracks).build();
    }

    @Path("/{playlistid}/tracks/{trackid}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTrackFromPlaylist(@PathParam("playlistid") int playlistId, @PathParam("trackid") int trackId, @QueryParam("token") String tokenStr) {
        TokenDTO token = tokenService.getToken(tokenStr);
        playlistService.deleteTrackFromPlaylist(playlistId, trackId, token.getUser());
        ArrayList<TrackDTO> tracks = trackService.getTracksInPlaylist(playlistId);
        return Response.ok(tracks).build();
    }

    @Path("/{id}/tracks")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTrackToPlaylist(@QueryParam("token") String tokenStr, @PathParam("id") int playlistId, TrackDTO toAddTrack) {
        TokenDTO token = tokenService.getToken(tokenStr);
        TrackDTO newTrack = trackService.assembleTrack(toAddTrack);
        playlistService.addTrackToPlaylist(playlistId, newTrack, token.getUser());
        ArrayList<TrackDTO> tracks = trackService.getTracksInPlaylist(playlistId);
        return Response.ok(tracks).build();
    }


}
