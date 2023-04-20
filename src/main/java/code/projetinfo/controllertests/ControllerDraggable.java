package code.projetinfo.controllertests;

import code.projetinfo.*;
import code.projetinfo.normalBlocks.*;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class ControllerDraggable implements Initializable {
    /**Controller of all the menu's buttons(assigned to DraggableTest.fxml)*/
    private final int tileSize = 50;
    @FXML
    private AnchorPane pane;

    @FXML
    private Button resetButton;

    private final ImageBlock[] everybodyDance = new ImageBlock[]{
            new Amogous(new Position(400,150)),new Baby(new Position(0,0)), new Bloby(new Position(0,0)),
            new BigBob(new Position(0,0)),new Scooboodoo(new Position(0,0)), new Geoffroy(new Position(0,0)),
            new Napsta(new Position(0,0)),new BooBelle(new Position(150,100)),new Bob(new Position(0,0)),
            new Redky(new Position(0,0)), new Scooboodoo(new Position(0,0)),new VicKing(new Position(0,0)),
            new Toowels(new Position(0,0)),new Wolfy(new Position(0,0)),new Phantom(new Position(0,0)),
            new LilDeath(new Position(0,0)), new King(new Position(0,0)), new PlagueDoc(new Position(0,0)),
            new GymBroo(new Position(0,0))
    };

    private final ImageBlock[] heart = new ImageBlock[] {new Amogous(new Position(50,10)),new Amogous(new Position(50,150)),
            new Scooboodoo(new Position(750,150)),new Geoffroy(new Position(750,150)),new Redky(new Position(1,150)),
            new Redky(new Position(0,10)),new Napsta(new Position(50,150)),new Napsta(new Position(800,150)),
            new Baby(new Position(1000,150)),};


    /**
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {




        Level level = new Level(new Cases(new CaseState[][] {
                {CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL,CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL},
                {CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY},
                {CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY},
                {CaseState.EMPTY,CaseState.FULL,CaseState.FULL,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY},
                {CaseState.SPECIAL,CaseState.FULL,CaseState.FULL,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL},
                {CaseState.SPECIAL,CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL,CaseState.SPECIAL},
                {CaseState.SPECIAL,CaseState.SPECIAL,CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL,CaseState.SPECIAL,CaseState.SPECIAL}
        }),heart);


        Position gridPos = new Position(pane.getPrefWidth()/2 - (double) (level.getGrid().getRow()*tileSize)/2,
                pane.getPrefHeight()/2- (double) (level.getGrid().getCol()*tileSize)/2);
        gridPos = new Position(gridPos.getX() - gridPos.getX()%50, gridPos.getY() - gridPos.getY()%50);
        drawGrid(level,gridPos);

        System.out.println(gridPos.getX());
        System.out.println(gridPos.getY());

        drawImageBlocks(level.getBlocks(),level,gridPos);



    }
    private void drawGrid(Level level, Position position){
        for (int i = 0; i < level.getGrid().getCol(); i++) {
            for (int j = 0; j < level.getGrid().getRow(); j++) {
                Rectangle rectangle = new Rectangle(i*tileSize+position.getX(),j*tileSize+position.getY(),tileSize,tileSize);
                rectangle.setFill(Paint.valueOf("#6666fc"));
                rectangle.setStroke(Paint.valueOf("#000000"));
                switch (level.getGrid().getState(i,j)){
                    case EMPTY -> pane.getChildren().add(rectangle);
                    case FULL -> {rectangle.setFill(Paint.valueOf("#000000"));
                    pane.getChildren().add(rectangle);}
                }
            }
        }
    }



    public void drawImageBlocks(ImageBlock[] imageBlocks,Level level,Position gridPos){
        for (ImageBlock imageBlock:
             imageBlocks) {
            pane.getChildren().add(imageBlock.getImageView());
            imageBlock.getImageView().setLayoutX(imageBlock.getSpawnPos().getX());
            imageBlock.getImageView().setLayoutY(imageBlock.getSpawnPos().getY());
            makeDraggable(imageBlock,level,gridPos);
        }
    }
    public void makeDraggable(ImageBlock imageBlock,Level level,Position gridPos){
        Node node = imageBlock.getImageView();

        node.setOnMousePressed(event ->{

            if(event.getButton() == MouseButton.SECONDARY ){
                FadeTransition fT = new FadeTransition(Duration.millis(80),node);
                fT.setByValue(1);
                fT.setToValue(0);
                fT.play();
                fT.setOnFinished(finishedEvent -> {
                    imageBlock.rotateGraphic();
                    imageBlock.rotateCases();
                    FadeTransition repopFT = new FadeTransition(Duration.millis(80),node);
                    repopFT.setByValue(0);
                    repopFT.setToValue(1);
                    repopFT.play();
                });

            }
            if(event.getButton() == MouseButton.PRIMARY){
                node.toFront();
                double posX = event.getSceneX() - imageBlock.getMidX();
                double posY = event.getSceneY() - imageBlock.getMidY();
                if ((posX > 0)){
                    posX = (int)((event.getSceneX() - imageBlock.getMidX()+25)/50)*50;
                    posY = (int)((event.getSceneY() - imageBlock.getMidY()+25)/50)*50;
                }
                node.setLayoutX(posX);
                node.setLayoutY(posY);
                if(level.isPlaced(imageBlock)){
                    level.remove(imageBlock,(int) (node.getLayoutX()-350)/50, (int) (node.getLayoutY()-100)/50);
                }
            }
        });
        node.setOnMouseDragged(mouseEvent->{

            double posX = mouseEvent.getSceneX() - imageBlock.getMidX();
            double posY = mouseEvent.getSceneY() - imageBlock.getMidY();

            if (posX > 0){
                posX = (int)((mouseEvent.getSceneX() - imageBlock.getMidX()+25)/50)*50;
                posY = (int)((mouseEvent.getSceneY() - imageBlock.getMidY()+25)/50)*50;
            }
            node.setLayoutX(posX);
            node.setLayoutY(posY);
        });

        node.setOnMouseReleased(event -> {
            if( !level.isPlacable(imageBlock,(int) (node.getLayoutX()- gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50))
            {
                FadeTransition fT = new FadeTransition(Duration.millis(80),node);
                fT.setByValue(1);
                fT.setToValue(0);
                fT.play();
                fT.setOnFinished(finishedEvent -> {
                    node.setLayoutX(imageBlock.getSpawnPos().getX());
                    node.setLayoutY(imageBlock.getSpawnPos().getY());
                    FadeTransition repopFT = new FadeTransition(Duration.millis(100),node);
                    repopFT.setByValue(0);
                    repopFT.setToValue(1);
                    repopFT.play();
                });
            }
            level.place(imageBlock,(int) (node.getLayoutX()-gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50);
            level.show();


        });
    }
}
