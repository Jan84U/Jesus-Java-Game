package sample;

import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

/*
* V triede Game nastavujeme počiatočnú hlasitosť premenná volume
* vytvárame lokálny jazyk
* zdroj jazyka je v baseName = ´lang´ v ResourceBundle
* parameter v konštruktore je Stage z triedy Main
* vytvorí sa MenuSettings s parametramy Handler(spístupňovač) a double(hlasitosť)
* do screenu sa nastaví stage a handler*/

public class Game {

    public Handler handler;
    private Scene scene;
    private MenuSettings menuSettings;
    private Screen screen;
    private Stage stage;
    private double volume;
    private Locale localeLanguages;
    private ResourceBundle resourceBundle;
    private boolean gamePlay, pauseGame, playMusic, winGame;

    public Game(Stage stage){
        volume = 0.5;
        //vytvára Handler s parametrom tochto objektu, handler pomáha k prístupu
        // k triede Game v iných triedach
        handler = new Handler(this);
        localeLanguages = new Locale("");
        resourceBundle = ResourceBundle.getBundle("lang",localeLanguages);
        this.stage = stage;
        try {
            menuSettings = new MenuSettings(handler,volume);
        } catch (IOException e) {
            e.printStackTrace();
        }
        gamePlay = true;
        pauseGame = false;
        playMusic = false;
        winGame = false;

        screen = new Screen(stage);
    }

    //do scény nastavuje Level1 scénu a tým spúšťa hru
    // (v triede Controller metóda startGame() -> metóda timelabel())
    public Scene playGame(){
        scene = screen.getLevel().getSceneMenu();
        return scene;
    }

    //vráti MenuSettings
    public MenuSettings getMenuSettings() {
        return menuSettings;
    }

    //vráti Screen
    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    //vráti double volume
    public double getVolume() {
        return volume;
    }

    //nastaví novú hodnotu double volume
    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Stage getStage() {
        return stage;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public boolean isPauseGame() {
        return pauseGame;
    }

    public void setPauseGame(boolean pauseGame) {
        this.pauseGame = pauseGame;
    }

    public boolean isPlayMusic() {
        return playMusic;
    }

    public void setPlayMusic(boolean playMusic) {
        this.playMusic = playMusic;
    }

    public boolean isGamePlay() {
        return gamePlay;
    }

    public void setGamePlay(boolean gameOver) {
        this.gamePlay = gameOver;
    }

    public boolean isWinGame() {
        return winGame;
    }

    public void setWinGame(boolean winGame) {
        this.winGame = winGame;
    }
}
