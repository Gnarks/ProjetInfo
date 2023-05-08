package code.projetinfo.controllertests;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class ControllerMenu extends ControllerParent{
    /**Controller of all the menu's buttons(assigned to MENUSTEST.fxml)*/
    @FXML
    private ImageView PlayButtonImage;
    @FXML
    private ImageView ExitButtonImage;
    @FXML
    private ImageView SettingsButtonImage;
    @FXML
    private ImageView CollectionButtonImage;


    @FXML
    protected void onExitEntered(){
        buttonImageChanger(ExitButtonImage,"Sprites/EXITPRESSED.png");
        ExitButtonImage.setOnMouseClicked(event -> Platform.exit());
    }
    @FXML
    protected void onExitExited(){
        buttonImageChanger(ExitButtonImage,"Sprites/EXIT.png");
    }
    @FXML
    protected void onCollectionEntered() {
        onButtonEntered(CollectionButtonImage,"Sprites/ButtonCollectionLight.png","Collection.fxml");
    }
    @FXML
    protected void onCollectionExited(){
        buttonImageChanger(CollectionButtonImage,"Sprites/ButtonCollection.png");
    }
    @FXML
    protected void onPlayEntered(){
        onButtonEntered(PlayButtonImage,"Sprites/ButtonPlayLight.png","MenuPlay.fxml");
    }
    @FXML
    protected void onPlayExited(){
        buttonImageChanger(PlayButtonImage,"Sprites/Buttonplay.png");
    }
    @FXML
    protected void onSettingsEntered() {
        buttonImageChanger(SettingsButtonImage,"Sprites/ButtonSettingsLight.png");
    }
    @FXML
    protected void onSettingsExited() {
        buttonImageChanger(SettingsButtonImage, "Sprites/ButtonSettings.png");
    }
}
