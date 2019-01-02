package io.github.TheCahyag.SpotifyUtil.gui.pane;

import com.wrapper.spotify.model_objects.specification.TrackSimplified;
import javafx.scene.text.Text;

import java.util.List;

/**
 * File: TracksSimplifiedPane.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class TracksSimplifiedPane extends AbstractViewsPane<TrackSimplified> {

    public TracksSimplifiedPane(List<TrackSimplified> list) {
        super(list);
    }

    @Override
    public void initData() {
        if (this.collection.size() == 0){
            this.addRow(0, new Text("No tracks"));
            return;
        }

        for (int i = 0; i < this.collection.size(); i++) {
            TrackSimplified track = this.collection.get(i);
            this.add(new Text(track.getName()), 0, i);
            this.add(new Text((track.getDurationMs() / 1000) + ""), 1, i);
            this.add(new Text(track.getType().toString()), 2, i);
        }
    }
}
