package code.projetinfo;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.InvocationTargetException;

public class LevelCreator{

    private final AnchorPane pane;

    private final LevelHandler levelHandler;

    private final Level level;

    private final double tileSize=50;

    private final int creatorGridSize = 8;

    private final int maximumBlocks = 12;


    public static int blocksCounter = 0;




    public LevelCreator(AnchorPane pane){
        this.pane = pane;
        Cases levelCase = new Cases(creatorGridSize,creatorGridSize,CaseState.EMPTY);
        ImageBlock[] imageBlocks = new ImageBlock[maximumBlocks+1];
        this.level = new Level("Created",levelCase, imageBlocks);
        this.levelHandler = new LevelHandler(level,pane);
    }


    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public Level getLevel() {
        return level;
    }

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
                rectangle.setOnMouseClicked(event -> levelModificator(event,rectangle));
            }
        }
    }

    private void levelModificator(MouseEvent event, Rectangle rectangle){
        Position rectanglePlacement =new Position( (int) ((event.getSceneX() - levelHandler.getGridPos().getX())/tileSize),(int) ((event.getSceneY() - levelHandler.getGridPos().getY())/tileSize));


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
        level.getGrid().show();
    }



    public void addBlock(Node button) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        if(level.getBlocks()[11] == null){
        Class<?> blockClass = Class.forName("code.projetinfo.normalBlocks." + button.getId());
        ImageBlock blockChosen = (ImageBlock) blockClass.getDeclaredConstructor(Position.class).newInstance(new Position(200,200));
        pane.getChildren().add(blockChosen.getImageView());
        blockChosen.setSpawnPos(new Position(200,700));
        level.getBlocks()[getIndex(level.getBlocks())] = blockChosen;

        levelHandler.makeDraggable(blockChosen);

        blocksCounter++;

        for (ImageBlock imageBlock : level.getBlocks()) {
            if (imageBlock != null) {
                System.out.println(imageBlock.getClass());
                System.out.println(imageBlock.getPlacedState());
            }
        }}
    }

    public void resetGrid(){
       for (int i = 20; i < pane.getChildren().size(); i++) {
            if (pane.getChildren().get(i).getClass() == Rectangle.class) {
                Rectangle rect = (Rectangle) pane.getChildren().get(i);
                rect.setFill(Paint.valueOf("#6666fc"));
                rect.setStroke(Paint.valueOf("#000000"));
            }
       }
       level.setGrid(new Cases(creatorGridSize, creatorGridSize,CaseState.EMPTY));
    }

    public void reset(){
        resetGrid();
        for (ImageBlock imageBlock:
                level.getBlocks()) {
            if(imageBlock != null){
                if(imageBlock.getPlacedState()){
            level.remove(imageBlock,(int) ((imageBlock.getLayoutX()-levelHandler.getGridPos().getX())/tileSize),
                    (int) ((imageBlock.getLayoutY()- levelHandler.getGridPos().getY())/tileSize));}}
        }

        level.setBlocks(new ImageBlock[maximumBlocks+1]);
        if(blocksCounter>0){
        pane.getChildren().remove(pane.getChildren().size()-blocksCounter,pane.getChildren().size());}
        blocksCounter=0;

    }

    public static int findBlock(ImageBlock imageBlock,ImageBlock[] imageBlocks){
        for (int i = 0; i < imageBlocks.length; i++) {
            if(imageBlock == imageBlocks[i]){
                return i;
            }
        }
        return 0;
    }

    public int getIndex(Object[] objects){
        for (int i = 0; i < objects.length; i++) {
            if(objects[i] == null){
                return i;
            }
        }
        return -1;
    }

    public void prepareToSave(){
        Position rightPos = new Position(7,7);
        int leftX = 0;
        int rightX = 0;
        int upY = 0;
        int bottomY = 0;
        //up, down, left, right
        boolean[] posOk = {false, false, false, false};

        Cases grid = level.getGrid();
        for (int i = 0; i < 8; i++){
            if (posOk[0] && posOk[1] && posOk[2] && posOk[3]){
                break;
            }
            for (int j = 0; j < 8; j++){
                if (posOk[0] && posOk[1] && posOk[2] && posOk[3]){
                    break;
                }
                if (grid.getState(i, j)==CaseState.FULL && !posOk[2]){
                    leftX = i;
                    posOk[2] = true;
                }
                if (grid.getState(7-i, j)==CaseState.FULL && !posOk[3]){
                    rightX = 7-i;
                    posOk[3] = true;
                }
                if (grid.getState(j, i)==CaseState.FULL && !posOk[0]){
                    upY = i;
                    posOk[0] = true;
                }
                if (grid.getState(j, 7-i)==CaseState.FULL && !posOk[1]){
                    bottomY = 7-i;
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
        level.getGrid().show();
    }

    public boolean canSave(){
        System.out.println(level.getPlaced());
        System.out.println(blocksCounter);
        return blocksCounter == level.getPlaced()&& blocksCounter>1;
    }

    public Cases gridToSave(){
        prepareToSave();
        for (ImageBlock imageBlock:
                level.getBlocks()) {
            if(imageBlock != null){
                if(imageBlock.getPlacedState()){
                    level.remove(imageBlock,(int) ((imageBlock.getLayoutX()-levelHandler.getGridPos().getX())/tileSize),
                            (int) ((imageBlock.getLayoutY()- levelHandler.getGridPos().getY())/tileSize));}}
        }
        level.getGrid().show();
        return new Cases(level.getGrid().getCases());
    }

    public ImageBlock[] prepareBlockList(){
        ImageBlock[] prepared = new ImageBlock[blocksCounter];
        for (int i = 0; i < level.getBlocks().length; i++) {
            if(level.getBlocks()[i]!= null){
                prepared[i] = level.getBlocks()[i];
                System.out.println(prepared[i]);
            }
        }
        return prepared;
    }
}

