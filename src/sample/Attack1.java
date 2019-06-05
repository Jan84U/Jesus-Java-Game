package sample;

import javafx.scene.image.Image;

public class Attack1 extends Attacks{

    Attack1(Double[] tileset){
        skin = new Image("sample/Img/Others/Attack1.gif");
        x=20;
        y=25;
        tilesetSize=tileset;
        step=10;
        damage=50;
    }

    public Image getSkin() {
        return skin;
    }

}
