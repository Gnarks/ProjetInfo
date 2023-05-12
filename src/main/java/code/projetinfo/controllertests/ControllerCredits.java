package code.projetinfo.controllertests;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ControllerCredits extends ControllerParent {
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
        onButtonEntered(BackButton,"Sprites/ButtonBackLight.png","Collection.fxml");
    }

    @FXML
    protected void onBackToCollectionExited() {
        buttonImageChanger(BackButton,"Sprites/ButtonBack.png");
    }


}
