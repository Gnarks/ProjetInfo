package code.projetinfo.controllertests;

import code.projetinfo.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController extends ControllerParent implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView BackToMenuButton;

    @FXML
    private ImageView ResetButton;

    private String levelName;


    public void setLevelName(String levelName){
        this.levelName = levelName;
    }

    @FXML
    protected void onBackEntered(){
        buttonImageChanger(BackToMenuButton,"Sprites/ButtonBackToMenulight.png");
    }

    @FXML
    protected void onBackExited(){
        buttonImageChanger(BackToMenuButton,"Sprites/ButtonBackToMenu.png");
    }

    @FXML
    protected void onResetEntered(){
        buttonImageChanger(ResetButton,"Sprites/ButtonResetLight.png");
    }

    @FXML
    protected void onResetExited(){
        buttonImageChanger(ResetButton,"Sprites/ButtonReset.png");
    }




    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle transi = new Rectangle(1600,900 , Paint.valueOf("222222"));
        pane.getChildren().add(transi);

        Platform.runLater(() ->{
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

            ResetButton.setOnMouseClicked(event -> {
                if(!levelHandler.getVictoryState()){
                levelHandler.reset();}
                else{
                    levelHandler.loadLevel(levelName,event);
            }});

            BackToMenuButton.setOnMouseClicked(event -> {
                if(!levelHandler.getVictoryState()){
                Rectangle rectangle = new Rectangle(1600, 900, Paint.valueOf("#000000"));
                rectangle.setOpacity(0.3);
                ImageView menuPause = createImageView("Sprites/FondChoser.png",600,500,150);
                ImageView save = createImageView("Sprites/SaveButton.png",400,600,375);
                ImageView leave = createImageView("Sprites/LeaveButton.png",400,600,525);
                ImageView resume = createImageView("Sprites/ResumeButton.png", 400,600,225);

                resume.setOnMouseClicked(resumeEvent -> pane.getChildren().remove(pane.getChildren().size()-5,pane.getChildren().size()));
                resume.setOnMouseEntered(resumeEvent2 -> buttonImageChanger(resume,"Sprites/ResumeButtonLight.png"));
                resume.setOnMouseExited(resumeEvent3 -> buttonImageChanger(resume,"Sprites/ResumeButton.png"));

                leave.setOnMouseClicked(leaveEvent -> {
                    if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                        loadScene("LevelSelector1to10.fxml",event);
                } else {
                        loadScene("LevelSelector11to20.fxml",event);
                }
                });

                leave.setOnMouseEntered(leaveEvent2 -> buttonImageChanger(leave,"Sprites/LeaveButtonLight.png"));
                leave.setOnMouseExited(leaveEvent3 -> buttonImageChanger(leave,"Sprites/LeaveButton.png"));

                save.setOnMouseClicked(saveEvent -> {
                    try {
                        level.saveState();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                        loadScene("LevelSelector1to10.fxml",event);
                    } else {
                        loadScene("LevelSelector11to20.fxml",event);
                    }
                });

                save.setOnMouseEntered(leaveEvent2 -> buttonImageChanger(save,"Sprites/SaveButtonLight.png"));
                save.setOnMouseExited(leaveEvent3 -> buttonImageChanger(save,"Sprites/SaveButton.png"));

                pane.getChildren().add(rectangle);
                pane.getChildren().add(menuPause);
                pane.getChildren().add(save);
                pane.getChildren().add(leave);
                pane.getChildren().add(resume);}

                else{
                    if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                        loadScene("LevelSelector1to10.fxml",event);
                    } else {
                        loadScene("LevelSelector11to20.fxml",event);
                    }
                }
            });
        });
    }
}