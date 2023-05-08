package code.projetinfo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Represents a block that owns an image.
 * It handles the position of the bloc on the screen and in the internal
 * grid.
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
    * (depends on the size of the cases of the grid)
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
    * The width of the block.
    * (depends on the size of the cases of the grid)
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
    *
    */
   protected void rotateCasesTo(int newRotateState){
      Cases newcases = new Cases(this.cases.getRow(), this.cases.getCol(), CaseState.EMPTY);

      Position startingMidTile = new Position((midPos.getX()-25)/50,(midPos.getY()-25)/50);
      Position newMidTilePos = new Position(startingMidTile.getX(),startingMidTile.getY());

      for (int k = (newRotateState - rotateState +4)%4; k>0 ; k--) {
         newMidTilePos = new Position(cases.getRow() - newMidTilePos.getY() -1, newMidTilePos.getX());

         for (int i = 0; i < cases.getCol(); i++){
            for (int j = 0; j < cases.getRow(); j++){
               newcases.set(j, i, cases.getState(i, cases.getRow()-j-1));
            }
         }
         this.cases = newcases;
         newcases = new Cases(cases.getRow(), cases.getCol(), CaseState.EMPTY);
      }
   }
   /**
    * @return the spawnPosition of the ImageBlock.
    */
   public Position getSpawnPos(){
      return new Position(spawnPos.getX(), spawnPos.getY());
   }

   public void setSpawnPos(Position newSpawnPos){
      this.spawnPos = newSpawnPos;
   }

   /**
    * @return the midPosition X of the block.
    */
   public double getMidX() {return midPos.getX();}

   /**
    * @return the midPosition Y of the block.
    */
   public double getMidY() {return midPos.getY();}
   /**
    * @return the actual rotate state.
    */
   public int getRotateState() {
      return rotateState;
   }
   /**
    * set the rotateState modulo 4.
    * @param rotateState the rotate state.
    */
   protected void setRotateState(int rotateState) {
      this.rotateState = rotateState%4;
   }

   /**
    * @return the number of rows of the block.
    */
   public int getRows(){
      return this.cases.getRow();
   }
   /**
    * @return the number of columns of the block.
    */
   public int getCols(){
      return this.cases.getCol();
   }
   /**
    * @param x column number
    * @param y row number
    * @return the CaseState of the (x,y) tile of the block
    */
   public CaseState getState(int x, int y){
      return cases.getState(x, y);
   }
   /**
    * @return the imageView of the block.
    */
   public ImageView getImageView() {//todo changer ce getter et établir de setter pour les layoutX et Y
      return imageView;
   }

   /** function called to make the block rotate to the specified rotateState graphically.
    * it depends on the normalBlock that calls the function.
    * has to be called after the rotateCasesTo method.
    * * @param newRotateState the wanted rotateState
    * @param generalUrl the url of the image without the rotateState and the ".png"
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
    * @param newRotateState the wanted rotateState
    */

   public abstract void rotateTo(int newRotateState);

   /**
    * Rotates the block to the next rotateState in Frontend AND Backend.
    * a single rotateState change corresponds to a 90-degree turn to the right.
    */
   public abstract void rotate();

   public void setPlaced(boolean state){
      this.isPlaced = state;
   }

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

   /**
    *
    * @return The X coordinate on the scene.
    */
   public double getLayoutX(){
      return imageView.getLayoutX();
   }

   /**
    *
     * @return The Y coordinate on the scene.
    */
   public double getLayoutY(){
      return imageView.getLayoutY();
   }
}