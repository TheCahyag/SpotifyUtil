package io.github.TheCahyag.SpotifyUtil.gui;

import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import io.github.TheCahyag.SpotifyUtil.SpotifyUtilMain;
import io.github.TheCahyag.SpotifyUtil.data.PreparedStatements;
import io.github.TheCahyag.SpotifyUtil.gui.pane.CreateNewPlaylistPane;
import io.github.TheCahyag.SpotifyUtil.spotify.Connector;
import io.github.TheCahyag.SpotifyUtil.spotify.SpotifyRequest;
import io.github.TheCahyag.SpotifyUtil.gui.pane.TracksPane;
import io.github.TheCahyag.SpotifyUtil.gui.pane.TracksSimplifiedPane;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.github.TheCahyag.SpotifyUtil.util.Resources.*;

/**
 * File: SpotifyUtilGUI.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class SpotifyUtilGUI extends Application {

    private Connector connector;

    private Scene scene;
    private BorderPane mainPane;
    private VBox sideMenu;

    private CreateNewPlaylistPane createNewPlaylistPane;


    @Override
    public void init() throws Exception {
        super.init();

        // Spotify api connection init
        this.connector = Connector.getConnector();

        // Frontend pane init
        this.mainPane = new BorderPane();
        this.sideMenu = new VBox(10);

        this.createNewPlaylistPane = new CreateNewPlaylistPane();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {



        StackPane root = new StackPane();
        root.getChildren().add(mainPane);
        this.scene = new Scene(root, APP_WIDTH, APP_HEIGHT);


        Button btn = new Button("Play Brandy");
        btn.setOnAction(event ->
//                SpotifyRequest.playTrack(PreparedStatements.getTrack("2BY7ALEWdloFHgQZG6VMLA"))
                        System.out.println(SpotifyRequest.getMe())
        );

        VBox vBox = new VBox(10);
        vBox.getChildren().addAll(this.getSideMenuItems());

        this.mainPane.setLeft(new ScrollPane(vBox));
        mainPane.setCenter(makeGridFromTracks(PreparedStatements.getAllTracks()));
        mainPane.setBottom(btn);



        primaryStage.setTitle(APP_TITLE);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private List<Node> getSideMenuItems(){
        List<Node> sideMenuItems = new ArrayList<>();

        Text albums = new Text("Albums");
        albums.setOnMouseClicked(event -> {
            List<TrackSimplified> tracks = SpotifyRequest.getAlbumTracks();
            this.mainPane.setCenter(makeGridFromTracksSimplified(tracks));
        });

        List<PlaylistSimplified> playlists = SpotifyRequest.getUserPlaylists(SpotifyUtilMain.thecahyagId);
        playlists.forEach(playlistSimplified -> {
            Text playlist = new Text(playlistSimplified.getName());
            sideMenuItems.add(GuiUtil.makeClickable(playlist, this.scene, event -> {
                Playlist playlist1 = SpotifyRequest.getPlaylist(playlistSimplified.getId());
                List<Track> trackSimplifieds = new ArrayList<>();
                Arrays.stream(playlist1.getTracks().getItems()).forEach(playlistTrack -> trackSimplifieds.add(playlistTrack.getTrack()));
                this.mainPane.setCenter(makeGridFromTracks(trackSimplifieds));
            }));
        });

        sideMenuItems.add(GuiUtil.makeClickable(new Text("Create Playlist"), this.scene, event -> this.mainPane.setCenter(this.createNewPlaylistPane)));

        sideMenuItems.add(albums);

        return sideMenuItems;
    }

    private Node makeGridFromTracksSimplified(List<TrackSimplified> tracks){
        return new ScrollPane(new TracksSimplifiedPane(tracks));
    }

    private Node makeGridFromTracks(List<Track> tracks){
        return new ScrollPane(new TracksPane(tracks));
    }



    public static void main(String[] args) {
        launch(args);
    }
}
