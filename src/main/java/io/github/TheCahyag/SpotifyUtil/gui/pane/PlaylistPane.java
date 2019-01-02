package io.github.TheCahyag.SpotifyUtil.gui.pane;

import com.wrapper.spotify.model_objects.specification.Playlist;

/**
 * File: PlaylistPane.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PlaylistPane extends AbstractViewPane<Playlist> {

    private Playlist playlist;

    public PlaylistPane(Playlist playlist){
        super(playlist);
        this.playlist = playlist;

    }


}
