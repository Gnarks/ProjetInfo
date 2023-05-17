package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ControllerCreatedLevelSelector extends ControllerParent{
    @FXML
    private Pane pane;
    @FXML
    private ImageView created1Button;
    @FXML
    private ImageView buttonBack;
    @FXML
    private ImageView created2Button;
    @FXML
    private ImageView created3Button;
    @FXML
    protected void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","MenuCreate.fxml");
    }
    @FXML
    protected void onCreated1Entered(){
        imageChanger(created1Button,"Sprites/Button_Created1_Light.png");
        LevelSelect(pane,"CreatedLevel1",created1Button);
    }
    @FXML
    protected void onCreated2Entered(){
        imageChanger(created2Button,"Sprites/Button_Created2_Light.png");

        LevelSelect(pane,"CreatedLevel2",created2Button);
    }
    @FXML
    protected void onCreated3Entered(){
        imageChanger(created3Button,"Sprites/Button_Created3_Light.png");

        LevelSelect(pane,"CreatedLevel3",created3Button);
    }
    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }
    @FXML
    protected void onCreated1Exited(){
        imageChanger(created1Button,"Sprites/Button_Created1.png");
    }
    @FXML
    protected void onCreated2Exited(){
        imageChanger(created2Button,"Sprites/Button_Created2.png");
    }
    @FXML
    protected void onCreated3Exited(){
        imageChanger(created3Button,"Sprites/Button_Created3.png");}
}
