package io.github.TheCahyag.SpotifyUtil.gui.pane;

import io.github.TheCahyag.SpotifyUtil.SpotifyUtilMain;
import io.github.TheCahyag.SpotifyUtil.gui.dialog.InfoDialog;
import io.github.TheCahyag.SpotifyUtil.spotify.SpotifyRequest;
import io.github.TheCahyag.SpotifyUtil.util.StringUtil;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * File: CreateNewPlaylistPane.java
 *
 * @author Brandon Bires-Navel (brandonnavel@outlook.com)
 */
public class CreateNewPlaylistPane extends BorderPane {

    private Text paneTitle;
    private Button createPlaylistButton, clearFormElementsButton;
    private Label playlistNameLabel, playlistDescriptionLabel;
    private TextField playlistNameTextField, playlistDescriptionTextField;
    private VBox form;
    private HBox actions;

    private final String createPlaylistButtonText = "Create Playlist";
    private final String clearFormElementsButtonText = "Clear";


    public CreateNewPlaylistPane(){
        super();

        // Instantiate form elements

        // Text
        this.paneTitle = new Text("Create Playlist");
        this.paneTitle.setStyle("-fx-font-size: 18pt");

        // Buttons
        this.createPlaylistButton = new Button(this.createPlaylistButtonText);
        this.clearFormElementsButton = new Button(this.clearFormElementsButtonText);
        this.setButtonActions();

        // Labels
        this.playlistNameLabel = new Label("Name: ");
        this.playlistDescriptionLabel = new Label("Description: ");

        // TextFields
        this.playlistNameTextField = new TextField();
        this.playlistDescriptionTextField = new TextField();
        this.playlistNameTextField.setMaxWidth(150);
        this.playlistDescriptionTextField.setMaxWidth(150);

        // VBox
        this.actions = new HBox(5);
        this.actions.getChildren().addAll(this.createPlaylistButton, this.clearFormElementsButton);

        // HBox
        this.form = new VBox(5);
        this.form.getChildren().addAll(
                this.playlistNameLabel,
                this.playlistNameTextField,
                this.playlistDescriptionLabel,
                this.playlistDescriptionTextField,
                this.actions
        );

        this.setCenter(this.form);
        this.setTop(this.paneTitle);
    }

    /**
     * Checks to ensure the form elements are filled in and are valid
     */
    private boolean validateFormElements(){
        String nameText = this.playlistNameTextField.getText();
        String descriptionText = this.playlistDescriptionTextField.getText();
        return StringUtil.exists(nameText) && StringUtil.exists(descriptionText);
    }

    /**
     * Sets button actions when clicked
     */
    private void setButtonActions(){
        this.createPlaylistButton.setOnAction(event -> {
            if (this.validateFormElements()){
                SpotifyRequest.createPlaylist(
                        SpotifyUtilMain.thecahyagId,
                        this.playlistNameTextField.getText(),
                        this.playlistDescriptionTextField.getText()
                );
            } else {
                new InfoDialog("Fill out the forms first.jfkldsajfldjsalkfjdaslkfjdsalfjdsalfjadsljf;sdaljflksajfklasjf;lkasdjflkjaslkfjaslkfjaslfjaslkfsadfasfasfasdfasdfasf");
            }
        });

        this.clearFormElementsButton.setOnAction(event -> {
            this.playlistNameTextField.setText("");
            this.playlistDescriptionTextField.setText("");
        });
    }
}
