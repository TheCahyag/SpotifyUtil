package io.github.TheCahyag.SpotifyUtil.gui.pane;

import javafx.scene.layout.GridPane;

import java.util.List;

/**
 * File: AbstractViewsPane.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public abstract class AbstractViewsPane<T> extends GridPane {
    protected List<T> collection;

    public AbstractViewsPane(List<T> list){
        this.collection = list;
        this.initData();
        this.initStyles();
    }

    private void initStyles(){
        this.setHgap(2);
        this.setVgap(2);
        this.setGridLinesVisible(true);
    }

    public abstract void initData();

}
