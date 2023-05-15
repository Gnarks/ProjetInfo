package code.projetinfo.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerCredits extends ControllerParent implements Initializable {
    /**Controller of all the Credit's buttons(assigned to Credits.fxml)*/
    @FXML
    private ImageView backButton;

    @FXML
    public Label leoDescription;

    @FXML
    public Label maximeDescription;

    @FXML
    public Label williamDescription ;

    @FXML
    protected void onBackToCollectionEntered(){
        onButtonEntered(backButton,"Sprites/ButtonBackLight.png","Collection.fxml");
    }

    @FXML
    protected void onBackToCollectionExited() {
        imageChanger(backButton,"Sprites/ButtonBack.png");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        williamDescription.setText("Creator of this world's aesthetic.\nCause of death:\n Had to join his ghosts.");
        maximeDescription.setText("Link between the universes.\nCause of death:\nTook himself for a shrimp and realized afterwards that he couldn't swim");
        leoDescription.setText("Creator of this world's physic.\nCause of death:\n Killed by a horde of goblins.");
    }
}
