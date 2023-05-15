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
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","MainMenu.fxml");
    }
    @FXML
    public void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }
    @FXML
    public void onLevelsEntered(){
        onButtonEntered(levelsButton,"Sprites/Button_Levels_Light.png","LevelSelector1to10.fxml");
    }
    @FXML
    public void onLevelsExited(){
        imageChanger(levelsButton,"Sprites/Button_Levels.png");
    }
    @FXML
    public void onRandomEntered(){
        onButtonEntered(randomButton,"Sprites/Button_Random_Light.png","RandomMenu.fxml");
    }
    @FXML
    public void onRandomExited(){
        imageChanger(randomButton,"Sprites/Button_Random.png");
    }

    @FXML
    public void onModsEntered(){
        onButtonEntered(modsButton,"Sprites/Button_Mods_Light.png","MenuMods.fxml");
    }
    @FXML
    public void onModsExited(){
        imageChanger(modsButton,"Sprites/Button_Mods.png");
    }

}
