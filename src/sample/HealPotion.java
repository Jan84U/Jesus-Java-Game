package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class HealPotion extends Entity{
    private boolean visible=false;  //viditelnost => existencia
    private int w=20,h=20;      //velkost obrazka

    //SFX
    private String musicFileHealPotion = "src/sample/msc/fx/healPotion.wav";
    private Media soundHealPotion = new Media(new File(musicFileHealPotion).toURI().toString());
    private MediaPlayer mediaPlayerHealPotion = new MediaPlayer(soundHealPotion);

    HealPotion(Double[] ts){
        skin=new Image("sample/Img/Others/healPotion.gif");
        tilesetSize=ts;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public MediaPlayer getMediaPlayerHealPotion() {
        return mediaPlayerHealPotion;
    }
}
