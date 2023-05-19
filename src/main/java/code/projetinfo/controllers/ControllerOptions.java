package code.projetinfo.controllers;

import code.projetinfo.AppMenu;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerOptions extends ControllerParent implements Initializable {

    /**Controller of all the options buttons(assigned to MenuOptions.fxml)*/
    @FXML
    private ImageView decreaseSound;

    @FXML
    private ImageView increaseSound;

    @FXML
    private ImageView buttonBack;

    @FXML
    private Label volume;

    public static double volumeValue = 0.5;

    private static final Media mediaMenu = new Media(String.valueOf(AppMenu.class.getResource("sounds/Theme_Menu.mp3")));

    private static final Media mediaLevel = new Media(String.valueOf(AppMenu.class.getResource("sounds/Theme_Level.mp3")));

    private static final Media mediaVictory = new Media(String.valueOf(AppMenu.class.getResource("sounds/Theme_Victory.mp3")));

    public static MediaPlayer mediaPlayerMenu;

    public static MediaPlayer mediaPlayerLevel;

    public static MediaPlayer mediaPlayerVictory;

    public static void setMediaPlayerMenu(){
        if(mediaPlayerVictory != null){
            mediaPlayerVictory.stop();
            mediaPlayerVictory = null;}

        if(mediaPlayerLevel!= null){
            mediaPlayerLevel.stop();
            mediaPlayerLevel = null;
        }
        if (mediaPlayerMenu == null){
            mediaPlayerMenu = new MediaPlayer(mediaMenu);
            mediaPlayerMenu.setVolume(volumeValue);
            mediaPlayerMenu.play();
            mediaPlayerMenu.setCycleCount(MediaPlayer.INDEFINITE);
        }
    }

    public static void setMediaPlayerLevel(){
        if(mediaPlayerVictory != null){
            mediaPlayerVictory.stop();
            mediaPlayerVictory = null;}

        if(mediaPlayerMenu != null){
        mediaPlayerMenu.stop();
        mediaPlayerMenu = null;}

        if (mediaPlayerLevel == null){
        mediaPlayerLevel = new MediaPlayer(mediaLevel);
        mediaPlayerLevel.setVolume(volumeValue);
        mediaPlayerLevel.play();
        mediaPlayerLevel.setCycleCount(MediaPlayer.INDEFINITE);}
    }

    public static void setMediaPlayerVictory(){
        if(mediaPlayerLevel!= null){
            mediaPlayerLevel.stop();
            mediaPlayerLevel = null;
        }

        if(mediaPlayerVictory == null){
            mediaPlayerVictory = new MediaPlayer(mediaVictory);
            mediaPlayerVictory.setVolume(volumeValue);
            mediaPlayerVictory.play();
            mediaPlayerVictory.setCycleCount(MediaPlayer.INDEFINITE);

        }

    }
    @FXML
    protected void onDecreaseEntered(){
        imageChanger(decreaseSound,"Sprites/Button_Arrow_LeftLight.png");

    }

    @FXML
    protected void onIncreaseEntered(){
        imageChanger(increaseSound,"Sprites/Button_Arrow_RightLight.png");

    }

    @FXML
    protected void onBackEntered(){
        imageChanger(buttonBack, "Sprites/Button_GoBack_Light.png");
        buttonBack.setOnMouseClicked(event -> loadScene("MainMenu.fxml",event));
    }
    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }

    @FXML
    protected void onDecreaseExited(){
        imageChanger(decreaseSound,"Sprites/Button_Arrow_Left.png");
    }

    @FXML
    protected void onIncreaseExited(){
        imageChanger(increaseSound,"Sprites/Button_Arrow_Right.png");}


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(mediaPlayerMenu.getVolume()==0){
            volume.setText("0");
            decreaseSound.setOpacity(0);
        }

        if(mediaPlayerMenu.getVolume()==1){
            volume.setText("10");
            increaseSound.setOpacity(0);
        }

        volume.setText(String.valueOf((int)(mediaPlayerMenu.getVolume()*10)));

        increaseSound.setOnMouseClicked(event ->{
            if(Integer.parseInt(volume.getText())==9){
                increaseSound.setOpacity(0);
            }
            if(Integer.parseInt(volume.getText())==0){
                decreaseSound.setOpacity(1);
            }
            if(Integer.parseInt(volume.getText())<10){
                volume.setText(String.valueOf(Integer.parseInt(volume.getText())+1));
                volumeValue =Math.round((volumeValue+0.1)*10) / 10.0;
                mediaPlayerMenu.setVolume(volumeValue);
            }
        });

        decreaseSound.setOnMouseClicked(event ->{
            if(Integer.parseInt(volume.getText())==1){
                decreaseSound.setOpacity(0);
            }
            if(Integer.parseInt(volume.getText())==10){
                increaseSound.setOpacity(1);
            }
            if(Integer.parseInt(volume.getText())>0){
                volume.setText(String.valueOf(Integer.parseInt(volume.getText())-1));

                volumeValue =Math.round((volumeValue-0.1)*10) / 10.0;
                mediaPlayerMenu.setVolume(volumeValue);
            }
        });

    }

}
