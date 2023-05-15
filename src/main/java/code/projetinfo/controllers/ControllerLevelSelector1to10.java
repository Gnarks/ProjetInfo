package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLevelSelector1to10 extends ControllerParent implements Initializable {
    /**Controller of all the level selector, from 1 to 10(assigned to LevelSelector1to10.fxml)*/
    @FXML
    private Pane pane;
    @FXML
    private ImageView buttonNext;
    @FXML
    private ImageView buttonBack;

    @FXML
    public void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","MenuPlay.fxml");
    }

    @FXML
    public void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }

    @FXML
    public void onNextEntered(){
        onButtonEntered(buttonNext,"Sprites/Button_Arrow_RightLight.png","LevelSelector11to20.fxml");
    }

    @FXML
    public void onNextExited(){
        imageChanger(buttonNext,"Sprites/Button_Arrow_Right.png");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass()==Button.class){
                LevelSelect(pane,pane.getChildren().get(i).getId(), pane.getChildren().get(i));
            }
        }
    }
}
