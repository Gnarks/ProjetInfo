package code.projetinfo;

import code.projetinfo.normalBlocks.Amogous;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class LevelGenerator {

    private Class<?>[] imageBlockClasses;
    int leastToPlace;
    int MaxToPlace;



    public LevelGenerator(ImageBlock[] imageBlocks, int leastToPlace,int maxToPlace,boolean alwaysDifferent){

        imageBlockClasses = new Class[imageBlocks.length];
        for (int i = 0; i < imageBlocks.length; i++) {
            imageBlockClasses[i] = imageBlocks[i].getClass();
        }

        this.leastToPlace = leastToPlace;
        this.MaxToPlace = maxToPlace;

    }

    public Level generate() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        Position mSPos = new Position(0,0);

        Random rnd = new Random();

        Cases grid = new Cases(8,8, CaseState.EMPTY);
        double[] distanceList = new double[4];

        for (int i = 0; i < leastToPlace; i++) {
            int randomInt = rnd.nextInt(imageBlockClasses.length);

            ImageBlock currentBlock = (ImageBlock) imageBlockClasses[randomInt].getDeclaredConstructor(Position.class).newInstance(new Position(0,0));

            //Generate a distance list for all the unit blocs
            for (int rotateState = 0; rotateState < 4; rotateState++) {
                currentBlock.rotateTo(rotateState);
                //Itéres dans tout le bloc si trouve un Full alors calcule sa distance avec (0,0)
                double distance = 0;
                for (int y = 0; y < currentBlock.getRows(); y++){
                    for (int x = 0; x < currentBlock.getCols(); x++){
                        //Calcule la distance et l'ajoute a la distance globale
                        if (currentBlock.getState(x, y) == CaseState.FULL){
                            distance += Math.sqrt(Math.pow(x-mSPos.getX(),2) + Math.pow(y- mSPos.getY(),2));
                        }
                    }
                }
                distanceList[rotateState] = distance;
            }
        }

        for (int i = 0; i < distanceList.length; i++) {
            System.out.println(distanceList[i]);
        }

        return null;
    }
}
