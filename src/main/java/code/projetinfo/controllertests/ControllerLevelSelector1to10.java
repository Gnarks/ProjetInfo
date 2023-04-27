package code.projetinfo.controllertests;

import code.projetinfo.AppGame;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLevelSelector1to10 implements Initializable {
    @FXML
    private Pane pane;
    @FXML
    private ImageView ButtonNext;

    @FXML
    private ImageView ButtonBack;

    @FXML
    public void onBackEntered(){
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
    public void onBackExited(){
        ButtonBack.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBack.png"))));


    }

    @FXML
    public void onNextEntered(){
        ButtonNext.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNextLight.png"))));

        ButtonNext.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("LevelSelector11to20.fxml"));
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
    public void onNextExited(){
        ButtonNext.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNext.png"))));
    }

    private Stage stage;
    private Scene scene;

    public void LevelSelect(String levelName, Button button){
        button.setOnAction(event -> {

            Rectangle transi = new Rectangle(1600,900, Paint.valueOf("222222"));
            transi.setLayoutY(900);
            pane.getChildren().add(transi);
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(-900);
            translateTransition.play();
            translateTransition.setOnFinished(event1 -> {
                FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("Game.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                GameController gameController = fxmlLoader.getController();
                gameController.setLevelName(levelName);

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1600, 900);

                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
        });
        });
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass()==Button.class){
                LevelSelect(pane.getChildren().get(i).getId(),(Button) pane.getChildren().get(i));
            }
        }
    }
}
