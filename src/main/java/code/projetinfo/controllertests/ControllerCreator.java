package code.projetinfo.controllertests;

import code.projetinfo.AppGame;
import code.projetinfo.CaseState;
import code.projetinfo.LevelCreator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
    private ImageView DecreaseSize;

    @FXML
    private ImageView IncreaseSize;

    @FXML
    private ImageView SetUpGridLeft;

    @FXML
    private ImageView SetUpGridRight;

    @FXML
    private Label GridSize;

    @FXML
    private Label FullGridState;



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

    @FXML
    protected void onDecreaseEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBackLight.png")));
        DecreaseSize.setImage(imageLight);

    }

    @FXML
    protected void onDecreaseExited(){
        Image imageDark = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBack.png")));
        DecreaseSize.setImage(imageDark);
    }


    @FXML
    protected void onIncreaseEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNextLight.png")));
        IncreaseSize.setImage(imageLight);

    }

    @FXML
    protected void onIncreaseExited(){
        Image imageDark = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNext.png")));
        IncreaseSize.setImage(imageDark);
    }


    @FXML
    protected void onSetUpLeftEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBackLight.png")));
        SetUpGridLeft.setImage(imageLight);

    }

    @FXML
    protected void onSetUpLeftExited(){
        Image imageDark = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonBack.png")));
        SetUpGridLeft.setImage(imageDark);
    }




    @FXML
    protected void onSetUpRightEntered(){
        Image imageLight = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNextLight.png")));
        SetUpGridRight.setImage(imageLight);

    }

    @FXML
    protected void onSetUpRightExited(){
        Image imageDark = new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNext.png")));
        SetUpGridRight.setImage(imageDark);
    }

    private void sizeSetUp(LevelCreator levelCreator ,int size){

        GridSize.setText(String.valueOf(size));
        levelCreator.setCreatorGridSize(size);
    }

    private void gridStateSetUp(LevelCreator levelCreator, String stateName, CaseState state){
        FullGridState.setText(stateName);
        levelCreator.fillLevelState(state);
    }






    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LevelCreator levelCreator = new LevelCreator(pane,9);

        levelCreator.drawGrid();

        ResetButton.setOnMouseClicked(event -> levelCreator.reset(FullGridState));
        DecreaseSize.setOnMouseClicked(event -> {
            int size = Integer.parseInt(GridSize.getText())-1;
            if (size>1){

                if(size == 2){
                    DecreaseSize.setOpacity(0);
                }

                else if (size == 8) {
                    IncreaseSize.setOpacity(1);
                }


                sizeSetUp(levelCreator,size);
                levelCreator.reset(FullGridState);
            }});

        IncreaseSize.setOnMouseClicked(event -> {
            int size = Integer.parseInt(GridSize.getText())+1;
            if(size<10){
                if (size ==9){
                    IncreaseSize.setOpacity(0);
                }
                else if(size == 3){
                    DecreaseSize.setOpacity(1);
                }

                sizeSetUp(levelCreator,size);
                levelCreator.reset(FullGridState);
            }
        });

        SetUpGridLeft.setOnMouseClicked(event -> {
            if(FullGridState.getText().equals("Empty")){
                gridStateSetUp(levelCreator,"Full",CaseState.FULL);
            }

            else if(FullGridState.getText().equals("Full")){
                gridStateSetUp(levelCreator,"Neutral",CaseState.EMPTY);
            }

            else if(FullGridState.getText().equals("Neutral")){
                gridStateSetUp(levelCreator,"Empty",CaseState.SPECIAL);
            }
        });

        SetUpGridRight.setOnMouseClicked(event -> {
            if(FullGridState.getText().equals("Neutral")){
                gridStateSetUp(levelCreator,"Full",CaseState.FULL);
            }

            else if(FullGridState.getText().equals("Empty")){
                gridStateSetUp(levelCreator,"Neutral",CaseState.EMPTY);
            }

            else if(FullGridState.getText().equals("Full")){
                gridStateSetUp(levelCreator,"Empty",CaseState.SPECIAL);
            }


    });}
}
