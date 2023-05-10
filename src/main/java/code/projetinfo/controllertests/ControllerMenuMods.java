package code.projetinfo.controllertests;

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
        buttonImageChanger(AskingSpeedrun,"Sprites/AskingButtonLight.png");
        askingInformation(pane,AskingSpeedrun,"Complete as much level as possible in the defined time!");
    }

    @FXML
    protected void onAskingDancingEntered(){
        buttonImageChanger(AskingDancing,"Sprites/AskingButtonLight.png");
        askingInformation(pane,AskingDancing,"Try too complete the levels while the ghosts are moving on the music's rhythm ");
    }
    @FXML
    protected void onAskingMemoryEntered(){
        buttonImageChanger(AskingMemory,"Sprites/AskingButtonLight.png");
        askingInformation(pane,AskingMemory,"The ghosts are shy and disappear after being placed, try to remember where they are");
    }
    @FXML
    protected void onAskingSpeedrunExited(){
        buttonImageChanger(AskingSpeedrun,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onAskingDancingExited(){
        buttonImageChanger(AskingDancing,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onAskingMemoryExited(){
        buttonImageChanger(AskingMemory,"Sprites/AskingButton.png");
    }
    @FXML
    protected void onBackEntered(){
        onButtonEntered(ButtonBack,"Sprites/ButtonBackToMenulight.png","MenuPlay.fxml");
    }
    @FXML
    protected void onSpeedrunEntered(){
        buttonImageChanger(SpeedrunButton,"Sprites/SpeedrunButtonLight.png");
    }
    @FXML
    protected void onDancingEntered(){
        buttonImageChanger(DancingButton,"Sprites/DancingButtonLight.png");
    }
    @FXML
    protected void onMemoryEntered(){
        buttonImageChanger(MemoryButton,"Sprites/MemoryButtonLight.png");
    }
    @FXML
    protected void onBackExited(){
        buttonImageChanger(ButtonBack,"Sprites/ButtonBackToMenu.png");
    }
    @FXML
    protected void onSpeedrunExited(){
        buttonImageChanger(SpeedrunButton,"Sprites/SpeedrunButton.png");
    }
    @FXML
    protected void onDancingExited(){
        buttonImageChanger(DancingButton,"Sprites/DancingButton.png");
    }
    @FXML
    protected void onMemoryExited(){buttonImageChanger(MemoryButton,"Sprites/MemoryButton.png");}


}
