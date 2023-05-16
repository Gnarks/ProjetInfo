package code.projetinfo;

import code.projetinfo.controllers.ControllerParent;
import code.projetinfo.controllers.GameController;
import javafx.animation.*;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The main class to handle a level.
 */
public class LevelHandler {

    /**
     * The level to be handled.
     */
    private final Level level;

    /**
     * The pane to draw all the elements on.
     */
    private final AnchorPane pane;

    /**
     * The size of the cases.
     */
    private final int  tileSize = 50;

    /**
     * The position of the top left of the grid relative to the pane.
     */
    private Position gridPos;

    private boolean victoryState = false;


    /** The Constructor of LevelHandler.
     *
     * @param level the instance of the level wanted to be played.
     * @param pane the pane to draw the level on.
     */
    public LevelHandler(Level level, AnchorPane pane){
        this.level = level;
        this.pane = pane;

        this.gridPos = new Position(pane.getPrefWidth()/2 - (double) (level.getGrid().getRow()*tileSize)/2,
                pane.getPrefHeight()/2- (double) (level.getGrid().getCol()*tileSize)/2);
        gridPos = new Position(gridPos.getX() - gridPos.getX()%tileSize, gridPos.getY() - gridPos.getY()%tileSize);
    }

    /** Draws the grid of the level.
     */
    public void drawGrid(){
        ImageView backGrid = new ImageView(String.valueOf(getClass().getResource("Sprites/BackGround_Level.png")));
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
                    case EMPTY :
                        pane.getChildren().add(rectangle);
                        break;
                    case FULL :
                        rectangle.setFill(Paint.valueOf("#000000"));
                        pane.getChildren().add(rectangle);
                        break;
                }
            }
        }
    }

    /** Draws all the ImageBlocks of the level and make them draggable
     *  by applying the {@link #makeDraggable(ImageBlock)} method.
     *
     */
    public void drawImageBlocks(){
        for (ImageBlock imageBlock:
                level.getBlocks()) {
            pane.getChildren().add(imageBlock.getImageView());
            if (!imageBlock.getPlacedState())
                imageBlock.setPosition(imageBlock.getSpawnPos());
            makeDraggable(imageBlock);
        }
    }

    /** Makes the ImageBlock draggable, in function of the grid in the grid bounds or freely outside.
     *
     * @param imageBlock The ImageBlock to make draggable.
     */
    private void makeDraggable(ImageBlock imageBlock){
        ColorAdjust c = new ColorAdjust(1,1,0.2,0);
        DropShadow dropShadow = new DropShadow(50, Color.color(0.7,0.7,0.7));
        Blend blend = new Blend(BlendMode.ADD,c,dropShadow);
        Node node = imageBlock.getImageView();
        node.setOnMousePressed(event ->{

            if(node.getOpacity()<1){return;}

            if(event.getButton() == MouseButton.SECONDARY ){
                node.toFront();
                tryRotate(imageBlock);
            }
            else if(event.getButton() == MouseButton.PRIMARY){
                node.toFront();
                node.setEffect(blend);
                if(imageBlock.getPlacedState()){
                    level.remove(imageBlock,(int) (imageBlock.getLayoutX()-gridPos.getX())/tileSize,
                            (int) (imageBlock.getLayoutY()- gridPos.getY())/tileSize);
                }
                moveBlock(imageBlock,event);
            }
        });
        node.setOnMouseDragged(mouseEvent-> {

            if(node.getOpacity()<1){return;}
            if (mouseEvent.getButton() == MouseButton.PRIMARY)
                moveBlock(imageBlock,mouseEvent);
        });

        node.setOnMouseReleased(event -> {
            node.setEffect(null);

            if(node.getOpacity()<1){return;}

            if (event.getButton() == MouseButton.PRIMARY) {
                if (inGridBounds(new Position(event.getSceneX(), event.getSceneY()))) {
                    if (level.isPlacable(imageBlock, (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize, (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize)){
                        level.place(imageBlock, (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize, (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize);
                        if (level.getPlaced() == level.getBlocks().length){
                            setVictoryState(true);
                            victoryCampaign();
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

    /**
     * Starts and loop the victory animation.
     */
    private void victoryAnimation(){
        Rectangle rectangle = new Rectangle(((level.getGrid().getCol()+2)*tileSize),(level.getGrid().getRow()+2)*tileSize);
        rectangle.setOpacity(0);
        rectangle.setLayoutX(gridPos.getX()-tileSize);
        rectangle.setLayoutY(gridPos.getY()-tileSize);
        pane.getChildren().add(rectangle);

        addVictoryText("VICTORY",635,100);
        addVictoryText("BACK TO MENUS",160,25);
        ImageView blocky = addVictoryGhost("Sprites/Ghost_Blocky.png",200,150);
        ImageView ghost1 = addVictoryGhost("Sprites/Ghost_Magky.png",200,500);
        ImageView ghost2 = addVictoryGhost("Sprites/Ghost_Clode.png",1175,200);
        ImageView ghost3 = addVictoryGhost("Sprites/Ghost_Bluky.png",1100,470);
        ghostDancing(blocky,ghost1,ghost2,ghost3);
    }

    /** Method called when the level is finished.
     * If the level was randomly generated asks if the player want to replay the level by the RE-ROLL button or save it.
     * If the level wasn't random generated links the next level.
     */
    private void victoryCampaign(){

        // is random
        if (level.getName().charAt(0) == 'G'){
            victoryAnimation();
            addVictoryText("RE-ROLL",630,650);
            ImageView buttonSave = ControllerParent.createImageView("Sprites/Button_Save.png",400,1035,730);
            buttonSave.setOnMouseEntered(event ->ControllerParent.imageChanger(buttonSave,"Sprites/Button_Save_Light.png"));
            buttonSave.setOnMouseExited(event ->ControllerParent.imageChanger(buttonSave,"Sprites/Button_Save.png"));

            pane.getChildren().add(buttonSave);

            buttonSave.setOnMouseClicked(clicked -> {
                ControllerParent.randomLevelsSaveMenu(pane, level);

            });


        }
        else {
            victoryAnimation();
            addVictoryText("NEXT LEVEL",1135,650);
            addVictoryText("RESTART",630,650);
            ImageView buttonNext = new ImageView(String.valueOf(AppMenu.class.getResource("Sprites/Button_Arrow_Right.png")));
            pane.getChildren().add(buttonNext);
            buttonNext.setFitHeight(150);
            buttonNext.setLayoutX(1200);
            buttonNext.setLayoutY(730);
            buttonNext.setOnMouseEntered(event ->buttonNext.setImage(new Image(String.valueOf(AppMenu.class.getResource("Sprites/Button_Arrow_RightLight.png")))));
            buttonNext.setOnMouseExited(event ->buttonNext.setImage(new Image(String.valueOf(AppMenu.class.getResource("Sprites/Button_Arrow_Right.png")))));
            buttonNext.setOnMouseClicked(event -> {
                String levelName = nextLevel(level.getName());
                if (levelName == null){
                    ControllerParent.loadScene("Credits.fxml",event);}
                else{
                    loadLevel(levelName,event);
                }
            });
        }


    }

    /** Sets the sprite of the ghost at the (layoutX,layoutY) position.
     *
     * @param sprite the ghost's sprite.
     * @param layoutX the X coordinate to be placed at.
     * @param layoutY the Y coordinate to be placed at.
     * @return The ImageView to be drawn.
     */
    public ImageView addVictoryGhost(String sprite,int layoutX,int layoutY){
        ImageView ghost = new ImageView(String.valueOf(AppMenu.class.getResource(sprite)));
        pane.getChildren().add(ghost);
        ghost.setPreserveRatio(true);
        ghost.setFitWidth(200);
        ghost.setLayoutX(layoutX);
        ghost.setLayoutY(layoutY);

        return ghost;
    }

    /** Write the specified text at the (layoutX,layoutY) coordinates.
     *
     * @param text the text wanted to be written.
     * @param layoutX the X coordinate to be placed at.
     * @param layoutY the Y coordinate to be placed at.
     */
    public void addVictoryText(String text,int layoutX, int layoutY){
        Label label = new Label(text);
        pane.getChildren().add(label);
        label.setTextFill(Paint.valueOf("#ffffff"));
        label.setLayoutX(layoutX);
        label.setLayoutY(layoutY);
        label.setPrefWidth(300);
        label.setWrapText(true);
        label.setUnderline(true);
        label.setAlignment(Pos.TOP_CENTER);
        Font font = new Font("System Bold Italic",50);
        label.setFont(font);
    }

    /** Makes all the ghosts dance.
     */
    public void ghostDancing(ImageView blocky,ImageView ghost1,ImageView ghost2,ImageView ghost3){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                ghost1.setScaleX(ghost1.getScaleX()*(-1));
                if(ghost1.getScene().getWindow()==null){
                    timer.cancel();
                }
            }
        };

        timer.schedule(task,0,250);

        RotateTransition rotateTransition = new RotateTransition(Duration.millis(1000),ghost2);
        rotateTransition.setByAngle(720);
        rotateTransition.setCycleCount(Animation.INDEFINITE);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();

        TranslateTransition ghost3tT = translateAnimation(ghost3, 500,150,0);
        ghost3tT.setCycleCount(Animation.INDEFINITE);
        ghost3tT.setAutoReverse(true);
        ghost3tT.play();


        TranslateTransition blockytT = translateAnimation(blocky, 1000,0,25);
        blockytT.setCycleCount(Animation.INDEFINITE);
        blockytT.setAutoReverse(true);
        blockytT.play();
    }

    /**
     *
     * @param levelName The actual levelName.
     * @return The Next levelName.
     */
    public String nextLevel(String levelName){
        String nextLevel = "Level";

        if (levelName.charAt(0)== 'R'){
            int randomLevelNumber = Character.getNumericValue(levelName.charAt(levelName.length()-1));
            randomLevelNumber = randomLevelNumber+1;
            return (randomLevelNumber == 4?null:String.format("RandomLevel%s",randomLevelNumber));
        }
        int levelNumber = Character.getNumericValue(levelName.charAt(5))*10 + Character.getNumericValue(levelName.charAt(6));

        if (levelNumber == 20){
            return null;
        }

        return String.format("%s%02d",nextLevel,(levelNumber+1));
    }

    /**
     * Makes the ImageBlock vibrate.
     * @param imageBlock The ImageBlock to vibrate.
     */
    public void vibration(ImageBlock imageBlock){
        RotateTransition rotateTransition = new RotateTransition(Duration.millis(50),imageBlock.getImageView());
        rotateTransition.setCycleCount(2);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setByAngle(5);
        rotateTransition.play();
        rotateTransition.setOnFinished(event -> imageBlock.getImageView().setOpacity(1));
    }


    /** Rotates the imageBlock if it can.
     * if it can't, {@link #vibration(ImageBlock)}.
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
                blockPop(imageBlock,100);
                if (!level.isPlacable(imageBlock, posX, posY)) {
                    imageBlock.rotateTo(initialRotateState);
                    vibration(imageBlock);
                }
                posX = (int) (imageBlock.getLayoutX() - gridPos.getX()) / tileSize;
                posY = (int) (imageBlock.getLayoutY() - gridPos.getY()) / tileSize;
                level.place(imageBlock,posX,posY);
            }
            else{
                imageBlock.rotate();

                blockPop(imageBlock,100);
            }
        });


    }

    /** Makes an animation to hide the loading of the level.
     *
     * @param levelName The level to be loaded.
     * @param event The clicked event from where the called to load a level came from.
     */
    public void loadLevel(String levelName,Event event){
        Rectangle transi = new Rectangle(1600,900, Paint.valueOf("222222"));
        transi.setLayoutY(900);
        pane.getChildren().add(transi);

        TranslateTransition transition = translateAnimation(transi, 500,0,-900);
        transition.play();
        transition.setOnFinished(event1 -> {
            FXMLLoader fxmlLoader = new FXMLLoader(AppMenu.class.getResource("Game.fxml"));
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


    /** Makes the ImageBlock disappear in a certain duration (fade).
     *
     * @param imageBlock The ImageBlock to make disappear.
     * @param duration the duration of the fade.
     * @return the FadeTransition of the fading from 0 to 1 opacity.
     */
    private FadeTransition blockDePop(ImageBlock imageBlock,int duration){
        Node node = imageBlock.getImageView();
        FadeTransition fT = new FadeTransition(Duration.millis(duration),node);
        fT.setByValue(1);
        fT.setToValue(0);
        fT.play();

        return fT;
    }

    /** Makes the ImageBlock rea-disappear in a certain duration (fade).
     * Does the opposite of {@link #blockDePop(ImageBlock, int)}.
     *
     * @param imageBlock The ImageBlock to make rea-disappear.
     * @param duration the duration of the fade.
     */
    private void blockPop(ImageBlock imageBlock, int duration){
        Node node = imageBlock.getImageView();
        FadeTransition rePopFT = new FadeTransition(Duration.millis(duration),node);
        rePopFT.setByValue(0);
        rePopFT.setToValue(1);
        rePopFT.play();
    }


    /** Moves the node to the x,y coordinates for a duration.
     *
     * @param node the node to be moved.
     * @param duration the duration of the movement.
     * @param toX X coordinate to go to.
     * @param toY Y coordinate to go to.
     * @return The TranslateTransition.
     */
    public TranslateTransition translateAnimation(Node node, int duration, double toX, double toY){
        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(duration),node);
        translateTransition.setToX(toX);
        translateTransition.setToY(toY);
        return translateTransition;
    }

    /** Makes the blocks go to his spawn Position.
     *
     * @param imageBlock The block going to his spawnPos.
     */
    private void goToSpawnPos(ImageBlock imageBlock)
    {
        FadeTransition fT = blockDePop(imageBlock,200);

        ImageBlock anim = imageBlock.clone();
        anim.rotateTo(imageBlock.getRotateState());
        anim.setPosition(new Position(imageBlock.getLayoutX(),imageBlock.getLayoutY()));

        pane.getChildren().add(anim.getImageView());
        anim.getImageView().setEffect( new ColorAdjust(0,0,-1,0));
        anim.getImageView().setOpacity(0.75);
        imageBlock.getImageView().toFront();
        fT.setOnFinished(finishedEvent -> {
            TranslateTransition tT = translateAnimation(anim.getImageView(),300,
                    imageBlock.getSpawnPos().getX()-imageBlock.getLayoutX(),imageBlock.getSpawnPos().getY()-imageBlock.getLayoutY());
            tT.play();
            tT.setOnFinished(event ->{blockPop(imageBlock,200);
                pane.getChildren().remove(anim.getImageView());});

            imageBlock.setPosition(imageBlock.getSpawnPos());
            for (ImageBlock collided:
                    collide(imageBlock)) {
                if (!collided.getSpawnPos().equals(new Position(collided.getLayoutX(),collided.getLayoutY())))
                    goToSpawnPos(collided);
            }

        });
    }


    /** Makes the block go to his spawnPos.
     *
     * @param imageBlock the block going to his spawnPos.
     */
    private void goToSpawnPosReset(ImageBlock imageBlock)
    { imageBlock.setPosition(imageBlock.getSpawnPos()); }

    /**
     * set all the blocks in the level to their spawnPos with an animation.
     */
    public void reset(){
        ImageView resetImage = new ImageView(String.valueOf(AppMenu.class.getResource("Sprites/Ghost_Reset.png")));

        resetImage.setFitHeight(900);
        resetImage.setFitWidth(1600);
        resetImage.setLayoutX(1600);
        pane.getChildren().add(resetImage);

        TranslateTransition tT = translateAnimation(resetImage, 800,-1600,0);
        tT.play();
        tT.setOnFinished(finishedEvent ->{
            for (ImageBlock imageBlock :
                    level.getBlocks()) {
                if(imageBlock.getPlacedState())
                    level.remove(imageBlock,(int) (imageBlock.getLayoutX()-gridPos.getX())/tileSize,
                            (int) (imageBlock.getLayoutY()- gridPos.getY())/tileSize);
                imageBlock.rotateTo(0);
                goToSpawnPosReset(imageBlock);
                imageBlock.setPlaced(false);
            }
            this.level.setPlaced(0);
            TranslateTransition comeBacktT = translateAnimation(resetImage, 800,1600,0);
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
            if(inGameBlock!=imageBlock &&node.getBoundsInParent().intersects(inGameBlock.getImageView().getBoundsInParent())
                    && !inGameBlock.getPlacedState()){
                colliding.add(inGameBlock);
            }
        }
        return colliding;
    }


    /** Moves the ImageBlock relative to the MouseEvent.
     *
     * @param imageBlock The ImageBlock to be moved
     * @param mouseEvent the mouse event the ImageBlock has to follow.
     */
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


    /** Set up all the SpawnPos of the level's Blocks.
     *
     */
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
