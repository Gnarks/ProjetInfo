package code.projetinfo.controllertests;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRandom extends ControllerParent implements Initializable {

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
    private Circle OnlySame;
    @FXML
    private Circle FullRandom;

    @FXML
    protected void onBackEntered(){
        onButtonEntered(ButtonBack,"Sprites/ButtonBackToMenulight.png","MenuPlay.fxml");
    }

    @FXML
    protected void onBackExited(){
        buttonImageChanger(ButtonBack,"Sprites/ButtonBackToMenu.png");
    }

    @FXML
    protected void onMinIncEntered(){
        buttonImageChanger(minimumIncrease,"Sprites/ButtonNextLight.png");
    }

    @FXML
    protected void onMinIncExited(){
        buttonImageChanger(minimumIncrease,"Sprites/ButtonNext.png");
    }
    @FXML
    protected void onMinDecEntered(){
        buttonImageChanger(minimumDecrease,"Sprites/ButtonBackLight.png");
    }
    @FXML
    protected void onMinDecExited(){
        buttonImageChanger(minimumDecrease,"Sprites/ButtonBack.png");
    }
    @FXML
    protected void onMaxIncEntered(){
        buttonImageChanger(maximumIncrease,"Sprites/ButtonNextLight.png");
    }
    @FXML
    protected void onMaxIncExited(){

        buttonImageChanger(maximumIncrease,"Sprites/ButtonNext.png");
    }


    @FXML
    protected void onMaxDecEntered(){
        buttonImageChanger(maximumDecrease,"Sprites/ButtonBackLight.png");
    }

    @FXML
    protected void onMaxDecExited(){
        buttonImageChanger(maximumDecrease,"Sprites/ButtonBack.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        OnlySame.setOnMouseClicked(event -> {
            if(OnlySame.getFill().equals(Paint.valueOf("#ff0000"))){
                OnlySame.setFill(Paint.valueOf("#00ff00"));
                FullRandom.setFill(Paint.valueOf("#ff0000"));
            }
            else if(OnlySame.getFill().equals(Paint.valueOf("#00ff00"))){
                OnlySame.setFill(Paint.valueOf("#ff0000"));
            }
        });
        FullRandom.setOnMouseClicked(event -> {
            if(FullRandom.getFill().equals(Paint.valueOf("#ff0000"))){
                FullRandom.setFill(Paint.valueOf("#00ff00"));
                OnlySame.setFill(Paint.valueOf("#ff0000"));
            }
            else if(FullRandom.getFill().equals(Paint.valueOf("#00ff00"))){
                FullRandom.setFill(Paint.valueOf("#ff0000"));
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
