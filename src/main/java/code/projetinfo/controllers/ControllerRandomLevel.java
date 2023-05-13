package code.projetinfo.controllers;

import code.projetinfo.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRandomLevel extends ControllerParent implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView BackToMenuButton;

    @FXML
    private ImageView ResetButton;

    private Class<ImageBlock>[] imageBlockClasses;

    private int leastToPlace;

    private int maxToPlace;

    boolean alwaysDifferent;

    public void setImageBlockClasses(Class<ImageBlock>[] imageBlockClasses) {
        this.imageBlockClasses = imageBlockClasses;
    }

    public void setLeastToPlace(int leastToPlace) {
        this.leastToPlace = leastToPlace;
    }

    public void setMaxToPlace(int maxToPlace) {
        this.maxToPlace = maxToPlace;
    }

    public void setAlwaysDifferent(boolean alwaysDifferent) {
        this.alwaysDifferent = alwaysDifferent;
    }

    @FXML
    protected void onBackEntered(){
        imageChanger(BackToMenuButton,"Sprites/ButtonBackToMenulight.png");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(BackToMenuButton,"Sprites/ButtonBackToMenu.png");
    }

    @FXML
    protected void onResetEntered(){
        imageChanger(ResetButton,"Sprites/ButtonResetLight.png");
    }

    @FXML
    protected void onResetExited(){
        imageChanger(ResetButton,"Sprites/ButtonReset.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle transi = new Rectangle(1600,900 , Paint.valueOf("222222"));
        pane.getChildren().add(transi);


        Platform.runLater(() ->{

            System.out.println(imageBlockClasses[0]);
            System.out.println(leastToPlace);
            System.out.println(maxToPlace);
            System.out.println(alwaysDifferent);
            LevelGenerator levelGenerator = new LevelGenerator(imageBlockClasses,leastToPlace,maxToPlace,alwaysDifferent);




            Level level;

            try {
                level = levelGenerator.generate();
            } catch (LevelGenerator.GenerateException e) {
                throw new RuntimeException(e);
            }


            LevelHandler levelHandler = new LevelHandler(level, pane);
            levelHandler.dispatchBlocks();
            levelHandler.drawGrid();
            levelHandler.drawImageBlocks();

            transi.toFront();
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(900);
            translateTransition.play();
            translateTransition.setOnFinished(event -> pane.getChildren().remove(transi));



            BackToMenuButton.setOnMouseClicked(event1 -> loadScene("RLGMenu.fxml",event1));
        });
    }
}
