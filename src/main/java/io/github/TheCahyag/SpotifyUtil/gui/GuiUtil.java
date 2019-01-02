package io.github.TheCahyag.SpotifyUtil.gui;

import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;

/**
 * File: GuiUtil.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class GuiUtil {

    public static Node makeClickable(Node node, Scene scene, EventHandler<? super MouseEvent> action){
        node.setOnMouseClicked(action);
        node.setOnMouseEntered(event -> scene.setCursor(Cursor.HAND));
        node.setOnMouseExited(event -> scene.setCursor(Cursor.DEFAULT));
        return node;
    }

}
