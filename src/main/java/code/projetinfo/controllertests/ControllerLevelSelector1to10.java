package code.projetinfo.controllertests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLevelSelector1to10 extends ControllerParent implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private ImageView ButtonNext;
    @FXML
    private ImageView ButtonBack;

    @FXML
    public void onBackEntered(){
        onButtonEntered(ButtonBack,"Sprites/ButtonBackToMenulight.png","MenuPlay.fxml");
    }

    @FXML
    public void onBackExited(){
        buttonImageChanger(ButtonBack,"Sprites/ButtonBackToMenu.png");
    }

    @FXML
    public void onNextEntered(){
        onButtonEntered(ButtonNext,"Sprites/ButtonNextLight.png","LevelSelector11to20.fxml");
    }

    @FXML
    public void onNextExited(){
        buttonImageChanger(ButtonNext,"Sprites/ButtonNext.png");
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass()==Button.class){
                LevelSelect(pane,pane.getChildren().get(i).getId(),(Button) pane.getChildren().get(i));
            }
        }
    }
}
