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
        imageChanger(askingSpeedrun,"Sprites/Button_Asking_Light.png");
        askingInformation(pane, askingSpeedrun,"Complete as much level as possible in the defined time!");
    }

    @FXML
    protected void onAskingDancingEntered(){
        imageChanger(askingDancing,"Sprites/Button_Asking_Light.png");
        askingInformation(pane, askingDancing,"Try too complete the levels while the ghosts are moving on the music's rhythm ");
    }
    @FXML
    protected void onAskingMemoryEntered(){
        imageChanger(askingMemory,"Sprites/Button_Asking_Light.png");
        askingInformation(pane, askingMemory,"The ghosts are shy and disappear after being placed, try to remember where they are");
    }
    @FXML
    protected void onAskingSpeedrunExited(){
        imageChanger(askingSpeedrun,"Sprites/Button_Asking.png");
    }
    @FXML
    protected void onAskingDancingExited(){
        imageChanger(askingDancing,"Sprites/Button_Asking.png");
    }
    @FXML
    protected void onAskingMemoryExited(){
        imageChanger(askingMemory,"Sprites/Button_Asking.png");
    }
    @FXML
    protected void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","MenuPlay.fxml");
    }
    @FXML
    protected void onSpeedrunEntered(){
        imageChanger(speedrunButton,"Sprites/Button_Speedrun_Light.png");
    }
    @FXML
    protected void onDancingEntered(){
        imageChanger(dancingButton,"Sprites/Button_Dancing_Light.png");
    }
    @FXML
    protected void onMemoryEntered(){
        imageChanger(memoryButton,"Sprites/Button_Memory_Light.png");

    }
    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }
    @FXML
    protected void onSpeedrunExited(){
        imageChanger(speedrunButton,"Sprites/Button_Speedrun.png");
    }
    @FXML
    protected void onDancingExited(){
        imageChanger(dancingButton,"Sprites/Button_Dancing.png");
    }
    @FXML
    protected void onMemoryExited(){
        imageChanger(memoryButton,"Sprites/Button_Memory.png");}


}
