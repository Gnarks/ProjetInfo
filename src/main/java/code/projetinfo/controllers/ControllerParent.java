package code.projetinfo.controllers;

import code.projetinfo.*;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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


/**
 * The general Controller.
 */
public class ControllerParent {



    /**
     * Change the sprite of an ImageView and set up an action
     * that load another scene when we click on it.
     *
     * @param button imageview which serves as a button.
     * @param sprite lighter sprite to set on the button.
     * @param nextScene scene to load after clicking on the button.
     */
    protected void onButtonEntered(ImageView button, String sprite, String nextScene){
        button.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
        button.setOnMouseClicked(event -> loadScene(nextScene,event));
    }

    /**
     * Change the sprite of an ImageView.
     *
     * @param imageView imageview we want to change the sprite
     * @param sprite sprite to set on the imageview
     */
    static public void imageChanger(ImageView imageView, String sprite){
        imageView.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
    }

    /**
     * In a level, load the pause menu via a chosen button
     *
     * @param pane scene's pane
     * @param levelHandler levelHandler of the level which is played
     * @param level level which is played
     * @param event mouseEvent when the player click on the pause button
     */
    protected void pauseMenu(Pane pane, LevelHandler levelHandler, Level level,Event event){
            if(!levelHandler.getVictoryState()){
                Rectangle rectangle = new Rectangle(1600, 900, Paint.valueOf("#000000"));
                rectangle.setOpacity(0.3);
                ImageView menuPause = createImageView("Sprites/BackGround_Choices.png",600,500,150);


                ImageView resume = createImageView("Sprites/Button_Resume.png", 400,600,225);
                setAsResumeButton(resume,pane);

                ImageView save = createImageView("Sprites/Button_SaveAndQuit.png",400,600,375);

                if (level.getName().charAt(0) == 'G'){
                    save.setOnMouseEntered(saveEvent -> imageChanger(save,"Sprites/Button_SaveAndQuit_Light.png"));
                    save.setOnMouseExited(saveEvent -> imageChanger(save,"Sprites/Button_SaveAndQuit.png"));
                    save.setOnMouseClicked(randomSaveMenu -> randomLevelsSaveMenu(pane,level));
                }
                else{
                    setAsSaveButton(save,"Sprites/Button_SaveAndQuit.png", level.getName(), level);
                }


                ImageView leave = createImageView("Sprites/Button_Leave.png",400,600,525);
                setAsExitButton(leave,level);

                pane.getChildren().addAll(rectangle,menuPause,save,leave,resume);
            }

            else{
                if (level.getName().charAt(0) == 'G'){
                    loadScene("RandomLevelGenerator.fxml",event);

                }

                else if (level.getName().charAt(0) == 'C'){
                    loadScene("CreatedLevelSelector.fxml",event);
                }
                else if (level.getName().charAt(0) == 'R'){
                    loadScene("MenuRandom.fxml",event);
                }
                else if (level.getName().charAt(5) == '0' || (level.getName().charAt(5) == '1'
                        && level.getName().charAt(6) == '0')) {
                    loadScene("LevelSelector1to10.fxml",event);
                }
                else {
                    loadScene("LevelSelector11to20.fxml",event);
                }
            }
    }

    /** Sets the specified button as a resume button.
     *
     * @param resume The ImageView of the resume button.
     * @param pane The pane to clear the buttons when resume is clicked.
     */
    private void setAsResumeButton(ImageView resume, Pane pane){
        resume.setOnMouseEntered(resumeEvent -> imageChanger(resume,"Sprites/Button_Resume_Light.png"));
        resume.setOnMouseExited(resumeEvent -> imageChanger(resume,"Sprites/Button_Resume.png"));
        resume.setOnMouseClicked(resumeEvent -> pane.getChildren().remove(pane.getChildren().size()-5,pane.getChildren().size()));
    }


    /** Sets the specified button as a save button.
     *
     * @param save The ImageView of the save button.
     * @param sprite The url to the basic Sprite of the button.
     * @param nameToSave Name to save in the json file.
     * @param levelSaved The level to save when clicked.
     */
    static private void setAsSaveButton(ImageView save, String sprite, String nameToSave, Level levelSaved){
        LevelCreator.blocksCounter = 0;
        save.setOnMouseEntered(saveEvent -> imageChanger(save,sprite.substring(0,sprite.length() - 4) + "_Light.png"));
        save.setOnMouseExited(saveEvent -> imageChanger(save,sprite));
        save.setOnMouseClicked(saveEvent -> {
            levelSaved.setName(nameToSave);
            try {
                if (levelSaved.getBlocks().length == levelSaved.getPlaced())
                {
                    for (ImageBlock imageBlock: levelSaved.getBlocks()
                    ) {
                        if (imageBlock.getPlacedState()){
                            imageBlock.setPlaced(false);
                            imageBlock.setPosition(imageBlock.getSpawnPos());
                        }
                    }
                }
                levelSaved.saveState();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            finally {
                ControllerOptions.setMediaPlayerMenu();
                if (nameToSave.charAt(0) == 'R'){
                    loadScene("MenuRandom.fxml",saveEvent);
                }
                else if (nameToSave.charAt(0) == 'C'){
                    loadScene("CreatedLevelSelector.fxml",saveEvent);
                }
                else if (nameToSave.charAt(5) == '0' || (nameToSave.charAt(5) == '1'
                        && nameToSave.charAt(6) == '0')) {
                    loadScene("LevelSelector1to10.fxml",saveEvent);
                } else {
                    loadScene("LevelSelector11to20.fxml",saveEvent);
                }
            }
        });
    }


    /** Sets the specified button as the exit button.
     *
     * @param imageView The ImageView of the exit button.
     * @param level The current level being played.
     */
    private void setAsExitButton(ImageView imageView,Level level){
        imageView.setOnMouseEntered(leaveEvent -> imageChanger(imageView,"Sprites/Button_Leave_Light.png"));
        imageView.setOnMouseExited(leaveEvent -> imageChanger(imageView,"Sprites/Button_Leave.png"));
        imageView.setOnMouseClicked(leaveEvent -> {
            ControllerOptions.setMediaPlayerMenu();
            if (level.getName().charAt(0)=='G'){
                loadScene("RandomLevelGenerator.fxml",leaveEvent);
            }
            else if (level.getName().charAt(0) == 'C'){
                loadScene("CreatedLevelSelector.fxml",leaveEvent);
            }
            else if (level.getName().charAt(0) == 'R'){
                loadScene("RandomLevelSelector.fxml",leaveEvent);
            }
            else if (level.getName().charAt(5) == '0' || (level.getName().charAt(5) == '1'
                    && level.getName().charAt(6) == '0')) {
                loadScene("LevelSelector1to10.fxml",leaveEvent);
            } else {
                loadScene("LevelSelector11to20.fxml",leaveEvent);
            }
        });
    }

    /** Draws the menu to save random level that had just been generated.
     *  Permits to select between 3 save slots.
     *
     * @param pane The pane to draw the saveMenu on.
     * @param levelSaved The level ro be saved in the save slots from 1 to 3.
     */
    static  public void  randomLevelsSaveMenu(Pane pane, Level levelSaved){
        Rectangle rectangle = new Rectangle(1600,900, Paint.valueOf("#222222"));
        rectangle.setOpacity(0.3);
        ImageView backGround = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/BackGround_Choices.png"))));
        backGround.setPreserveRatio(true);
        backGround.setLayoutX(500);
        backGround.setLayoutY(150);
        backGround.setFitWidth(600);


        ImageView randomSave1 = createImageView("Sprites/Button_Random1.png",500,550,200);
        setAsSaveButton(randomSave1,"Sprites/Button_Random1.png","RandomLevel1",levelSaved);
        randomSave1.setOnMouseExited(event -> imageChanger(randomSave1,"Sprites/Button_Random1.png"));


        ImageView randomSave2 = createImageView("Sprites/Button_Random2.png",500,550,375);
        setAsSaveButton(randomSave2,"Sprites/Button_Random2.png","RandomLevel2",levelSaved);
        randomSave2.setOnMouseExited(event -> imageChanger(randomSave2,"Sprites/Button_Random2.png"));


        ImageView randomSave3 = createImageView("Sprites/Button_Random3.png",500,550,550);
        setAsSaveButton(randomSave3,"Sprites/Button_Random3.png","RandomLevel3",levelSaved);
        randomSave3.setOnMouseExited(event -> imageChanger(randomSave3,"Sprites/Button_Random3.png"));

        ImageView leaveSave = createImageView("Sprites/Button_Arrow_Left.png",150,725,700);
        leaveSave.setOnMouseEntered(event -> imageChanger(leaveSave,"Sprites/Button_Arrow_LeftLight.png"));
        leaveSave.setOnMouseExited(event ->imageChanger(leaveSave,"Sprites/Button_Arrow_Left.png"));
        leaveSave.setOnMouseClicked(event -> pane.getChildren().removeAll(leaveSave,rectangle,backGround,randomSave1,randomSave2,randomSave3));

        pane.getChildren().addAll(rectangle,backGround,randomSave1,randomSave2,randomSave3,leaveSave);
    }

    static  public void  CreatedLevelsSaveMenu(Pane pane, Level levelSaved){
        Rectangle rectangle = new Rectangle(1600,900, Paint.valueOf("#222222"));
        rectangle.setOpacity(0.3);
        ImageView backGround = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/BackGround_Choices.png"))));
        backGround.setPreserveRatio(true);
        backGround.setLayoutX(500);
        backGround.setLayoutY(150);
        backGround.setFitWidth(600);


        ImageView createSave1 = createImageView("Sprites/Button_Created1.png",500,550,200);
        setAsSaveButton(createSave1,"Sprites/Button_Created1.png","CreatedLevel1",levelSaved);
        createSave1.setOnMouseExited(event -> imageChanger(createSave1,"Sprites/Button_Created1.png"));


        ImageView createSave2 = createImageView("Sprites/Button_Created2.png",500,550,375);
        setAsSaveButton(createSave2,"Sprites/Button_Created2.png","CreatedLevel2",levelSaved);
        createSave2.setOnMouseExited(event -> imageChanger(createSave2,"Sprites/Button_Created2.png"));


        ImageView createSave3 = createImageView("Sprites/Button_Created3.png",500,550,550);
        setAsSaveButton(createSave3,"Sprites/Button_Created3.png","CreatedLevel3",levelSaved);
        createSave3.setOnMouseExited(event -> imageChanger(createSave3,"Sprites/Button_Created3.png"));

        ImageView leaveSave = createImageView("Sprites/Button_Arrow_Left.png",150,725,700);
        leaveSave.setOnMouseEntered(event -> imageChanger(leaveSave,"Sprites/Button_Arrow_LeftLight.png"));
        leaveSave.setOnMouseExited(event ->imageChanger(leaveSave,"Sprites/Button_Arrow_Left.png"));
        leaveSave.setOnMouseClicked(event -> pane.getChildren().removeAll(leaveSave,rectangle,backGround,createSave1,createSave2,createSave3));

        pane.getChildren().addAll(rectangle,backGround,createSave1,createSave2,createSave3,leaveSave);
    }
    /**
     * Make a button load a level via it's name
     *
     * @param pane pane of the Selector Scene
     * @param levelName name of the level we want to load
     * @param button button we want to assign the action
     */
    protected void LevelSelect(Pane pane, String levelName, Node button){
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
    static public ImageView createImageView(String image,double width,double layoutX,double layoutY){
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
}
