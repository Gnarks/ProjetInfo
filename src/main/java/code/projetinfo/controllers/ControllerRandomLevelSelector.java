package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ControllerRandomLevelSelector extends ControllerParent{
    @FXML
    private Pane pane;
    @FXML
    private ImageView random1Button;
    @FXML
    private ImageView buttonBack;
    @FXML
    private ImageView random2Button;
    @FXML
    private ImageView random3Button;
    @FXML
    protected void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/ButtonBackToMenulight.png","MenuPlay.fxml");
    }
    @FXML
    protected void onRandom1Entered(){
        imageChanger(random1Button,"Sprites/Random1ButtonLight.png");
        random1Button.setOnMouseClicked(event -> LevelSelect(pane,"RandomLevel1",random1Button));
    }
    @FXML
    protected void onRandom2Entered(){
        imageChanger(random2Button,"Sprites/Random2ButtonLight.png");

        random2Button.setOnMouseClicked(event -> LevelSelect(pane,"RandomLevel2",random2Button));
    }
    @FXML
    protected void onRandom3Entered(){
        imageChanger(random3Button,"Sprites/Random3ButtonLight.png");

        random3Button.setOnMouseClicked(event -> LevelSelect(pane,"RandomLevel3",random3Button));
    }
    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    protected void onRandom1Exited(){
        imageChanger(random1Button,"Sprites/Random1Button.png");
    }
    @FXML
    protected void onRandom2Exited(){
        imageChanger(random2Button,"Sprites/Random2Button.png");
    }
    @FXML
    protected void onRandom3Exited(){
        imageChanger(random3Button,"Sprites/Random3Button.png");}
}
