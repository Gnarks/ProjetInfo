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



public class ControllerParent {
    /**
     * Change the sprite of an ImageView and set up an action
     * that load another scene when we click on it
     *
     * @param button imageview which serves as a button
     * @param sprite lighter sprite to set on the button
     * @param nextScene scene to load after clicking on the button
     */
    public void onButtonEntered(ImageView button, String sprite, String nextScene){
        button.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
        button.setOnMouseClicked(event -> loadScene(nextScene,event));
    }

    /**
     * Change the sprite of an ImageView
     *
     * @param imageView imageview we want to change the sprite
     * @param sprite sprite to set on the imageview
     */
    public void imageChanger(ImageView imageView, String sprite){
        imageView.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
    }

    /**
     * In a level, load the pause menu via a chosen button
     *
     * @param pane scene's pane
     * @param levelHandler levelHandler of the level which is played
     * @param level level which is played
     * @param levelName levelName of the level
     * @param event mouseEvent when the player click on the pause button
     */
    public void pauseMenu(Pane pane, LevelHandler levelHandler, Level level,String levelName,Event event){

            if(!levelHandler.getVictoryState()){
                Rectangle rectangle = new Rectangle(1600, 900, Paint.valueOf("#000000"));
                rectangle.setOpacity(0.3);
                ImageView menuPause = createImageView("Sprites/FondChoser.png",600,500,150);
                ImageView save = createImageView("Sprites/SaveAndQuitButton.png",400,600,375);
                ImageView leave = createImageView("Sprites/LeaveButton.png",400,600,525);
                ImageView resume = createImageView("Sprites/ResumeButton.png", 400,600,225);

                resume.setOnMouseEntered(resumeEvent -> imageChanger(resume,"Sprites/ResumeButtonLight.png"));
                resume.setOnMouseExited(resumeEvent -> imageChanger(resume,"Sprites/ResumeButton.png"));
                resume.setOnMouseClicked(resumeEvent -> pane.getChildren().remove(pane.getChildren().size()-5,pane.getChildren().size()));

                leave.setOnMouseEntered(leaveEvent -> imageChanger(leave,"Sprites/LeaveButtonLight.png"));
                leave.setOnMouseExited(leaveEvent -> imageChanger(leave,"Sprites/LeaveButton.png"));
                leave.setOnMouseClicked(leaveEvent -> {
                    if(!levelHandler.isRandom()){
                       if (levelName.charAt(0)=='R'){
                        loadScene("MenuMods.fxml",event);
                    }
                        else if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                        loadScene("LevelSelector1to10.fxml",event);
                    }
                        else {
                        loadScene("LevelSelector11to20.fxml",event);
                    }}
                    else{
                        loadScene("RandomLevelGenerator.fxml",event);}
                });

                save.setOnMouseEntered(saveEvent -> imageChanger(save,"Sprites/SaveAndQuitButtonLight.png"));
                save.setOnMouseExited(saveEvent -> imageChanger(save,"Sprites/SaveAndQuitButton.png"));
                save.setOnMouseClicked(saveEvent -> {
                    if(!levelHandler.isRandom()){
                    try {
                        level.saveState();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                        if (levelName.charAt(0)=='R'){
                            loadScene("MenuMods.fxml",event);
                        }
                        else if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                            loadScene("LevelSelector1to10.fxml",event);
                        }
                        else {
                            loadScene("LevelSelector11to20.fxml",event);
                    }}
                    else{
                        saveMenu(pane,level);
                    }
                });

                pane.getChildren().addAll(rectangle,menuPause,save,leave,resume);
            }

            else{
                if(!levelHandler.isRandom()){
                    if (levelName.charAt(0)=='R'){
                        loadScene("RandomMenu.fxml",event);
                    }
                    else if (levelName.charAt(5) == '0' || (levelName.charAt(5) == '1' && levelName.charAt(6) == '0')) {
                        loadScene("LevelSelector1to10.fxml",event);
                    }
                    else {
                        loadScene("LevelSelector11to20.fxml",event);
                    }}
                else{
                    loadScene("RandomLevelGenerator.fxml",event);}
            }
    }

    private void saveButton(ImageView imageview,String sprite, String name,Level levelSaved){
        imageview.setOnMouseEntered(event -> {
            imageChanger(imageview,sprite);

            imageview.setOnMouseClicked(event1 -> {
                levelSaved.setName(name);
                try {
                    levelSaved.saveState();
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
                finally {
                    ControllerParent.loadScene("RandomLevelGenerator.fxml",event1);
                }
            });

        });
    }
    public void saveMenu(Pane pane, Level levelSaved){
        Rectangle rectangle = new Rectangle(1600,900, Paint.valueOf("#222222"));
        ImageView fond = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/FondChoser.png"))));
        fond.setPreserveRatio(true);
        fond.setLayoutX(500);
        fond.setLayoutY(150);
        fond.setFitWidth(600);

        ImageView randomSave1 = createImageView("Sprites/Random1Button.png",500,550,200);
        saveButton(randomSave1,"Sprites/Random1ButtonLight.png","RandomLevel1",levelSaved);
        randomSave1.setOnMouseExited(event -> imageChanger(randomSave1,"Sprites/Random1Button.png"));


        ImageView randomSave2 = createImageView("Sprites/Random2Button.png",500,550,375);
        saveButton(randomSave2,"Sprites/Random2ButtonLight.png","RandomLevel2",levelSaved);
        randomSave2.setOnMouseExited(event -> imageChanger(randomSave2,"Sprites/Random2Button.png"));


        ImageView randomSave3 = createImageView("Sprites/Random3Button.png",500,550,550);
        saveButton(randomSave3,"Sprites/Random3ButtonLight.png","RandomLevel3",levelSaved);
        randomSave3.setOnMouseExited(event -> imageChanger(randomSave3,"Sprites/Random3Button.png"));

        pane.getChildren().addAll(rectangle,fond,randomSave1,randomSave2,randomSave3);


    }

    /**
     * Make a button load a level via it's name
     *
     * @param pane pane of the Selector Scene
     * @param levelName name of the level we want to load
     * @param button button we want to assign the action
     */
    public void LevelSelect(Pane pane, String levelName, Node button){
        button.setOnMouseClicked(event -> {
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
     * Method that load a scene via an event chosen
     *
     * @param nextScene scene we want to load
     * @param event event chosen
     */
    public static void loadScene(String nextScene, Event event){
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
     * Create an ImageView with a position and a length chosen
     *
     * @param image ImageView's sprite
     * @param width ImageView's width
     * @param layoutX ImageView's position on X
     * @param layoutY ImageView's position on Y
     * @return the ImageView with all its properties
     */
    public ImageView createImageView(String image,double width,double layoutX,double layoutY){
        ImageView imageView = new ImageView(new Image(String.valueOf(AppMenu.class.getResource(image))));
       imageView.setPreserveRatio(true);
       imageView.setFitWidth(width);
       imageView.setLayoutX(layoutX);
       imageView.setLayoutY(layoutY);
       return imageView;
    }

    /**
     * Show a screen with information about an element via an ImageView's action
     *
     * @param pane pane of the Scene in which the ImageView is
     * @param askingButton ImageView that will activate the event
     * @param text information we want to show
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
     * For the Random Menu, to choose the block we want to have in our level generated
     *
     * @param pane pane of the Scene
     * @param button Button of the block we want to add in the list
     * @param blockChosen List of the blocks already chosen
     */
    protected void selectBlock(Pane pane, Button button, ArrayList<Class<ImageBlock>> blockChosen) throws ClassNotFoundException {
        Class<?> block = Class.forName("code.projetinfo.normalBlocks." + button.getId());
        blockChosen.add((Class<ImageBlock>) block);
        Rectangle rectangle = new Rectangle(button.getLayoutX(),button.getLayoutY(),button.getWidth(),button.getHeight());
        rectangle.setOpacity(0.2);
        rectangle.setFill(Paint.valueOf("#00ff00"));
        pane.getChildren().add(rectangle);
        rectangle.setOnMouseClicked(event -> {
            pane.getChildren().remove(rectangle);
            blockChosen.remove(block);
        });

    }

}
