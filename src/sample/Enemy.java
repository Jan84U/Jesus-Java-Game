package sample;


import javafx.scene.image.Image;

import java.util.Random;

public class Enemy extends Entity {

    private Random rand = new Random();
    private Integer[][] numericMap; //mapa lvlu
    private double maxWidth;
    private double maxHeight;
    private int activeDistane=150;  //vzdialenost viditelnosti hraca
    private int injury;
    private int typeEnemy;


    Enemy(Integer[][] nM,Double[] ts,double w,double h, int typeEnemy){
        maxWidth=w;
        maxHeight=h;
        numericMap=nM;
        tilesetSize=ts;
        this.typeEnemy = typeEnemy;
        spawn();

        if(typeEnemy == 1){
            skin = new Image("sample/Img/Enemy/demon_idle.gif");
            direction=Direction.NO;
            damage=5;
            EXPKillReward=2;
        }else if(typeEnemy == 2){
            skin = new Image("sample/Img/Enemy/skeleton_walk.gif");
            direction=Direction.NO;
            damage=10;
            EXPKillReward=5;
        }else skin = null;
          direction=Direction.NO;
          damage=5;
          EXPKillReward=2;
    }


    private void spawn(){
        boolean freePlace=false;
        while (!freePlace){
            numMapPos[0]= rand.nextInt(58);
            numMapPos[1]=rand.nextInt(33);
            if(numericMap[numMapPos[1]][numMapPos[0]]==0){
                freePlace=true;
                x=(int)(numMapPos[0]*tilesetSize[0])+(int)(tilesetSize[0]/2);
                y=(int)(numMapPos[1]*tilesetSize[1]);
            }
        }

    } //spaw na random volne miesto

    public int doYouWantwalk(double heroX,double heroY){

        int intDirection;   //pomocna premenna random cislo, bud smer pohybu alebo zostane na mieste
        intDirection=rand.nextInt(15);

            switch (intDirection){
                case 0:
                    if(numericMap[numMapPos[1]+1][numMapPos[0]]==0){
                        direction=Direction.SOUTH;
                        break;
                    }
                    break;
                case 1:
                    if(numericMap[numMapPos[1]][numMapPos[0]+1]==0){
                        direction=Direction.EAST;
                        break;
                    }
                    break;
                case 2:
                    if(numericMap[numMapPos[1]-1][numMapPos[0]]==0){
                        direction=Direction.NORTH;
                        break;
                    }
                    break;
                case 3:
                    if(numericMap[numMapPos[1]][numMapPos[0]-1]==0){
                        direction=Direction.WEST;
                        break;
                    }
                    break;
                default:
                    direction=Direction.NO;

            } //nastavenie smeru pohybu

        if (x+activeDistane>heroX && x-activeDistane<heroX && this.y+activeDistane>heroY && this.y-activeDistane<heroY){

            if (heroX>x){
                if (heroY>y){
                    direction=Direction.SOUTH_EAST;
                    System.out.println("SE");
                }else{
                    direction=Direction.NORTH_EAST;
                    System.out.println("NE");
                }
            }else{
                if (heroY>y){
                    direction=Direction.SOUTH_WEST;
                    System.out.println("SW");
                }else{
                    direction=Direction.NORTH_WEST;
                    System.out.println("NW");
                }
            }
        }

        if (direction!=Direction.NO){
            return 1;
        }else{
            return 0;
        }
    } //otazka na enemy ci chce pohyb a ktorym smerom


    public void walk(){
        double stepSizex=tilesetSize[0]/10; //jeden krok je desatina z jedneho tilesetu, kedze pohyb je rozdeleny na 10 casti
        double stepSizey=tilesetSize[1]/10;


        if(direction!=Direction.NO){

            switch (direction){
                case NORTH:
                    setY(y-stepSizey);
                    break;
                case WEST:
                    setX(x-stepSizex);
                    break;
                case SOUTH:
                    setY(y+stepSizey);
                    break;
                case EAST:
                    setX(x+stepSizex);
                    break;
                case NORTH_EAST:
                    setX(x+stepSizex);
                    setY(y-stepSizey);
                    break;
                case NORTH_WEST:
                    setX(x-stepSizex);
                    setY(y-stepSizey);
                    break;
                case SOUTH_EAST:
                    setX(x+stepSizex);
                    setY(y+stepSizey);
                    break;
                case SOUTH_WEST:
                    setX(x-stepSizex);
                    setY(y+stepSizey);
                    break;
                default:
                    System.out.println("ERROR MOTION...");
            }
        }
    } //pohyb

    public int getActiveDistane() {
        return activeDistane;
    }

    public int getInjury() {
        return injury;
    }

    public void setInjury(int injury) {
        this.injury = injury;
    }

    public Image getSkin() {
        return skin;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
}
