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
        onButtonEntered(buttonBack,"Sprites/ButtonBackToMenulight.png","MainMenu.fxml");
    }
    @FXML
    public void onBackExited(){
        imageChanger(buttonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    public void onRLSExited(){
        imageChanger(randomLevelsSaved,"Sprites/RandomLevelSaved.png");
    }
    @FXML
    public void onRLSEntered(){
        onButtonEntered(randomLevelsSaved,"Sprites/RandomLevelSavedButtonLight.png","RandomLevelSelector.fxml");
    }
    @FXML
    public void onRLGExited(){
        imageChanger(randomLevelGenerator,"Sprites/RandomLevelGeneratorButton.png");
    }

    @FXML
    public void onRLGEntered(){
        onButtonEntered(randomLevelGenerator,"Sprites/RandomLevelGeneratorButtonLight.png","RandomLevelGenerator.fxml");
    }


}