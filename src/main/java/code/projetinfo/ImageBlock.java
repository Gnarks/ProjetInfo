package code.projetinfo;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * Represents block that owns an image.
 * It handles the position of the bloc on the screen and in the internal
 * grid.
 */
public abstract class ImageBlock{
   /**
    * spawnPos : The initial coordinates of the block on the screen.
    * cases : A matrix that describes each block.
    * midPos : Position of the middle of the middle block.
    * rotateState : integer in [0,3] that represents the rotation state.
    */
   private Position spawnPos;
   private Cases cases;
   private Position midPos;
   private int rotateState;
   private final ImageView imageView;
   private final int width;
   private final int height;
   private boolean isPlaced;


   /** The main constructor of blocks
    *
    * @param spawnPos the Position at which the block will spawn in the scene.
    * @param cases the representation of the block with CaseStates.
    * @param midPos the Position of the middlePosition in the scene.
    * @param imageView the imageView of the block.
    * @see Cases
    *
    */
   public ImageBlock(Position spawnPos, Cases cases, Position midPos, ImageView imageView,int width,int height){
      this.spawnPos = spawnPos;
      this.cases = cases;
      this.midPos = midPos;
      this.rotateState = 0;
      this.imageView = imageView;
      this.width = width;
      this.height = height;
      imageView.setFitWidth(width);
      imageView.setFitHeight(height);
      this.isPlaced = false;
   }
   /**
    * This method rotate the selected block:
    * - In the internal Grid
    * - NOT Graphically on the screen
    */
   protected void rotateCasesTo(int newRotateState){
      Cases newcases = new Cases(this.cases.getRow(), this.cases.getCol(), CaseState.EMPTY);

      for (int k = (newRotateState - rotateState +4)%4; k>0 ; k--) {
         for (int i = 0; i < this.cases.getCol(); i++){
            for (int j = 0; j < this.cases.getRow(); j++){
               newcases.set(j, i, cases.getState(i, this.cases.getRow()-j-1));
            }
         }
         this.cases = newcases;
         newcases = new Cases(this.cases.getRow(), this.cases.getCol(), CaseState.EMPTY);
      }
   }
   /**
    * @return the spawnPosition of the block
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
    * @param changes the array of changes of each rotateState compared to the initial rotateState
    * @param newRotateState the wanted rotateState
    * @param generalUrl the url of the image without the rotateState and the ".png"
    */
   protected void rotateGraphicallyTo(Position[] changes, int newRotateState, String generalUrl){
      double x = - changes[rotateState].getX() + changes[newRotateState%4].getX();
      double y = - changes[rotateState].getY() + changes[newRotateState%4].getY();

      imageView.setLayoutX(imageView.getLayoutX() +x);
      imageView.setLayoutY(imageView.getLayoutY() +y);

      midPos = new Position(midPos.getX() - x, midPos.getY()-y);

      imageView.setImage(new Image(String.valueOf(ImageBlock.class.getResource(String.format("%s%s.png",generalUrl,newRotateState%4)))));

      imageView.setFitHeight((newRotateState%2 == 0?height:width));
      imageView.setFitWidth((newRotateState%2 == 0?width:height));
   }

   /** rotates the block to the specified rotateState in Frontend AND Backend.
    * a single rotateState change corresponds to a 90-degree turn to the right.
    * @param newRotateState the wanted rotateState
    */

   public abstract void rotateTo(int newRotateState);

   /**
    * rotates the block to the next rotateState in Frontend AND Backend.
    * a single rotateState change corresponds to a 90-degree turn to the right.
    */
   public abstract void rotate();

   public void setPlaced(boolean state){
      this.isPlaced = state;
   }

   public boolean getPlacedState(){
      return this.isPlaced;
   }
}