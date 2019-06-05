package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class DamageText {
    private int time=50;    //dlzka casu zobrazovania
    private int actualTime=0;   //kedy sa zacina zobrazovat
    private int endTime=actualTime+time;    //koniec zobrazenia
    private boolean active=false;           //priebeh zobrazovania
    private GraphicsContext gc;
    private double x,y; //suradnice zobrazovania
    private int damage; //zobrazujuce cislo utoku
    private String text;//pretypovanie a finalny text na zobrazenie
    private Font theFont;


    DamageText(GraphicsContext gc){
        this.gc=gc;
    }



    public void activate(double x,double y,int damage,int actualtime){
        this.x=x;
        this.y=y;
        this.damage=damage;
        this.actualTime=actualtime;
        endTime=actualtime+time;
        active=true;
    }//nastav suradnice, text, cas, a aktivuj zobrazovanie

    public void show(GraphicsContext gc){
        if (endTime>actualTime){

            gc.setFont( theFont );
            text= "-"+String.valueOf(damage);
            actualTime++;
            gc.setFill(Color.RED);
            theFont = Font.font( "Minecraft", FontWeight.BOLD, endTime-actualTime );
            gc.fillText(text,x-5,y);
            gc.setFill(Color.BLACK);
        }else{
            active=false;
            /*System.out.println("deaktivujem");
            System.out.println("endtime: "+endTime);
            System.out.println("aktualny cas: "+actualTime);*/
        }
    }//vykreslovanie textu pripadne deaktivacia zobrazenia

    public boolean isActive() {
        return active;
    }

}
