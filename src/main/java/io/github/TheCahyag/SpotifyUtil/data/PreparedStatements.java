package io.github.TheCahyag.SpotifyUtil.data;

import com.wrapper.spotify.enums.ModelObjectType;
import com.wrapper.spotify.model_objects.specification.AlbumSimplified;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * File: PreparedStatements.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PreparedStatements {

    public static void addTrack(Track track){
        if (track.getId() == null){
            // This is a custom song that was uploaded from a computer and doesn't have an id
            // don't input these songs
            return;
        }
        try {
            Connection conn = SQLManager.getConnection();
            PreparedStatement preparedStatement = conn
                    .prepareStatement("INSERT INTO tracks VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

            // id
            preparedStatement.setString(1, track.getId());
            // name
            preparedStatement.setString(2, track.getName());
            // href
            preparedStatement.setString(3, track.getHref());
            // uri
            preparedStatement.setString(4, track.getUri());
            // explicit
            preparedStatement.setBoolean(5, track.getIsExplicit());
            // type
            preparedStatement.setString(6, track.getType().toString());
            // popularity
            preparedStatement.setInt(7, track.getPopularity());
            // duration_ms
            preparedStatement.setInt(8, track.getDurationMs());
            // disc_number
            preparedStatement.setInt(9, track.getDiscNumber());
            // is_playable
            preparedStatement.setBoolean(10, track.getIsPlayable());
            // preview_url
            preparedStatement.setString(11, track.getPreviewUrl());
            // track_number
            preparedStatement.setInt(12, track.getTrackNumber());
            // album_id
            preparedStatement.setString(13, track.getAlbum().getId());

            // Get the list of artists and string together the ids separated by commas
            ArtistSimplified[] artists = track.getArtists();
            String artist_ids = artists[0].getId();
            for (int i = 1; i < artists.length; i++) {
                artist_ids = artist_ids.concat("," + artists[i].getId());
            }

            // artist_ids
            preparedStatement.setString(14, artist_ids);

            preparedStatement.executeUpdate();

            System.out.println("Added song to database: " + track.getName());

        } catch (SQLIntegrityConstraintViolationException e){
            System.err.println("Data entry error: " + e.getMessage());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static List<Track> getAllTracks(){
        List<Track> tracks = new ArrayList<>();
        try {
            Connection conn = SQLManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM tracks LIMIT 100");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){

                String[] artist_ids = rs.getString(14).split(",");
                ArtistSimplified[] artists = new ArtistSimplified[artist_ids.length];
                for (int i = 0; i < artist_ids.length; i++) {
                    artists[i] = new ArtistSimplified.Builder().setId(artist_ids[i]).build();
                }

                Track track = new Track.Builder()
                        .setId(rs.getString(1))
                        .setName(rs.getString(2))
                        .setHref(rs.getString(3))
                        .setUri(rs.getString(4))
                        .setExplicit(rs.getBoolean(5))
                        .setType(ModelObjectType.valueOf(rs.getString(6)))
                        .setPopularity(rs.getInt(7))
                        .setDurationMs(rs.getInt(8))
                        .setDiscNumber(rs.getInt(9))
                        .setIsPlayable(rs.getBoolean(10))
                        .setPreviewUrl(rs.getString(11))
                        .setTrackNumber(rs.getInt(12))
                        .setAlbum(new AlbumSimplified.Builder().setId(rs.getString(13)).build())
                        .setArtists(artists)
                        .build();

                tracks.add(track);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return tracks;
    }

    public static Track getTrack(String id){
        Track track = null;
        try {
            Connection conn = SQLManager.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM tracks where id = ?");
            preparedStatement.setString(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.first()){
                String[] artist_ids = rs.getString(14).split(",");
                ArtistSimplified[] artists = new ArtistSimplified[artist_ids.length];
                for (int i = 0; i < artist_ids.length; i++) {
                    artists[i] = new ArtistSimplified.Builder().setId(artist_ids[i]).build();
                }

                track = new Track.Builder()
                        .setId(rs.getString(1))
                        .setName(rs.getString(2))
                        .setHref(rs.getString(3))
                        .setUri(rs.getString(4))
                        .setExplicit(rs.getBoolean(5))
                        .setType(ModelObjectType.valueOf(rs.getString(6)))
                        .setPopularity(rs.getInt(7))
                        .setDurationMs(rs.getInt(8))
                        .setDiscNumber(rs.getInt(9))
                        .setIsPlayable(rs.getBoolean(10))
                        .setPreviewUrl(rs.getString(11))
                        .setTrackNumber(rs.getInt(12))
                        .setAlbum(new AlbumSimplified.Builder().setId(rs.getString(13)).build())
                        .setArtists(artists)
                        .build();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return track;
    }
}
