package code.projetinfo.normalBlocks;

import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.ImageBlock;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Napsta extends ImageBlock {

    public Napsta(Position spawnPos) {
        super(spawnPos,
                new Cases(new CaseState[][]{
                        {CaseState.FULL},
                        {CaseState.FULL},
                        {CaseState.FULL}}
                ), new Position(25, 75),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_Ghost1x3_Rotation0.png"))),
                50, 150);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);

        String generalURL = "Sprite_Ghosts/Sprite_Ghost1x3_Rotation";
        switch (getRotateState() % 4) {
            case 1 -> super.rotateGraphicStep(-50, 50, generalURL);
            case 2 -> super.rotateGraphicStep(50, -50, generalURL);
            case 3 -> super.rotateGraphicStep(-50, 50, generalURL);
            case 0 -> super.rotateGraphicStep(50, -50, generalURL);
        }
    }
}
