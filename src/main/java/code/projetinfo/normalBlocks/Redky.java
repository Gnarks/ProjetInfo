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
                new ImageView("code/projetinfo/Sprite_Ghosts/Sprite_Ghost2x2_Rotation0.png"),
                100,100);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalURL = "code.projetinfo/Sprite_Ghosts/Sprite_Ghost2x2_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(-50, 0, generalURL);
            case 2 -> super.rotateGraphicStep(0, -50, generalURL);
            case 3 -> super.rotateGraphicStep(50, 0, generalURL);
            case 0 -> super.rotateGraphicStep(0, 50, generalURL);
        }
    }
}
