package code.projetinfo.controllertests;


import code.projetinfo.AppGame;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;



import java.io.IOException;


public class ControllerMenu {
    /**Controller of all the menu's buttons(assigned to MENUSTEST.fxml)*/
    @FXML
    private ImageView PlayButtonImage;
    @FXML
    private ImageView ExitButtonImage;
    @FXML
    private ImageView SettingsButtonImage;
    @FXML
    private ImageView CollectionButtonImage;

    private Stage stage;

    private Scene scene;

    @FXML
    protected void onExitEntered(){
        /**For all the on...Entered(): if the cursor comes over the button, the button become lighter*/
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/EXITPRESSED.png")));
        ExitButtonImage.setImage(imageLight);

        ExitButtonImage.setOnMouseClicked((event) -> {
            /**Close the game*/
            Platform.exit();
        });
    }

    @FXML
    protected void onExitExited(){
        /**For all the on...Exited(): if the cursor quit the button, the button retakes his origin sprite*/
        Image imageDark = new Image(String.valueOf(AppGame.class.getResource("Sprites/EXIT.png")));
        ExitButtonImage.setImage(imageDark);
    }
    @FXML
    protected void onCollectionEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonCollectionLight.png")));
        CollectionButtonImage.setImage(imageLight);

        CollectionButtonImage.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("Collection.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1600, 900);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.show();
        });

    }
    @FXML
    protected void onCollectionExited(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonCollection.png")));
        CollectionButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onPlayEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonPlayLight.png")));
        PlayButtonImage.setImage(imageLight);

        PlayButtonImage.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("LevelSelector.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1600, 900);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.show();
        });
    }
    @FXML
    protected void onPlayExited(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/Buttonplay.png")));
        PlayButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onSettingsEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonSettingsLight.png")));
        SettingsButtonImage.setImage(imageLight);
    }
    @FXML
    protected void onSettingsExited(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonSettings.png")));
        SettingsButtonImage.setImage(imageLight);
    }


}
