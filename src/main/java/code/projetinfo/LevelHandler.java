package code.projetinfo;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LevelHandler {

    private Level level;

    private AnchorPane pane;

    private int tileSize = 50;

    private Position gridPos;


    public LevelHandler(Level level, AnchorPane pane){
        this.level = level;
        this.pane = pane;
        this.gridPos = new Position(pane.getPrefWidth()/2 - (double) (level.getGrid().getRow()*tileSize)/2,
                pane.getPrefHeight()/2- (double) (level.getGrid().getCol()*tileSize)/2);
        gridPos = new Position(gridPos.getX() - gridPos.getX()%50, gridPos.getY() - gridPos.getY()%50);
    }

    public void drawGrid(){
        for (int i = 0; i < level.getGrid().getCol(); i++) {
            for (int j = 0; j < level.getGrid().getRow(); j++) {
                Rectangle rectangle = new Rectangle(i*tileSize+gridPos.getX(),j*tileSize+gridPos.getY(),tileSize,tileSize);
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



    public void drawImageBlocks(){
        for (ImageBlock imageBlock:
                level.getBlocks()) {
            pane.getChildren().add(imageBlock.getImageView());
            imageBlock.getImageView().setLayoutX(imageBlock.getSpawnPos().getX());
            imageBlock.getImageView().setLayoutY(imageBlock.getSpawnPos().getY());
            makeDraggable(imageBlock);
        }
    }
    private void makeDraggable(ImageBlock imageBlock){
        Node node = imageBlock.getImageView();

        node.setOnMousePressed(event ->{
            if(event.getButton() == MouseButton.SECONDARY ){
                rotateImageBlock(imageBlock);
            }

            if(event.getButton() == MouseButton.PRIMARY){
                node.toFront();
                double posX = event.getSceneX() - imageBlock.getMidX();
                double posY = event.getSceneY() - imageBlock.getMidY();
                //si dans gridbounds => make gridDraggable
                if (inGridBound(new Position(posX,posY))){
                    posX = (int)((event.getSceneX() - imageBlock.getMidX()+25)/50)*50;
                    posY = (int)((event.getSceneY() - imageBlock.getMidY()+25)/50)*50;
                }
                node.setLayoutX(posX);
                node.setLayoutY(posY);
                if(level.isPlaced(imageBlock)){
                    System.out.println((int) (node.getLayoutX()-350)/50);
                    level.remove(imageBlock,(int) (node.getLayoutX()-350)/50, (int) (node.getLayoutY()-100)/50);
                }
            }
        });
        node.setOnMouseDragged(mouseEvent->{

            double posX = mouseEvent.getSceneX() - imageBlock.getMidX();
            double posY = mouseEvent.getSceneY() - imageBlock.getMidY();
            //si dans gridbounds => make gridDraggable
            if (inGridBound(new Position(posX,posY))){
                posX = (int)((mouseEvent.getSceneX() - imageBlock.getMidX()+25)/50)*50;
                posY = (int)((mouseEvent.getSceneY() - imageBlock.getMidY()+25)/50)*50;
            }
            node.setLayoutX(posX);
            node.setLayoutY(posY);
        });

        node.setOnMouseReleased(event -> {
            if(inGridBound(new Position(event.getSceneX(),event.getSceneY())) && !level.isPlacable(imageBlock,(int) (node.getLayoutX()- gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50))
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
            if (inGridBound(new Position(event.getSceneX(),event.getSceneY())) && level.isPlacable(imageBlock,(int) (node.getLayoutX()- gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50))
                level.place(imageBlock,(int) (node.getLayoutX()-gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50);
            level.show();
        });
    }

    private void rotateImageBlock(ImageBlock imageBlock){
        // check if can rotate (if inbounds check the cases surrounding the block)
        //if can rotate =>
        FadeTransition fT = new FadeTransition(Duration.millis(80),imageBlock.getImageView());
        fT.setByValue(1);
        fT.setToValue(0);
        fT.play();
        fT.setOnFinished(finishedEvent -> {
            imageBlock.rotateGraphic();
            imageBlock.rotateCases();
            FadeTransition repopFT = new FadeTransition(Duration.millis(80),imageBlock.getImageView());
            repopFT.setByValue(0);
            repopFT.setToValue(1);
            repopFT.play();
        });
    }

    public boolean inGridBound(Position position) {
        return position.getX() >= gridPos.getX()-tileSize && position.getX() <= gridPos.getX()+level.getGrid().getCol()*tileSize &&
                position.getY() >= gridPos.getY()-tileSize && position.getY() <= gridPos.getY()+level.getGrid().getRow()*tileSize;
    }
}