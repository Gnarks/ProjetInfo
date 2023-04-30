package code.projetinfo;

import code.projetinfo.controllertests.GameController;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.Blend;
import javafx.scene.effect.BlendMode;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;

public class LevelHandler {

    private final Level level;

    private final AnchorPane pane;

    private final int  tileSize = 50;

    private Position gridPos;

    private boolean victoryState = false;


    public LevelHandler(Level level, AnchorPane pane){
        this.level = level;
        this.pane = pane;
        this.gridPos = new Position(pane.getPrefWidth()/2 - (double) (level.getGrid().getRow()*tileSize)/2,
                pane.getPrefHeight()/2- (double) (level.getGrid().getCol()*tileSize)/2);
        gridPos = new Position(gridPos.getX() - gridPos.getX()%tileSize, gridPos.getY() - gridPos.getY()%tileSize);
    }

    public void drawGrid(){
        ImageView backGrid = new ImageView(String.valueOf(getClass().getResource("Sprites/BackGridLevel.png")));
        backGrid.setLayoutX(gridPos.getX()-tileSize);
        backGrid.setLayoutY(gridPos.getY()-tileSize);
        backGrid.setFitWidth((level.getGrid().getCol()+2)*tileSize);
        backGrid.setFitHeight((level.getGrid().getRow()+2)*tileSize);

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
            if (!imageBlock.getPlacedState())
                imageBlock.setPosition(imageBlock.getSpawnPos());
            makeDraggable(imageBlock);
        }
    }
    private void makeDraggable(ImageBlock imageBlock){
        ColorAdjust c = new ColorAdjust(1,1,0.2,0);
        DropShadow dropShadow = new DropShadow(50, Color.color(0.7,0.7,0.7));
        Blend blend = new Blend(BlendMode.ADD,c,dropShadow);
        Node node = imageBlock.getImageView();
        node.setOnMousePressed(event ->{

            node.setEffect(blend);


            if(event.getButton() == MouseButton.SECONDARY ){
                tryRotate(imageBlock);
            }
            else if(event.getButton() == MouseButton.PRIMARY){
                node.toFront();

                if(imageBlock.getPlacedState()){
                    level.remove(imageBlock,(int) (imageBlock.getLayoutX()-gridPos.getX())/tileSize,
                            (int) (imageBlock.getLayoutY()- gridPos.getY())/tileSize);
                }
                moveBlock(imageBlock,event);
            }
        });
        node.setOnMouseDragged(mouseEvent-> {
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                moveBlock(imageBlock,mouseEvent);
        });

        node.setOnMouseReleased(event -> {
            node.setEffect(null);
            if (event.getButton() == MouseButton.PRIMARY) {
                if (inGridBounds(new Position(event.getSceneX(), event.getSceneY()))) {
                    if (level.isPlacable(imageBlock, (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize, (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize)){
                        level.place(imageBlock, (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize, (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize);
                        if (level.getPlaced() == level.getBlocks().length){
                            setVictoryState(true);
                            victoryAnimation();
                        }
                    }
                    else
                        goToSpawnPos(imageBlock);
                }
                else if (collideBetweenBlocks(imageBlock))
                    goToSpawnPos(imageBlock);
            }
        });
    }

    public void setVictoryState(boolean victoryState) {
        this.victoryState = victoryState;
    }

    public boolean getVictoryState(){
        return this.victoryState;
    }

    private void victoryAnimation(){
        Rectangle rectangle = new Rectangle(((level.getGrid().getCol()+2)*tileSize),(level.getGrid().getRow()+2)*tileSize);
        rectangle.setOpacity(0);
        rectangle.setLayoutX(gridPos.getX()-tileSize);
        rectangle.setLayoutY(gridPos.getY()-tileSize);
        pane.getChildren().add(rectangle);
        ImageView buttonNext = new ImageView(String.valueOf(AppGame.class.getResource("Sprites/ButtonNext.png")));
        pane.getChildren().add(buttonNext);
        buttonNext.setFitHeight(150);
        buttonNext.setLayoutX(1200);
        buttonNext.setLayoutY(730);
        buttonNext.setOnMouseEntered(event ->buttonNext.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNextLight.png")))));
        buttonNext.setOnMouseExited(event ->buttonNext.setImage(new Image(String.valueOf(AppGame.class.getResource("Sprites/ButtonNext.png")))));
        buttonNext.setOnMouseClicked(event -> {
            String levelName = nextLevel(level.getName());
            System.out.println(levelName);
            if (levelName == null){
                    FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("LevelSelector11to20.fxml"));
                    Stage stage;
                    Scene scene;
                    stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
                    try {
                        scene = new Scene(fxmlLoader.load(), 1600, 900);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    stage.setScene(scene);
                    stage.setResizable(false);
                    stage.show();}

            else{
                loadLevel(levelName,event);
            }

        });
    }

    public String nextLevel(String levelName){
        String nextLevel = "Level";
        int units =  Character.getNumericValue(levelName.charAt(6));
        System.out.println(units);
        units +=1;
        int dizaine = Character.getNumericValue(levelName.charAt(5))*10;
        System.out.println(dizaine);

        if(units == 10){
            nextLevel += dizaine +10;
            System.out.println(nextLevel);
            return nextLevel;
        }
        if(dizaine == 0)
        {
            nextLevel += 0;
            nextLevel += units;
            System.out.println(nextLevel);
            return nextLevel;
        }

        int num = dizaine + units;
        if (num > 20){
            return null;
        }
        nextLevel += num;

        return nextLevel;
    }

    /** rotates the imageBlock if it can.
     * if it can't, does an animation to show it can't.
     *
     * @param imageBlock the imageBlock trying to rotate.
     */

    private void tryRotate(ImageBlock imageBlock){
        FadeTransition dePop=blockDePop(imageBlock,100);
        dePop.setOnFinished(event -> {
            if (imageBlock.getPlacedState()) {
                int posX = (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize;
                int posY = (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize;
                level.remove(imageBlock, posX, posY);

                int initialRotateState = imageBlock.getRotateState();

                imageBlock.rotate();
                posX = (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize;
                posY = (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize;
                if (!level.isPlacable(imageBlock, posX, posY)) {
                    imageBlock.rotateTo(initialRotateState);
                }
                posX = (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize;
                posY = (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize;
                level.place(imageBlock,posX,posY);
            }
            else{
                imageBlock.rotate();
            }
            blockPop(imageBlock,100);
        });


    }

    public void loadLevel(String levelName,Event event){
        Rectangle transi = new Rectangle(1600,900, Paint.valueOf("222222"));
        transi.setLayoutY(900);
        pane.getChildren().add(transi);
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500),transi);
        translateTransition.setToY(-900);
        translateTransition.play();
        translateTransition.setOnFinished(event1 -> {
            FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("Game.fxml"));
            Parent root;
            try {
                root = fxmlLoader.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            GameController gameController = fxmlLoader.getController();
            gameController.setLevelName(levelName);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1600, 900);

            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
        });}



    private FadeTransition blockDePop(ImageBlock imageBlock,int duration){
        Node node = imageBlock.getImageView();
        FadeTransition fT = new FadeTransition(Duration.millis(duration),node);
        fT.setByValue(1);
        fT.setToValue(0);
        fT.play();

        return fT;
    }

    private void blockPop(ImageBlock imageBlock, int duration){
        Node node = imageBlock.getImageView();
        FadeTransition rePopFT = new FadeTransition(Duration.millis(duration),node);
        rePopFT.setByValue(0);
        rePopFT.setToValue(1);
        rePopFT.play();
    }


    /**
     * makes the block go to his spawnPos.
     * @param imageBlock the block going to his spawnPos.
     */
    private void goToSpawnPos(ImageBlock imageBlock)
    {
        FadeTransition fT = blockDePop(imageBlock,100);
        fT.setOnFinished(finishedEvent -> {
            imageBlock.setPosition(imageBlock.getSpawnPos());
            for (ImageBlock collided:
                 collide(imageBlock)) {
                if (!collided.getSpawnPos().equals(new Position(collided.getLayoutX(),collided.getLayoutY())))
                    goToSpawnPos(collided);
            }
            blockPop(imageBlock,100);
        });
    }

    /**
     * set all the blocks in the level to their spawnPos with an animation.
     */
    public void reset(){
        ImageView resetImage = new ImageView(String.valueOf(AppGame.class.getResource("Sprites/ResetGhost.png")));

        resetImage.setFitHeight(900);
        resetImage.setFitWidth(1600);
        resetImage.setLayoutX(1600);
        pane.getChildren().add(resetImage);

        TranslateTransition tT = new TranslateTransition(Duration.millis(800),resetImage);
        tT.setToX(-1600);
        tT.play();
        tT.setOnFinished(finishedEvent ->{
        for (ImageBlock imageBlock :
                level.getBlocks()) {
            if(imageBlock.getPlacedState())
                level.remove(imageBlock,(int) (imageBlock.getLayoutX()-gridPos.getX())/tileSize,
                        (int) (imageBlock.getLayoutY()- gridPos.getY())/tileSize);
            imageBlock.rotateTo(0);
            goToSpawnPos(imageBlock);
            imageBlock.setPlaced(false);
        }
        this.level.setPlaced(0);
            TranslateTransition comeBacktT = new TranslateTransition(Duration.millis(800),resetImage);
            comeBacktT.setToX(1600);
            comeBacktT.play();
            comeBacktT.setOnFinished(event -> pane.getChildren().remove(resetImage));
        });
    }

    /**
     *
     * @param position the Position to be checked.
     * @return if the Position is in the gridBounds.
     */
    private boolean inGridBounds(Position position) {
        return position.getX() >= gridPos.getX()-tileSize && position.getX() <= gridPos.getX()+(level.getGrid().getCol()+1)*tileSize &&
                position.getY() >= gridPos.getY()-tileSize && position.getY() <= gridPos.getY()+(level.getGrid().getRow()+1)*tileSize;
    }

    private boolean collideBetweenBlocks(ImageBlock imageBlock){
        return collide(imageBlock).size() !=0;
    }

    /**
     * @param imageBlock the block to check the collisions of.
     * @return the list of the blocks that collide with imageBlock.
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
            posX = (int)((mouseEvent.getSceneX() - imageBlock.getMidX()+25)/tileSize)*tileSize;
            posY = (int)((mouseEvent.getSceneY() - imageBlock.getMidY()+25)/tileSize)*tileSize;
        }
        if (posX <0)
            posX =0;
        else if(posX+(imageBlock.getCols()*tileSize)> pane.getPrefWidth())
            posX = pane.getPrefWidth() - imageBlock.getCols()*tileSize;
        if (posY <0)
            posY = 0;
        else if(posY +(imageBlock.getRows()*tileSize)> pane.getPrefHeight())
            posY = pane.getPrefHeight() - imageBlock.getRows()*tileSize;

        imageBlock.setPosition(new Position(posX,posY));
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
                if(spawnPos.getY()+level.getBlocks()[i].getRows()*tileSize>pane.getPrefHeight()-tileSize){
                    if(overPane == 0) {
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() - 550, gridPos.getY()-100));
                    }
                    else{
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() - 550,overPane));
                    }
                    overPane = level.getBlocks()[i].getRows()*tileSize + tileSize + gridPos.getY();
                }
                else {
                    level.getBlocks()[i].setSpawnPos(spawnPos);
                    spawnPos = new Position(level.getBlocks()[i - 1].getSpawnPos().getX(), tileSize + level.getBlocks()[i - 1].getSpawnPos().getY() + level.getBlocks()[i - 1].getRows() * tileSize);
                }
               }

        }
        overPane = 0;
        spawnPos = new Position(300 + gridPos.getX()+level.getGrid().getCol()*tileSize, gridPos.getY());
        for (int i = level.getBlocks().length / 2; i < level.getBlocks().length; i++) {
            if (i == level.getBlocks().length / 2) {
                level.getBlocks()[i].setSpawnPos(spawnPos);
                spawnPos = new Position(100 + gridPos.getX()+level.getGrid().getCol()*tileSize, gridPos.getY()-100);
            }
            else {
                if(spawnPos.getY()+level.getBlocks()[i].getRows()*tileSize>pane.getPrefHeight()-tileSize){
                    if(overPane == 0) {
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() + level.getGrid().getCol()*tileSize + 500, gridPos.getY()-100));
                    }
                    else{
                        level.getBlocks()[i].setSpawnPos(new Position(gridPos.getX() + level.getGrid().getCol()*tileSize + 500,overPane));

                    }
                    overPane = level.getBlocks()[i].getSpawnPos().getY() + tileSize + level.getBlocks()[i].getRows()*tileSize;
                }

                else {
                        level.getBlocks()[i].setSpawnPos(spawnPos);
                        spawnPos = new Position(level.getBlocks()[i - 1].getSpawnPos().getX(), tileSize + level.getBlocks()[i - 1].getSpawnPos().getY() + level.getBlocks()[i - 1].getRows() * tileSize);
                    }
                }
            }
        }
    }