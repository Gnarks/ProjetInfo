package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ControllerCredits extends ControllerParent {
    @FXML
    private ImageView backButton;

    @FXML
    public Label leoDescription;

    @FXML
    public Label maximeDescription;

    @FXML
    public Label williamDescription;

    @FXML
    protected void onBackToCollectionEntered(){
        onButtonEntered(backButton,"Sprites/ButtonBackLight.png","Collection.fxml");
    }

    @FXML
    protected void onBackToCollectionExited() {
        imageChanger(backButton,"Sprites/ButtonBack.png");
    }


}
