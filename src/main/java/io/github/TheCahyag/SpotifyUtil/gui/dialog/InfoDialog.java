package io.github.TheCahyag.SpotifyUtil.gui.dialog;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * File: InfoDialog.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class InfoDialog {

    /**
     * Displays a message with an 'Ok' button
     * @param message
     */
    public InfoDialog(String message){
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setMinWidth(250);

        Label label = new Label();
        label.setText(message);
        label.setStyle("-fx-wrap-text: true");

        Button ok = new Button("Ok");

        ok.setOnAction(e -> window.close());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, ok);
        layout.setAlignment(Pos.CENTER);
        label.setPadding(new Insets(5, 0, 0, 0));

        Scene scene = new Scene(layout, 250, 100);
        window.setScene(scene);
        window.showAndWait();
    }
}
