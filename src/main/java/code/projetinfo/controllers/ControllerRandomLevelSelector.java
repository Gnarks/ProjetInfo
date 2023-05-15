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
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","MenuPlay.fxml");
    }
    @FXML
    protected void onRandom1Entered(){
        imageChanger(random1Button,"Sprites/Button_Random1_Light.png");
        LevelSelect(pane,"RandomLevel1",random1Button);
    }
    @FXML
    protected void onRandom2Entered(){
        imageChanger(random2Button,"Sprites/Button_Random2_Light.png");

        LevelSelect(pane,"RandomLevel2",random2Button);
    }
    @FXML
    protected void onRandom3Entered(){
        imageChanger(random3Button,"Sprites/Button_Random3_Light.png");

        LevelSelect(pane,"RandomLevel3",random3Button);
    }
    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }
    @FXML
    protected void onRandom1Exited(){
        imageChanger(random1Button,"Sprites/Button_Random1.png");
    }
    @FXML
    protected void onRandom2Exited(){
        imageChanger(random2Button,"Sprites/Button_Random2.png");
    }
    @FXML
    protected void onRandom3Exited(){
        imageChanger(random3Button,"Sprites/Button_Random3.png");}
}
