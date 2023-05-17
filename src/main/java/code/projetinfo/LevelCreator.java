package code.projetinfo;

import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.lang.reflect.InvocationTargetException;

public class LevelCreator {

    private final AnchorPane pane;

    private LevelHandler levelHandler;

    private Level level;

    private ImageBlock[] imageBlocks = new ImageBlock[]{};

    private final double tileSize=50;

    private Cases levelCase;




    public LevelCreator(AnchorPane pane, int creatorGridSize){
        this.pane = pane;
        this.levelCase = new Cases(creatorGridSize,creatorGridSize,CaseState.EMPTY);
        this.level = new Level("Created",levelCase,imageBlocks);
        this.levelHandler = new LevelHandler(level,pane);
    }


    public Cases getLevelCase() {
        return levelCase;
    }

    public LevelHandler getLevelHandler() {
        return levelHandler;
    }

    public void drawGrid(){
        ImageView backGrid = new ImageView(String.valueOf(getClass().getResource("Sprites/BackGround_Level.png")));
        backGrid.setLayoutX(levelHandler.getGridPos().getX()-tileSize);
        backGrid.setLayoutY(levelHandler.getGridPos().getY()-tileSize);
        backGrid.setFitWidth(10*tileSize);
        backGrid.setFitHeight(10*tileSize);


        pane.getChildren().add(backGrid);
        for (int i = 0; i <8; i++) {
            for (int j = 0; j < 8; j++) {
                Rectangle rectangle = new Rectangle(i*tileSize+levelHandler.getGridPos().getX(),j*tileSize+levelHandler.getGridPos().getY(),tileSize,tileSize);
                rectangle.setFill(Paint.valueOf("#6666fc"));
                rectangle.setStroke(Paint.valueOf("#000000"));
                pane.getChildren().add(rectangle);
                rectangle.setOnMouseClicked(event -> {
                    levelModificator(event,rectangle);
                    levelCase.show();

                });
            }
        }
    }

    private void levelModificator(MouseEvent event, Rectangle rectangle){
        if(rectangle.getFill().equals(Paint.valueOf("#000000"))){
            rectangle.setFill(Paint.valueOf("#6666fc"));
            rectangle.setStroke(Paint.valueOf("#000000"));
            levelCase.set((int) ((event.getSceneX() - levelHandler.getGridPos().getX())/tileSize),(int) ((event.getSceneY() - levelHandler.getGridPos().getY())/tileSize),CaseState.EMPTY);
        }

        else if(rectangle.getFill().equals(Paint.valueOf("#6666fc"))){
            rectangle.setFill(Paint.valueOf("#000000"));
            rectangle.setStroke(Paint.valueOf("#ffffff"));
            levelCase.set((int) ((event.getSceneX() - levelHandler.getGridPos().getX())/tileSize),(int) ((event.getSceneY() - levelHandler.getGridPos().getY())/tileSize),CaseState.FULL);
        }
    }

    public void addBlock(Node button) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> blockClass = Class.forName("code.projetinfo.normalBlocks." + button.getId());
        ImageBlock blockChosen = (ImageBlock) blockClass.getDeclaredConstructor(Position.class).newInstance(new Position(0,0));
        pane.getChildren().add(blockChosen.getImageView());
        blockChosen.setPosition(new Position(200,200));
        levelHandler.makeDraggable(blockChosen);

    }
}

