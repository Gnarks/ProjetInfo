package code.projetinfo;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class LevelHandler {

    private final Level level;

    private final AnchorPane pane;

    private final int  tileSize = 50;

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
                //todo check if can rotate
                if(imageBlock.getplacedState()){
                    level.remove(imageBlock,(int) (node.getLayoutX()-gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50);}

                rotateImageBlock(imageBlock);
            }

            if(event.getButton() == MouseButton.PRIMARY){
                node.toFront();
                if(imageBlock.getplacedState()){
                    level.remove(imageBlock,(int) (node.getLayoutX()-gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50);
                }

                moveBlock(imageBlock,event);
            }
        });
        node.setOnMouseDragged(mouseEvent-> moveBlock(imageBlock,mouseEvent));

        node.setOnMouseReleased(event -> {
            if (inGridBounds(new Position(event.getSceneX(),event.getSceneY()))
                    && event.getButton() == MouseButton.PRIMARY){
                if (level.isPlacable(imageBlock,(int) (node.getLayoutX()- gridPos.getX())/50, (int) (node.getLayoutY()- gridPos.getY())/50))
                    level.place(imageBlock, (int) (node.getLayoutX() - gridPos.getX()) / 50, (int) (node.getLayoutY() - gridPos.getY()) / 50);

                else
                    goToSpawnPos(imageBlock);
            }
            else if (collideBetweenBlocks(imageBlock))
                goToSpawnPos(imageBlock);

            level.show();
            //System.out.println(collideBetweenBlocks(imageBlock));
        });
    }


    private void rotateImageBlock(ImageBlock imageBlock){

        // todo check if can rotate (if inbounds check the cases surrounding the block)
        //if can rotate =>
        FadeTransition fT = new FadeTransition(Duration.millis(80),imageBlock.getImageView());
        fT.setByValue(1);
        fT.setToValue(0);
        fT.play();
        fT.setOnFinished(finishedEvent -> {
            imageBlock.rotate();
            FadeTransition rePopFT = new FadeTransition(Duration.millis(80),imageBlock.getImageView());
            rePopFT.setByValue(0);
            rePopFT.setToValue(1);
            rePopFT.play();
        });}



    private void goToSpawnPos(ImageBlock imageBlock)
    {
        Node node = imageBlock.getImageView();
        FadeTransition fT = new FadeTransition(Duration.millis(80),node);
        fT.setByValue(1);
        fT.setToValue(0);
        fT.play();
        fT.setOnFinished(finishedEvent -> {
            node.setLayoutX(imageBlock.getSpawnPos().getX());
            node.setLayoutY(imageBlock.getSpawnPos().getY());
            FadeTransition rePopFT = new FadeTransition(Duration.millis(100),node);
            rePopFT.setByValue(0);
            rePopFT.setToValue(1);
            rePopFT.play();
        });
    }

    public void reset(){
        for (ImageBlock imageBlock:
                level.getBlocks()) {
            if(imageBlock.getplacedState())
                level.remove(imageBlock,(int) (imageBlock.getImageView().getLayoutX()-gridPos.getX())/50,
                        (int) (imageBlock.getImageView().getLayoutY()- gridPos.getY())/50);
            imageBlock.rotateTo(0);
            goToSpawnPos(imageBlock);
            imageBlock.setIsplaced(false);
        }
        this.level.setPlaced(0);
    }

    private boolean inGridBounds(Position position) {
        return position.getX() >= gridPos.getX()-tileSize && position.getX() <= gridPos.getX()+(level.getGrid().getCol()+1)*tileSize &&
                position.getY() >= gridPos.getY()-tileSize && position.getY() <= gridPos.getY()+(level.getGrid().getRow()+1)*tileSize;
    }

    private boolean collideBetweenBlocks(ImageBlock imageBlock){
        Node node = imageBlock.getImageView();
        for (ImageBlock InGameBlock:
                level.getBlocks()) {
            if(InGameBlock!=imageBlock &&node.getBoundsInParent().intersects(InGameBlock.getImageView().getBoundsInParent())){
                return true;
            }
        }
        return false;
    }

    private void moveBlock(ImageBlock imageBlock, MouseEvent mouseEvent){
        double posX = mouseEvent.getSceneX() - imageBlock.getMidX();
        double posY = mouseEvent.getSceneY() - imageBlock.getMidY();
        //if  inGridBounds => make gridDraggable
        if (inGridBounds(new Position(mouseEvent.getSceneX(),mouseEvent.getSceneY()))){
            posX = (int)((mouseEvent.getSceneX() - imageBlock.getMidX()+25)/50)*50;
            posY = (int)((mouseEvent.getSceneY() - imageBlock.getMidY()+25)/50)*50;
        }
        imageBlock.getImageView().setLayoutX(posX);
        imageBlock.getImageView().setLayoutY(posY);
    }
}