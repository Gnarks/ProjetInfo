package code.projetinfo;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

public class LevelGenerator {

    private Class<?>[] imageBlockClasses;
    private int leastToPlace;
    private int maxToPlace;

    private Cases grid;



    public LevelGenerator(ImageBlock[] imageBlocks, int leastToPlace,int maxToPlace,boolean alwaysDifferent){

        imageBlockClasses = new Class[imageBlocks.length];
        for (int i = 0; i < imageBlocks.length; i++) {
            imageBlockClasses[i] = imageBlocks[i].getClass();
        }

        this.leastToPlace = leastToPlace;
        this.maxToPlace = maxToPlace;

    }

    /** Generate and returns the generated Level with the Constructor parameters.
     *
     * @return The generated Level.
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public Level generate() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {


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

        // the Most Significant Position is initialized at (0,0).
        Position MSPos = new Position(0,0);

        Random rnd = new Random();


        // initialize a grid on which the block will be placed.
        grid = new Cases(8,8, CaseState.EMPTY);



        // the array of block used in the generation of the level.
        ArrayList<ImageBlock> blocksUsed = new ArrayList<ImageBlock>();

        //loop trying to place the minimum asked number of block. todo patch la boucle
        for (int i = 0; i < leastToPlace; i++) {
            int randomInt = rnd.nextInt(imageBlockClasses.length);


            //arrayList to store all the PossiblePlacements for a single block.
            ArrayList<PossiblePlacementData> possiblePlacements = new ArrayList<PossiblePlacementData>();


            // chooses a random ImageBlock in the imageBlockClasses list.
            ImageBlock currentBlock = (ImageBlock) imageBlockClasses[randomInt].getDeclaredConstructor(Position.class).newInstance(new Position(0,0));

            //loop on all the rotateState to add them in the PossiblePlacements
            for (int rotateState = 0; rotateState < 4; rotateState++) {
                currentBlock.rotateTo(rotateState);

                double score = 0;
                Position positionToPlace = MSPos.clone();
                // Iterates in all the cases of the Block.
                for (int y = 0; y < currentBlock.getRows(); y++){
                    for (int x = 0; x < currentBlock.getCols(); x++){
                        // todo check if the (0,0) case of the block is EMPTY, if yes redo a loop to tweak with the block position
                        // if the case is full, add the distance to the overall score of this PossiblePlacement
                        if (currentBlock.getState(x, y) == CaseState.FULL){
                            score += Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
                        }
                    }
                }
                if (isPlacable(currentBlock,positionToPlace, rotateState))
                    possiblePlacements.add(new PossiblePlacementData(positionToPlace,rotateState,score));
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
                // places the bestBlock on the grid
                placeBestBlock(currentBlock,bestPossiblePlacement.position, bestPossiblePlacement.rotateState);
                //set up the new MS pos
                MSPos = getNewMSPos(bestPossiblePlacement.position.clone());
                System.out.printf("choose the : %s position\n",MSPos);
                blocksUsed.add(currentBlock);
            }
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
                        grid.getState((int)position.getX()+ j,(int)position.getY() + i) != CaseState.EMPTY &&
                                imageBlock.getState(j, i) == CaseState.FULL) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * @return the closest EMPTY case's position to the origin of the grid.
     */
    private Position getNewMSPos(){

        int offset= 1;
        // using an array because we want to take a random position between the positions with the same best score.
        ArrayList<Position> bestPositions = new ArrayList<Position>();
        boolean continuing;

        while (bestPositions.size() ==0){
            for (int x = -offset; x <= offset; x++) {
                for (int y = -offset; y <= offset; y++) {
                    //check if we didn't already visit the tile. todo pas correct
                    Position trying = new Position(x,y);
                    if(x != -offset && x!= offset && y!= offset && y!= -offset)
                        continue;
                    // checks if the case is in the grid then checks if the tile of the grid is EMPTY.
                    if (x  >= 0 && y  >= 0 &&
                            y < grid.getCol() && y  < grid.getRow()
                            && grid.getState(x, y) == CaseState.EMPTY){
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
}
