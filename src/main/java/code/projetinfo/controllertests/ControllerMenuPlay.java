package code.projetinfo.controllertests;

import code.projetinfo.AppGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class ControllerMenuPlay {


    @FXML
    private ImageView LevelsButton;
    @FXML
    private ImageView ModsButton;
    @FXML
    private ImageView RandomButton;

    @FXML
    private ImageView ButtonBack;

    private Stage stage;

    private Scene scene;

    private void entered(ImageView button,String spriteLight, String nextStage){
        button.setImage(new Image(String.valueOf(AppGame.class.getResource(spriteLight))));
        button.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource(nextStage));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1600, 900);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }

    public void exited(ImageView button, String spriteDark){
        button.setImage(new Image(String.valueOf(AppGame.class.getResource(spriteDark))));
    }
    @FXML
    public void onBackEntered(){
        entered(ButtonBack,"Sprites/ButtonBackToMenulight.png","MainMenu.fxml");
    }
    @FXML
    public void onBackExited(){
        exited(ButtonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    public void onLevelsEntered(){
        entered(LevelsButton,"Sprites/LevelsButtonLight.png","LevelSelector1to10.fxml");
    }
    @FXML
    public void onLevelsExited(){
        exited(LevelsButton,"Sprites/LevelsButton.png");
    }
    @FXML
    public void onRandomEntered(){
        entered(RandomButton,"Sprites/RandomButtonLight.png","LevelSelector1to10.fxml");
    }
    @FXML
    public void onRandomExited(){
        exited(RandomButton,"Sprites/RandomButton.png");
    }

    @FXML
    public void onModsEntered(){
        entered(ModsButton,"Sprites/modsButtonlight.png","MenuMods.fxml");
    }
    @FXML
    public void onModsExited(){
        exited(ModsButton,"Sprites/modsButton.png");
    }

}
