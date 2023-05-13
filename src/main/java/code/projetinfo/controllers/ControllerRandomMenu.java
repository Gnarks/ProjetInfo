package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerRandomMenu extends ControllerParent implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private ImageView ButtonBack;
    @FXML
    private Label maximum;
    @FXML
    private ImageView maximumIncrease;
    @FXML
    private ImageView maximumDecrease;
    @FXML
    private Label minimum;
    @FXML
    private ImageView minimumIncrease;
    @FXML
    private ImageView minimumDecrease;
    @FXML
    private Circle AlwaysDifferent;
    @FXML
    private Circle FullRandom;

    private boolean alwaysDifferentState = false;

    private boolean fullRandomState = true;

    @FXML
    protected void onBackEntered(){
        onButtonEntered(ButtonBack,"Sprites/ButtonBackToMenulight.png","MenuPlay.fxml");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(ButtonBack,"Sprites/ButtonBackToMenu.png");
    }

    @FXML
    protected void onMinIncEntered(){
        imageChanger(minimumIncrease,"Sprites/ButtonNextLight.png");
    }

    @FXML
    protected void onMinIncExited(){ imageChanger(minimumIncrease,"Sprites/ButtonNext.png");
    }
    @FXML
    protected void onMinDecEntered(){
        imageChanger(minimumDecrease,"Sprites/ButtonBackLight.png");
    }
    @FXML
    protected void onMinDecExited(){
        imageChanger(minimumDecrease,"Sprites/ButtonBack.png");
    }
    @FXML
    protected void onMaxIncEntered(){
        imageChanger(maximumIncrease,"Sprites/ButtonNextLight.png");
    }
    @FXML
    protected void onMaxIncExited(){

        imageChanger(maximumIncrease,"Sprites/ButtonNext.png");
    }


    @FXML
    protected void onMaxDecEntered(){
        imageChanger(maximumDecrease,"Sprites/ButtonBackLight.png");
    }

    @FXML
    protected void onMaxDecExited(){
        imageChanger(maximumDecrease,"Sprites/ButtonBack.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass() == Button.class){
                int finalI = i;
                pane.getChildren().get(i).setOnMouseClicked(event->{
                    selectBlock(pane,(Button)pane.getChildren().get(finalI),alwaysDifferentState);
                    this.fullRandomState = false;
                    FullRandom.setFill(Paint.valueOf("#ff0000"));
                });}
        }

        AlwaysDifferent.setOnMouseClicked(event -> {
            if(AlwaysDifferent.getFill().equals(Paint.valueOf("#ff0000"))){
                AlwaysDifferent.setFill(Paint.valueOf("#00ff00"));
                FullRandom.setFill(Paint.valueOf("#ff0000"));
                this.alwaysDifferentState = true;
                this.fullRandomState = false;

            }
            else if(AlwaysDifferent.getFill().equals(Paint.valueOf("#00ff00"))){
                AlwaysDifferent.setFill(Paint.valueOf("#ff0000"));
                this.alwaysDifferentState = false;
            }
        });


        FullRandom.setOnMouseClicked(event -> {
            if(FullRandom.getFill().equals(Paint.valueOf("#ff0000"))){
                FullRandom.setFill(Paint.valueOf("#00ff00"));
                AlwaysDifferent.setFill(Paint.valueOf("#ff0000"));
                this.alwaysDifferentState = false;
                this.fullRandomState = true;
                ArrayList<Rectangle> rectangles = new ArrayList<>();
                for (int i = 0; i < pane.getChildren().size(); i++) {
                    System.out.println(i);
                    if(pane.getChildren().get(i).getClass()== Rectangle.class){
                        rectangles.add((Rectangle) pane.getChildren().get(i));
                    }
                }
                for (int i = 0; i < rectangles.toArray().length ; i++) {

                    pane.getChildren().remove(rectangles.get(i));

                }

            }
            else if(FullRandom.getFill().equals(Paint.valueOf("#00ff00"))){
                FullRandom.setFill(Paint.valueOf("#ff0000"));
                fullRandomState = false;
            }
        });






        minimumIncrease.setOnMouseClicked(event ->{
            if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                minimumIncrease.setOpacity(0);
                maximumDecrease.setOpacity(0);
            }

            else if(Integer.parseInt(minimum.getText())==11){
                minimumIncrease.setOpacity(0);
                maximumDecrease.setOpacity(0);
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())+1));
            }
            else if(Integer.parseInt(minimum.getText())==2){
                minimumDecrease.setOpacity(1);

                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())+1));
                if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
            else if(Integer.parseInt(minimum.getText())<11){
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())+1));

                if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
        });
        minimumDecrease.setOnMouseClicked(event ->{
            if(Integer.parseInt(minimum.getText())==3){
                minimumDecrease.setOpacity(0);
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())-1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
            else if(Integer.parseInt(minimum.getText())==12){
                minimumIncrease.setOpacity(1);

                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())-1));
                minimumIncrease.setOpacity(1);
                maximumDecrease.setOpacity(1);

            }
            else if(Integer.parseInt(minimum.getText())>3){
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())-1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
        });




        maximumIncrease.setOnMouseClicked(event ->{

            if(Integer.parseInt(maximum.getText())==11){
                maximumIncrease.setOpacity(0);
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())+1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
            else if(Integer.parseInt(maximum.getText())==2){
                maximumDecrease.setOpacity(1);
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())+1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
            else if(Integer.parseInt(maximum.getText())<12){
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())+1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
        });

        maximumDecrease.setOnMouseClicked(event ->{
            if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                minimumIncrease.setOpacity(0);
                maximumDecrease.setOpacity(0);
            }
            else if(Integer.parseInt(maximum.getText())==3){
                maximumDecrease.setOpacity(0);
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())-1));
                if (Integer.parseInt(maximum.getText())==Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
            else if(Integer.parseInt(maximum.getText())==12){
                maximumIncrease.setOpacity(1);

                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())-1));
                if (Integer.parseInt(maximum.getText())==Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }

            }
            else if(Integer.parseInt(maximum.getText())>3){
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())-1));

                if (Integer.parseInt(maximum.getText())!=Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
                else if (Integer.parseInt(maximum.getText())==Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
        });
    }
}
