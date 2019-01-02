package io.github.TheCahyag.SpotifyUtil.spotify;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.miscellaneous.Device;
import com.wrapper.spotify.model_objects.specification.*;
import com.wrapper.spotify.requests.data.albums.GetAlbumsTracksRequest;
import com.wrapper.spotify.requests.data.player.GetUsersAvailableDevicesRequest;
import com.wrapper.spotify.requests.data.player.StartResumeUsersPlaybackRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetListOfUsersPlaylistsRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.GetPlaylistsTracksRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * File: SpotifyRequest.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SpotifyRequest {

    private static Connector connector = Connector.getConnector();

    private static GetUsersAvailableDevicesRequest getUsersAvailableDevicesRequest(SpotifyApi api){
        return api.getUsersAvailableDevices().build();
    }

    private static StartResumeUsersPlaybackRequest startResumeUsersPlaybackRequest(SpotifyApi api, Track track){
        return api.startResumeUsersPlayback()
                .uris(SpotifyRequestUtil.getJSONFromTrack(track))
                .build();
    }

    private static GetAlbumsTracksRequest getAlbumsTracksRequest(SpotifyApi api, String id, Integer offset){
        return api.getAlbumsTracks(id)
                .limit(10)
                .offset(offset)
                .market(CountryCode.US)
                .build();
    }


    private static GetPlaylistsTracksRequest getPlaylistTracksRequest(SpotifyApi api, String id, Integer offset){
        return api.getPlaylistsTracks(id)
                .limit(50)
                .offset(offset)
                .market(CountryCode.US)
                .build();
    }

    private static GetPlaylistRequest getPlaylistRequest(SpotifyApi api, String id){
        return api.getPlaylist(id)
                .market(CountryCode.US)
                .build();
    }

    private static GetListOfUsersPlaylistsRequest getGetListOfUsersPlaylistsRequest(SpotifyApi api, String userId){
        return api.getListOfUsersPlaylists(userId)
                .limit(50)
                .offset(0)
                .build();
    }

    private static CreatePlaylistRequest createPlaylistRequest(SpotifyApi api, String userId, String playlistName, String description){
        return api.createPlaylist(userId, playlistName)
                .description(description)
                .public_(true)
                .build();
    }

    public static Playlist createPlaylist(String userId, String playlistName, String description){
        try {
            return createPlaylistRequest(connector.getSpotifyApi(), userId, playlistName, description).execute();
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<TrackSimplified> getAlbumTracks(){
        try {
            final Paging<TrackSimplified> trackSimplifiedPaging = getAlbumsTracksRequest(connector.getSpotifyApi(), "3BHe7LbW5yRjyqXNJ3A6mW", 0).execute();
            return Arrays.asList(trackSimplifiedPaging.getItems());
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Track> getPlaylistTracks(String playlistId, Integer offset){
        try {
            final Paging<PlaylistTrack> trackSimplifiedPaging = getPlaylistTracksRequest(connector.getSpotifyApi(), playlistId, offset).execute();
            List<Track> tracks = new ArrayList<>();
            Arrays.asList(trackSimplifiedPaging.getItems()).forEach(playlistTrack -> tracks.add(playlistTrack.getTrack()));
            return tracks;
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Playlist getPlaylist(String id){
        try {
            return getPlaylistRequest(connector.getSpotifyApi(), id).execute();
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<PlaylistSimplified> getUserPlaylists(String userId){
        try {
            return Arrays.asList(getGetListOfUsersPlaylistsRequest(connector.getSpotifyApi(), userId).execute().getItems());
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
        return null;
    }

    public static List<Device> getPlayBackDevices(){
        try {
            return Arrays.asList(getUsersAvailableDevicesRequest(connector.getSpotifyApi()).execute());
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
        return null;
    }

    public static void playTrack(Track track){
        try {
            startResumeUsersPlaybackRequest(connector.getSpotifyApi(), track).execute();
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
    }

    public static User getMe(){
        try {
            return connector.getSpotifyApi().getCurrentUsersProfile().build().execute();
        } catch (IOException | SpotifyWebApiException e){
            e.printStackTrace();
        }
        return null;
    }
}
