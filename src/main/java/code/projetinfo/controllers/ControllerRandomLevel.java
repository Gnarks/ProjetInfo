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
import javafx.scene.control.Button;
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

    private final int randomnessValue = 1;

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
        imageChanger(backToMenuButton,"Sprites/ButtonBackToMenulight.png");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(backToMenuButton,"Sprites/ButtonBackToMenu.png");
    }

    @FXML
    protected void onResetEntered(){
        imageChanger(resetButton,"Sprites/ButtonResetLight.png");
    }

    @FXML
    protected void onResetExited(){
        imageChanger(resetButton,"Sprites/ButtonReset.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle transi = new Rectangle(1600,900 , Paint.valueOf("222222"));
        pane.getChildren().add(transi);


        Platform.runLater(() ->{
            LevelGenerator levelGenerator = new LevelGenerator(imageBlockClasses,leastToPlace,maxToPlace,alwaysDifferent);
            Level level;
            try {
                level = levelGenerator.generate();

            } catch (LevelGenerator.GenerateException e) {
                Button error = new Button(e.getMessage());
                error.setLayoutX(700);
                error.setLayoutY(450);
                pane.getChildren().add(error);
                error.setOnAction(event -> loadScene("RLGMenu.fxml",event));
                return;
            }

            LevelHandler levelHandler = new LevelHandler(level, pane,true);
            levelHandler.dispatchBlocks();
            levelHandler.drawGrid();
            levelHandler.drawImageBlocks();

            transi.toFront();
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(900);
            translateTransition.play();
            translateTransition.setOnFinished(event -> pane.getChildren().remove(transi));

            Level finalLevel = level;
            resetButton.setOnMouseClicked(event -> {
                if (!levelHandler.getVictoryState()) {
                    levelHandler.reset();
                }
                else{
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
            Level levelCopy = new Level("Copy", finalLevel.getGrid(), finalLevel.getBlocks());
            backToMenuButton.setOnMouseClicked(event1 -> pauseMenuRandom(pane,levelHandler,levelCopy,event1));
        });
    }
}
