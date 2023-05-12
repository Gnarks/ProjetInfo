package code.projetinfo.controllers;

import code.projetinfo.AppGame;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class ControllerCollection extends ControllerParent {

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

    @FXML
    protected void onButtonBackEntered(){
        onButtonEntered(ButtonBack,"Sprites/ButtonBackLight.png","MainMenu.fxml");
    }
    @FXML
    protected void onButtonBackExited(){
        buttonImageChanger(ButtonBack,"Sprites/ButtonBack.png");
    }
    @FXML
    protected void onButtonNextEntered(){
        onButtonEntered(ButtonNext,"Sprites/ButtonNextLight.png","Credits.fxml");
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
        GhostName.setTextFill(Paint.valueOf("d0d0ff"));
        GhostDescription.setTextFill(Paint.valueOf("#d0d0ff"));
    }


    @FXML
    protected void PlagueDocPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostS3x2_Rotation0.png"))),200,300,650,180);
        setGhostName("Plague Doc",725);
        GhostDescription.setText("A well known doctor from the XIV century.\nCause of death: \nUnfortunately for him his mask wasn't as efficient as a covid mask.");
    }
    @FXML
    protected void ScooboodooPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostJ2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Scooboodoo",705);
        GhostDescription.setText("Has resolved as much mystery than things he ate(a lot).\nCause of death: \nSuffocated in scooby snacks.");
    }
    @FXML
    protected void NapstaPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost1x3_Rotation0.png"))),300,100,750,150);
        setGhostName("Napsta",750);
        GhostDescription.setText("Didn't have a crazy life but has always chilled in music.\nCause of death: \nListened too much sad music.");

    }
    @FXML
    protected void BigBobPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x3_Rotation0.png"))),240,160,720,180);
        setGhostName("Big Bob",745);
        GhostDescription.setText("Bob's (BIG) brother. \nCause of death: \nTried to open a coconut with his head.");

    }
    @FXML
    protected void BobPressed() {
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost1x2_Rotation0.png"))),250,125,732.5,180);
        setGhostName("Bob",780);
        GhostDescription.setText("He is more scared of you than you are of him.\nCause of death: \nHad a heart attack from his hamster sneezing.");
    }
    @FXML
    protected void AmogousPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostL2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Amogous",745);
        GhostDescription.setText("A crew member of the Skeld.\nCause of death: \nGot ejected from the spaceship for being too lazy to do tasks.");
    }
    @FXML
    protected void BooBellePressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostBigL3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("BooBelle",745);
        GhostDescription.setText("Has always lived in a trashcan.\nCause of death: \nIt was (maybe) unhealthy to live there.");

    }
    @FXML
    protected void ToowelsPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostL2x4_Rotation0.png"))),250,125,732.5,180);
        setGhostName("Toowels",750);
        GhostDescription.setText("So stupid that he is stuck in a towel since 1983.\nCause of death: \nSlipped on a banana.");
    }
    @FXML
    protected void LilDeathPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostJ2x3_Rotation0.png"))),240,160,720,180);
        setGhostName("Lil Death",745);
        GhostDescription.setText("The little son of the Death.\nCause of death: \nUnknown (did his father killed him ?).");
    }
    @FXML
    protected void VicKingPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("Vic King", 745);
        GhostDescription.setText("He is very brutal but fortunately too lazy to hit someone.\nCause of death: \nFelt from the drakkar and couldn't swim.");
    }
    @FXML
    protected void RedkyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Redky",765);
        GhostDescription.setText("A red ghost stuck in a puzzle.\nCause of death: \nGot eaten by a yellow ball.");}
    @FXML
    protected void BabyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost1x1_Rotation0.png"))),250,250,675,180);
        setGhostName("Baby",775);
        GhostDescription.setText("It's a little baby\nCause of death: \nToo sad to be explicated");
    }
    @FXML
    protected void BlobyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2TopLeft_Rotation0.png"))),240,160,720,180);
        setGhostName("Bloby",765);
        GhostDescription.setText("Too cute to scare anyone.\nCause of death: \nHe was so cute that GymBroo couldn't resist to hug him (maybe with too much force).");
    }
    @FXML
    protected void NessyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2TopRight_Rotation0.png"))),240,160,720,180);
        setGhostName("Nessy",765);
        GhostDescription.setText("Some rumors says it lived in Scotland.\nCause of death: \nDrowned.");
    }
    @FXML
    protected void KingPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x4_Rotation0.png"))),250,125,732.5,180);
        setGhostName("King",775);
        GhostDescription.setText("Can't do anything by himself but he is the king so he has an excuse.\nCause of death: \nHad to walk 2 meters and had a respiratory failure.");
    }
    @FXML
    protected void GeoffreyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost2x6_Rotation0.png"))),300,100,750,150);
        setGhostName("Geoffrey",740);
        GhostDescription.setText("A wealthy old man, probably created a crisps brand.\nCause of death: \nHis old age.");
    }
    @FXML
    protected void BigBrooPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostC3x2_Rotation0.png"))),200,300,650,180);
        setGhostName("Big Broo",745);
        GhostDescription.setText("Was Always at the gym but somehow skipped every leg day.\nCause of death: \nProtein overdose.");
    }
    @FXML
    protected void PhantomPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_GhostBigJ3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("Phantom",745);
        GhostDescription.setText("Was a great opera singer.\nCause of death: \nA pot felt on his head during a show.");
    }
    @FXML
    protected void WolfyPressed(){
        setGhostImage(new Image(String.valueOf(AppGame.class.getResource("Sprite_Ghosts/Sprite_Ghost3x32TopRight_Rotation0.png"))),250,187.5,706.25,180);
        setGhostName("Wolfy",765);
        GhostDescription.setText("Hunter who literally put himself in the shoes of his prey\nCause of death: \nHis camouflage didn't really work.");
    }
}