package code.projetinfo.controllertests;

import code.projetinfo.*;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView BackToMenuButton;

    @FXML
    private ImageView ResetButton;

    private String levelName;


    public void setLevelNamm(String levelName){
        this.levelName = levelName;
    }


    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Rectangle transi = new Rectangle(1600,900 , Paint.valueOf("222222"));
        pane.getChildren().add(transi);
        Platform.runLater(() ->{
            Level level = null;
            try {
                level = new Level(levelName);
            } catch (IOException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                     InstantiationException | IllegalAccessException e) {
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

            ResetButton.setOnMouseClicked(event -> {
                levelHandler.reset();
            });
            BackToMenuButton.setOnMouseClicked(event ->{
                FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("LevelSelector.fxml"));
                Stage stage;
                Scene scene;
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
            System.out.println(levelName);

        });
    }
}