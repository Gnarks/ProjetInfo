package code.projetinfo.controllertests;

import code.projetinfo.*;
import code.projetinfo.normalBlocks.*;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDraggable implements Initializable {
    /**Controller of all the menu's buttons(assigned to DraggableTest.fxml)*/
    private final int tileSize = 50;
    @FXML
    private AnchorPane pane;

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

        ImageBlock[] blocks = {
                new Amogous(new Position(0,0)),new Amogous(new Position(0,0)),new Scooboodoo(new Position(0,0))
                ,new Redky(new Position(0,0)),new Redky(new Position(0,0)),new Napsta(new Position(0,0)),
                new Geoffroy(new Position(0,0)),new Baby(new Position(0,0)),new Napsta(new Position(0,0))};

        Level level = new Level(new Cases(new CaseState[][] {
                {CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL,CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL},
                {CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY},
                {CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY},
                {CaseState.EMPTY,CaseState.FULL,CaseState.FULL,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY},
                {CaseState.SPECIAL,CaseState.FULL,CaseState.FULL,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL},
                {CaseState.SPECIAL,CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL,CaseState.SPECIAL},
                {CaseState.SPECIAL,CaseState.SPECIAL,CaseState.SPECIAL,CaseState.EMPTY,CaseState.EMPTY,CaseState.SPECIAL,CaseState.SPECIAL,CaseState.SPECIAL}
        }),blocks);


        Position gridPos = new Position(pane.getPrefWidth()/2 - (double) (level.getGrid().getRow()*tileSize)/2,
                pane.getPrefHeight()/2- (double) (level.getGrid().getCol()*tileSize)/2);
        gridPos = new Position(gridPos.getX() - gridPos.getX()%50, gridPos.getY() - gridPos.getY()%50);

        drawGrid(level,gridPos);

        drawImageBlocks(level.getBlocks());

        Circle circle = new Circle(gridPos.getX(),gridPos.getY(),5);
        pane.getChildren().add(circle);


    }
    private void drawGrid(Level level, Position position){
        for (int i = 0; i < level.getGrid().getCol(); i++) {
            for (int j = 0; j < level.getGrid().getRow(); j++) {
                Rectangle rectangle = new Rectangle(i*tileSize+position.getX(),j*tileSize+position.getY(),tileSize,tileSize);
                rectangle.setFill(Paint.valueOf("#ffffff"));
                rectangle.setStroke(Paint.valueOf("#000000"));
                switch (level.getGrid().getState(i,j)){
                    case EMPTY -> pane.getChildren().add(rectangle);
                    case FULL -> {rectangle.setFill(Paint.valueOf("#000000"));
                    pane.getChildren().add(rectangle);}
                }
            }
        }
    }

    public void drawImageBlocks(ImageBlock[] imageBlocks){
        for (ImageBlock imageBlock:
             imageBlocks) {
            pane.getChildren().add(imageBlock.getImageView());
            imageBlock.getImageView().setLayoutX(imageBlock.getSpawnPos().getX());
            imageBlock.getImageView().setLayoutY(imageBlock.getSpawnPos().getY());
            makeDraggable(imageBlock);
        }
    }
    public void makeDraggable(ImageBlock imageBlock){
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

                double posX = event.getSceneX() - imageBlock.getMidX();
                double posY = event.getSceneY() - imageBlock.getMidY();
                if ((posX > 0)){
                    posX = (int)((event.getSceneX() - imageBlock.getMidX()+25)/50)*50;
                    posY = (int)((event.getSceneY() - imageBlock.getMidY()+25)/50)*50;
                }
                node.setLayoutX(posX);
                node.setLayoutY(posY);
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
    }
}
