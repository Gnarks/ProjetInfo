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

public class ControllerCredits {
    @FXML
    private ImageView BackButton;

    @FXML
    public Label LeoDescription;

    @FXML
    public Label MaximeDescription;

    @FXML
    public Label WilliamDescription;

    @FXML
    protected void onBackToCollectionEntered(){
        BackButton.setImage(new Image("code/projetinfo/Sprites/ButtonBackLight.png"));
        BackButton.setOnMouseClicked(event ->{
            Scene scene;
            Stage stage;
            FXMLLoader fxmlLoader = new FXMLLoader(AppDraggable.class.getResource("Collection.fxml"));
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
    protected void onBackToCollectionExited() {
        BackButton.setImage(new Image("code/projetinfo/Sprites/ButtonBack.png"));
    }


}
