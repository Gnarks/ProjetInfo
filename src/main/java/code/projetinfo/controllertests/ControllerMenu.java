package code.projetinfo.controllertests;


import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ControllerMenu {
    /**Controller of all the menu's buttons(assigned to MENUSTEST.fxml)*/
    @FXML
    private Button PlayButton;
    @FXML
    private ImageView PlayButtonImage;
    @FXML
    private Button ExitButton;
    @FXML
    private ImageView ExitButtonImage;
    @FXML
    private Button SettingsButton;
    @FXML
    private ImageView SettingsButtonImage;
    @FXML
    private Button CollectionButton;
    @FXML
    private ImageView CollectionButtonImage;

    @FXML
    protected void onPlayClicked() {
    }
    @FXML
    protected void onSettingsClicked() {
    }
    @FXML
    protected void onCollectionClicked() {
    }
    @FXML
    protected void onExitClicked() {
        ExitButton.setOnAction((ActionEvent event) -> {
            /**Close the game*/
            Platform.exit();
        });}
    @FXML
    protected void onExitEntered(){
        /**For all the on...Entered(): if the cursor comes over the button, the button become lighter*/
        Image imageLight = new Image("PlayableMenu/Sprites/EXITPRESSED.png");
        ExitButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onExitExited(){
        /**For all the on...Exited(): if the cursor quit the button, the button retakes his origin sprite*/
        Image imageDark = new Image("PlayableMenu/Sprites/EXIT.png");
        ExitButtonImage.setImage(imageDark);
    }
    @FXML
    protected void onCollectionEntered(){
        Image imageLight = new Image("PlayableMenu/Sprites/ButtonCollectionLight.png");
        CollectionButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onCollectionExited(){
        Image imageLight = new Image("PlayableMenu/Sprites/ButtonCollection.png");
        CollectionButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onPlayEntered(){
        Image imageLight = new Image("PlayableMenu/Sprites/ButtonPlayLight.png");
        PlayButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onPlayExited(){
        Image imageLight = new Image("PlayableMenu/Sprites/Buttonplay.png");
        PlayButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onSettingsEntered(){
        Image imageLight = new Image("PlayableMenu/Sprites/ButtonSettingsLight.png");
        SettingsButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onSettingsExited(){
        Image imageLight = new Image("PlayableMenu/Sprites/ButtonSettings.png");
        SettingsButtonImage.setImage(imageLight);
    }
}
