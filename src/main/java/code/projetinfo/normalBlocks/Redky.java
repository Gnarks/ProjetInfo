package code.projetinfo.normalBlocks;

import code.projetinfo.ImageBlock;
import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Redky extends ImageBlock {

    public Redky(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL }}
                ), new Position(25,25),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_Ghost2x2_Rotation0.png"))),
                100,100);
    }

    /**
     * rotates the block to the specified rotateState in Frontend AND Backend.
     * a single rotateState change corresponds to a 90-degree turn to the right.
     *
     * @param newRotateState the wanted rotateState
     */
    @Override
    public void rotateTo(int newRotateState) {
        if (newRotateState<0 || newRotateState >3)
            throw new IllegalArgumentException("rotateState must be between 0 and 3 included");

        super.rotateCasesTo(newRotateState);

        String generalUrl = "Sprite_Ghosts/Sprite_Ghost2x2_Rotation";
        Position[] changes = new Position[]{ new Position(0,0),new Position(-50,0),
                new Position(-50,-50),new Position(0,-50)};
        super.rotateGraphicallyTo(changes,newRotateState,generalUrl);

        super.setRotateState(newRotateState);
    }
    /**
     * rotates the block to the next rotateState in Frontend AND Backend.
     * a single rotateState change corresponds to a 90-degree turn to the right.
     */
    @Override
    public void rotate() {
        rotateTo((getRotateState()+1)%4);
    }
}
