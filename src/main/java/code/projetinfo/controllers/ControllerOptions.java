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

    private static final Media media = new Media(String.valueOf(AppMenu.class.getResource("Game_Theme.mp3")));

    private static MediaPlayer mediaPlayer;

    public static void setMusic(){

        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0);
            mediaPlayer.play();
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
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
        if(mediaPlayer.getVolume()==0){
            volume.setText("0");
            decreaseSound.setOpacity(0);
        }

        if(mediaPlayer.getVolume()==1){
            volume.setText("10");
            increaseSound.setOpacity(0);
        }

        volume.setText(String.valueOf((int)(mediaPlayer.getVolume()*10)));

        increaseSound.setOnMouseClicked(event ->{
            if(Integer.parseInt(volume.getText())==9){
                increaseSound.setOpacity(0);
            }
            if(Integer.parseInt(volume.getText())==0){
                decreaseSound.setOpacity(1);
            }
            if(Integer.parseInt(volume.getText())<10){
                volume.setText(String.valueOf(Integer.parseInt(volume.getText())+1));
                mediaPlayer.setVolume(Math.round((mediaPlayer.getVolume()+0.1)*10) / 10.0);
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
                mediaPlayer.setVolume(Math.round((mediaPlayer.getVolume()-0.1)*10) / 10.0);
            }
        });

    }

}
