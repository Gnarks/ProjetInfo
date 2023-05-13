package code.projetinfo.controllers;

import code.projetinfo.Level;
import code.projetinfo.LevelHandler;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRandomLevel extends ControllerParent implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView BackToMenuButton;

    @FXML
    private ImageView ResetButton;


    @FXML
    protected void onBackEntered(){
        imageChanger(BackToMenuButton,"Sprites/ButtonBackToMenulight.png");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(BackToMenuButton,"Sprites/ButtonBackToMenu.png");
    }

    @FXML
    protected void onResetEntered(){
        imageChanger(ResetButton,"Sprites/ButtonResetLight.png");
    }

    @FXML
    protected void onResetExited(){
        imageChanger(ResetButton,"Sprites/ButtonReset.png");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle transi = new Rectangle(1600,900 , Paint.valueOf("222222"));
        pane.getChildren().add(transi);

        /**
        Platform.runLater(() ->{

            LevelHandler levelHandler = new LevelHandler(level, pane);
            levelHandler.dispatchBlocks();
            levelHandler.drawGrid();
            levelHandler.drawImageBlocks();

            transi.toFront();
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(900);
            translateTransition.play();
            translateTransition.setOnFinished(event -> pane.getChildren().remove(transi));

            ResetButton.setOnMouseClicked(event -> {
                if(!levelHandler.getVictoryState()){
                    levelHandler.reset();}
                else{
                    levelHandler.loadLevel(levelName,event);
                }});
            BackToMenuButton.setOnMouseClicked(event -> pauseMenu(pane,levelHandler,level,levelName,event));
        });
             */
    }
}
