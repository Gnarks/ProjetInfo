package code.projetinfo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AppGame extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppGame.class.getResource("Game.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 607.5);
        stage.setTitle("Ghost Arranged by Yourself, GAY");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        //launch();

        /*Level level = new Level("heart",new Cases(new CaseState[][]{
                {CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.EMPTY, CaseState.FULL, CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY},
                {CaseState.SPECIAL, CaseState.FULL, CaseState.FULL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL},
                {CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL},
                {CaseState.SPECIAL, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.EMPTY, CaseState.EMPTY, CaseState.SPECIAL, CaseState.SPECIAL, CaseState.SPECIAL}
        }), new ImageBlock[]{new Amogous(new Position(50, 10)), new Amogous(new Position(50, 150)),
                new Scooboodoo(new Position(750, 150)), new Geoffroy(new Position(750, 150)), new Redky(new Position(1, 150)),
                new Redky(new Position(0, 10)), new Napsta(new Position(50, 150)), new Napsta(new Position(800, 150)),
                new Baby(new Position(1000, 150)),});

        level.loadState("heart");
        level.show();*/
    }
}
