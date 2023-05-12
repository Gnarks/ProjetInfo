package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
public class ControllerMenuMods extends ControllerParent{

    @FXML
    private Pane pane;

    @FXML
    private ImageView AskingSpeedrun;
    @FXML
    private ImageView AskingDancing;
    @FXML
    private ImageView AskingMemory;
    @FXML
    private ImageView ButtonBack;
    @FXML
    private ImageView MemoryButton;
    @FXML
    private ImageView DancingButton;
    @FXML
    private ImageView SpeedrunButton;
    @FXML
    protected void onAskingSpeedrunEntered(){
        imageChanger(AskingSpeedrun,"Sprites/AskingButtonLight.png");
        askingInformation(pane,AskingSpeedrun,"Complete as much level as possible in the defined time!");
    }

    @FXML
    protected void onAskingDancingEntered(){
        imageChanger(AskingDancing,"Sprites/AskingButtonLight.png");
        askingInformation(pane,AskingDancing,"Try too complete the levels while the ghosts are moving on the music's rhythm ");
    }
    @FXML
    protected void onAskingMemoryEntered(){
        imageChanger(AskingMemory,"Sprites/AskingButtonLight.png");
        askingInformation(pane,AskingMemory,"The ghosts are shy and disappear after being placed, try to remember where they are");
    }
    @FXML
    protected void onAskingSpeedrunExited(){
        imageChanger(AskingSpeedrun,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onAskingDancingExited(){
        imageChanger(AskingDancing,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onAskingMemoryExited(){
        imageChanger(AskingMemory,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onBackEntered(){
        onButtonEntered(ButtonBack,"Sprites/ButtonBackToMenulight.png","MenuPlay.fxml");
    }
    @FXML
    protected void onSpeedrunEntered(){
        imageChanger(SpeedrunButton,"Sprites/SpeedrunButtonLight.png");
    }
    @FXML
    protected void onDancingEntered(){
        imageChanger(DancingButton,"Sprites/DancingButtonLight.png");
    }
    @FXML
    protected void onMemoryEntered(){
        imageChanger(MemoryButton,"Sprites/MemoryButtonLight.png");
    }
    @FXML
    protected void onBackExited(){
        imageChanger(ButtonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    protected void onSpeedrunExited(){
        imageChanger(SpeedrunButton,"Sprites/SpeedrunButton.png");
    }
    @FXML
    protected void onDancingExited(){
        imageChanger(DancingButton,"Sprites/DancingButton.png");
    }
    @FXML
    protected void onMemoryExited(){
        imageChanger(MemoryButton,"Sprites/MemoryButton.png");}


}
