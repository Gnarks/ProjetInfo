package code.projetinfo.controllers;

import code.projetinfo.AppMenu;
import code.projetinfo.ImageBlock;
import code.projetinfo.normalBlocks.*;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerRLG extends ControllerParent implements Initializable {

    /**Controller of all the level generator menu's buttons(assigned to RLGMenu.fxml)*/
    @FXML
    private Pane pane;
    @FXML
    private ImageView buttonBack;
    @FXML
    private Label maximum;
    @FXML
    private ImageView maximumIncrease;
    @FXML
    private ImageView maximumDecrease;
    @FXML
    private Label minimum;
    @FXML
    private ImageView minimumIncrease;
    @FXML
    private ImageView minimumDecrease;
    @FXML
    private Circle alwaysDifferent;
    @FXML
    private Circle fullRandom;

    @FXML
    private ImageView createButton;

    private ArrayList<Class<ImageBlock>> blockChosen = new ArrayList<>(19);

    private boolean alwaysDifferentState = false;

    private boolean fullRandomState = true;

    @FXML
    protected void onBackEntered(){
        onButtonEntered(buttonBack,"Sprites/Button_GoBack_Light.png","RandomMenu.fxml");
    }

    @FXML
    protected void onBackExited(){
        imageChanger(buttonBack,"Sprites/Button_GoBack.png");
    }

    @FXML
    protected void onCreateEntered(){
        imageChanger(createButton,"Sprites/Button_Create_Light.png");
    }

    @FXML
    protected void onCreateExited(){
        imageChanger(createButton,"Sprites/Button_Create.png");
    }

    @FXML
    protected void onMinIncEntered(){
        imageChanger(minimumIncrease,"Sprites/Button_Arrow_RightLight.png");
    }

    @FXML
    protected void onMinIncExited(){ imageChanger(minimumIncrease,"Sprites/Button_Arrow_Right.png");
    }
    @FXML
    protected void onMinDecEntered(){
        imageChanger(minimumDecrease,"Sprites/Button_Arrow_LeftLight.png");
    }
    @FXML
    protected void onMinDecExited(){
        imageChanger(minimumDecrease,"Sprites/Button_Arrow_Left.png");
    }
    @FXML
    protected void onMaxIncEntered(){
        imageChanger(maximumIncrease,"Sprites/Button_Arrow_RightLight.png");
    }
    @FXML
    protected void onMaxIncExited(){
        imageChanger(maximumIncrease,"Sprites/Button_Arrow_Right.png");
    }
    @FXML
    protected void onMaxDecEntered(){
        imageChanger(maximumDecrease,"Sprites/Button_Arrow_LeftLight.png");
    }
    @FXML
    protected void onMaxDecExited(){
        imageChanger(maximumDecrease,"Sprites/Button_Arrow_Left.png");
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initialize(URL location, ResourceBundle resources) {
        final Class<ImageBlock>[] allBlocks = new Class[]{Amogous.class, Baby.class, BigBob.class, Bloby.class, BooBelle.class, Geoffroy.class,
                GymBroo.class, King.class, LilDeath.class, Napsta.class, Nessy.class, Phantom.class, PlagueDoc.class, Redky.class, Scooboodoo.class,
                Toowels.class, VicKing.class, Wolfy.class};
        for (int i = 0; i < pane.getChildren().size(); i++) {
            if(pane.getChildren().get(i).getClass() == Button.class){
                int finalI = i;
                pane.getChildren().get(i).setOnMouseClicked(event->{
                    try {
                        Button button = (Button)pane.getChildren().get(finalI);
                        Class<ImageBlock>block = (Class<ImageBlock>) Class.forName("code.projetinfo.normalBlocks." + button.getId());
                        System.out.println(block);
                        blockChosen.add(block);
                        Rectangle rectangle = new Rectangle(button.getLayoutX(),button.getLayoutY(),button.getWidth(),button.getHeight());
                        rectangle.setOpacity(0.2);
                        rectangle.setFill(Paint.valueOf("#00ff00"));
                        pane.getChildren().add(rectangle);
                        rectangle.setOnMouseClicked(removeEvent -> {
                            pane.getChildren().remove(rectangle);
                            blockChosen.remove(block);
                        });
                    } catch (ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                    this.fullRandomState = false;
                    fullRandom.setFill(Paint.valueOf("#ff0000"));
                });}
        }

        alwaysDifferent.setOnMouseClicked(event -> {
            if(alwaysDifferent.getFill().equals(Paint.valueOf("#ff0000"))){
                alwaysDifferent.setFill(Paint.valueOf("#00ff00"));
                fullRandom.setFill(Paint.valueOf("#ff0000"));
                this.alwaysDifferentState = true;
                this.fullRandomState = false;

            }
            else if(alwaysDifferent.getFill().equals(Paint.valueOf("#00ff00"))){
                alwaysDifferent.setFill(Paint.valueOf("#ff0000"));
                this.alwaysDifferentState = false;
            }
        });


        fullRandom.setOnMouseClicked(event -> {
            if(fullRandom.getFill().equals(Paint.valueOf("#ff0000"))){
                fullRandom.setFill(Paint.valueOf("#00ff00"));
                alwaysDifferent.setFill(Paint.valueOf("#ff0000"));
                this.alwaysDifferentState = false;
                this.fullRandomState = true;
                ArrayList<Rectangle> rectangles = new ArrayList<>();
                for (int i = 0; i < pane.getChildren().size(); i++) {
                    if(pane.getChildren().get(i).getClass()== Rectangle.class){
                        rectangles.add((Rectangle) pane.getChildren().get(i));
                    }
                }
                for (int i = 0; i < rectangles.toArray().length ; i++) {

                    pane.getChildren().remove(rectangles.get(i));
                }
                blockChosen = new ArrayList<>(19);

            }
            else if(fullRandom.getFill().equals(Paint.valueOf("#00ff00"))){
                fullRandom.setFill(Paint.valueOf("#ff0000"));
                fullRandomState = false;
            }
        });


        minimumIncrease.setOnMouseClicked(event ->{
            if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                minimumIncrease.setOpacity(0);
                maximumDecrease.setOpacity(0);
            }

            else if(Integer.parseInt(minimum.getText())==11){
                minimumIncrease.setOpacity(0);
                maximumDecrease.setOpacity(0);
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())+1));
            }
            else if(Integer.parseInt(minimum.getText())==2){
                minimumDecrease.setOpacity(1);

                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())+1));
                if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
            else if(Integer.parseInt(minimum.getText())<11){
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())+1));

                if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
        });
        minimumDecrease.setOnMouseClicked(event ->{
            if(Integer.parseInt(minimum.getText())==3){
                minimumDecrease.setOpacity(0);
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())-1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
            else if(Integer.parseInt(minimum.getText())==12){
                minimumIncrease.setOpacity(1);

                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())-1));
                minimumIncrease.setOpacity(1);
                maximumDecrease.setOpacity(1);

            }
            else if(Integer.parseInt(minimum.getText())>3){
                minimum.setText(String.valueOf(Integer.parseInt(minimum.getText())-1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
        });




        maximumIncrease.setOnMouseClicked(event ->{

            if(Integer.parseInt(maximum.getText())==11){
                maximumIncrease.setOpacity(0);
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())+1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
            else if(Integer.parseInt(maximum.getText())==2){
                maximumDecrease.setOpacity(1);
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())+1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
            else if(Integer.parseInt(maximum.getText())<12){
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())+1));
                if (Integer.parseInt(minimum.getText())!=Integer.parseInt(maximum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
            }
        });

        maximumDecrease.setOnMouseClicked(event ->{
            if (Integer.parseInt(minimum.getText())==Integer.parseInt(maximum.getText())){
                minimumIncrease.setOpacity(0);
                maximumDecrease.setOpacity(0);
            }
            else if(Integer.parseInt(maximum.getText())==3){
                maximumDecrease.setOpacity(0);
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())-1));
                if (Integer.parseInt(maximum.getText())==Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
            else if(Integer.parseInt(maximum.getText())==12){
                maximumIncrease.setOpacity(1);

                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())-1));
                if (Integer.parseInt(maximum.getText())==Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }

            }
            else if(Integer.parseInt(maximum.getText())>3){
                maximum.setText(String.valueOf(Integer.parseInt(maximum.getText())-1));

                if (Integer.parseInt(maximum.getText())!=Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(1);
                    maximumDecrease.setOpacity(1);
                }
                else if (Integer.parseInt(maximum.getText())==Integer.parseInt(minimum.getText())){
                    minimumIncrease.setOpacity(0);
                    maximumDecrease.setOpacity(0);
                }
            }
        });

        createButton.setOnMouseClicked(event -> {

            Rectangle transi = new Rectangle(1600,900, Paint.valueOf("222222"));
            transi.setLayoutY(900);
            pane.getChildren().add(transi);
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(-900);
            translateTransition.play();
            translateTransition.setOnFinished(event1 -> {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource("RandomLevel.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                ControllerRandomLevel rlgController = fxmlLoader.getController();
                rlgController.setLeastToPlace(Integer.parseInt(minimum.getText()));
                rlgController.setMaxToPlace(Integer.parseInt(maximum.getText()));
                if(fullRandomState || blockChosen.size() == 0){
                    rlgController.setImageBlockClasses(allBlocks);
                }
                else{
                    Class<ImageBlock>[] blocks =(Class<ImageBlock>[]) blockChosen.toArray(new Class[0]);
                    rlgController.setImageBlockClasses(blocks);}
                rlgController.setAlwaysDifferent(alwaysDifferentState);


                Stage stage;
                Scene scene;

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root, 1600, 900);

                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            });
        });
    }
}
