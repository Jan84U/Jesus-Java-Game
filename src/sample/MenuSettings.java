package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;

/*
*V triede MenuSettings sa deklaruje Menu a Settings
* menu a settings sú vytvorené pomocou FXML a CSS
* vytvára MediaPlayer a spúšťa zvuk
*/
public class MenuSettings {
    private Handler handler;
    private String musicFile = "src/sample/msc/mainmenu2.mp3";     // For example
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);
    private Scene scene;

    public MenuSettings(Handler handler, double volume) throws IOException {
        this.handler = handler;
        Parent root = FXMLLoader.load(getClass().getResource("menu.fxml"));
        scene = new Scene(root,1360,768);
        scene.getStylesheets().add(MenuSettings.class.getResource("css/menuStyle.css").toExternalForm());
        getMediaPlayer().setCycleCount(5);
        getMediaPlayer().setVolume(volume);
        getMediaPlayer().play();
    }

    //Vracia Scene
    public Scene getScene() {
        return scene;
    }

    //Vracia MediaPlayer
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

}
