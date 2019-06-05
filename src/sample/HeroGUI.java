package sample;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class HeroGUI {

    private GraphicsContext gc;
    private Image head; //obrazok hore vlavo

    HeroGUI(GraphicsContext gc){
        this.gc=gc;
        head = new Image("sample/Img/Jesus/head.png");
    }


    public void show(int maxLife,int life){
        gc.fillOval(10,10,50,50);
        gc.drawImage(head,-15,-5,100,100);
        gc.setLineWidth(5);
        gc.strokeRect(70,20,100,20);
        gc.setLineWidth(5);
        gc.setFill(Color.RED);
        gc.fillRect(70,20, (100/(double)maxLife)*life,20);
        gc.setFill(Color.BLACK);
        gc.fillText(String.valueOf(life),100,30);
    } //zobrazenie zivota a avatara hore vlavo
}
