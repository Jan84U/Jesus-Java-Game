package sample;

import javafx.scene.image.Image;

public class Coins extends Entity{
    private boolean visible=false;  //viditelnost => existencia
    private int w=15,h=15;      //velkost obrazka



    Coins(Double[] ts){
        skin=new Image("sample/Img/Others/coin.gif");
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
}
