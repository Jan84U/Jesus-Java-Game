package sample;

public class AttacksBarScreen {
    private int x,y,w,h;       //suradnice + velkost

    AttacksBarScreen(int x,int y){
        this.x=x;

        this.w=58;
        this.h=60;
        this.y=y-h;
    }



    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }



    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }
}
