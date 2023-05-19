package code.projetinfo;

import code.projetinfo.controllers.ControllerParent;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import java.lang.reflect.InvocationTargetException;

/**
 * Class used to create levels manually
 */
public class LevelCreator{

    /**
     * The pane to draw elements on
     */
    private final AnchorPane pane;
    /**
     * The levelHandler which supervises all the block and the level
     */

    private final LevelHandler levelHandler;

    /**
     * The level which is the base of the creation
     */
    private final Level level;

    /**
     * the size of the cases
     */
    private final double tileSize=50;
    /**
     * The size of the levels sides
     */
    private final int creatorGridSize = 8;
    /**
     * The maximum of blocks accepted in the level
     */
    private final int maximumBlocks = 12;
    /**
     * A counter for the current blocks in the level
     */
    public static int blocksCounter = 0;
    /**
     * A counter for the current blocks in the level but not in the grid
     */
    public static int inventoryCounter = 0;
    /**
     * The list of the block in the inventory
     */
    public static ImageBlock[] inventoryList = new ImageBlock[3];

    /**
     * the number of columns erased during the preparation of the level
     */
    public int columnErased = 0;
    /**
     * the number of rows erased during the preparation of the level
     */
    public int rowErased = 0;

    /**
     * The constructor of the LevelCreator
     * @param pane the pane to draw the level on
     */
    public LevelCreator(AnchorPane pane){
        this.pane = pane;
        Cases levelCase = new Cases(creatorGridSize,creatorGridSize,CaseState.EMPTY);
        ImageBlock[] imageBlocks = new ImageBlock[maximumBlocks+1];
        this.level = new Level("Created",levelCase, imageBlocks);
        this.levelHandler = new LevelHandler(level,pane);
    }

    /**
     * Returns the current level
     * @return the current level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * Draw a grid over a specified size {@link #creatorGridSize}
     * where which case can be change {@link #levelModifier(MouseEvent, Rectangle)}
     */
    public void drawGrid(){
        ImageView backGrid = new ImageView(String.valueOf(getClass().getResource("Sprites/BackGround_Level.png")));
        backGrid.setLayoutX(levelHandler.getGridPos().getX()-tileSize);
        backGrid.setLayoutY(levelHandler.getGridPos().getY()-tileSize);
        backGrid.setFitWidth(10*tileSize);
        backGrid.setFitHeight(10*tileSize);
        pane.getChildren().add(backGrid);

        for (int i = 0; i <creatorGridSize; i++) {
            for (int j = 0; j < creatorGridSize; j++) {
                Rectangle rectangle = new Rectangle(i*tileSize+levelHandler.getGridPos().getX(),j*tileSize+levelHandler.getGridPos().getY(),tileSize,tileSize);
                rectangle.setFill(Paint.valueOf("#6666fc"));
                rectangle.setStroke(Paint.valueOf("#000000"));
                pane.getChildren().add(rectangle);
                rectangle.setOnMouseClicked(event -> levelModifier(event,rectangle));
            }
        }
    }


    /**
     * Changes the grid in 2 ways:
     * if the case is blue; it became black and the CaseState of the case become FULL
     * if the case is black; it became blue and the CaseState of the case become EMPTY
     * @param event the click event associated to the rectangle
     * @param rectangle the rectangle we want to change
     */
    private void levelModifier(MouseEvent event, Rectangle rectangle){
        Position rectanglePlacement =new Position( (int) ((event.getSceneX()-levelHandler.getGridPos().getX())/tileSize),
                (int) ((event.getSceneY() - levelHandler.getGridPos().getY())/tileSize));

        if(rectangle.getFill().equals(Paint.valueOf("#000000"))){
            rectangle.setFill(Paint.valueOf("#6666fc"));
            rectangle.setStroke(Paint.valueOf("#000000"));
            levelHandler.getLevel().getGrid().set((int)rectanglePlacement.getX(),(int)rectanglePlacement.getY(),CaseState.EMPTY);
        }

        else if(rectangle.getFill().equals(Paint.valueOf("#6666fc"))){
            if(levelHandler.getLevel().getGrid().getState((int)rectanglePlacement.getX(),(int)rectanglePlacement.getY()) == CaseState.FULL){return;}
            rectangle.setFill(Paint.valueOf("#000000"));
            rectangle.setStroke(Paint.valueOf("#ffffff"));
            levelHandler.getLevel().getGrid().set((int)rectanglePlacement.getX(),(int)rectanglePlacement.getY(),CaseState.FULL);
        }
    }

    /**
     * Sets the ImageBlock's position in function of his place in the inventoryList
     * @param imageBlock the ImageBlock to place
     */
    public void setPositionBlock(ImageBlock imageBlock){
        int indexLocated = getFirstIndexNull(inventoryList);
        inventoryList[indexLocated] = imageBlock;
        imageBlock.setPosition(new Position(25+175*indexLocated,225+Math.pow(-1,indexLocated)*50));

    }

    /**
     * Add a block on the level if we click on the specified button
     * @param button button to add a block
     * @throws ClassNotFoundException .
     * @throws NoSuchMethodException .
     * @throws InvocationTargetException .
     * @throws InstantiationException .
     * @throws IllegalAccessException .
     */
    public void addBlock(Node button) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        if(inventoryCounter < 3 && blocksCounter == inventoryCounter + level.getPlaced() && nullNumber() > 1){

        Class<?> blockClass = Class.forName("code.projetinfo.normalBlocks." + button.getId());
        ImageBlock blockChosen = (ImageBlock) blockClass.getDeclaredConstructor(Position.class).newInstance(new Position(0,0));
        pane.getChildren().add(blockChosen.getImageView());
        blockChosen.setSpawnPos(new Position(200,700));
        setPositionBlock(blockChosen);
        level.getBlocks()[getFirstIndexNull(level.getBlocks())] = blockChosen;

        levelHandler.makeDraggable(blockChosen);

        blocksCounter++;
        inventoryCounter++;
        } else if (blocksCounter == 12) {
            ControllerParent.warningMessage(pane, "12 blocks is the maximum on the scene authorized");

        }

        else {
            ControllerParent.warningMessage(pane, "You can't add more blocks.\n" +
                    "Put some blocks in the grid to empty your inventory and than retry");
        }
    }

    /**
     * Calculates the number of object equals to null in the level's list of blocks
     * @return the number of null elements
     */
    public int nullNumber(){
        int nullNumber = 0;
        for (int i = 0; i < level.getBlocks().length; i++) {
            if(level.getBlocks()[i] == null){
                nullNumber++;
            }
        }
        return nullNumber;
    }

    /**
     * Reset the grid graphically  of the level
     */
    public void resetGrid(){
       for (int i = 20; i < pane.getChildren().size(); i++) {
            if (pane.getChildren().get(i).getClass() == Rectangle.class) {
                Rectangle rect = (Rectangle) pane.getChildren().get(i);
                if(rect.getFill().equals(Paint.valueOf("#000000"))){
                rect.setFill(Paint.valueOf("#6666fc"));
                rect.setStroke(Paint.valueOf("#000000"));}
            }
       }
       level.setGrid(new Cases(creatorGridSize, creatorGridSize,CaseState.EMPTY));
    }

    /**
     * Remove all the level's blocks and reset the grid
     */
    public void resetBlocks(){
        level.setBlocks(new ImageBlock[maximumBlocks+1]);
        if(blocksCounter>0){
        pane.getChildren().remove(pane.getChildren().size()-blocksCounter-1,pane.getChildren().size()-1);}
        blocksCounter=0;
        inventoryCounter =0;
        inventoryList = new ImageBlock[3];
    }

    /**
     * Reset graphically all the level
     */
    public void reset(){
        ImageView resetImage = new ImageView(String.valueOf(AppMenu.class.getResource("Sprites/Ghost_Reset.png")));

        resetImage.setFitHeight(900);
        resetImage.setFitWidth(1600);
        resetImage.setLayoutX(1600);
        pane.getChildren().add(resetImage);

        TranslateTransition tT = levelHandler.translateAnimation(resetImage, 800,-1600,0);
        tT.play();
        tT.setOnFinished(finishedEvent ->{
            resetGrid();
            resetBlocks();
            this.level.setPlaced(0);
            TranslateTransition comeBacktT = levelHandler.translateAnimation(resetImage, 800,1600,0);
            comeBacktT.play();
            comeBacktT.setOnFinished(event -> pane.getChildren().remove(resetImage));
        });
    }


    /**
     * Find an ImageBlock's index in an ImageBlock list
     * @param imageBlock the ImageBlock we want to search
     * @param imageBlocks the list we want to inspect
     * @return the index if the ImageBlock is in the list
     *         -1 the imageBlock is not in the list
     */
    public static int findIndexBlock(ImageBlock imageBlock, ImageBlock[] imageBlocks){
        for (int i = 0; i < imageBlocks.length; i++) {
            if(imageBlock == imageBlocks[i]){
                return i;
            }
        }
        return -1;
    }

    /**
     * Find the index of the first object equals to null in a list
     * @param objects the list we want to inspect
     * @return the index of the first "null" in the list
     *         -1 if there is not "null" in the list
     */
    public int getFirstIndexNull(Object[] objects){
        for (int i = 0; i < objects.length; i++) {
            if(objects[i] == null){
                return i;
            }
        }
        return -1;
    }

    /**
     * Verifies the conditions to save the level
     * @return if the level can be saved or not
     */
    public boolean canSave(){
        return blocksCounter == level.getPlaced()&& blocksCounter>1;
    }

    /**
     * Sets the level's grid to a specific way to prepare the level's saving.
     * Removes all the extern empty lines of the level
     * Sets all the EMPTY cases left to SPECIAL
     */
    public void prepareToSave(){
        int leftX = 0;
        int rightX = 0;
        int upY = 0;
        int bottomY = 0;
        //up, down, left, right
        boolean[] posOk = {false, false, false, false};

        Cases grid = level.getGrid();
        for (int i = 0; i < creatorGridSize; i++){
            if (posOk[0] && posOk[1] && posOk[2] && posOk[3]){
                break;
            }
            for (int j = 0; j < creatorGridSize; j++){
                if (posOk[0] && posOk[1] && posOk[2] && posOk[3]){
                    break;
                }
                if (grid.getState(i, j)==CaseState.FULL && !posOk[2]){
                    leftX = i;
                    columnErased = leftX;
                    posOk[2] = true;
                }
                if (grid.getState(creatorGridSize-1-i, j)==CaseState.FULL && !posOk[3]){
                    rightX = creatorGridSize-1-i;
                    posOk[3] = true;
                }
                if (grid.getState(j, i)==CaseState.FULL && !posOk[0]){
                    upY = i;
                    rowErased = upY;
                    posOk[0] = true;
                }
                if (grid.getState(j, creatorGridSize-1-i)==CaseState.FULL && !posOk[1]){
                    bottomY = creatorGridSize-1-i;
                    posOk[1] = true;
                }
            }
        }
        Cases result = new Cases(Math.abs(rightX-leftX)+1, Math.abs(upY-bottomY)+1);
        for (int i = 0; i < result.getRow(); i++){
            for (int j = 0; j < result.getCol(); j++){
                if (grid.getState(leftX+j, upY+i) == CaseState.EMPTY){
                    result.set(j,i,CaseState.SPECIAL);
                }else {
                    result.set(j, i, CaseState.FULL);
                }
            }
        }
        level.setGrid(result);
    }


    /**
     * Removes all the blocks of the grid and then get the grid to save
     * @return the final grid to save
     */
    public Cases gridToSave(){

        prepareToSave();

        for (ImageBlock imageBlock:
                level.getBlocks()) {
            if(imageBlock != null){
                if(imageBlock.getPlacedState()){
                    level.remove(imageBlock,(int) ((imageBlock.getLayoutX()-levelHandler.getGridPos().getX()- this.columnErased*50)/tileSize),
                            (int) ((imageBlock.getLayoutY()- levelHandler.getGridPos().getY()-this.rowErased*50)/tileSize));}}
        }
        return level.getGrid();
    }

    /**
     * Prepares a list of the block that can be interpreted in a true level playable
     * @return an array that contains exactly the blocks of the level
     */
    public ImageBlock[] prepareBlockList(){
        ImageBlock[] prepared = new ImageBlock[blocksCounter];

        for (int i = 0; i < level.getBlocks().length; i++) {
            if(level.getBlocks()[i]!= null){
                prepared[i] = level.getBlocks()[i];
            }
        }
        return prepared;
    }
}

