package code.projetinfo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class LevelGenerator {

    /**
     * GenerateException will be thrown if there is an issue in the generate method
     *  with the specified issue in the message.
     */
    public static class GenerateException extends Exception {
        public GenerateException(String errorMessage) {
            super(errorMessage);
        }
    }


    /** Local class used to represent a possible position to place the block
     *  for a certain rotateState with a certain score.
     */
    class PossiblePlacementData {
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

        @Override
        public String toString() {
            return String.format("Position : %s\nRotateState:%s\nScore:%s",position,rotateState,score);
        }
    }

    private Class<?>[] imageBlockClasses;
    private int leastToPlace;
    private int maxToPlace;

    private Cases grid;

    boolean alwaysDifferent;



    public LevelGenerator(Class<ImageBlock>[] imageBlocksClasses, int leastToPlace,int maxToPlace,boolean alwaysDifferent){

        this.imageBlockClasses =imageBlocksClasses;
        this.leastToPlace = leastToPlace;
        this.maxToPlace = maxToPlace;
        this.alwaysDifferent = alwaysDifferent;
    }

    /** Generate and returns the generated Level with the Constructor parameters.
     *
     * @return The generated Level.
     */
    public Level generate() throws GenerateException {

        // the Most Significant Position is initialized at (0,0).
        Position MSPos = new Position(0,0);

        Random rnd = new Random();

        // initialize a grid on which the block will be placed.
        grid = new Cases(8,8, CaseState.EMPTY);


        // the array of block used in the generation of the level.
        ArrayList<ImageBlock> blocksUsed = new ArrayList<ImageBlock>();

        //loop trying to place the maximum asked number of block.
        for (int i = 0; i < maxToPlace; i++) {
            int randomInt = rnd.nextInt(imageBlockClasses.length);
            //arrayList to store all the PossiblePlacements for a single block.
            ArrayList<PossiblePlacementData> possiblePlacements = new ArrayList<PossiblePlacementData>();

            // chooses a random ImageBlock in the imageBlockClasses list.
            ImageBlock currentBlock = null;
            try {
                currentBlock = (ImageBlock) imageBlockClasses[randomInt].getDeclaredConstructor(Position.class).newInstance(new Position(0,0));
            } catch (InstantiationException | NoSuchMethodException | IllegalAccessException |
                     InvocationTargetException e) {
                throw new RuntimeException(e);
            }
            //loop on all the rotateState to add them in the PossiblePlacements.
            for (int rotateState = 0; rotateState < 4; rotateState++) {

                currentBlock.rotateTo(rotateState);

                double score = 0;

                Position positionToPlace = MSPos.clone();
                score = calculateScore(currentBlock,new Position(0,0));

                if (isPlacable(currentBlock,positionToPlace, rotateState) && currentBlock.getState(0,0) == CaseState.FULL)
                    possiblePlacements.add(new PossiblePlacementData(positionToPlace,rotateState,score));
                else {
                    int xDelta = 0;
                    while (positionToPlace.getX() + xDelta >=0 &&grid.getState((int)(positionToPlace.getX() + xDelta),
                            (int)(positionToPlace.getY())) == CaseState.EMPTY){
                        xDelta--;
                    }
                    if (isPlacable(currentBlock,new Position(positionToPlace.getX()+xDelta,positionToPlace.getY()), rotateState)
                        && xDelta !=0){
                        score = calculateScore(currentBlock, new Position(xDelta,0));
                        possiblePlacements.add(new PossiblePlacementData(
                                new Position(positionToPlace.getX()+xDelta, positionToPlace.getY())
                                ,rotateState,score));
                    }

                    int yDelta = 0;
                    while (positionToPlace.getY() +yDelta >=0 && grid.getState((int)(positionToPlace.getX()),
                            (int)(positionToPlace.getY() -yDelta)) == CaseState.EMPTY){
                        yDelta--;
                    }
                    if (isPlacable(currentBlock,new Position(positionToPlace.getX(),positionToPlace.getY()+yDelta), rotateState)){
                        score = calculateScore(currentBlock,new Position(0,yDelta));
                        possiblePlacements.add(new PossiblePlacementData(
                                new Position(positionToPlace.getX(), positionToPlace.getY()+yDelta)
                                ,rotateState,score));
                    }
                }
            }
            PossiblePlacementData bestPossiblePlacement = null;
            if (possiblePlacements.size() >0){
                // set up the first as the best
                bestPossiblePlacement = possiblePlacements.get(0);

                for (PossiblePlacementData possiblePlacement: possiblePlacements) {
                    // prints the different possiblePlacements
                    System.out.println(possiblePlacement);
                    System.out.println("---------------");

                    for (int j = 1; j < possiblePlacements.size(); j++) {
                        if (bestPossiblePlacement.score > possiblePlacement.score){
                            //todo check en fct si le block s'est déplacé le privilégier
                            bestPossiblePlacement = possiblePlacement; // select the best by the score
                        }
                    }
                }
            }
            if (bestPossiblePlacement != null){
                // places the bestBlock on the grid.
                placeBestBlock(currentBlock,bestPossiblePlacement.position, bestPossiblePlacement.rotateState);
                //set up the new MS pos
                blocksUsed.add(currentBlock);
            }
            MSPos = getNewMSPos(MSPos);
        }
        return new Level("randomLevel",grid,blocksUsed.toArray(new ImageBlock[0]));
    }

    private void placeBestBlock(ImageBlock imageBlock, Position position, int rotateState){
        imageBlock.rotateTo(rotateState);
        for (int i = 0; i < imageBlock.getRows(); i++){
            for (int j = 0; j < imageBlock.getCols(); j++){
                if (imageBlock.getState(j,i) == CaseState.FULL)
                    grid.set((int)position.getX()+j, (int)position.getY()+i, imageBlock.getState(j, i));
            }
        }
        grid.show();
    }

    private boolean isPlacable(ImageBlock imageBlock,Position position, int rotateState) {
        imageBlock.rotateTo(rotateState);
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
     * @return the closest EMPTY case's position to the origin of the grid that isn't the old MsPos.
     */
    private Position getNewMSPos(Position oldMsPo){

        int offset= 1;
        // using an array because we want to take a random position between the positions with the same best score.
        ArrayList<Position> bestPositions = new ArrayList<Position>();
        boolean continuing;

        while (bestPositions.size() ==0){
            for (int x = 0; x <= offset; x++) {
                for (int y =0; y <= offset; y++) {
                    if(x!= offset && y!= offset)
                        continue;
                    // checks if the case is in the grid then checks if the tile of the grid is EMPTY.
                    if (y < grid.getCol() && y  < grid.getRow()
                            && grid.getState(x, y) == CaseState.EMPTY
                            && !(new Position(x,y).equals(oldMsPo))){
                        bestPositions.add(new Position( x, y));
                    }
                }
            }
            // re loop if after looking around the placedPosition no bestPositions where found.
            offset+=1;
        }

        Random rnd = new Random();

        // sort the list so that the best position are at the beginning.
        bestPositions.sort(Position::compareTo);

        //get the bestScore
        double bestScore = bestPositions.get(0).getX() + bestPositions.get(0).getY();

        int sameScoreCount=0;

        for (Position position:bestPositions) {
            if (position.getY()+position.getX() <= bestScore){
                System.out.println(position);
                sameScoreCount++;
            }
            else
                break;
        }
        return bestPositions.get(rnd.nextInt(sameScoreCount));
    }

    /**
     * calculates the score of the currentBlock at the offset position.
     * @param currentBlock
     * @param offset
     * @return
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
}
