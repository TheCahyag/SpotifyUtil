package io.github.TheCahyag.SpotifyUtil.gui.pane;

/**
 * File: AbstractViewPane.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public abstract class AbstractViewPane<T> {

    private T obj;

    public AbstractViewPane(T obj){
        this.obj = obj;
    }
}
