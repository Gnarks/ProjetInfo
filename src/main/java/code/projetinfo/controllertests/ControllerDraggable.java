package code.projetinfo.controllertests;

import code.projetinfo.*;
import code.projetinfo.normalBlocks.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
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
    private ImageView BackToMenuButton;

    @FXML
    private ImageView ResetButton;

    private final ImageBlock[] everybodyDance = new ImageBlock[]{
            new Amogous(new Position(400, 150)), new Baby(new Position(0, 0)), new Bloby(new Position(0, 0)),
            new BigBob(new Position(0, 0)), new Scooboodoo(new Position(0, 0)), new Geoffroy(new Position(0, 0)),
            new Napsta(new Position(0, 0)), new BooBelle(new Position(150, 100)), new Bob(new Position(0, 0)),
            new Redky(new Position(0, 0)), new Scooboodoo(new Position(0, 0)), new VicKing(new Position(0, 0)),
            new Toowels(new Position(0, 0)), new Wolfy(new Position(0, 0)), new Phantom(new Position(0, 0)),
            new LilDeath(new Position(0, 0)), new King(new Position(0, 0)), new PlagueDoc(new Position(0, 0)),
            new GymBroo(new Position(0, 0))
    };

    private final Cases heartGrid = new Cases(new CaseState[][]{
        {CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL},
        {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
        {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
        {CaseState.EMPTY, CaseState.FULL, CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
        {CaseState.SPECIAL, CaseState.FULL, CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL},
        {CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL},
        {CaseState.SPECIAL, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.SPECIAL}});


    public final ImageBlock[] bobynininet = new ImageBlock[]{new Bob(new Position(50, 10))};

    private final ImageBlock[] heart = new ImageBlock[]{new Amogous(new Position(150, 100)), new Amogous(new Position(850, 100)),
            new Scooboodoo(new Position(50, 200)), new Geoffroy(new Position(800, 200)), new Redky(new Position(950, 430)),
            new Redky(new Position(50, 450)), new Napsta(new Position(150, 300)), new Napsta(new Position(960, 120)),
            new Baby(new Position(975, 300)),};


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


        Level level = new Level(heartGrid, heart);



        LevelHandler levelHandler = new LevelHandler(level, pane);
        ImageView backGrid = new ImageView("code/projetinfo/Sprites/BackGridLevel.png");
        backGrid.setLayoutX(levelHandler.getGridPos().getX()-50);
        backGrid.setLayoutY(levelHandler.getGridPos().getY()-50);
        backGrid.setFitWidth((level.getGrid().getCol()+2)*50);
        backGrid.setFitHeight((level.getGrid().getRow()+2)*50);

        pane.getChildren().add(backGrid);

        levelHandler.drawGrid();
        levelHandler.drawImageBlocks();
        ResetButton.setOnMouseClicked(event -> {
            levelHandler.reset();
        });


        BackToMenuButton.setOnMouseClicked(event ->{
            FXMLLoader fxmlLoader = new FXMLLoader(AppDraggable.class.getResource("MainMenu.fxml"));
            Stage stage;
            Scene scene;
            stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
            try {
                scene = new Scene(fxmlLoader.load(), 1080, 607.5);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            stage.setScene(scene);
            stage.show();
        });
    }
}