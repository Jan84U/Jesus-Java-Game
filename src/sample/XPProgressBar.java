package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;


public class XPProgressBar { // progres bar pre XP hraca
    private double x,y,w,h; //suradnice + velkost
    private Color progresBarEmptyColor,progresBarFillColor,progresBarStrokeColor; //farby progres baru, prazny,vypln,okraje
    private int strokeWidth;    //hrubka okrajov

    XPProgressBar(Canvas canvas){
        x=20;
        y= canvas.getHeight()-15;
        w=canvas.getWidth()-35;
        h=10;
        progresBarEmptyColor= Color.GRAY;
        progresBarFillColor= Color.YELLOW;
        progresBarStrokeColor=Color.BLACK;
        strokeWidth=2;
    }


    public double fillSize(int heroLVL,double heroEXP,int expJMP){
        double a = heroEXP/(heroLVL*expJMP);
        double result = w*a ;
        return result;
    } //velkost kolko % sa vyplni farbou

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public Color getProgresBarEmptyColor() {
        return progresBarEmptyColor;
    }

    public Color getProgresBarFillColor() {
        return progresBarFillColor;
    }

    public Color getProgresBarStrokeColor() {
        return progresBarStrokeColor;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }
}
