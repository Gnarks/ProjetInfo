package code.projetinfo.normalBlocks;

import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.ImageBlock;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Toowels extends ImageBlock {

    public Toowels(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.FULL,CaseState.EMPTY},
                        {CaseState.FULL,CaseState.EMPTY},
                        {CaseState.FULL,CaseState.EMPTY},
                        {CaseState.FULL,CaseState.FULL}}),
                new Position(25,125),
                new ImageView("code/projetinfo/Sprite_Ghosts/Sprite_GhostL2x4_Rotation0.png"),
                100,200);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalURL = "code.projetinfo/Sprite_Ghosts/Sprite_GhostL2x4_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(-50, 100, generalURL);
            case 2 -> super.rotateGraphicStep(0, -50, generalURL);
            case 3 -> super.rotateGraphicStep(-50, 0, generalURL);
            case 0 -> super.rotateGraphicStep(100, -50, generalURL);
        }
    }
}
