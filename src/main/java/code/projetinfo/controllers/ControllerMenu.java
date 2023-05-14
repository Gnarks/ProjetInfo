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
        imageChanger(exitButtonImage,"Sprites/EXITPRESSED.png");
        exitButtonImage.setOnMouseClicked(event -> Platform.exit());
    }
    @FXML
    protected void onExitExited(){
        imageChanger(exitButtonImage,"Sprites/EXIT.png");
    }
    @FXML
    protected void onCollectionEntered() {
        onButtonEntered(collectionButtonImage,"Sprites/ButtonCollectionLight.png","Collection.fxml");
    }
    @FXML
    protected void onCollectionExited(){
        imageChanger(collectionButtonImage,"Sprites/ButtonCollection.png");
    }
    @FXML
    protected void onPlayEntered(){
        onButtonEntered(playButtonImage,"Sprites/ButtonPlayLight.png","MenuPlay.fxml");
    }
    @FXML
    protected void onPlayExited(){
        imageChanger(playButtonImage,"Sprites/Buttonplay.png");
    }
    @FXML
    protected void onOptionsEntered() {
        imageChanger(optionsButtonImage, "Sprites/ButtonOptionsLight.png");
        optionsButtonImage.setOnMouseClicked(event ->loadScene("OptionsMenu.fxml",event));
    }
    @FXML
    protected void onOptionsExited() {
        imageChanger(optionsButtonImage, "Sprites/ButtonOptions.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerOptions.setMusic();
    }
}
