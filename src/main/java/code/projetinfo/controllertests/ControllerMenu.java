package code.projetinfo.controllertests;

import code.projetinfo.AppGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;


public class ControllerMenu extends ControllerParent implements Initializable {
    /**Controller of all the menu's buttons(assigned to MENUSTEST.fxml)*/
    @FXML
    private ImageView PlayButtonImage;
    @FXML
    private ImageView ExitButtonImage;
    @FXML
    private ImageView SettingsButtonImage;
    @FXML
    private ImageView CollectionButtonImage;

    private Media media =new Media(String.valueOf(AppGame.class.getResource("Theme.mp3")));
    private MediaPlayer mediaPlayer = new MediaPlayer(media);

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
        buttonImageChanger(SettingsButtonImage, "Sprites/ButtonSettingsLight.png");
        SettingsButtonImage.setOnMouseClicked(event ->loadScene("SettingsMenu.fxml",event));
    }
    @FXML
    protected void onSettingsExited() {
        buttonImageChanger(SettingsButtonImage, "Sprites/ButtonSettings.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ControllerSettings.setMusic();
    }
}
