package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;
import java.util.ArrayList;
/*
 * Trieda  Main spúšťa hru
 */


public class Main extends Application {

    private Game game;
    private int time = 0;   //pomocna premenna ktora sa vynuluje kazdych x sekund, aby sme sa vyhli preteceniu
    public static ArrayList<String> input = new ArrayList<String>(); //znaky z klavesnice

    /*
    * V metóde start s parametrom Stage, do stage nastaví titulok
    * vytvorí objekt triedy Game s parametrom Stage*/
    @Override
    public void start(Stage theStage) {
        theStage.setTitle( "JESUS - Ultimate Holy Warrior");
        game = new Game(theStage);

        //do Stage nastaví scénu z triedy Game dá triedu MenuSettings dá Scénu
        //nastaví teda do stage zobrazovanie menu a settings
        theStage.setScene(game.getMenuSettings().getScene());

        //pomocou triedy Game dá triedu Screen a spustí čítanie kláves
        game.getScreen().readKeys(input);

        //Cas je hlavnou jednotkou programu, od neho sa odvyja kazdy event

        new AnimationTimer()
        {
            public void handle(long currentNanoTime)
            {
                if(!game.isPauseGame()){
                    time++;
                    game.getScreen().getLevel().setTime(time);
                    if (game.isPlayMusic()){
                        game.getScreen().getLevel().getMediaPlayer().play();
                    }
                    if(time%500==0){ // nulovanie casu
                        time=0;
                    }

                    //pomocou triedy Game dá triedu Screen a spúšťa pohyb pomocou kláves
                    game.getScreen().keyEvaluation(input);

                    game.getScreen().mouseRead(theStage.getScene());
                }else{
                    game.getScreen().getLevel().setTime(0);

                    game.getScreen().keyPause(input,"P");
                    //pomocou triedy Game dá triedu Screen a spúšťa pohyb pomocou kláves
                    game.getScreen().getLevel().getMediaPlayer().pause();
                    game.getScreen().mouseRead(theStage.getScene());
                }

            }


        }.start();  //pocitanie casu


        //Zobrazí aktuálny Stage
        theStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}