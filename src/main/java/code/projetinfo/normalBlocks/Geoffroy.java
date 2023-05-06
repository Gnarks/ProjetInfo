package code.projetinfo.normalBlocks;

import code.projetinfo.ImageBlock;
import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Geoffroy extends ImageBlock {
    public Geoffroy(Position layoutPos){
        super(layoutPos,
                new Cases(new CaseState[][] {
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL}}),
                new Position(75,175),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_Ghost2x6_Rotation0.png"))),
                100,300);
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

        String generalUrl = "Sprite_Ghosts/Sprite_Ghost2x6_Rotation";
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

    @Override
    public ImageBlock clone() {
        return new Geoffroy(new Position(0,0));
    }

}
