package io.github.TheCahyag.SpotifyUtil.gui.pane;

import com.wrapper.spotify.model_objects.specification.PlaylistSimplified;
import javafx.scene.text.Text;

import java.util.List;

/**
 * File: PlaylistListPane.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class PlaylistListPane extends AbstractViewsPane<PlaylistSimplified> {

    private List<PlaylistSimplified> playlists;

    public PlaylistListPane(List<PlaylistSimplified> list) {
        super(list);
        this.playlists = list;
    }

    @Override
    public void initData() {
        if (this.collection.size() == 0){
            this.addRow(0, new Text("No playlists"));
            return;
        }

        for (int i = 0; i < this.collection.size(); i++) {
            PlaylistSimplified playlist = this.collection.get(i);
            this.add(new Text(playlist.getName()), 0, i);
        }
    }
}
