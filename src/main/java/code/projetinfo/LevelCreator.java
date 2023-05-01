package code.projetinfo;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class LevelCreator {

    private Pane pane;

    private Position gridPos;

    private double tileSize=50;

    private int creatorGridSize;

    private Cases levelCase;




    public LevelCreator(AnchorPane pane,int creatorGridSize){
        this.pane = pane;
        this.creatorGridSize = creatorGridSize;
        this.gridPos = new Position(pane.getPrefWidth()/2 -(creatorGridSize*tileSize)/2,
                pane.getPrefHeight()/2-(creatorGridSize*tileSize)/2);
        gridPos = new Position(gridPos.getX() - gridPos.getX()%tileSize, gridPos.getY() - gridPos.getY()%tileSize);
        this.levelCase = new Cases(creatorGridSize,creatorGridSize,CaseState.SPECIAL);
    }


    public void drawGrid(){
        ImageView backGrid = new ImageView(String.valueOf(getClass().getResource("Sprites/BackGridLevel.png")));
        backGrid.setLayoutX(gridPos.getX()-tileSize);
        backGrid.setLayoutY(gridPos.getY()-tileSize);
        backGrid.setFitWidth((creatorGridSize+2)*tileSize);
        backGrid.setFitHeight((creatorGridSize+2)*tileSize);


        pane.getChildren().add(backGrid);
        for (int i = 0; i <creatorGridSize; i++) {
            for (int j = 0; j < creatorGridSize; j++) {
                Rectangle rectangle = new Rectangle(i*tileSize+gridPos.getX(),j*tileSize+gridPos.getY(),tileSize,tileSize);
                rectangle.setFill(Paint.valueOf("#ffffff"));
                rectangle.setStroke(Paint.valueOf("#000000"));
                pane.getChildren().add(rectangle);
                rectangle.setOnMouseClicked(event -> {
                    levelModificator(event,rectangle);
                    levelCase.show();

                });
            }
        }
    }

    private void levelModificator(MouseEvent event,Rectangle rectangle){
        if(rectangle.getFill().equals(Paint.valueOf("#ffffff"))){
            rectangle.setFill(Paint.valueOf("#6666fc"));
            levelCase.set((int) ((event.getSceneX() - gridPos.getX())/tileSize),(int) ((event.getSceneY() - gridPos.getY())/tileSize),CaseState.EMPTY);
            return;
        }
        if(rectangle.getFill().equals(Paint.valueOf("#6666fc"))){
            rectangle.setFill(Paint.valueOf("#000000"));
            rectangle.setStroke(Paint.valueOf("#ffffff"));
            levelCase.set((int) ((event.getSceneX() - gridPos.getX())/tileSize),(int) ((event.getSceneY() - gridPos.getY())/tileSize),CaseState.FULL);
            return;
        }
        if(rectangle.getFill().equals(Paint.valueOf("#000000"))){
            rectangle.setFill(Paint.valueOf("#ffffff"));
            rectangle.setStroke(Paint.valueOf("#000000"));
            levelCase.set((int) ((event.getSceneX() - gridPos.getX())/tileSize),(int) ((event.getSceneY() - gridPos.getY())/tileSize),CaseState.SPECIAL);
        }
    }

    public void fillLevelState(CaseState state){
        Paint fill = null;
        Paint stroke = Paint.valueOf("#000000");
        if(state == CaseState.SPECIAL ){
            fill = Paint.valueOf("#ffffff");

        }
        else if(state == CaseState.FULL ){
            fill = Paint.valueOf("#000000");
            stroke = Paint.valueOf("#ffffff");
        }
        else if(state == CaseState.EMPTY ){
            fill = Paint.valueOf("#6666fc");
        }

        levelCase.fill(state);
        int indexLastRect= 0;
        for (int i = pane.getChildren().size()-1; i > 2; i--) {
            if(pane.getChildren().get(i).getClass() == Rectangle.class){
                Rectangle rectangle = (Rectangle) pane.getChildren().get(i);
                rectangle.setFill(fill);
                rectangle.setStroke(stroke);
                indexLastRect = i;
                System.out.println(rectangle == pane.getChildren().get(i));
            }
            else if(i == indexLastRect-1){
                break;
            }
        }
    }

    public void setCreatorGridSize(int size){
        int indexLastRect = 0;
        for (int i = pane.getChildren().size()-1; i > 2; i--) {
            if(pane.getChildren().get(i).getClass() == Rectangle.class){
                pane.getChildren().remove(i);
                indexLastRect = i;
            }
            else if(i == indexLastRect-1){
                pane.getChildren().remove(i);
                break;
            }
        }
        this.creatorGridSize = size;
        this.gridPos = new Position(pane.getPrefWidth()/2 -(creatorGridSize*tileSize)/2,
                pane.getPrefHeight()/2-(creatorGridSize*tileSize)/2);
        gridPos = new Position(gridPos.getX() - gridPos.getX()%tileSize, gridPos.getY() - gridPos.getY()%tileSize);
        drawGrid();
    }

    public void reset(Label FullGridState){

        if(FullGridState.getText().equals("Neutral")){
            fillLevelState(CaseState.EMPTY);
        }

        else if(FullGridState.getText().equals("Empty")){
            fillLevelState(CaseState.SPECIAL);
        }

        else if(FullGridState.getText().equals("Full")){
            fillLevelState(CaseState.FULL);
        }
    }
}
