package io.github.TheCahyag.SpotifyUtil.gui.pane;

import com.wrapper.spotify.model_objects.specification.Track;
import javafx.scene.text.Text;

import java.util.List;

/**
 * File: TracksPane.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class TracksPane extends AbstractViewsPane<Track> {

    public TracksPane(List<Track> tracks) {
        super(tracks);
    }

    @Override
    public void initData() {
        if (this.collection.size() == 0){
            this.addRow(0, new Text("No tracks"));
            return;
        }

        for (int i = 0; i < this.collection.size(); i++) {
            Track track = this.collection.get(i);
            this.addRow(i, new Text(track.getName()), new Text((track.getDurationMs() / 1000) + ""), new Text(track.getType().toString()));
        }
    }
}
