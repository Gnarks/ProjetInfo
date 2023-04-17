package code.projetinfo.normalBlocks;

import code.projetinfo.ImageBlock;
import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Scooboodoo extends ImageBlock {

    public Scooboodoo(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.EMPTY,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL }}),
                new Position(75,75),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_GhostJ2x2_Rotation0.png"))),
                100,100);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalURL = "Sprite_Ghosts/Sprite_GhostJ2x2_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(50, 0, generalURL);
            case 2 -> super.rotateGraphicStep(0, 50, generalURL);
            case 3 -> super.rotateGraphicStep(-50, 0, generalURL);
            case 0 -> super.rotateGraphicStep(0, -50, generalURL);
        }
    }
}
