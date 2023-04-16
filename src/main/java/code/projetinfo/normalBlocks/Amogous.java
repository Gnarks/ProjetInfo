package code.projetinfo.normalBlocks;

import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.ImageBlock;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Amogous extends ImageBlock {

    public Amogous(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.FULL,CaseState.EMPTY},
                        {CaseState.FULL,CaseState.FULL}}),
                new Position(25,75),
                new ImageView("code/projetinfo/Sprite_Ghosts/Sprite_GhostL2x2_Rotation0.png"),
                100,100);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);

        String generalUrl = "code.projetinfo/Sprite_Ghosts/Sprite_GhostL2x2_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(0, 50, generalUrl);
            case 2 -> super.rotateGraphicStep(-50, 0, generalUrl);
            case 3 -> super.rotateGraphicStep(0, -50, generalUrl);
            case 0 -> super.rotateGraphicStep(50, 0, generalUrl);
        }
    }
}
