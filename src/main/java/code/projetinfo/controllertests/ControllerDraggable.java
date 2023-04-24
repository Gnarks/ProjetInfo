package code.projetinfo.controllertests;

import code.projetinfo.*;
import code.projetinfo.normalBlocks.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerDraggable implements Initializable {
    /**
     * Controller of all the menu's buttons(assigned to DraggableTest.fxml)
     */
    private final int tileSize = 50;
    @FXML
    private AnchorPane pane;

    @FXML
    private Button resetButton;

    private final ImageBlock[] everybodyDance = new ImageBlock[]{
            new Amogous(new Position(400, 150)), new Baby(new Position(0, 0)), new Bloby(new Position(0, 0)),
            new BigBob(new Position(0, 0)), new Scooboodoo(new Position(0, 0)), new Geoffroy(new Position(0, 0)),
            new Napsta(new Position(0, 0)), new BooBelle(new Position(150, 100)), new Bob(new Position(0, 0)),
            new Redky(new Position(0, 0)), new VicKing(new Position(0, 0)), new Toowels(new Position(0, 0)),
            new Wolfy(new Position(0, 0)), new Phantom(new Position(0, 0)), new LilDeath(new Position(0, 0)),
            new King(new Position(0, 0)), new PlagueDoc(new Position(0, 0)), new GymBroo(new Position(0, 0))
    };

    public final ImageBlock[] bobynininet = new ImageBlock[]{new Bob(new Position(50, 10))};

    private final ImageBlock[] heart = new ImageBlock[]{new Amogous(new Position(50, 10)), new Amogous(new Position(50, 150)),
            new Scooboodoo(new Position(750, 150)), new Geoffroy(new Position(750, 150)), new Redky(new Position(1, 150)),
            new Redky(new Position(0, 10)), new Napsta(new Position(50, 150)), new Napsta(new Position(800, 150)),
            new Baby(new Position(1000, 150)),};


    /**
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        //changer AppDraggable en AppGame
        // faire en sorte qu'après les choix de l'utilisateur (front) on prenne une certaine
        // instance de Level qui sera injectée dans LevelHandler pour gérer tout le lien entre front et back.


        Level level = new Level("heart",new Cases(new CaseState[][]{
                {CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.SPECIAL, CaseState.FULL, CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL},
                {CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL},
                {CaseState.SPECIAL, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.SPECIAL}
        }), heart);

        LevelHandler levelHandler = new LevelHandler(level, pane);
        levelHandler.drawGrid();
        levelHandler.drawImageBlocks();
        resetButton.setOnAction(event -> {levelHandler.reset();});
    }
}