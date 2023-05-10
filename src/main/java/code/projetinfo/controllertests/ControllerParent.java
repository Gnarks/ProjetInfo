package code.projetinfo.controllertests;

import code.projetinfo.AppMenu;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerParent {

    protected void onButtonEntered(ImageView button, String sprite, String nextStage){
        button.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
        button.setOnMouseClicked(event -> loadScene(nextStage,event));
    }

    protected void buttonImageChanger(ImageView button, String sprite){
        button.setImage(new Image(String.valueOf(AppMenu.class.getResource(sprite))));
    }

    protected void LevelSelect(Pane pane, String levelName, Button button){
        button.setOnAction(event -> {
            Rectangle transi = new Rectangle(1600,900, Paint.valueOf("222222"));
            transi.setLayoutY(900);
            pane.getChildren().add(transi);
            TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
            translateTransition.setToY(-900);
            translateTransition.play();
            translateTransition.setOnFinished(event1 -> {
                FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource("Game.fxml"));
                Parent root;
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                GameController gameController = fxmlLoader.getController();
                gameController.setLevelName(levelName);

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
    protected void loadScene(String nextScene, Event event){
            FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource(nextScene));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1600, 900);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
    }

    protected ImageView createImageView(String image,double width,double layoutX,double layoutY){
        ImageView imageView = new ImageView(new Image(String.valueOf(AppMenu.class.getResource(image))));
       imageView.setPreserveRatio(true);
       imageView.setFitWidth(width);
       imageView.setLayoutX(layoutX);
       imageView.setLayoutY(layoutY);
       return imageView;
    }

    protected void askingInformation(Pane pane,ImageView askingButton,String text){
        askingButton.setOnMouseClicked(event ->{
            Rectangle rectangle = new Rectangle(1600,900, Paint.valueOf("#000000"));
            rectangle.setOpacity(0.2);
            ImageView fondInt = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/BackGridLevel.png"))));
            fondInt.setFitWidth(800);
            fondInt.setFitHeight(400);
            fondInt.setLayoutX(400);
            fondInt.setLayoutY(250);
            ImageView backButton = new ImageView(new Image(String.valueOf(AppMenu.class.getResource("Sprites/ButtonBack.png"))));
            backButton.setPreserveRatio(true);
            backButton.setLayoutX(700);
            backButton.setLayoutY(600);
            backButton.setFitHeight(100);
            backButton.setOnMouseEntered(event1 -> buttonImageChanger(backButton,"Sprites/ButtonBackLight.png"));
            backButton.setOnMouseExited(event1 -> buttonImageChanger(backButton,"Sprites/ButtonBack.png"));
            backButton.setOnMouseClicked(event1 -> pane.getChildren().remove(pane.getChildren().size()-4,pane.getChildren().size()));
            Label label = new Label(text);
            Font font = new Font("System Bold Italic",50);
            label.setFont(font);
            label.setTextFill(Paint.valueOf("#000000"));
            label.setUnderline(true);
            label.setAlignment(Pos.CENTER);
            label.setWrapText(true);
            label.setPrefWidth(700);
            label.setPrefHeight(350);
            label.setLayoutX(450);
            label.setLayoutY(250);
            pane.getChildren().addAll(rectangle,fondInt,label,backButton);
        });
    }

}
