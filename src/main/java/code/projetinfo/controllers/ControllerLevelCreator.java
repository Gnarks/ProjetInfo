package code.projetinfo.controllers;

import code.projetinfo.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLevelCreator extends ControllerParent implements Initializable {
    @FXML
    private AnchorPane pane;

    @FXML
    private ImageView backButton;

    @FXML
    private ImageView resetButton;



    @FXML
    protected void onBackEntered(){
        onButtonEntered(backButton,"Sprites/Button_GoBack_Light.png","MenuCreate.fxml");
    }

    @FXML
    protected void onBackExited(){

        Image imageDark = new Image(String.valueOf(AppMenu.class.getResource("Sprites/ButtonBackToMenu.png")));
        backButton.setImage(imageDark);
    }

    @FXML
    protected void onResetEntered(){
        Image imageLight = new Image(String.valueOf(AppMenu.class.getResource("Sprites/ButtonResetLight.png")));
        resetButton.setImage(imageLight);

    }

    @FXML
    protected void onResetExited(){
        Image imageDark = new Image(String.valueOf(AppMenu.class.getResource("Sprites/ButtonReset.png")));
        resetButton.setImage(imageDark);
    }


    @Override 
    public void initialize(URL location, ResourceBundle resources) {

        LevelCreator levelCreator = new LevelCreator(pane,8);
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass() == Button.class)
            {
                int finalI = i;
                pane.getChildren().get(i).setOnMouseClicked(event -> {
                    try {
                        levelCreator.addBlock(pane.getChildren().get(finalI));
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException |
                             InstantiationException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
        levelCreator.drawGrid();




        resetButton.setOnMouseClicked(event -> levelCreator.prepareToSave());
    }
}