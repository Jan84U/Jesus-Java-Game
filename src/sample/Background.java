package sample;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;


public class Background {
    protected Canvas canvas = new Canvas(1360, 768);
    protected GraphicsContext gc = canvas.getGraphicsContext2D();
    protected Integer numericMap[][] = new Integer[33][58]; //numericka mapa lvlu
    protected Double tileset[] = new Double[2];
}