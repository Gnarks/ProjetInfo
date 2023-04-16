package code.projetinfo.normalBlocks;

import code.projetinfo.ImageBlock;
import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class VicKing extends ImageBlock {

    public VicKing(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.FULL,CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL,CaseState.FULL}}
                ), new Position(75,75),
                new ImageView("code/projetinfo/Sprite_Ghosts/Sprite_Ghost3x3_Rotation0.png"),
                150,150);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalURL = "code.projetinfo/Sprite_Ghosts/Sprite_Ghost3x3_Rotation";
        super.rotateGraphicStep(0,0, generalURL);
    }
}


