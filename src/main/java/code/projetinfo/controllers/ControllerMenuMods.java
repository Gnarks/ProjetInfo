package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class ControllerMenuMods extends ControllerParent{

    /**Controller of all the mods menu's buttons(assigned to MenuMods.fxml)*/

    @FXML
    private Pane pane;

    @FXML
    private ImageView askingSpeedrun;
    @FXML
    private ImageView askingDancing;
    @FXML
    private ImageView askingMemory;
    @FXML
    private ImageView buttonBack;
    @FXML
    private ImageView memoryButton;
    @FXML
    private ImageView dancingButton;
    @FXML
    private ImageView speedrunButton;
    @FXML
    protected void onAskingSpeedrunEntered(){
        imageChanger(askingSpeedrun,"Sprites/AskingButtonLight.png");
        askingInformation(pane, askingSpeedrun,"Complete as much level as possible in the defined time!");
    }

    @FXML
    protected void onAskingDancingEntered(){
        imageChanger(askingDancing,"Sprites/AskingButtonLight.png");
        askingInformation(pane, askingDancing,"Try too complete the levels while the ghosts are moving on the music's rhythm ");
    }
    @FXML
    protected void onAskingMemoryEntered(){
        imageChanger(askingMemory,"Sprites/AskingButtonLight.png");
        askingInformation(pane, askingMemory,"The ghosts are shy and disappear after being placed, try to remember where they are");
    }
    @FXML
    protected void onAskingSpeedrunExited(){
        imageChanger(askingSpeedrun,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onAskingDancingExited(){
        imageChanger(askingDancing,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onAskingMemoryExited(){
        imageChanger(askingMemory,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/ButtonBackToMenulight.png","MenuPlay.fxml");
    }
    @FXML
    protected void onSpeedrunEntered(){
        imageChanger(speedrunButton,"Sprites/SpeedrunButtonLight.png");
        speedrunButton.setOnMouseClicked(event -> LevelSelect(pane,"RandomLevel1",speedrunButton));
    }
    @FXML
    protected void onDancingEntered(){
        imageChanger(dancingButton,"Sprites/DancingButtonLight.png");

        dancingButton.setOnMouseClicked(event -> LevelSelect(pane,"RandomLevel2",dancingButton));
    }
    @FXML
    protected void onMemoryEntered(){
        imageChanger(memoryButton,"Sprites/MemoryButtonLight.png");

        memoryButton.setOnMouseClicked(event -> LevelSelect(pane,"RandomLevel3",memoryButton));
    }
    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    protected void onSpeedrunExited(){
        imageChanger(speedrunButton,"Sprites/SpeedrunButton.png");
    }
    @FXML
    protected void onDancingExited(){
        imageChanger(dancingButton,"Sprites/DancingButton.png");
    }
    @FXML
    protected void onMemoryExited(){
        imageChanger(memoryButton,"Sprites/MemoryButton.png");}


}
