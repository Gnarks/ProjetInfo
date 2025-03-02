package code.projetinfo.controllers;

import code.projetinfo.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends ControllerParent implements Initializable {

    /**Controller of all the in game buttons (for the classic level), the creation of the level and so on(assigned to Game.fxml)*/
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView backToMenuButton;

    @FXML
    private ImageView resetButton;

    private String levelName;

    public void setLevelName(String levelName){
        this.levelName = levelName;
    }

    @FXML
    protected void onBackEntered(){
        imageChanger(backToMenuButton,"Sprites/Button_GoBack_Light.png");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(backToMenuButton,"Sprites/Button_GoBack.png");
    }

    @FXML
    protected void onResetEntered(){
        imageChanger(resetButton,"Sprites/Button_Reset_Light.png");
    }

    @FXML
    protected void onResetExited(){
        imageChanger(resetButton,"Sprites/Button_Reset.png");
    }




    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ControllerOptions.setMediaPlayerLevel();
        Rectangle transi = new Rectangle(1600,900 , Paint.valueOf("222222"));
        pane.getChildren().add(transi);


        Platform.runLater(() ->{
            Label label  = new Label(levelName);
            label.setUnderline(true);
            label.setTextFill(Paint.valueOf("#ffffff"));
            label.setLayoutX(740);
            label.setLayoutY(30);
            label.setAlignment(Pos.TOP_CENTER);
            Font font = new Font("System Bold Italic",30);
            label.setFont(font);
            pane.getChildren().add(label);


            Level level;
            try {
                level = new Level(levelName);
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            LevelHandler levelHandler = new LevelHandler(level, pane);
            levelHandler.dispatchBlocks();
            levelHandler.drawGrid();
            levelHandler.drawImageBlocks();

            transi.toFront();
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(900);
            translateTransition.play();
            translateTransition.setOnFinished(event -> pane.getChildren().remove(transi));

            resetButton.setOnMouseClicked(event -> {
                if(!levelHandler.getVictoryState()){
                    levelHandler.reset();}
                else{
                    levelHandler.loadLevel(levelName,event);
            }});
            backToMenuButton.setOnMouseClicked(event -> pauseMenu(pane,levelHandler,level,event));
        });
    }
}