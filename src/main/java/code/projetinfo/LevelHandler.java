package code.projetinfo;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;

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
        ImageView backGrid = new ImageView(String.valueOf(getClass().getResource("Sprites/BackGridLevel.png")));
        backGrid.setLayoutX(gridPos.getX()-50);
        backGrid.setLayoutY(gridPos.getY()-50);
        backGrid.setFitWidth((level.getGrid().getCol()+2)*50);
        backGrid.setFitHeight((level.getGrid().getRow()+2)*50);

        pane.getChildren().add(backGrid);
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
            if (imageBlock.getLayoutX() == 0 && imageBlock.getLayoutY() == 0)
                imageBlock.setPosition(imageBlock.getSpawnPos());
            makeDraggable(imageBlock);
        }
    }
    private void makeDraggable(ImageBlock imageBlock){
        Node node = imageBlock.getImageView();

        node.setOnMousePressed(event ->{
            if(event.getButton() == MouseButton.SECONDARY ){
                tryRotate(imageBlock);
            }

            if(event.getButton() == MouseButton.PRIMARY){
                node.toFront();
                if(imageBlock.getPlacedState()){
                    level.remove(imageBlock,(int) (imageBlock.getLayoutX()-gridPos.getX())/50, 
                            (int) (imageBlock.getLayoutY()- gridPos.getY())/50);
                }

                moveBlock(imageBlock,event);
            }
        });
        node.setOnMouseDragged(mouseEvent-> moveBlock(imageBlock,mouseEvent));

        node.setOnMouseReleased(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (inGridBounds(new Position(event.getSceneX(), event.getSceneY()))) {
                    if (level.isPlacable(imageBlock, (int) (imageBlock.getLayoutX() - gridPos.getX()) / 50, (int) (imageBlock.getLayoutY() - gridPos.getY()) / 50))
                        level.place(imageBlock, (int) (imageBlock.getLayoutX() - gridPos.getX()) / 50, (int) (imageBlock.getLayoutY() - gridPos.getY()) / 50);

                    else
                        goToSpawnPos(imageBlock);
                }
                else if (collideBetweenBlocks(imageBlock))
                    goToSpawnPos(imageBlock);
            }
            level.show();
        });
    }

    /** rotates the imageBlock if it can.
     * if it can't, does an animation to show it can't.
     *
     * @param imageBlock the imageBlock trying to rotate.
     */
    private void tryRotate(ImageBlock imageBlock){
        if (imageBlock.getPlacedState()) {
            int posX = (int) (imageBlock.getLayoutX() - gridPos.getX()) / 50;
            int posY = (int) (imageBlock.getLayoutY() - gridPos.getY()) / 50;
            level.remove(imageBlock, posX, posY);

            int initialRotateState = imageBlock.getRotateState();

            imageBlock.rotate();
            posX = (int) (imageBlock.getLayoutX() - gridPos.getX()) / 50;
            posY = (int) (imageBlock.getLayoutY() - gridPos.getY()) / 50;
            if (!level.isPlacable(imageBlock, posX, posY)) {
                imageBlock.rotateTo(initialRotateState);
                Node node = imageBlock.getImageView();
                FadeTransition fT = new FadeTransition(Duration.millis(80),node);
                fT.setByValue(1);
                fT.setToValue(0);
                fT.play();
                fT.setOnFinished(finishedEvent -> {
                    FadeTransition rePopFT = new FadeTransition(Duration.millis(100),node);
                    rePopFT.setByValue(0);
                    rePopFT.setToValue(1);
                    rePopFT.play();
                });
            }
            posX = (int) (imageBlock.getLayoutX() - gridPos.getX()) / 50;
            posY = (int) (imageBlock.getLayoutY() - gridPos.getY()) / 50;
            level.place(imageBlock,posX,posY);
        }
        else
            imageBlock.rotate();
    }


    private void goToSpawnPos(ImageBlock imageBlock)
    {
        Node node = imageBlock.getImageView();
        FadeTransition fT = new FadeTransition(Duration.millis(80),node);
        fT.setByValue(1);
        fT.setToValue(0);
        fT.play();
        fT.setOnFinished(finishedEvent -> {
            imageBlock.setPosition(imageBlock.getSpawnPos());
            for (ImageBlock collided:
                 collide(imageBlock)) {
                if (!collided.getSpawnPos().equals(new Position(collided.getLayoutX(),collided.getLayoutY())))
                    goToSpawnPos(collided);
            }
            FadeTransition rePopFT = new FadeTransition(Duration.millis(100),node);
            rePopFT.setByValue(0);
            rePopFT.setToValue(1);
            rePopFT.play();
        });
    }

    public void reset(){
        Rectangle transi = new Rectangle(1600,900,Paint.valueOf("#6666fc"));
        transi.setLayoutX(1600);
        pane.getChildren().add(transi);

        TranslateTransition tT = new TranslateTransition(Duration.millis(1000),transi);
        tT.setToX(-1600);
        tT.play();
        tT.setOnFinished(finishedEvent ->{
        for (ImageBlock imageBlock :
                level.getBlocks()) {
            if(imageBlock.getPlacedState())
                level.remove(imageBlock,(int) (imageBlock.getLayoutX()-gridPos.getX())/50,
                        (int) (imageBlock.getLayoutY()- gridPos.getY())/50);
            imageBlock.rotateTo(0);
            goToSpawnPos(imageBlock);
            imageBlock.setPlaced(false);
        }
        this.level.setPlaced(0);
            TranslateTransition comeBacktT = new TranslateTransition(Duration.millis(1000),transi);
            comeBacktT.setToX(1600);
            comeBacktT.play();
            comeBacktT.setOnFinished(event -> pane.getChildren().remove(transi));
        });
    }

    private boolean inGridBounds(Position position) {
        return position.getX() >= gridPos.getX()-tileSize && position.getX() <= gridPos.getX()+(level.getGrid().getCol()+1)*tileSize &&
                position.getY() >= gridPos.getY()-tileSize && position.getY() <= gridPos.getY()+(level.getGrid().getRow()+1)*tileSize;
    }

    private boolean collideBetweenBlocks(ImageBlock imageBlock){
        return collide(imageBlock).size() !=0;
    }

    /**
     *
     * @param imageBlock
     * @return la liste de blocks avec lequel le block passé en argument collisionne.
     */
    private ArrayList<ImageBlock> collide(ImageBlock imageBlock){
        ArrayList<ImageBlock> colliding = new ArrayList<>();
        Node node = imageBlock.getImageView();

        for (ImageBlock inGameBlock:
                level.getBlocks()) {
            if(inGameBlock!=imageBlock &&node.getBoundsInParent().intersects(inGameBlock.getImageView().getBoundsInParent())){
                colliding.add(inGameBlock);
            }
        }
        return colliding;
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

    public void dispatchBlocks() {
        Position spawnPos = new Position(gridPos.getX()-200, gridPos.getY());
        double overPane = 0;
        for (int i = 0; i < level.getBlocks().length / 2; i++) {
            if (i == 0) {
                level.getBlocks()[i].setSpawnPos(spawnPos);
                spawnPos = new Position(gridPos.getX()-400, gridPos.getY()-100);
            }
            else {
                if(spawnPos.getY()+level.getBlocks()[i].getRows()*50>pane.getPrefHeight()-50){
                    if(overPane == 0) {
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() - 550, gridPos.getY()-100));
                    }
                    else{
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() - 550,overPane));
                    }
                    overPane = level.getBlocks()[i].getRows()*50 + 50 + gridPos.getY();
                }
                else {
                    level.getBlocks()[i].setSpawnPos(spawnPos);
                    spawnPos = new Position(level.getBlocks()[i - 1].getSpawnPos().getX(), 50 + level.getBlocks()[i - 1].getSpawnPos().getY() + level.getBlocks()[i - 1].getRows() * 50);
                }
               }

        }
        overPane = 0;
        spawnPos = new Position(300 + gridPos.getX()+level.getGrid().getCol()*50, gridPos.getY());
        for (int i = level.getBlocks().length / 2; i < level.getBlocks().length; i++) {
            if (i == level.getBlocks().length / 2) {
                level.getBlocks()[i].setSpawnPos(spawnPos);
                spawnPos = new Position(100 + gridPos.getX()+level.getGrid().getCol()*50, gridPos.getY()-100);
            }
            else {
                if(spawnPos.getY()+level.getBlocks()[i].getRows()*50>pane.getPrefHeight()-50){
                    if(overPane == 0) {
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() + level.getGrid().getCol()*50 + 500, gridPos.getY()-100));
                    }
                    else{
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() + level.getGrid().getCol()*50 + 500,overPane));

                    }
                    overPane = level.getBlocks()[i].getSpawnPos().getY() + 50 + level.getBlocks()[i].getRows()*50;
                }

                else {
                        level.getBlocks()[i].setSpawnPos(spawnPos);
                        spawnPos = new Position(level.getBlocks()[i - 1].getSpawnPos().getX(), 50 + level.getBlocks()[i - 1].getSpawnPos().getY() + level.getBlocks()[i - 1].getRows() * 50);
                    }
                }
            }
        }
    }