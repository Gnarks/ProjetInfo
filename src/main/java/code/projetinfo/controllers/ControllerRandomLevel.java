package code.projetinfo.controllers;

import code.projetinfo.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerRandomLevel extends ControllerParent implements Initializable {

    /**Controller of all the in game buttons (for the random generated ones), the creation of the level and so on(assigned to RandomLevel.fxml)*/
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView backToMenuButton;

    @FXML
    private ImageView resetButton;

    private Class<ImageBlock>[] imageBlockClasses;

    private int leastToPlace;

    private int maxToPlace;

    private boolean alwaysDifferent;


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
        imageChanger(backToMenuButton,"Sprites/Button_GoBack_Light.png");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(backToMenuButton,"Sprites/Button_GoBack.png");
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
        ControllerOptions.setMediaPlayerLevel();
        Rectangle transi = new Rectangle(1600,900 , Paint.valueOf("222222"));
        pane.getChildren().add(transi);


        Platform.runLater(() ->{
            LevelGenerator levelGenerator = new LevelGenerator(imageBlockClasses,leastToPlace,maxToPlace,alwaysDifferent);
            Level level;
            try {
                level = levelGenerator.generate();

            } catch (LevelGenerator.GenerateException e) {
                ImageView errorMessage = warningMessage(pane, e.getMessage());
                errorMessage.setOnMouseClicked(event -> loadScene("RandomLevelGenerator.fxml",event));
                return;
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

            resetButton.setOnMouseClicked(event -> {
                if (!levelHandler.getVictoryState()) {
                    levelHandler.reset();
                }
                else{
                    //re-roll
                    Rectangle transition = new Rectangle(1600,900, Paint.valueOf("222222"));
                    transition.setLayoutY(900);
                    pane.getChildren().add(transition);
                    TranslateTransition translateTransition1 = new TranslateTransition(Duration.millis(500),transition);
                    translateTransition1.setToY(-900);
                    translateTransition1.play();

                    translateTransition1.setOnFinished(event1 -> {
                        FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource("RandomLevel.fxml"));
                        Parent root;
                        try {
                            root = fxmlLoader.load();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        ControllerRandomLevel rlgController = fxmlLoader.getController();
                        rlgController.setLeastToPlace(leastToPlace);
                        rlgController.setMaxToPlace(maxToPlace);
                        rlgController.setImageBlockClasses(imageBlockClasses);
                        rlgController.setAlwaysDifferent(alwaysDifferent);


                        Stage stage;
                        Scene scene;

                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root, 1600, 900);

                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    });}
            });

            backToMenuButton.setOnMouseClicked(event1 -> pauseMenu(pane,levelHandler,level, event1));
        });
    }
}
