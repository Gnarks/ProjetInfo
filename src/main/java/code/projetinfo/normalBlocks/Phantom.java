package code.projetinfo.normalBlocks;

import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.ImageBlock;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Phantom extends ImageBlock {

    public Phantom(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.EMPTY,CaseState.FULL,CaseState.FULL},
                        {CaseState.EMPTY,CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL,CaseState.FULL}}
                ), new Position(75,75),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_GhostBigJ3x3_Rotation0.png"))),
                150,150);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalURL = "Sprite_Ghosts/Sprite_GhostBigJ3x3_Rotation";
        super.rotateGraphicStep(0,0, generalURL);
    }
}