package code.projetinfo.normalBlocks;

import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.ImageBlock;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Amogous extends ImageBlock {

    public Amogous(Position layoutPosition){
        super(layoutPosition,
                new Cases(new CaseState[][] {
                        {CaseState.FULL,CaseState.EMPTY},
                        {CaseState.FULL,CaseState.FULL}}),
                new Position(25,75),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_GhostL2x2_Rotation0.png"))),
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

        String generalUrl = "Sprite_Ghosts/Sprite_GhostL2x2_Rotation";
        super.rotateGraphicallyTo(newRotateState,generalUrl);

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

