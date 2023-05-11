package code.projetinfo.controllertests;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class ControllerMenuPlay extends ControllerParent{
    @FXML
    private ImageView LevelsButton;
    @FXML
    private ImageView ModsButton;
    @FXML
    private ImageView RandomButton;

    @FXML
    private ImageView ButtonBack;


    @FXML
    public void onBackEntered(){
        onButtonEntered(ButtonBack,"Sprites/ButtonBackToMenulight.png","MainMenu.fxml");
    }
    @FXML
    public void onBackExited(){
        buttonImageChanger(ButtonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    public void onLevelsEntered(){
        onButtonEntered(LevelsButton,"Sprites/LevelsButtonLight.png","LevelSelector1to10.fxml");
    }
    @FXML
    public void onLevelsExited(){
        buttonImageChanger(LevelsButton,"Sprites/LevelsButton.png");
    }
    @FXML
    public void onRandomEntered(){
        onButtonEntered(RandomButton,"Sprites/RandomButtonLight.png","RLGMenu.fxml");
    }
    @FXML
    public void onRandomExited(){
        buttonImageChanger(RandomButton,"Sprites/RandomButton.png");
    }

    @FXML
    public void onModsEntered(){
        onButtonEntered(ModsButton,"Sprites/modsButtonlight.png","MenuMods.fxml");
    }
    @FXML
    public void onModsExited(){
        buttonImageChanger(ModsButton,"Sprites/modsButton.png");
    }

}
