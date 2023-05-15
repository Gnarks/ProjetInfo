package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLevelSelector11to20 extends ControllerParent implements Initializable {
    /**Controller of all the level selector, from 11 to 20(assigned to LevelSelector11to20.fxml)*/
    @FXML
    private Pane pane;
    @FXML
    private ImageView buttonBack;

    @FXML
    public void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_Arrow_LeftLight.png","LevelSelector1to10.fxml");
    }

    @FXML
    public void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_Arrow_Left.png");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass()==Button.class){
                LevelSelect(pane,pane.getChildren().get(i).getId(),pane.getChildren().get(i));
            }
        }
    }
}
