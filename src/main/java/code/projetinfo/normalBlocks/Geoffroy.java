package code.projetinfo.normalBlocks;

import code.projetinfo.ImageBlock;
import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Geoffroy extends ImageBlock {
    public Geoffroy(Position spawnPos){
        super(spawnPos,
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
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalUrl = "Sprite_Ghosts/Sprite_Ghost2x6_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(-50, 100, generalUrl);
            case 2 -> super.rotateGraphicStep(100, -50, generalUrl);
            case 3 -> super.rotateGraphicStep(-150, 100, generalUrl);
            case 0 -> super.rotateGraphicStep(100, -150, generalUrl);
        }
    }
}
