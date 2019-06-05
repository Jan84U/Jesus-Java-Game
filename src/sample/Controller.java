package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

import static java.lang.Thread.sleep;

public class Controller{
    public ProgressIndicator progress;
    public Label menuL;
    public Label labelSlider;
    public Slider slider;
    @FXML
    private boolean skSelected = false;
    @FXML
    private boolean enSelected = false;
    private ResourceBundle resourceBundle;
    private Timeline animation;
    private Screen screen;
    private Level level;
    private int i = 0; //casovac zaciatku hry
    public double valueVolume;
    public boolean changed = false;

    //Menu
    @FXML
    private HBox menuBox;
    @FXML
    private Label menu;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;

    //SETTINGS
    @FXML
    private HBox settingBox;
    @FXML
    private Button back;
    @FXML
    private Button submit;
    @FXML
    private Label setting;
    @FXML
    private Label volume;
    @FXML
    private Label lang;

    //Game Over or Win
    @FXML
    private HBox gameMain;
    @FXML
    private HBox winGame;
    /*
    * Start hry z FXML menu, button Start Game, spúšťa animáciu
    * odpočítavanie času a prepína hru z FXML do Canvas v
    * metóde timelabel()
    */
    @FXML
    private void startGame(){
        progress.setVisible(true);
        animation = new Timeline(new KeyFrame(Duration.seconds(1), event -> timelabel()));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.play();
    }

    /*
    *Prepne menu do settings, button Settings, mení visibility
    * HBox menuBox a settingBox
    */
    @FXML
    private void settingGame(){
        menuBox.setVisible(false);
        gameMain.setVisible(false);
        winGame.setVisible(false);
        settingBox.setVisible(true);
    }

    /*
    *Vypne hru , button Quit Game
    */
    @FXML
    private void quitGame() throws InterruptedException {
        sleep(1000);
        System.exit(0);
    }

    /*
     *Prepne settings do menu, button BACK, mení visibility
     * HBox menuBox a settingBox
     */
    @FXML
    private void backToMenu(){
        Handler.game.setGamePlay(false);
        settingBox.setVisible(false);
        gameMain.setVisible(false);
        winGame.setVisible(false);
        menuBox.setVisible(true);
    }


    /*
    *V settings potvrdí vykonané zmeny a vráti sa do menu
    * nastaví zvuk, a jazyk
    */
    @FXML
    private void submitChanges(){
        /*
            Ak nebola sliderom zmenená hodnota, nastaví sa predvolená hodnota
            z triedy Game
        */
        if (!changed){
            valueVolume = Handler.game.getVolume();
        }
        /*
            Nastavenie zmenenej alebo nezmenenej hodnoty zvuku
            do premennej volume v triede Game pre ďalšie použitie v hre
         */
        Handler.game.setVolume(valueVolume);
        /*
            Nastavenie zmenenej alebo nezmenenej hodnoty zvuku
            do MediaPlayeru v triede MenuSettings
         */
        Handler.game.getMenuSettings().getMediaPlayer().setVolume(valueVolume);

        // Nastavenie vybratého jazyka(private method, popis nižšie)
        setLanguage();

        //Nastavenie textu podľa vybratého jazyka
        setText(resourceBundle);

        //presun do Menu cez visibility HBoxov v FXML
        settingBox.setVisible(false);
        gameMain.setVisible(false);
        winGame.setVisible(false);
        menuBox.setVisible(true);
    }

    /*
    *Odpočítava čas pri stlačení buttonu Start Game
    */
    private void timelabel(){
        if (i > 0){
            i--;
        }
        //V Labeli v menu vypisuje odpočítavajúci čas
        menuL.setText(Integer.toString(i));

        /*
            Keď sa i rovná nule do premennej valueVolume sa nastaví
            zmenená alebo nezmenená hodnota daná z premennej v
            triede Game
            V triede Game sa nastaví scéna playGame()
            Vypne sa prehrávač hudby v Menu
            Nastaví sa zmenená alebo nezmenená hlasitosť do hry a do premennej v Leveli 1
            Spustí sa prehrávač v Hre
        */
        if(i == 0){
            valueVolume = Handler.game.getVolume();
            animation.stop();
            if(Handler.game.isGamePlay()){
                Handler.game.getScreen().getTheStage().setScene(Handler.game.playGame());
                Handler.game.getMenuSettings().getMediaPlayer().stop();
                progress.setVisible(false);
                menuL.setText("");
                Handler.game.getScreen().getLevel().getMediaPlayer().setVolume(valueVolume);
                Handler.game.getScreen().getLevel().setVolume(valueVolume);
                Handler.game.getScreen().getLevel().getMediaPlayer().play();
            }else{
                if(Handler.game.getScreen().getLevel().getHero().getLife() <= 0 || Handler.game.isWinGame()){
                        Handler.game.getMenuSettings().getMediaPlayer().stop();
                        Handler.game.setWinGame(false);
                        level = new Level(Handler.game.getStage(), "level1",10,0,0);
                        screen = new Screen(Handler.game.getStage());
                        screen.setLevel(level);
                        screen.setHero(level.getHero());
                        Handler.game.setScreen(screen);
                        Handler.game.setGamePlay(true);
                        ArrayList<String> nextInput = new ArrayList<>();
                        Main.input = nextInput;
                        Handler.game.getScreen().readKeys(Main.input);
                        Handler.game.getScreen().getTheStage().setScene(Handler.game.playGame());
                        Handler.game.getScreen().getLevel().getMediaPlayer().setVolume(valueVolume);
                        Handler.game.getScreen().getLevel().setVolume(valueVolume);
                        Handler.game.getScreen().getLevel().getMediaPlayer().play();
                }
            }
        }
    }

    /*
    *V metóde sa do premennej valueVolume nastavuje hodnota zo Slidera (0 až 1)
    * a v Labeli labelSlider sa vypisuje hodnota zo Slidera násobená 100 (0 až 100)
    * zároveň ak bola zmenená hodnota hlasitosti Sliderom tak sa do premennej changed nastaví true
    */
    @FXML
    private void sliderRead(){
        slider.valueProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldValue, Number newValue) {
                //number = newValue;
                valueVolume = (double)newValue;
                int text = (int)(valueVolume*100);
                labelSlider.setText(Integer.toString(text));
                changed = true;
            }
        });

    }

    /*
    *V metóde sa nastavuje jazyk, ak skSelected je true, tak sa nastaví Slovak jazyk
    * ak enSelected je true, tak sa nastaví English
    * ak sú obidva false je použitý prednastavený jazyk tiež English
    * skSelected, enSelected sa nastavujú v metódach setSlovak, setEnglish (popis nižšie)
    * metóda setLanguage() sa spúšťa v tejto triede v metóde submitChanges()
    */
    private void setLanguage(){
        if (skSelected){
            Locale.setDefault(new Locale("sk"));
            resourceBundle = ResourceBundle.getBundle("lang",Locale.getDefault());
        }else if (enSelected){
            Locale.setDefault(new Locale("en"));
            resourceBundle = ResourceBundle.getBundle("lang",Locale.getDefault());
        }else{
            Locale.setDefault(new Locale(""));
            resourceBundle = ResourceBundle.getBundle("lang",Locale.getDefault());
        }
    }

    /*
    *V metóde sa nastavuje vybratý jazyk do Labelov a Buttonov v Menu a Settings
    * podľa vybraného jazyka nastavuje text podľa kľúča z resource bundle
    * metóda setText() sa spúšťa v tejto triede v metóde submitChanges() po metóde setLanguage()
    */
    private void setText(ResourceBundle resource){
        //menu
        menu.setText(resource.getString("menu"));
        btn1.setText(resource.getString("start"));
        btn2.setText(resource.getString("sett"));
        btn3.setText(resource.getString("quit"));
        //settings
        back.setText(resource.getString("back"));
        setting.setText(resource.getString("settings"));
        volume.setText(resource.getString("volume"));
        lang.setText(resource.getString("language"));
        submit.setText(resource.getString("submit"));
    }

    /*
    *V metóde sa po stlačení myšou na slovenskú vlajku v Settings
    * nastaví do skSelected true a do enSelected false
    */
    public void setSlovak(MouseEvent mouseEvent) {
        skSelected = true;
        enSelected = false;
    }


    /*
     *V metóde sa po stlačení myšou na anglickú vlajku v Settings
     * nastaví do skSelected false a do enSelected true
     */
    public void setEnglish(MouseEvent mouseEvent) {
        skSelected = false;
        enSelected = true;
    }

    public void gameOver(MouseEvent mouseEvent) {
        if(Handler.game.isGamePlay() && Handler.game.getScreen().getLevel().getHero().getLife() <= 0){
            gameMain.setVisible(true);
            winGame.setVisible(false);
            menuBox.setVisible(false);
            settingBox.setVisible(false);
        }
        if (Handler.game.isGamePlay() && Handler.game.isWinGame() && Handler.game.getScreen().getLevel().getHero().getLife() > 0){
            gameMain.setVisible(false);
            winGame.setVisible(true);
            menuBox.setVisible(false);
            settingBox.setVisible(false);
        }
    }
}
