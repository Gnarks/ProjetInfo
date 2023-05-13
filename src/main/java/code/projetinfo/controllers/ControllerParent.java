package code.projetinfo.controllers;

import code.projetinfo.AppMenu;
import code.projetinfo.ImageBlock;
import code.projetinfo.Level;
import code.projetinfo.LevelHandler;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.assertThrows;


public class ControllerParent {
    /**
     *
     *
     * @param button imageview which serves as a button
     * @param sprite lighter sprite to set on the button
     * @param nextScene scene to load after clicking on the button
     */
    protected void onButtonEntered(ImageView button, String sprite, String nextScene){
        button.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
        button.setOnMouseClicked(event -> loadScene(nextScene,event));
    }

    /**
     *
     * @param imageView set another image on an imageview
     * @param sprite sprite to set on the imageview
     */
    protected void imageChanger(ImageView imageView, String sprite){
        imageView.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
    }

    /**
     *
     * @param pane scene's pane
     * @param levelHandler levelHandler of the level which is played
     * @param level level which is played
     * @param levelName levelName of the level
     * @param event mouseEvent when the player click on the pause button
     */
    protected void pauseMenu(Pane pane, LevelHandler levelHandler, Level level,String levelName,Event event){

            if(!levelHandler.getVictoryState()){
                Rectangle rectangle = new Rectangle(1600, 900, Paint.valueOf("#000000"));
                rectangle.setOpacity(0.3);
                ImageView menuPause = createImageView("Sprites/FondChoser.png",600,500,150);
                ImageView save = createImageView("Sprites/SaveButton.png",400,600,375);
                ImageView leave = createImageView("Sprites/LeaveButton.png",400,600,525);
                ImageView resume = createImageView("Sprites/ResumeButton.png", 400,600,225);

                resume.setOnMouseEntered(resumeEvent -> imageChanger(resume,"Sprites/ResumeButtonLight.png"));
                resume.setOnMouseExited(resumeEvent -> imageChanger(resume,"Sprites/ResumeButton.png"));
                resume.setOnMouseClicked(resumeEvent -> pane.getChildren().remove(pane.getChildren().size()-5,pane.getChildren().size()));

                leave.setOnMouseEntered(leaveEvent -> imageChanger(leave,"Sprites/LeaveButtonLight.png"));
                leave.setOnMouseExited(leaveEvent -> imageChanger(leave,"Sprites/LeaveButton.png"));
                leave.setOnMouseClicked(leaveEvent -> {
                    if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                        loadScene("LevelSelector1to10.fxml",event);
                    } else {
                        loadScene("LevelSelector11to20.fxml",event);
                    }
                });

                save.setOnMouseEntered(saveEvent -> imageChanger(save,"Sprites/SaveButtonLight.png"));
                save.setOnMouseExited(saveEvent -> imageChanger(save,"Sprites/SaveButton.png"));
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

                pane.getChildren().addAll(rectangle,menuPause,save,leave,resume);
            }

            else{
                if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                    loadScene("LevelSelector1to10.fxml",event);
                } else {
                    loadScene("LevelSelector11to20.fxml",event);
                }
            }
    }

    /**
     *
     * @param pane
     * @param levelName
     * @param button button
     */
    protected void LevelSelect(Pane pane, String levelName, Button button){
        button.setOnAction(event -> {
            Rectangle transi = new Rectangle(1600,900, Paint.valueOf("222222"));
            transi.setLayoutY(900);
            pane.getChildren().add(transi);
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(-900);
            translateTransition.play();
            translateTransition.setOnFinished(event1 -> {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource("Game.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                GameController gameController = fxmlLoader.getController();
                gameController.setLevelName(levelName);

                Stage stage;
                Scene scene;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1600, 900);

                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            });
        });
    }

    /**
     *
     * @param nextScene
     * @param event
     */
    protected void loadScene(String nextScene, Event event){
            FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource(nextScene));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1600, 900);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
    }

    /**
     *
     * @param image
     * @param width
     * @param layoutX
     * @param layoutY
     * @return
     */
    protected ImageView createImageView(String image,double width,double layoutX,double layoutY){
        ImageView imageView = new ImageView(new Image(String.valueOf(AppMenu.class.getResource(image))));
       imageView.setPreserveRatio(true);
       imageView.setFitWidth(width);
       imageView.setLayoutX(layoutX);
       imageView.setLayoutY(layoutY);
       return imageView;
    }

    /**
     *
     * @param pane
     * @param askingButton
     * @param text
     */
    protected void askingInformation(Pane pane,ImageView askingButton,String text){
        askingButton.setOnMouseClicked(event ->{
            Rectangle rectangle = new Rectangle(1600,900, Paint.valueOf("#000000"));
            rectangle.setOpacity(0.2);
            ImageView fondInt = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/BackGridLevel.png"))));
            fondInt.setFitWidth(800);
            fondInt.setFitHeight(400);
            fondInt.setLayoutX(400);
            fondInt.setLayoutY(250);
            ImageView backButton = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/ButtonBack.png"))));
            backButton.setPreserveRatio(true);
            backButton.setLayoutX(700);
            backButton.setLayoutY(600);
            backButton.setFitHeight(100);
            backButton.setOnMouseEntered(event1 -> imageChanger(backButton,"Sprites/ButtonBackLight.png"));
            backButton.setOnMouseExited(event1 -> imageChanger(backButton,"Sprites/ButtonBack.png"));
            backButton.setOnMouseClicked(event1 -> pane.getChildren().remove(pane.getChildren().size()-4,pane.getChildren().size()));
            Label label = new Label(text);
            Font font = new Font("System Bold Italic",50);
            label.setFont(font);
            label.setTextFill(Paint.valueOf("#000000"));
            label.setUnderline(true);
            label.setAlignment(Pos.CENTER);
            label.setWrapText(true);
            label.setPrefWidth(700);
            label.setPrefHeight(350);
            label.setLayoutX(450);
            label.setLayoutY(250);
            pane.getChildren().addAll(rectangle,fondInt,label,backButton);
        });
    }

    /**
     *
     * @param pane
     * @param button
     * @param blockChosen
     */
    protected void selectBlock(Pane pane, Button button, ArrayList<Class<ImageBlock>> blockChosen) throws ClassNotFoundException {
        Class blockChosed = Class.forName("code.projetinfo.normalBlocks." + button.getId());
        blockChosen.add(blockChosed);
        Rectangle rectangle = new Rectangle(button.getLayoutX(),button.getLayoutY(),button.getWidth(),button.getHeight());
        rectangle.setOpacity(0.2);
        rectangle.setFill(Paint.valueOf("#00ff00"));
        pane.getChildren().add(rectangle);
        System.out.println(blockChosed);
        rectangle.setOnMouseClicked(event -> {
            pane.getChildren().remove(rectangle);
            blockChosen.remove(blockChosed);
            System.out.println(blockChosen.toArray().length);

        });

    }

}
