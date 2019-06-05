package sample;

import javafx.scene.image.Image;

public class Entity {
    //suradnice
    protected double x;
    protected double y;

    protected int[] numMapPos = new int[2]; //suradnice na numerickej mape

    protected Double[] tilesetSize = new Double[2]; //velkost jedneho bloku na numerickej mape
    protected Direction direction ; //smer otocenia
    protected boolean motion; //hýbe sa

    protected Image skin;      //viditelna reprezentacia entity

    protected int life;     //zivot
    protected int level;    //level
    protected double experience;    //skusenosti
    protected int damage;           //sila utoku
    protected int defence;          //sila obrany
    protected double EXPKillReward;     //odmena za zabitie
    protected int maxLife=100;          //maximalny dosiahnutelny zivot


    Entity() {
        x=0;
        y=0;
        numMapPos[0]=0;
        numMapPos[1]=0;
        life=100;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        numMapPos[0]=(int)Math.round(x/tilesetSize[0]);
    } //nastavenie x + prepocet na numericku mapu

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        Long L = Math.round(tilesetSize[1]);
        int i = Integer.valueOf(L.intValue());
        //numMapPos[1]=y/i;
        numMapPos[1]=(int)Math.round(y/tilesetSize[1]);
        /*System.out.println("------------------------------------------------");
        System.out.println("Nastavujem y na:"+y+"  numMapPos: "+numMapPos[1]);
        System.out.println("novy vypocet: "+ y/tilesetSize[1]);
        System.out.println("pretypovanie: "+ (int)(y/tilesetSize[1]));
        System.out.println("zaokruhlenie: "+ Math.round(y/tilesetSize[1]));
        System.out.println("zaokruhlenie + pretypovanie: "+ (int)Math.round(y/tilesetSize[1]));*/
    }   //nastavenie y + prepocet na numericku mapu

    public int getLife() {
        return life;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setLifeRandom(int life) {
        this.life = (int) (life+life/10-Math.random()*life/5);
        /*if(this.life>maxLife){
            this.life=maxLife;
        }*/
    }   //nastavenie priblizneho zivota

    public void setLife(int life) {
        if(life <= maxLife){
            this.life = life;
        }else this.life = maxLife;
    }

    public int getNumMapPos(int i) {
        return numMapPos[i];
    }

    public void setNumMapPos(int i,int j) {
        this.numMapPos[i] = j;
    }

    public Image getSkin() {
        return skin;
    }

    public void despawn(){
        setLife(0);
        setX(0);
        setY(0);
        setDirection(Direction.NO);
    }

    public int getLevel() {
        return level;
    }

    public double getExperience() {
        return experience;
    }

    public double getEXPKillReward() {
        return EXPKillReward;
    }
}