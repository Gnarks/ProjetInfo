package code.projetinfo.controllertests;

import code.projetinfo.AppGame;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import java.io.IOException;

public class ControllerCollection {

    @FXML
    private ImageView GhostImage;
    @FXML
    private ImageView ButtonBack;
    @FXML
    private ImageView ButtonNext;
    @FXML
    private Label GhostName;
    @FXML
    private Label GhostDescription;
    private Stage stage;
    private Scene scene;

    @FXML
    protected void onButtonBackEntered(){
        ButtonBack.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBackLight.png"))));

        ButtonBack.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("MainMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1600, 900);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }
    @FXML
    protected void onButtonBackExited(){
        ButtonBack.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBack.png"))));
    }
    @FXML
    protected void onButtonNextEntered(){
        ButtonNext.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNextLight.png"))));
        ButtonNext.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("Credits.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1600, 900);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });
    }
    @FXML
    protected void onButtonNextExited(){
        ButtonNext.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNext.png"))));
    }
    @FXML
    protected void setGhostImage(Image image,double height,double width,double posX,double posY){
        GhostImage.setImage(image);
        GhostImage.setFitHeight(height);
        GhostImage.setFitWidth(width);
        GhostImage.setLayoutX(posX);
        GhostImage.setLayoutY(posY);
    }
    @FXML
    protected void setGhostName(String name,double posX){
        GhostName.setText(name);
        GhostName.setLayoutX(posX);
    }


    @FXML
    protected void PlagueDocPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostS3x2_Rotation0.png"))),200,300,650,180);
        setGhostName("Plague Doc",725);
        GhostDescription.setText("It was not as useful as a covid mask.");
    }
    @FXML
    protected void ScooboodooPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostJ2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Scooboodoo",705);
        GhostDescription.setText("Has resolved as much mystery than things he ate(a lot).");
    }
    @FXML
    protected void NapstaPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost1x3_Rotation0.png"))),300,100,750,150);
        setGhostName("Napsta",750);
        GhostDescription.setText("Didn't have a crazy life but has always chilled in music.");

    }
    @FXML
    protected void BigBobPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x3_Rotation0.png"))),240,160,720,180);
        setGhostName("Big Bob",745);
        GhostDescription.setText("Bob's brother. Just an advice, DON'T hurt Bob.");

    }
    @FXML
    protected void BobPressed() {
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost1x2_Rotation0.png"))),250,125,732.5,180);
        setGhostName("Bob",780);
        GhostDescription.setText("He is more scared of you than you are of him.");
    }
    @FXML
    protected void AmogousPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostL2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Amogous",745);
        GhostDescription.setText("Has been ejected from a spaceship for being too lazy to do tasks.");
    }
    @FXML
    protected void BooBellePressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostBigL3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("BooBelle",745);
        GhostDescription.setText("Has always lived(and died) in a trash.");

    }
    @FXML
    protected void ToowelsPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostL2x4_Rotation0.png"))),250,125,732.5,180);
        setGhostName("Toowels",750);
        GhostDescription.setText("So stupid that he is stuck in a towel since 1983.");
    }
    @FXML
    protected void LilDeathPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostJ2x3_Rotation0.png"))),240,160,720,180);
        setGhostName("Lil Death",745);
        GhostDescription.setText("The son of the Death (did his father killed him ?).");
    }
    @FXML
    protected void VicKingPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("Vic King", 745);
        GhostDescription.setText("He is very brutal but fortunately too lazy to hit someone.");
    }
    @FXML
    protected void RedkyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Redky",765);
        GhostDescription.setText("He really likes yellow balls but it is not mutual...");}
    @FXML
    protected void BabyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost1x1_Rotation0.png"))),250,250,675,180);
        setGhostName("Baby",775);
        GhostDescription.setText("It's a little is a baby");
    }
    @FXML
    protected void BlobyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2TopLeft_Rotation0.png"))),240,160,720,180);
        setGhostName("Bloby",765);
        GhostDescription.setText("Too cute to scare anyone.");
    }
    @FXML
    protected void NessyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2TopRight_Rotation0.png"))),240,160,720,180);
        setGhostName("Nessy",765);
        GhostDescription.setText("He is the oldest ghost known and live somewhere in Scotland.");
    }
    @FXML
    protected void KingPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x4_Rotation0.png"))),250,125,732.5,180);
        setGhostName("King",775);
        GhostDescription.setText("Can't do anything by himself but he is the king so he has an excuse.");
    }
    @FXML
    protected void GeoffreyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x6_Rotation0.png"))),300,100,750,150);
        setGhostName("Geoffrey",740);
        GhostDescription.setText("A wealthy old man, probably created a crisps brand");
    }
    @FXML
    protected void BigBrooPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostC3x2_Rotation0.png"))),200,300,650,180);
        setGhostName("Big Broo",745);
        GhostDescription.setText("Always at the gym but somehow skips every leg day.");
    }
    @FXML
    protected void PhantomPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostBigJ3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("Phantom",745);
        GhostDescription.setText("Was a great opera singer but a spot fell on his head during a show.");
    }
    @FXML
    protected void WolfyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost3x32TopRight_Rotation0.png"))),250,187.5,706.25,180);
        setGhostName("Wolfy",765);
        GhostDescription.setText("Hunter who literally put himself in the shoes of his prey");
    }





}