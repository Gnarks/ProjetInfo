package code.projetinfo.normalBlocks;

import code.projetinfo.ImageBlock;
import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class King extends ImageBlock {

    public King(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL}}),
                new Position(25,75),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_Ghost2x4_Rotation0.png"))),
                100,200);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);

        String generalUrl = "Sprite_Ghosts/Sprite_Ghost2x4_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(-100, 50, generalUrl);
            case 2 -> super.rotateGraphicStep(50, -100, generalUrl);
            case 3 -> super.rotateGraphicStep(0, +50, generalUrl);
            case 0 -> super.rotateGraphicStep(50, 0, generalUrl);
        }
    }
}
