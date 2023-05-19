package code.projetinfo.controllers;

import code.projetinfo.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
        imageChanger(backButton,"Sprites/Button_GoBack_Light.png");
        backButton.setOnMouseClicked(event -> {
            loadScene("MenuCreate.fxml",event);
            LevelCreator.inventoryCounter =0;
            LevelCreator.blocksCounter=0;

        });
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



        Label label = new Label("Graveyard");
        label.setAlignment(Pos.CENTER);
        label.setTextFill(Paint.valueOf("#ffffff"));
        Font font = new Font("System Bold Italic",28);
        label.setFont(font);
        label.setLayoutY(550);
        label.setPrefWidth(500);
        label.setPrefHeight(200);
        label.setUnderline(true);

        Rectangle graveyard = new Rectangle(500,300, Paint.valueOf("#555555"));
        graveyard.setStroke(Paint.valueOf("#000000"));
        graveyard.setStrokeWidth(10);
        graveyard.setLayoutY(600);
        graveyard.setLayoutX(0);
        pane.getChildren().addAll(graveyard,label,buttonSave);

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
            Rectangle rectangle1 = new Rectangle(1600,900, Paint.valueOf("#222222"));
            rectangle1.setOpacity(0.3);
            ImageView backGround = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/BackGround_Choices.png"))));
            backGround.setPreserveRatio(true);
            backGround.setLayoutX(500);
            backGround.setLayoutY(150);
            backGround.setFitWidth(600);

            ImageView leaveSave = createImageView("Sprites/Button_Arrow_Left.png",150,725,700);
            leaveSave.setOnMouseEntered(event -> imageChanger(leaveSave,"Sprites/Button_Arrow_LeftLight.png"));
            leaveSave.setOnMouseExited(event ->imageChanger(leaveSave,"Sprites/Button_Arrow_Left.png"));

            Label labelWarning = new Label("");
            labelWarning.setAlignment(Pos.CENTER);
            labelWarning.setTextFill(Paint.valueOf("#ffffff"));
            Font fontWarning = new Font("System Bold Italic",40);
            labelWarning.setFont(fontWarning);
            labelWarning.setLayoutY(150);
            labelWarning.setLayoutX(550);
            labelWarning.setPrefWidth(500);
            labelWarning.setPrefHeight(500);
            labelWarning.setWrapText(true);

            leaveSave.setOnMouseClicked(event -> pane.getChildren().removeAll(labelWarning,leaveSave,rectangle1,backGround));

            pane.getChildren().addAll(rectangle1,backGround,labelWarning);


                if(levelCreator.canSave()){
                    labelWarning.setText("Are you sure to want to save your level?");
                    Button buttonYes = new Button("YES");
                    buttonYes.setPrefWidth(150);
                    buttonYes.setPrefHeight(100);
                    buttonYes.setLayoutY(600);
                    buttonYes.setLayoutX(600);


                    Button buttonNo = new Button("NO");
                    buttonNo.setPrefWidth(150);
                    buttonNo.setPrefHeight(100);
                    buttonNo.setLayoutY(600);
                    buttonNo.setLayoutX(900);

                    pane.getChildren().addAll(buttonYes,buttonNo);

                    buttonNo.setOnAction(event -> pane.getChildren().removeAll(rectangle1,backGround,labelWarning,buttonNo,buttonYes));
                    buttonYes.setOnAction(event ->{
                        pane.getChildren().removeAll(rectangle1,backGround,labelWarning,buttonNo,buttonYes);
                        ControllerParent.CreatedLevelsSaveMenu(pane, new Level("Created",levelCreator.gridToSave(),levelCreator.prepareBlockList()));});

                }
                else{
                    labelWarning.setText("""
                            It's seems that you can't save now. To save you have to:\s
                            -Put all the blocks in the scene in the grid
                            -Put minimum 2 blocks in the grid\s""");
                    pane.getChildren().add(leaveSave);
                }
        });

        resetButton.setOnMouseClicked(event -> levelCreator.reset());
    }
}