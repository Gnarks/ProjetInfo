package code.projetinfo.controllertests;

import code.projetinfo.AppGame;
import code.projetinfo.LevelCreator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCreator implements Initializable {

    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView BackToMenuButton;

    @FXML
    private ImageView ResetButton;

    @FXML
    protected void onBackEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBackToMenulight.png")));
        BackToMenuButton.setImage(imageLight);

        BackToMenuButton.setOnMouseClicked(event -> {
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("MainMenu.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene;
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
    protected void onBackExited(){
        Image imageDark = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBackToMenu.png")));
        BackToMenuButton.setImage(imageDark);
    }

    @FXML
    protected void onResetEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonResetLight.png")));
        ResetButton.setImage(imageLight);

    }

    @FXML
    protected void onResetExited(){
        Image imageDark = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonReset.png")));
        ResetButton.setImage(imageDark);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LevelCreator levelCreator = new LevelCreator(pane,8);

        levelCreator.drawGrid();
    }
}
