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
   private final Position spawnPos;
   private Cases cases;
   private Position midPos;
   private int rotateState;
   private final ImageView imageView;
   private final int width;
   private final int height;



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
   }
   /**
    * This method rotate the selected block:
    * - In the internal Grid
    * - NOT Graphically on the screen
    */
   public void rotateCases(){
      Cases newcases = new Cases(this.cases.getRow(), this.cases.getCol(), CaseState.EMPTY);
      for (int i = 0; i < this.cases.getCol(); i++){
         for (int j = 0; j < this.cases.getRow(); j++){
            newcases.set(j, i, cases.getState(i, this.cases.getRow()-j-1));
         }
      }
      this.cases = newcases;
   }
   /**
    * @return the spawnPosition of the block
    */
   public Position getSpawnPos(){
      return spawnPos;
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
   public void setRotateState(int rotateState) {
      this.rotateState = rotateState%4;
   }

   /**
    * rotate the block graphically.
    */
   public abstract void rotateGraphic();
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
   public ImageView getImageView() {
      return imageView;
   }
   /** function called to make a single graphical rotation of 90 degrees to the right.
    * it depends on the normalBlock that calls the function.
    * @param moveX delta X
    * @param moveY delta Y
    * @param generalUrl the url of the image without the rotateState and the ".png"
    */
   protected void rotateGraphicStep(int moveX,int moveY, String generalUrl){
      imageView.setLayoutX(imageView.getLayoutX() +moveX);
      imageView.setLayoutY(imageView.getLayoutY() +moveY);
      midPos = new Position(midPos.getX() -moveX, midPos.getY() - moveY);

      imageView.setImage(new Image(String.valueOf(ImageBlock.class.getResource(String.format("%s%s.png",generalUrl,rotateState)))));

      imageView.setFitHeight((rotateState%2 == 0?height:width));
      imageView.setFitWidth((rotateState%2 == 0?width:height));
   }

   public Position getMidPos() {
      return midPos;
   }

   public Position[] dispatchPos(ImageBlock[] blockslist){
      double screenheight = 607.5;
      Position currentpos = new Position(10, 10);
      Position[] result = new Position[blockslist.length];

      for (int i = 0; i < blockslist.length/2; i++){
         result[i] = currentpos;
         if (blockslist[i].getMidY() + currentpos.getY() > screenheight){
            currentpos.setX(currentpos.getX() + 300);
            currentpos.setY(10);
         }
      }

      currentpos = new Position(1000, 10);
      for (int i = blockslist.length/2; i < blockslist.length; i++){
         result[i] = currentpos;
         if (blockslist[i].getMidY() + currentpos.getY() > screenheight){
            currentpos.setX(currentpos.getX() + -300);
            currentpos.setY(10);
         }
      }
      return result;
   }

}