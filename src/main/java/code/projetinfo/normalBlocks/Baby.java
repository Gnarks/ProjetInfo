package code.projetinfo.normalBlocks;

import code.projetinfo.CaseState;
import code.projetinfo.Cases;
import code.projetinfo.ImageBlock;
import code.projetinfo.Position;
import javafx.scene.image.ImageView;

public class Baby extends ImageBlock {

    public Baby(Position spawnPos){
        super(spawnPos,
                new Cases(new CaseState[][] {
                        {CaseState.FULL}}),
                new Position(25,25),
                new ImageView(String.valueOf(ImageBlock.class.getResource("Sprite_Ghosts/Sprite_Ghost1x1_Rotation0.png"))),
                50,50);
    }
    public void rotateGraphic() {
        setRotateState(getRotateState()+1);
        String generalUrl = "Sprite_Ghosts/Sprite_Ghost1x1_Rotation";
        super.rotateGraphicStep(0,0, generalUrl);
    }
}