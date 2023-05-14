package code.projetinfo;

import code.projetinfo.normalBlocks.PlagueDoc;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

/**
 * Class used to generate a random Level with certain parameters.
 */
public class LevelGenerator {

    /**
     * GenerateException will be thrown if there is an issue in the generate method
     *  with the specified issue in the message.
     *  Exemple of message :
     *  - not enough blocks selected to place only different blocks.
     *  - couldn't place the least amount of blocks.
     */
    public static class GenerateException extends Exception {
        public GenerateException(String errorMessage) {
            super(errorMessage);
        }
    }


    /** Intern class used to represent a possible position to place a block
     *  for a certain rotateState with a certain score.
     */
    static class PossiblePlacementData {
        /**
         * The position to place the Block to.
         */
        Position position;
        /**
         * The rotateState of the Block.
         */
        int rotateState;

        /**
         * The addition of all the direct distance between the Most Significant Position (MSPos) and every
         * full case of the block.
         * the direct distance D between the MSPos and a full case is calculated by :
         * D = sqrt(X²+Y²)
         * X : the distance between the MSPos and the full case in the X axis.
         * Y : the distance between the MSPos and the full case in the Y axis.
         */
        double score;
        PossiblePlacementData(Position position, int rotateState, double score){
            this.position = position;
            this.rotateState = rotateState;
            this.score = score;
        }
    }

    private final Class<?>[] imageBlockClasses;
    private final int leastToPlace;
    private final int maxToPlace;

    private Cases grid;

    boolean alwaysDifferent;


    /** Constructor of the LevelGenerator Class.
     *
     * @param imageBlocksClasses an array of ImageBlock classes.
     * @param leastToPlace the least amount of blocks wanted to be placed.
     * @param maxToPlace the maximum amount of blocks wanted to be placed.
     * @param alwaysDifferent boolean specifying if the block placed must be always different.
     */
    public LevelGenerator(Class<ImageBlock>[] imageBlocksClasses, int leastToPlace,int maxToPlace,boolean alwaysDifferent){

        this.imageBlockClasses =imageBlocksClasses;
        this.leastToPlace = leastToPlace;
        this.maxToPlace = maxToPlace;
        this.alwaysDifferent = alwaysDifferent;
    }

    /**
     *
     * @return the Generated Level.
     * @throws GenerateException if an exception occurs in the generation of the Level.
     */
    public Level generate() throws GenerateException {

        if (leastToPlace > imageBlockClasses.length && alwaysDifferent){
            throw new GenerateException("not enough blocks selected to place only different blocks");
        }

        if (imageBlockClasses.length == 1  && imageBlockClasses[0].equals(PlagueDoc.class)){
            throw new GenerateException("PlagueDoc can't create a random level by is own.");
        }

        // the Most Significant Position is initialized at (0,0).
        Position MSPos = new Position(0,0);
        Random rnd = new Random();
        // initialize a grid on which the block will be placed as a 8x8 grid simply because it's big enough.
        grid = new Cases(8,8, CaseState.EMPTY);


        // the list of block we're currently trying to place.
        ArrayList<Class<?>> tryingToPlace = new ArrayList<>(Arrays.asList(imageBlockClasses));

        // the array of block used in the generation of the level.
        ArrayList<ImageBlock> blocksUsed = new ArrayList<>();

        //loop trying to place the maximum asked number of block.
        while (blocksUsed.size() < maxToPlace && tryingToPlace.size() >0){
            int randomInt = rnd.nextInt(tryingToPlace.size());
            //arrayList to store all the PossiblePlacements for a single block.
            ArrayList<PossiblePlacementData> possiblePlacements = new ArrayList<>();

            // chooses a random ImageBlock in the imageBlockClasses list.
            ImageBlock currentBlock;
            try {
                currentBlock = (ImageBlock) tryingToPlace.get(randomInt).getDeclaredConstructor(Position.class).newInstance(new Position(0,0));
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            //loop on all the rotateState to add them in the PossiblePlacements.
            for (int rotateState = 0; rotateState < 4; rotateState++) {

                currentBlock.rotateCasesTo(rotateState);
                currentBlock.setRotateState(rotateState);


                //if the block doesn't have to be moved:
                if (isPlacable(currentBlock,MSPos, rotateState) && currentBlock.getState(0,0) == CaseState.FULL){
                    double score = calculateScore(currentBlock,new Position(0,0));
                    possiblePlacements.add(new PossiblePlacementData(MSPos, rotateState, score));
                }
                else {
                    possiblePlacements.addAll(getMovedPossiblePlacements(currentBlock,MSPos,rotateState));
                }
            }
            //remove the block from the tryingToPlace list if the program couldn't find any possiblePlacement
            // or if the user only want different blocks.
            if (possiblePlacements.size() == 0 || alwaysDifferent) {
                tryingToPlace.remove(currentBlock.getClass());
            }
            System.out.println(tryingToPlace.size());
            //if we can place the block.
            if (possiblePlacements.size() >0) {
                // we want to place the moved Blocks in priority by making a list of them.
                ArrayList<PossiblePlacementData> movedBlocksPlacements = new ArrayList<>();

                // set up the first as the best
                PossiblePlacementData bestPossibleScorePlacement = possiblePlacements.get(0);

                for (PossiblePlacementData possiblePlacement: possiblePlacements) {
                    if (bestPossibleScorePlacement.score >= possiblePlacement.score){
                        bestPossibleScorePlacement = possiblePlacement; // select the best by the score
                    }
                    if (!(possiblePlacement.position.equals(MSPos))){
                        movedBlocksPlacements.add(possiblePlacement); // select the best by the fact that the block moved
                    }
                }

                if (movedBlocksPlacements.size() >0){
                    randomInt = rnd.nextInt(movedBlocksPlacements.size());
                    placeBlock(currentBlock,movedBlocksPlacements.get(randomInt).position,
                            movedBlocksPlacements.get(randomInt).rotateState);
                }
                else {
                    placeBlock(currentBlock,bestPossibleScorePlacement.position, bestPossibleScorePlacement.rotateState);
                }
                MSPos = getNewMSPos(MSPos);
                currentBlock.rotateCasesTo(0);
                currentBlock.setRotateState(0);
                currentBlock.rotateTo(0);
                blocksUsed.add(currentBlock);
            }
        }

        if (blocksUsed.size() < leastToPlace){
            throw new GenerateException("couldn't place the least amount of blocks.");
        }

        return new Level("randomLevel",prepareGrid(grid),blocksUsed.toArray(new ImageBlock[0]));
    }


    /** places the block in the grid.
     *
     * @param imageBlock the ImageBlock wanted to be placed.
     * @param position the position to place the ImageBlock to
     * @param rotateState the rotateState of the ImageBlock
     */
    private void placeBlock(ImageBlock imageBlock, Position position, int rotateState){
        imageBlock.rotateCasesTo(rotateState);
        imageBlock.setRotateState(rotateState);
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)
                    grid.set((int)position.getX()+j, (int)position.getY()+i, imageBlock.getState(j, i));
            }
        }
    }

    /**
     *
     * @param imageBlock the ImageBlock wanted to be placed.
     * @param position the position try to place the ImageBlock to.
     * @param rotateState the rotateState of the ImageBlock
     * @return if the ImageBlock is can be placed or not.
     */
    private boolean isPlacable(ImageBlock imageBlock,Position position, int rotateState) {
        imageBlock.rotateCasesTo(rotateState);
        imageBlock.setRotateState(rotateState);
        for (int i = 0; i < imageBlock.getRows(); i++) {
            for (int j = 0; j < imageBlock.getCols(); j++) {
                if (position.getX() + j >= grid.getCol() || position.getY() + i >= grid.getRow() ||
                        position.getX() +j <0 || position.getY() + i <0||
                        grid.getState((int)position.getX()+ j,(int)position.getY() + i) != CaseState.EMPTY &&
                                imageBlock.getState(j, i) == CaseState.FULL) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @param oldMsPos the old most significant position.
     * @return the closest EMPTY case's position to the origin of the grid that isn't the old MsPos.
     */
    private Position getNewMSPos(Position oldMsPos){

        int offset= 1;
        // using an array because we want to take a random position between the positions with the same best score.
        ArrayList<Position> bestPositions = new ArrayList<>();

        while (bestPositions.size() ==0){
            for (int x = 0; x <= offset; x++) {
                for (int y =0; y <= offset; y++) {
                    if(x!= offset && y!= offset)
                        continue;
                    // checks if the case is in the grid then checks if the tile of the grid is EMPTY.
                    if (y < grid.getCol() && x  < grid.getRow()
                            && grid.getState(x, y) == CaseState.EMPTY
                            && !(new Position(x,y).equals(oldMsPos))){
                        bestPositions.add(new Position( x, y));
                    }
                }
            }
            // re loop if after looking around the placedPosition no bestPositions where found.
            offset+=1;
            if (offset >8){
                return null;
            }
        }

        Random rnd = new Random();

        // sort the list so that the best position are at the beginning.
        bestPositions.sort(Position::compareTo);

        //get the bestScore
        double bestScore = bestPositions.get(0).getX() + bestPositions.get(0).getY();

        int sameScoreCount=0;

        for (Position position:bestPositions) {
            if (position.getY()+position.getX() <= bestScore){
                sameScoreCount++;
            }
            else
                break;
        }
        return bestPositions.get(rnd.nextInt(sameScoreCount));
    }

    /** Calculates the addition of all the direct distance between the Most Significant Position (MSPos) and every
      * full case of the block.
      * the direct distance D between the MSPos and a full case is calculated by :
      * D = sqrt((X+Xoffset)²+(Y+Yoffset)²)
      * X : the distance between the MSPos and the full case in the X axis.
      * Y : the distance between the MSPos and the full case in the Y axis.
     *
     * @param currentBlock the ImageBlock to calculate the score of.
     * @param offset the offset to add to the calcul.
     * @return the score of the ImageBlockBlock.
      */

    private double calculateScore(ImageBlock currentBlock,Position offset){
        double score = 0;
        for (int y = 0; y < currentBlock.getRows(); y++){
            for (int x = 0; x < currentBlock.getCols(); x++){
                // if the case is full, add the distance to the overall score of this PossiblePlacement.
                if (currentBlock.getState(x, y) == CaseState.FULL){
                    score += Math.sqrt(Math.pow(x+offset.getX(),2) + Math.pow(y+offset.getY(),2));
                }
            }
        }
        return score;
    }

    /** Method to calculate and returns the PlacementDatas when the block can be moved.
     *
     * @param currentBlock the currentBlock.
     * @param positionToPlace the initial positionToPlace.
     * @param rotateState the rotateState.
     * @return an ArrayList of PossiblePlacementData where the blocks have moved.
     * (the Position of the PossiblePlacementData might not be equal to the initial PositionToPlace)
     */
    private ArrayList<PossiblePlacementData> getMovedPossiblePlacements(ImageBlock currentBlock,Position positionToPlace,int rotateState){

        ArrayList<PossiblePlacementData> returnPossiblePlacements = new ArrayList<>();

        int xDelta = 0;
        while (positionToPlace.getX() + xDelta >=0 &&grid.getState((int)(positionToPlace.getX() + xDelta),
                (int)(positionToPlace.getY())) == CaseState.EMPTY){
            xDelta--;
        }
        if (isPlacable(currentBlock,new Position(positionToPlace.getX()+xDelta,positionToPlace.getY()), rotateState)
                && xDelta !=0){
            double score = calculateScore(currentBlock, new Position(xDelta,0));
            returnPossiblePlacements.add(new PossiblePlacementData(
                    new Position(positionToPlace.getX() + xDelta, positionToPlace.getY())
                    , rotateState, score));
        }
        int yDelta = 0;
        while (positionToPlace.getY() + yDelta >=0 && grid.getState((int)(positionToPlace.getX()),
                (int)(positionToPlace.getY() +yDelta)) == CaseState.EMPTY){
            yDelta--;
        }
        if (isPlacable(currentBlock,new Position(positionToPlace.getX(),positionToPlace.getY()+yDelta), rotateState)){
            double score = calculateScore(currentBlock,new Position(0,yDelta));
            returnPossiblePlacements.add(new PossiblePlacementData(
                    new Position(positionToPlace.getX(), positionToPlace.getY() + yDelta)
                    , rotateState, score));
        }
        return returnPossiblePlacements;
    }


    /** Prepares the intern grid by turning all the EMPTY to SPECIAL and all the FULL to EMPTY
     *  also adds some random FULL in the grid.
     *
     * @param grid the not prepared grid.
     * @return the prepared grid.
     */
    private Cases prepareGrid(Cases grid) {
        int rowsBorder=0;
        int colsBorder=0;
        for (int i = 0; i < grid.getCol(); i++) {
            for (int j = 0; j < grid.getRow(); j++) {
                if (grid.getState(i,j) == CaseState.FULL){
                    if (i> colsBorder){
                        colsBorder = i;
                    }
                    if (j > rowsBorder)
                        rowsBorder = j;
                }
            }
        }
        Cases preparedGrid = new Cases(colsBorder+1,rowsBorder+1);
        for (int i = 0; i < colsBorder+1; i++) {
            for (int j = 0; j < rowsBorder+1; j++) {
                switch (grid.getState(i, j)) {
                    case EMPTY:
                        preparedGrid.set(i, j, CaseState.SPECIAL);
                        break;
                    case FULL:
                        preparedGrid.set(i, j, CaseState.EMPTY);
                }

            }
        }
        for (int i = 0; i < 10; i++) {
            Random rnd = new Random();
            int x = rnd.nextInt(preparedGrid.getCol());
            int y = rnd.nextInt(preparedGrid.getRow());
            if (preparedGrid.getState(x,y) == CaseState.SPECIAL)
                preparedGrid.set(x,y,CaseState.FULL);
        }
        return preparedGrid;
    }
}
