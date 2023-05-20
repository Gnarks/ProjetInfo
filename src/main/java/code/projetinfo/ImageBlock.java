package code.projetinfo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.lang.reflect.InvocationTargetException;

/**
 * Represents a block that owns an image.
 * It handles the position of the bloc on the screen and is placedState.
 */
public abstract class ImageBlock{
   /**
    * The initial coordinates of the block on the screen.
    */
   private Position spawnPos;
   /**
    * The 2D array used to represent the Block.
    * @see Cases
    */
   private Cases cases;
   /**
    * The coordinates of the middle of the middle case of the block.
    * (depends on the ImageBlock represented)
    */
   private Position midPos;
   /**
    * Integer in [0,3] that represent the rotateState of the Block.
    */
   private int rotateState;
   /**
    * The ImageView used to draw the Block on the screen.
    */
   private final ImageView imageView;

   /**
    * Boolean representing if the block is placed on the grid or not.
    */
   private boolean isPlaced;


   /** The main constructor of ImageBlock.
    *
    * @param layoutPos the coordinates of the block on the scene.
    * @param cases the representation of the block with CaseStates.
    * @param midPos the coordinates of the middle of the middle case in the scene.
    * @param imageView the imageView of the block.
    * @see Cases
    */
   public ImageBlock(Position layoutPos, Cases cases, Position midPos, ImageView imageView,int width,int height){
      this.cases = cases;
      this.midPos = midPos;
      this.rotateState = 0;
      this.imageView = imageView;
      imageView.setFitWidth(width);
      imageView.setFitHeight(height);
      this.isPlaced = false;
      this.setPosition(layoutPos);
   }
   /**
    * Rotates the Cases of the ImageBlock to the specified RotateState.
    */
   protected void rotateCasesTo(int newRotateState){
      Cases newcases = new Cases(this.cases.getRow(), this.cases.getCol(), CaseState.EMPTY);

      Position startingMidTile = new Position((midPos.getX()-25)/50,(midPos.getY()-25)/50);
      Position newMidTilePos = startingMidTile.clone();

      for (int k = (newRotateState - rotateState +4)%4; k>0 ; k--) {
         newMidTilePos = new Position(cases.getRow() - newMidTilePos.getY() -1, newMidTilePos.getX());

         for (int i = 0; i < cases.getCol(); i++){
            for (int j = 0; j < cases.getRow(); j++){
               newcases.setState(j, i, cases.getState(i, cases.getRow()-j-1));
            }
         }
         this.cases = newcases;
         newcases = new Cases(cases.getRow(), cases.getCol(), CaseState.EMPTY);
      }
   }
   /** Returns the spawnPos.
    *
    * @return the spawnPosition.
    */
   public Position getSpawnPos(){
      return spawnPos.clone();
   }

   /** Sets the spawnPos of the ImageBlock by the specified Position.
    *
    * @param newSpawnPos the new spawnPos to be set.
    */
   public void setSpawnPos(Position newSpawnPos){
      this.spawnPos = newSpawnPos;
   }

   /** Returns the X coordinates of the middle case.
    *
    * @return the midPosition X of the block.
    */
   public double getMidX() {return midPos.getX();}

   /** Returns the Y coordinates of the middle case.
    *
    * @return the midPosition Y of the block.
    */
   public double getMidY() {return midPos.getY();}

   /**Returns the actual rotateState.
    *
    * @return the actual rotate state.
    */
   public int getRotateState() {
      return rotateState;
   }
   /** Sets the rotateState modulo 4.
    *
    * @param rotateState the rotate state to bee set.
    */
   protected void setRotateState(int rotateState) {
      this.rotateState = rotateState%4;
   }

   /** Returns the number of rows.
    *
    * @return the number of rows.
    */
   public int getRows(){
      return this.cases.getRow();
   }
   /** Returns the number of columns.
    *
    * @return the number of columns.
    */
   public int getCols(){
      return this.cases.getCol();
   }

   /** Returns the CaseState of the (x,y) Position in the
    * 2D array representing the ImageBlock.
    *
    * @param x column number.
    * @param y row number.
    * @return the CaseState of the (x,y) tile of the block.
    */
   public CaseState getState(int x, int y){
      return cases.getState(x, y);
   }
   /**Returns the instance of the imageView of the block.
    *
    * @return the imageView of the block.
    */
   public ImageView getImageView() {
      return imageView;
   }

   /** function called to make the block rotate to the specified rotateState graphically.
    * it depends on the normalBlock that calls the function.
    *
    * @param newRotateState the wanted rotateState.
    * @param generalUrl the url of the image without the rotateState and the ".png".
    */
      protected void rotateGraphicallyTo(int newRotateState, String generalUrl){

      Position newMidTilePos = new Position(0,0);
      int initialX = (int) (midPos.getX()-25)/50;
      int initialY = (int) (midPos.getY()-25)/50;

      switch ((newRotateState - rotateState +4)%4){
         case 1:
            newMidTilePos = new Position(cases.getCol() - initialY-1,initialX);
            break;
         case 2:
            newMidTilePos = new Position(getCols() - initialX-1,
                    getRows() - initialY - 1);
            break;
         case 3:
            newMidTilePos = new Position(initialY,getRows()-initialX-1);
            break;
         case 0:
            newMidTilePos = new Position(initialX,initialY);
            break;
      }

      Position change = new Position((initialX - newMidTilePos.getX())*50,
              (initialY - newMidTilePos.getY())*50);
      // during rotation blocks changes position
      // look at the change in position between the initial and new position

      midPos = new Position(newMidTilePos.getX()*50+25,newMidTilePos.getY()*50+25);
      this.setPosition(new Position(this.getLayoutX()+change.getX(),this.getLayoutY()+change.getY()));

      imageView.setImage(new Image(String.valueOf(ImageBlock.class.getResource(String.format("%s%s.png",generalUrl,newRotateState%4)))));

      double height = imageView.getFitHeight();
      imageView.setFitHeight(((rotateState - newRotateState+4)%2 == 0?height: getImageView().getFitWidth()));
      imageView.setFitWidth(((rotateState - newRotateState+4)%2 == 0? imageView.getFitWidth():height));
   }

   /** Rotates the block to the specified rotateState in Frontend AND Backend.
    * a single rotateState change corresponds to a 90-degree turn to the right.
    *
    * @param newRotateState the wanted rotateState to be rotated to.
    */
   public abstract void rotateTo(int newRotateState);

   /**
    * Rotates the block to the next rotateState in Frontend AND Backend.
    * a single rotateState change corresponds to a 90-degree turn to the right.
    */
   public abstract void rotate();

   /** Sets the placedState.
    *
    * @param state the placedState to be set.
    */
   public void setPlaced(boolean state){
      this.isPlaced = state;
   }

   /** Returns the placedState.
    *
    * @return the actual placedState.
    */
   public boolean getPlacedState(){
      return this.isPlaced;
   }

   /** Sets the coordinates of the ImageView of the current ImageBlock.
    *
    * @param layoutPosition the coordinates to set the ImageView to.
    */
   public void setPosition(Position layoutPosition){
      imageView.setLayoutX(layoutPosition.getX());
      imageView.setLayoutY(layoutPosition.getY());
   }

   /** Returns the X coordinate of the ImageView of the current ImageBlock.
    *
    * @return The X coordinate on the scene.
    */
   public double getLayoutX(){
      return imageView.getLayoutX();
   }

   /** Returns the Y coordinate of the ImageView of the current ImageBlock.
    *
     * @return The Y coordinate on the scene.
    */
   public double getLayoutY(){
      return imageView.getLayoutY();
   }

   /** Returns the Cases representing the ImageBlock.
    *
    * @return the cases representing the imageBlock.
    */
   public Cases getGrid(){
      return cases;
   }

   /** Returns a clone of the actual ImageBlock.
    *
    * @return a clone of the actual ImageBlock.
    */

   @Override
   public ImageBlock clone(){
      ImageBlock cloned;
      try {
         cloned = getClass().getDeclaredConstructor(Position.class).newInstance(new Position(getLayoutX(),getLayoutY()));
         cloned.rotateTo(rotateState);
         cloned.setRotateState(rotateState);

      } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
         throw new RuntimeException(e);
      }
      return cloned;
   }
}