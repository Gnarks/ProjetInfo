package code.projetinfo.controllers;

import code.projetinfo.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLevelCreator extends ControllerParent implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView backButton;

    @FXML
    private ImageView resetButton;



    @FXML
    protected void onBackEntered(){
        onButtonEntered(backButton,"Sprites/Button_GoBack_Light.png","MenuCreate.fxml");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(backButton,"Sprites/Button_GoBack.png");
    }

    @FXML
    protected void onResetEntered(){
        imageChanger(resetButton,"Sprites/Button_Reset_Light.png");

    }

    @FXML
    protected void onResetExited(){

        imageChanger(resetButton,"Sprites/Button_Reset.png");
    }


    @Override 
    public void initialize(URL location, ResourceBundle resources) {

        ImageView buttonSave = ControllerParent.createImageView("Sprites/Button_Save.png",250,1035,750);
        buttonSave.setOnMouseEntered(event ->ControllerParent.imageChanger(buttonSave,"Sprites/Button_Save_Light.png"));
        buttonSave.setOnMouseExited(event ->ControllerParent.imageChanger(buttonSave,"Sprites/Button_Save.png"));



        Label label = new Label("Trash");
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Paint.valueOf("#ffffff"));
        Font font = new Font("System Bold Italic",28);
        label.setFont(font);
        label.setLayoutY(550);
        label.setPrefWidth(500);
        label.setPrefHeight(200);
        label.setUnderline(true);

        Rectangle rectangle = new Rectangle(500,300, Paint.valueOf("#000000"));
        rectangle.setStroke(Paint.valueOf("#ffffff"));
        rectangle.setStrokeWidth(10);
        rectangle.setLayoutY(600);
        rectangle.setLayoutX(0);
        pane.getChildren().addAll(rectangle,label,buttonSave);

        LevelCreator levelCreator = new LevelCreator(pane);
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass() == Button.class)
            {
                int finalI = i;
                pane.getChildren().get(i).setOnMouseClicked(event -> {
                    try {
                        levelCreator.addBlock(pane.getChildren().get(finalI));
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                             InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        levelCreator.drawGrid();



        buttonSave.setOnMouseClicked(clicked ->{

                if(levelCreator.canSave()){
                    ControllerParent.CreatedLevelsSaveMenu(pane, new Level("Created",levelCreator.gridToSave(),levelCreator.prepareBlockList()));
                }
                else{
                    System.out.println("Oh no, cringe");
                }
        });

        resetButton.setOnMouseClicked(event -> levelCreator.reset());
    }
}