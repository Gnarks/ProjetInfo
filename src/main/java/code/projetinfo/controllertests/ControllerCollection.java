package code.projetinfo.controllertests;

import code.projetinfo.AppDraggable;
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
        ButtonBack.setImage(new Image("code/projetinfo/Sprites/ButtonBackLight.png"));

        ButtonBack.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppDraggable.class.getResource("MainMenu.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1080, 607.5);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.show();
        });
    }
    @FXML
    protected void onButtonBackExited(){
        ButtonBack.setImage(new Image("code/projetinfo/Sprites/ButtonBack.png"));
    }
    @FXML
    protected void onButtonNextEntered(){
        ButtonNext.setImage(new Image("code/projetinfo/Sprites/ButtonNextLight.png"));
        ButtonNext.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppDraggable.class.getResource("Credits.fxml"));
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1080, 607.5);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.show();
        });
    }
    @FXML
    protected void onButtonNextExited(){
        ButtonNext.setImage(new Image("code/projetinfo/Sprites/ButtonNext.png"));
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
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostS3x2_Rotation0.png"),300,200,440,150);
        setGhostName("Plague Doc",460);
        GhostDescription.setText("He fought the plague with courage but he lost...");
    }
    @FXML
    protected void ScooboodooPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostJ2x2_Rotation0.png"),150,150,465,125);
        setGhostName("Scooboodoo",450);
        GhostDescription.setText("Has resolved as much mystery than things he ate(a lot).");
    }
    @FXML
    protected void NapstaPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost1x3_Rotation0.png"),210,70,505,100);
        setGhostName("Napsta",500);
        GhostDescription.setText("Didn't have a crazy life but has always chilled in music.");

    }
    @FXML
    protected void BigBobPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost2x3_Rotation0.png"),200,200,475,100);
        setGhostName("Big Bob",480);
        GhostDescription.setText("Bob's brother. Just an advice, DON'T hurt Bob.");

    }
    @FXML
    protected void BobPressed() {
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost1x2_Rotation0.png"),160,80,500,125);
        setGhostName("Bob",520);
        GhostDescription.setText("He is more scared of you than you of him.");
    }
    @FXML
    protected void AmogousPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostL2x2_Rotation0.png"),150,150,465,125);
        setGhostName("Amogous",475);
        GhostDescription.setText("Has been ejected from a spaceship for being too lazy to do tasks.");
    }
    @FXML
    protected void BooBellePressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostBigL3x3_Rotation0.png"),150,150,465,125);
        setGhostName("BooBelle",480);
        GhostDescription.setText("Was homeless during his life by choice cause he truly loves trashes");

    }
    @FXML
    protected void ToowelsPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostL2x4_Rotation0.png"),160,80,500,125);
        setGhostName("Toowels",480);
        GhostDescription.setText("So stupid that he is stuck in a towel since 1983.");
    }
    @FXML
    protected void LilDeathPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostJ2x3_Rotation0.png"),200,200,475,100);
        setGhostName("Lil Death",475);
        GhostDescription.setText("The son of the Death but he prefers puzzles than kill people.");
    }
    @FXML
    protected void VicKingPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost3x3_Rotation0.png"),150,150,465,125);
        setGhostName("Vic King", 480);
        GhostDescription.setText("He is very brutal but fortunately too lazy to hit someone.");
    }
    @FXML
    protected void RedkyPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost2x2_Rotation0.png"),150,150,465,125);
        setGhostName("Redky",500);
        GhostDescription.setText("He really likes yellow balls but it is not mutual...");}
    @FXML
    protected void BabyPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost1x1_Rotation0.png"),150,150,465,125);
        setGhostName("Baby",510);
        GhostDescription.setText("He is little, he is a baby. That's it");
    }
    @FXML
    protected void BlobyPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost2x2TopLeft_Rotation0.png"),200,200,475,100);
        setGhostName("Bloby",505);
        GhostDescription.setText("Too cute to scare someone.");
    }
    @FXML
    protected void NessyPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost2x2TopRight_Rotation0.png"),200,200,475,100);
        setGhostName("Nessy",505);
        GhostDescription.setText("He is the oldest ghost known and live somewhere in Scotland.");
    }
    @FXML
    protected void KingPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost2x4_Rotation0.png"),160,80,500,125);
        setGhostName("King",515);
        GhostDescription.setText("Can't do anything by himself but he is the king so he has an excuse.");
    }
    @FXML
    protected void GeoffreyPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost2x6_Rotation0.png"),210,70,505,100);
        setGhostName("Geoffrey",475);
        GhostDescription.setText("A wealthy old man, probably created a crisps brand");
    }
    @FXML
    protected void BigBrooPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostC3x2_Rotation0.png"),300,200,440,150);
        setGhostName("Big Broo",475);
        GhostDescription.setText("Always at the gym but somehow skips every leg days.");
    }
    @FXML
    protected void PhantomPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_GhostBigJ3x3_Rotation0.png"),150,150,465,125);
        setGhostName("Phantom",475);
        GhostDescription.setText("Was a great opera singer but a spot fell on his head during a show.");
    }
    @FXML
    protected void WolfyPressed(){
        setGhostImage(new Image("code/projetinfo/Sprite_Ghosts/Sprite_Ghost3x32TopRight_Rotation0.png"),200,200,460,100);
        setGhostName("Wolfy",505);
        GhostDescription.setText("Hunter who literally put himself in the shoes of his prey");
    }





}