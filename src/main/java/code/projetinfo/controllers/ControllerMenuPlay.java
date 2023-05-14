package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;


public class ControllerMenuPlay extends ControllerParent{

    /**Controller of all the play menu's buttons(assigned to MenuPlay.fxml)*/
    @FXML
    private ImageView levelsButton;
    @FXML
    private ImageView modsButton;
    @FXML
    private ImageView randomButton;

    @FXML
    private ImageView buttonBack;


    @FXML
    public void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/ButtonBackToMenulight.png","MainMenu.fxml");
    }
    @FXML
    public void onBackExited(){
        imageChanger(buttonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    public void onLevelsEntered(){
        onButtonEntered(levelsButton,"Sprites/LevelsButtonLight.png","LevelSelector1to10.fxml");
    }
    @FXML
    public void onLevelsExited(){
        imageChanger(levelsButton,"Sprites/LevelsButton.png");
    }
    @FXML
    public void onRandomEntered(){
        onButtonEntered(randomButton,"Sprites/RandomButtonLight.png","RLGMenu.fxml");
    }
    @FXML
    public void onRandomExited(){
        imageChanger(randomButton,"Sprites/RandomButton.png");
    }

    @FXML
    public void onModsEntered(){
        onButtonEntered(modsButton,"Sprites/modsButtonlight.png","MenuMods.fxml");
    }
    @FXML
    public void onModsExited(){
        imageChanger(modsButton,"Sprites/modsButton.png");
    }

}
