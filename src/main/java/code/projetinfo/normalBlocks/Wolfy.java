package code.projetinfo.normalBlocks;

import code.projetinfo.ImageBlock;
import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.Position;

import javafx.scene.image.ImageView;

public class Wolfy extends ImageBlock {

    public Wolfy(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.EMPTY,CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL,CaseState.FULL},
                        {CaseState.FULL,CaseState.FULL,CaseState.FULL}}
                ),
                new Position(75,125),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_Ghost3x32TopRight_Rotation0.png"))),
                150,200);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalURL = "Sprite_Ghosts/Sprite_Ghost3x32TopRight_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(0, 50, generalURL);
            case 2 -> super.rotateGraphicStep(0, 0, generalURL);
            case 3 -> super.rotateGraphicStep(-50, 0, generalURL);
            case 0 -> super.rotateGraphicStep(50, -50, generalURL);
        }
    }
}