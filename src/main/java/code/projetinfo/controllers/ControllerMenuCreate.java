package code.projetinfo.controllers;

import code.projetinfo.ImageBlock;
import code.projetinfo.LevelCreator;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class ControllerMenuCreate extends ControllerParent{

    /**Controller of all the mods menu's buttons(assigned to MenuCreate.fxml)*/

    @FXML
    private ImageView buttonBack;
    @FXML
    private ImageView levelsCreated;
    @FXML
    private ImageView create;
    @FXML
    protected void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","MenuPlay.fxml");
    }
    @FXML
    protected void onCreateEntered(){
        onButtonEntered(create,"Sprites/Button_Create_Light.png","LevelCreator.fxml");
    }
    @FXML
    protected void onLCEntered(){
        LevelCreator.inventoryCounter = 0;
        LevelCreator.blocksCounter = 0;
        LevelCreator.inventoryList = new ImageBlock[3];
        onButtonEntered(levelsCreated,"Sprites/Button_LevelCreated_Light.png","CreatedLevelSelector.fxml");
    }
    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }
    @FXML
    protected void onCreateExited(){
        imageChanger(create,"Sprites/Button_Create.png");
    }
    @FXML
    protected void onLCExited(){
        imageChanger(levelsCreated,"Sprites/Button_LevelCreated.png");
    }


}
