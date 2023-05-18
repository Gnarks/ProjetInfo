package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ControllerRandomMenu extends ControllerParent {
    @FXML
    private ImageView buttonBack;

    @FXML
    private ImageView randomLevelsSaved;

    @FXML
    private ImageView randomLevelGenerator;

    @FXML
    public void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","MenuPlay.fxml");
    }
    @FXML
    public void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }
    @FXML
    public void onRLSExited(){
        imageChanger(randomLevelsSaved,"Sprites/Button_RLS.png");
    }
    @FXML
    public void onRLSEntered(){
        onButtonEntered(randomLevelsSaved,"Sprites/Button_RLS_Light.png","RandomLevelSelector.fxml");
    }
    @FXML
    public void onRLGExited(){
        imageChanger(randomLevelGenerator,"Sprites/Button_RLG.png");
    }

    @FXML
    public void onRLGEntered(){
        onButtonEntered(randomLevelGenerator,"Sprites/Button_RLG_Light.png","RandomLevelGenerator.fxml");
    }


}