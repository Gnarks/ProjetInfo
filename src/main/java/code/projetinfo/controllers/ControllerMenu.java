package code.projetinfo.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerMenu extends ControllerParent implements Initializable {
    /**Controller of all the menu's buttons(assigned to MENUSTEST.fxml)*/
    @FXML
    private ImageView PlayButtonImage;
    @FXML
    private ImageView ExitButtonImage;
    @FXML
    private ImageView OptionsButtonImage;
    @FXML
    private ImageView CollectionButtonImage;

    @FXML
    protected void onExitEntered(){
        imageChanger(ExitButtonImage,"Sprites/EXITPRESSED.png");
        ExitButtonImage.setOnMouseClicked(event -> Platform.exit());
    }
    @FXML
    protected void onExitExited(){
        imageChanger(ExitButtonImage,"Sprites/EXIT.png");
    }
    @FXML
    protected void onCollectionEntered() {
        onButtonEntered(CollectionButtonImage,"Sprites/ButtonCollectionLight.png","Collection.fxml");
    }
    @FXML
    protected void onCollectionExited(){
        imageChanger(CollectionButtonImage,"Sprites/ButtonCollection.png");
    }
    @FXML
    protected void onPlayEntered(){
        onButtonEntered(PlayButtonImage,"Sprites/ButtonPlayLight.png","MenuPlay.fxml");
    }
    @FXML
    protected void onPlayExited(){
        imageChanger(PlayButtonImage,"Sprites/Buttonplay.png");
    }
    @FXML
    protected void onOptionsEntered() {
        imageChanger(OptionsButtonImage, "Sprites/ButtonOptionsLight.png");
        OptionsButtonImage.setOnMouseClicked(event ->loadScene("OptionsMenu.fxml",event));
    }
    @FXML
    protected void onOptionsExited() {
        imageChanger(OptionsButtonImage, "Sprites/ButtonOptions.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerOptions.setMusic();
    }
}
