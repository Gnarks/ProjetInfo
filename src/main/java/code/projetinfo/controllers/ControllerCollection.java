package code.projetinfo.controllers;

import code.projetinfo.AppMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;

public class ControllerCollection extends ControllerParent {
    /**Controller of all the Collection's buttons(assigned to Collection.fxml)*/

    @FXML
    private ImageView ghostImage;
    @FXML
    private ImageView buttonBack;
    @FXML
    private ImageView buttonNext;
    @FXML
    private Label ghostName;
    @FXML
    private Label ghostDescription;

    /**
     * Set up the button to come back to the Main Menu
     */
    @FXML
    protected void onButtonBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_Arrow_LeftLight.png","MainMenu.fxml");
    }
    @FXML
    protected void onButtonBackExited(){
        imageChanger(buttonBack,"Sprites/Button_Arrow_Left.png");
    }

    /**
     * Set up the button to go to the credits
     */
    @FXML
    protected void onButtonNextEntered(){
        onButtonEntered(buttonNext,"Sprites/Button_Arrow_RightLight.png","Credits.fxml");
    }
    @FXML
    protected void onButtonNextExited(){
        buttonNext.setImage(new Image(String.valueOf(AppMenu.class.getResource("Sprites/Button_Arrow_Right.png"))));
    }

    /**
     * Set an image in the middle of the Scene to show the wanted ghost
     * @param image ghost's sprite
     * @param height ghost's height
     * @param width ghost's width
     * @param posX ghost's position on X
     * @param posY ghost's position on Y
     */
    @FXML
    protected void setGhostImage(Image image,double height,double width,double posX,double posY){
        ghostImage.setImage(image);
        ghostImage.setFitHeight(height);
        ghostImage.setFitWidth(width);
        ghostImage.setLayoutX(posX);
        ghostImage.setLayoutY(posY);
    }

    /**
     * Set up the description of the wanted ghost
     * @param name ghost's name
     * @param posX position X of the name
     */
    @FXML
    protected void setGhostName(String name,double posX){
        ghostName.setText(name);
        ghostName.setLayoutX(posX);
        ghostName.setTextFill(Paint.valueOf("#d0d0ff"));
        ghostDescription.setTextFill(Paint.valueOf("#d0d0ff"));
    }


    /**
     * for each ...Pressed()
     * set up the ghost's description,image and name
     */

    @FXML
    protected void plagueDocPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostS3x2_Rotation0.png"))),200,300,650,180);
        setGhostName("Plague Doc",725);
        ghostDescription.setText("A well known doctor from the XIV century.\nCause of death: \nUnfortunately for him his mask wasn't as efficient as a covid mask.");
    }
    @FXML
    protected void scooboodooPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostJ2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Scooboodoo",705);
        ghostDescription.setText("Has resolved as much mystery than things he ate(a lot).\nCause of death: \nSuffocated in scooby snacks.");
    }
    @FXML
    protected void napstaPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost1x3_Rotation0.png"))),300,100,750,150);
        setGhostName("Napsta",750);
        ghostDescription.setText("Didn't have a crazy life but has always chilled in music.\nCause of death: \nListened too much sad music.");

    }
    @FXML
    protected void bigBobPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost2x3_Rotation0.png"))),240,160,720,180);
        setGhostName("Big Bob",745);
        ghostDescription.setText("Bob's (BIG) brother. \nCause of death: \nTried to open a coconut with his head.");

    }
    @FXML
    protected void bobPressed() {
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost1x2_Rotation0.png"))),250,125,732.5,180);
        setGhostName("Bob",780);
        ghostDescription.setText("He is more scared of you than you are of him.\nCause of death: \nHad a heart attack from his hamster sneezing.");
    }
    @FXML
    protected void amogousPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostL2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Amogous",745);
        ghostDescription.setText("A crew member of the Skeld.\nCause of death: \nGot ejected from the spaceship for being too lazy to do tasks.");
    }
    @FXML
    protected void booBellePressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostBigL3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("BooBelle",745);
        ghostDescription.setText("Has always lived in a trashcan.\nCause of death: \nIt was (maybe) unhealthy to live there.");

    }
    @FXML
    protected void toowelsPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostL2x4_Rotation0.png"))),250,125,732.5,180);
        setGhostName("Toowels",750);
        ghostDescription.setText("So stupid that he is stuck in a towel since 1983.\nCause of death: \nSlipped on a banana.");
    }
    @FXML
    protected void lilDeathPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostJ2x3_Rotation0.png"))),240,160,720,180);
        setGhostName("Lil Death",745);
        ghostDescription.setText("The little son of the Death.\nCause of death: \nUnknown (did his father killed him ?).");
    }
    @FXML
    protected void vicKingPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("Vic King", 745);
        ghostDescription.setText("He is very brutal but fortunately too lazy to hit someone.\nCause of death: \nFelt from the drakkar and couldn't swim.");
    }
    @FXML
    protected void redkyPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2_Rotation0.png"))),250,250,675,180);
        setGhostName("Redky",765);
        ghostDescription.setText("A red ghost stuck in a puzzle.\nCause of death: \nGot eaten by a yellow ball.");}
    @FXML
    protected void babyPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost1x1_Rotation0.png"))),250,250,675,180);
        setGhostName("Baby",775);
        ghostDescription.setText("It's a little baby\nCause of death: \nToo sad to be explicated");
    }
    @FXML
    protected void blobyPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2TopLeft_Rotation0.png"))),240,160,720,180);
        setGhostName("Bloby",765);
        ghostDescription.setText("Too cute to scare anyone.\nCause of death: \nHe was so cute that GymBroo couldn't resist to hug him (maybe with too much force).");
    }
    @FXML
    protected void nessyPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2TopRight_Rotation0.png"))),240,160,720,180);
        setGhostName("Nessy",765);
        ghostDescription.setText("Some rumors says it lived in Scotland.\nCause of death: \nDrowned.");
    }
    @FXML
    protected void kingPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost2x4_Rotation0.png"))),250,125,732.5,180);
        setGhostName("King",775);
        ghostDescription.setText("Can't do anything by himself but he is the king so he has an excuse.\nCause of death: \nHad to walk 2 meters and had a respiratory failure.");
    }
    @FXML
    protected void geoffreyPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost2x6_Rotation0.png"))),300,100,750,150);
        setGhostName("Geoffrey",740);
        ghostDescription.setText("A wealthy old man, probably created a crisps brand.\nCause of death: \nHis old age.");
    }
    @FXML
    protected void bigBrooPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostC3x2_Rotation0.png"))),200,300,650,180);
        setGhostName("Big Broo",745);
        ghostDescription.setText("Was Always at the gym but somehow skipped every leg day.\nCause of death: \nProtein overdose.");
    }
    @FXML
    protected void phantomPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_GhostBigJ3x3_Rotation0.png"))),250,250,675,180);
        setGhostName("Phantom",745);
        ghostDescription.setText("Was a great opera singer.\nCause of death: \nA pot felt on his head during a show.");
    }
    @FXML
    protected void wolfyPressed(){
        setGhostImage(new Image(String.valueOf(AppMenu.class.getResource("Sprite_Ghosts/Sprite_Ghost3x32TopRight_Rotation0.png"))),250,187.5,706.25,180);
        setGhostName("Wolfy",765);
        ghostDescription.setText("Hunter who literally put himself in the shoes of his prey\nCause of death: \nHis camouflage didn't really work.");
    }
}