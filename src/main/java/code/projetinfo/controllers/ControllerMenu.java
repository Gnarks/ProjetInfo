package code.projetinfo.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerMenu extends ControllerParent implements Initializable {
    /**Controller of all the main menu's buttons(assigned to MainMenu.fxml)*/
    @FXML
    private ImageView playButtonImage;
    @FXML
    private ImageView exitButtonImage;
    @FXML
    private ImageView optionsButtonImage;
    @FXML
    private ImageView collectionButtonImage;

    @FXML
    protected void onExitEntered(){
        imageChanger(exitButtonImage,"Sprites/Button_Exit_Light.png");
        exitButtonImage.setOnMouseClicked(event -> Platform.exit());
    }
    @FXML
    protected void onExitExited(){
        imageChanger(exitButtonImage,"Sprites/Button_Exit.png");
    }
    @FXML
    protected void onCollectionEntered() {
        onButtonEntered(collectionButtonImage,"Sprites/Button_Collection_Light.png","Collection.fxml");
    }
    @FXML
    protected void onCollectionExited(){
        imageChanger(collectionButtonImage,"Sprites/Button_Collection.png");
    }
    @FXML
    protected void onPlayEntered(){
        onButtonEntered(playButtonImage,"Sprites/Button_Play_Light.png","MenuPlay.fxml");
    }
    @FXML
    protected void onPlayExited(){
        imageChanger(playButtonImage,"Sprites/Button_Play.png");
    }
    @FXML
    protected void onOptionsEntered() {
        imageChanger(optionsButtonImage, "Sprites/Button_Options_Light.png");
        optionsButtonImage.setOnMouseClicked(event ->loadScene("MenuOptions.fxml",event));
    }
    @FXML
    protected void onOptionsExited() {
        imageChanger(optionsButtonImage, "Sprites/Button_Options.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerOptions.setMediaPlayerMenu();
    }
}
