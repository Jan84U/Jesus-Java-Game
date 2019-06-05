package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

public class Attacks extends Entity {
    protected int step=1;   // rychlost pohybu
    protected int damage;   // sila utoku

    //SFX
    private String musicFile = "src/sample/msc/fx/Attack1-hit.wav";
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer attack_SFX = new MediaPlayer(sound);

    public int getStep() {
        return step;
    }

    public int getDamage() {
        return damage;
    }



    public MediaPlayer getAttack_SFX() {
        return attack_SFX;
    }
}
