package sample;

import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.util.ArrayList;

public class Hero extends Entity{

    private ArrayList<Image> Imgs = new ArrayList<Image>();     //zoznam viditelnej/grafickej reprezentacie hraca
    private ArrayList<Attacks> attacks = new ArrayList<>(); //zoznam utokov
    private int unlockedAttacks=1;  //odomknute utoky
    private int expJump=10;         // skoky levelo - nasoby  sa levelom

    //SFX - utok 1
    private String musicFile = "src/sample/msc/fx/sword_1.mp3";
    private Media sound = new Media(new File(musicFile).toURI().toString());
    private MediaPlayer mediaPlayer = new MediaPlayer(sound);


    //SFX - level up
    private String musicFileNewLevel = "src/sample/msc/fx/lvl_up2.wav";
    private Media soundNewLevel = new Media(new File(musicFileNewLevel).toURI().toString());
    private MediaPlayer mediaPlayerNewLevel = new MediaPlayer(soundNewLevel);



    Hero(int x,int y, Double[] tileset){
        this.x=x;
        this.y=y;
        this.tilesetSize=tileset;
        Imgs.add(0,new Image( "sample/Img/Jesus/jesus_walk_down.gif"));
        Imgs.add(1,new Image( "sample/Img/Jesus/jesus_walk_left.gif" ));
        Imgs.add(2,new Image( "sample/Img/Jesus/jesus_walk_right.gif" ));
        Imgs.add(3,new Image( "sample/Img/Jesus/jesus_walk_up.gif" ));
        Imgs.add(4,new Image( "sample/Img/Jesus/static_idle/down.png" ));
        Imgs.add(5,new Image( "sample/Img/Jesus/static_idle/up.png" ));
        Imgs.add(6,new Image( "sample/Img/Jesus/static_idle/left.png" ));
        Imgs.add(7,new Image( "sample/Img/Jesus/static_idle/right.png" ));
        direction = Direction.NO;
        attacks.add(new Attack1(tilesetSize));
        defence=1;
        level=1;
        experience=0;
    }


    public void giveEXP(double amount){
        if ((level*expJump)>amount+experience){
            experience=experience+amount;
        }else{
            level++;
            experience=0;
            mediaPlayerNewLevel.stop();
            mediaPlayerNewLevel.play();
            maxLife+=20;
            life+=20;
        }
    } //rozdavanie skusenosti pripadne novy level

    public ArrayList<Image> getImgs() {
        return Imgs;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public ArrayList<Attacks> getAttacks() {
        return attacks;
    }

    public int getUnlockedAttacks() {
        return unlockedAttacks;
    }

    public int getExpJump() {
        return expJump;
    }
}
