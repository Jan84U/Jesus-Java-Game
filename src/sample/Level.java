package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.io.File;
import java.util.ArrayList;

public class Level extends Background {

    private double volume;
    private Group group = new Group();
    private Scene scene = new Scene(group);
    private Hero hero;  //HERO -> hrac
    private Image background;
    private Image heroIMG; //aktualna grafika hraca
    private int time,enterPressTimer;
    private  int pressedAttack; //aktualny vybrany utok
    private ArrayList<DamageText> damagesToShow = new ArrayList<>();
    private int reversetime[]; //cas pre chodzu enemy
    private HeroGUI heroGUI;


    private int enemy1Cout;  //maximalny pocet enemy1
    private Enemy[] enemy1;

    private int enemy2Cout;  //maximalny pocet enemy1
    private Enemy[] enemy2;

    private int enemy3Cout;  //maximalny pocet enemy1
    private Enemy[] enemy3;

    private ArrayList<Enemy> enemies;

    private int totalEnemyCount;
    private String levelName;

    private int coinCount=0;    // maximalny pocet coinov
    private Coins[] coin = new Coins[coinCount];

    private int healPotionCount=3;     //maximalny pocet health potionov
    private HealPotion[] healPotion = new HealPotion[healPotionCount];

    //SFX -hudba v pozadi
    private String musicFile;
    private Media sound;
    private MediaPlayer mediaPlayer;

    private ReadFile reader;




    Level(Stage theStage, String levelName, int enemy1Cout, int enemy2Cout,int enemy3Cout){
        this.background = new Image( "sample/Img/Maps/"+levelName+".png",canvas.getWidth(),canvas.getHeight(),true,true );

        this.enemy1Cout = enemy1Cout;
        this.enemy2Cout = enemy2Cout;
        this.enemy3Cout = enemy3Cout;

        this.enemy1 = new Enemy[enemy1Cout];
        this.enemy2 = new Enemy[enemy2Cout];
        this.enemy3 = new Enemy[enemy3Cout];

        enemies = new ArrayList<>();

        this.levelName = levelName;
        totalEnemyCount = enemy1Cout+enemy2Cout+enemy3Cout;

        this.musicFile = "src/sample/msc/"+levelName+".mp3";
        this.sound = new Media(new File(musicFile).toURI().toString());
        this.mediaPlayer = new MediaPlayer(sound);
        reversetime = new int[totalEnemyCount];
        for (int i = 0; i<totalEnemyCount;i++){
            reversetime[i] = 100;
        }
        reader = new ReadFile(levelName);
        theStage.setScene( scene );
        group.getChildren().add(canvas);
        hero = new Hero(300,370,tileset);
        tileset[0]=canvas.getWidth()/numericMap[0].length;
        tileset[1]=canvas.getHeight()/numericMap.length;
        System.out.println("tilesetSize x: "+tileset[0]+" y: "+tileset[1]);
        numericMap=reader.getMatrix();
        for(int i=0;i<numericMap.length;i++){
            for(int j=0;j<numericMap[0].length;j++){
                System.out.print(numericMap[i][j]);
                //numericMap[i][j]=0;
            }
            System.out.println();
        }
        for(int i=0;i<enemy1Cout;i++){
            enemy1[i]=new Enemy(numericMap,tileset,canvas.getWidth(),canvas.getHeight(),1);
            enemy1[i].setLifeRandom(100);
            enemies.add(enemy1[i]);
        }

        for(int i=0;i<enemy2Cout;i++){
            enemy2[i]=new Enemy(numericMap,tileset,canvas.getWidth(),canvas.getHeight(),2);
            enemy2[i].setLifeRandom(200);
            enemies.add(enemy2[i]);
        }

        for (int i=0;i<coinCount;i++){
            coin[i]=new Coins(tileset);
        }
        for (int i=0;i<healPotionCount;i++){
            healPotion[i]=new HealPotion(tileset);
        }

        heroGUI= new HeroGUI(gc);

        volume = Handler.game.getVolume();
        mediaPlayer.setVolume(volume);
    }

    public void drawLevel(int grid){

        //System.out.println(hero.getDirection());
        switch (hero.getDirection()){

            case NORTH:
                if(hero.motion)
                    heroIMG = hero.getImgs().get(3);
                else
                    heroIMG = hero.getImgs().get(5);
                break;
            case SOUTH:
                if (hero.motion)
                    heroIMG = hero.getImgs().get(0);
                else
                    heroIMG = hero.getImgs().get(4);
                break;
            case EAST:
                if(hero.motion)
                    heroIMG = hero.getImgs().get(2);
                else
                    heroIMG = hero.getImgs().get(7);
                break;
            case WEST:
                if(hero.motion)
                    heroIMG = hero.getImgs().get(1);
                else
                    heroIMG = hero.getImgs().get(6);
                break;
            default:
                heroIMG = hero.getImgs().get(4);

        }

        gc.drawImage( background, 0, 0 );

        showGrid(grid);
    } //vyber obrazku pre hraca + vykreslenie levelu

    private void showGrid(int grid){
        switch (grid){
            case 1:
                for(int i=0;i<numericMap.length;i++){
                    for(int j=0;j<numericMap[0].length;j++){
                        if(numericMap[i][j]==1){
                            gc.fillRect(tileset[0]*j,tileset[1]*i,tileset[0],tileset[1]);
                        }
                    }
                }
                break;
            case 2:
                for(int i=0;i<numericMap[0].length;i++){
                    gc.strokeLine((canvas.getWidth()/numericMap[0].length)*i,0,(canvas.getWidth()/numericMap[0].length)*i,canvas.getHeight());
                }
                for(int i=0;i<numericMap.length;i++){
                    gc.strokeLine(0,(canvas.getHeight()/numericMap.length)*i,canvas.getWidth(),(canvas.getHeight()/numericMap.length)*i);
                }
                break;
            case 3:
                gc.strokeRect(hero.getX()-heroIMG.getWidth(),hero.getY()-heroIMG.getHeight()*2,heroIMG.getWidth()*2,heroIMG.getHeight()*3);
                for(Enemy enemy: enemies){
                    gc.strokeRect(enemy.getX()-enemy.getSkin().getWidth()/2,enemy.getY()-enemy.getSkin().getHeight()/2,enemy.getSkin().getWidth(),enemy.getSkin().getHeight());
                    gc.fillOval(enemy.getX()-5, enemy.getY()-5, 10, 10);
                    gc.setStroke(Color.RED);
                    gc.strokeRect(enemy.getX()-enemy.getActiveDistane(),enemy.getY()-enemy.getActiveDistane(),enemy.getActiveDistane()*2,enemy.getActiveDistane()*2);
                    gc.setStroke(Color.BLACK);
                }

                gc.fillOval(hero.getX()-5, hero.getY()-5, 10, 10);
                break;

        } //vyvojarska pomocka F5



        for (int j=0;j<coinCount;j++){
            if (coin[j].isVisible()){
                gc.drawImage(coin[j].getSkin(),coin[j].getX(),coin[j].getY(),coin[j].getW(),coin[j].getH());
            }
        }

        for (int j=0;j<healPotionCount;j++){
            if (healPotion[j].isVisible()){
                gc.drawImage(healPotion[j].getSkin(),healPotion[j].getX(),healPotion[j].getY(),healPotion[j].getW(),healPotion[j].getH());
            }
        }



        //VYKRESLENIE ENEMY + HRAC ///////////////////////////////////////////////////////////////////////////////////
        /**/for(Enemy enemy: enemies){
            if (enemy.getLife()>0){
                if (enemy.getY()<=hero.getY()){
                    gc.drawImage(enemy.getSkin(),enemy.getX()-enemy.getSkin().getWidth()/2,enemy.getY()-enemy.getSkin().getHeight()/2,enemy.getSkin().getWidth(),enemy.getSkin().getHeight());
                    if (hero.getX()+enemy.getActiveDistane()>enemy.getX() && hero.getX()-enemy.getActiveDistane()<enemy.getX() && hero.getY()-enemy.getActiveDistane()<enemy.getY()){
                        String xyz = "hp: "+String.valueOf(enemy.getLife());
                        Font theFont = Font.font("Minecraft", FontWeight.BOLD, 14);
                        gc.setFont(theFont);
                        gc.fillText(xyz,enemy.getX()-enemy.getSkin().getHeight()/2,enemy.getY()-enemy.getSkin().getHeight()/2);
                    }
                }
            }
        }
        /**/gc.drawImage(heroIMG,hero.getX()-heroIMG.getWidth()-10,hero.getY()-heroIMG.getHeight()*2,80,80);
        /**/for(Enemy enemy: enemies){
            if (enemy.getLife()>0){
                if (enemy.getY()>hero.getY()){
                    gc.drawImage(enemy.getSkin(),enemy.getX()-enemy.getSkin().getWidth()/2,enemy.getY()-enemy.getSkin().getHeight()/2,enemy.getSkin().getWidth(),enemy.getSkin().getHeight());
                    if (hero.getX()+enemy.getActiveDistane()>enemy.getX() && hero.getX()-enemy.getActiveDistane()<enemy.getX() && hero.getY()+enemy.getActiveDistane()>enemy.getY()){
                        String xyz = "hp: "+String.valueOf(enemy.getLife());
                        Font theFont = Font.font("Minecraft", FontWeight.BOLD, 14);
                        gc.setFont(theFont);
                        gc.fillText(xyz,enemy.getX()-enemy.getSkin().getHeight()/2,enemy.getY()-enemy.getSkin().getHeight()/2);
                    }
                }
            }
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (hero.getAttacks().get(pressedAttack).getLife()>0){
            gc.drawImage(hero.getAttacks().get(pressedAttack).getSkin(),hero.getAttacks().get(pressedAttack).getX(),hero.getAttacks().get(pressedAttack).getY()-15,30,30);
        } //vykreslenie attacku


        for (int i=0;i<damagesToShow.size();i++){
            if (damagesToShow.get(i).isActive()){
                damagesToShow.get(i).show(gc);
            }

        }

        heroGUI.show(hero.maxLife, hero.getLife());
    } //HLAVNE vykreslovanie

    public void enter(int pressedAttack){
        hero.getAttacks().get(pressedAttack).setLifeRandom(100);
        numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=0;
        enterPressTimer=1;
        this.pressedAttack=pressedAttack;
        hero.getAttacks().get(pressedAttack).direction=hero.getDirection();
        hero.getAttacks().get(pressedAttack).setX(hero.getX());
        hero.getAttacks().get(pressedAttack).setY(hero.getY());


    } //stlacil sa ENTER co teraz... zapnut zvoleny utok

    public void enterAction(){
        int x1=0,y1=0,step=hero.getAttacks().get(pressedAttack).getStep();
        if (hero.getAttacks().get(pressedAttack).getLife()>0 && hero.getAttacks().get(pressedAttack).getNumMapPos(0)<57){
            switch (hero.getAttacks().get(pressedAttack).direction){

                case NORTH:
                    if (numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)-1][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]==0){
                        y1-=step;
                    }else{
                        int whereX=hero.getAttacks().get(pressedAttack).getNumMapPos(1)-1;
                        int whereY=hero.getAttacks().get(pressedAttack).getNumMapPos(0);
                        damage(whereX,whereY,hero.getAttacks().get(pressedAttack).getDamage());
                        //numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=0;

                    }
                    break;
                case SOUTH:
                    if (numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)+1][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]==0){
                        y1+=step;
                    }else{
                        int whereX=hero.getAttacks().get(pressedAttack).getNumMapPos(1)+1;
                        int whereY=hero.getAttacks().get(pressedAttack).getNumMapPos(0);
                        damage(whereX,whereY,hero.getAttacks().get(pressedAttack).getDamage());
                        //numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=0;
                    }
                    break;
                case WEST:
                    if (numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)-1]==0){
                        x1-=step;
                    }else{
                        int whereX=hero.getAttacks().get(pressedAttack).getNumMapPos(1);
                        int whereY=hero.getAttacks().get(pressedAttack).getNumMapPos(0)-1;
                        damage(whereX,whereY,hero.getAttacks().get(pressedAttack).getDamage());
                        //numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=0;

                    }
                    break;
                case EAST:
                    if (numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)+1]==0){
                        x1+=step;
                    }else{
                        int whereX=hero.getAttacks().get(pressedAttack).getNumMapPos(1);
                        int whereY=hero.getAttacks().get(pressedAttack).getNumMapPos(0)+1;
                        damage(whereX,whereY,hero.getAttacks().get(pressedAttack).getDamage());
                        //numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=0;

                    }
                    break;
            }
        }

        numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=0;

        hero.getAttacks().get(pressedAttack).setX(hero.getAttacks().get(pressedAttack).getX()+x1);
        hero.getAttacks().get(pressedAttack).setY(hero.getAttacks().get(pressedAttack).getY()+y1);
        //hero.getAttacks().get(pressedAttack).setNumMapPos(0,hero.getAttacks().get(pressedAttack).getNumMapPos(0)+x1);
        //hero.getAttacks().get(pressedAttack).setNumMapPos(1,hero.getAttacks().get(pressedAttack).getNumMapPos(1)+y1);
        numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=1;
    }

    public void setTime(int time) {
        this.time = time;

        if (enterPressTimer<100 && enterPressTimer>0 && hero.getAttacks().get(pressedAttack).getNumMapPos(0)>1 && hero.getAttacks().get(pressedAttack).getNumMapPos(1)>1 && hero.getAttacks().get(pressedAttack).getNumMapPos(0)<58 && hero.getAttacks().get(pressedAttack).getNumMapPos(1)<33){
            //if (time % 10==0){
                enterPressTimer++;
                enterAction();
            //}
        }
        enemyMoving();
        if(time%101==0){
            every500();
        }
    }

    public void damage(int whereX,int whereY,int damage){

        boolean foundDamageText=false;
        for (Enemy enemy: enemies){
            if (enemy.getNumMapPos(0)==whereY && enemy.getNumMapPos(1)==whereX){
                numericMap[hero.getAttacks().get(pressedAttack).getNumMapPos(1)][hero.getAttacks().get(pressedAttack).getNumMapPos(0)]=0;
                hero.getAttacks().get(pressedAttack).despawn();
                hero.getAttacks().get(pressedAttack).getAttack_SFX().stop();
                hero.getAttacks().get(pressedAttack).getAttack_SFX().play();
                //System.out.println("DING DING["+i+ "]");
                enemy.setInjury((int) (damage-damage/10-Math.random()*damage/5));
                enemy.setLife(enemy.getLife()-enemy.getInjury());

                for (int j=0;j<damagesToShow.size();j++){
                    if (!damagesToShow.get(j).isActive() && !foundDamageText){
                        damagesToShow.get(j).activate(enemy.getX(),enemy.getY(),enemy.getInjury(),time);
                        foundDamageText=true;
                        break;
                    }
                }
                if (foundDamageText){
                    foundDamageText=false;
                }else{
                    damagesToShow.add(new DamageText(gc));
                    for (int j=0;j<damagesToShow.size();j++){
                        if (damagesToShow.get(j).isActive()==false && !foundDamageText){
                            damagesToShow.get(j).activate(enemy.getX(),enemy.getY(),enemy.getInjury(),time);
                            foundDamageText=true;
                            break;
                        }
                    }
                }

                if (enemy.getLife()<1){
                    numericMap[enemy.getNumMapPos(1)][enemy.getNumMapPos(0)]=0;
                    hero.giveEXP(enemy.getEXPKillReward());

                    if (Math.random()<0.9){ //sanca na spawn
                        /*for (int j=0;j<coinCount;j++){
                            if (!coin[j].isVisible()){
                                coin[j].setX(enemy1[i].getX());
                                coin[j].setY(enemy1[i].getY());
                                coin[j].setVisible(true);
                                break;
                            }
                        }*/
                    }else{
                        for (int j=0;j<healPotionCount;j++){
                            if (!healPotion[j].isVisible()){
                                healPotion[j].setX(enemy.getX());
                                healPotion[j].setY(enemy.getY());
                                healPotion[j].setVisible(true);
                                break;
                            }
                        }
                    }
                    enemy.despawn();

                }
            }
        }
        for(Enemy enemy: enemies){
            if(enemy.getLife() > 0){
                continue;
            }else {
                enemies.remove(enemy);
                totalEnemyCount -= 1;
                break;
            }
        }
    } //hracov utok a jeho nasledky

    private void damageHero(int damage){
        if (hero.getLife()>0){
            hero.setLife(hero.getLife()-(damage-hero.defence));
        }
        if(hero.getLife() <= 0){
            Handler.game.getScreen().getTheStage().setScene(Handler.game.getMenuSettings().getScene());
            mediaPlayer.stop();
            Handler.game.getMenuSettings().getMediaPlayer().play();
        }
    } // ubratie zivota herovi

    private void enemyMoving(){

        for (int i = 0;i<totalEnemyCount;i++){
            //System.out.println(i+". "+enemies.get(i).getLife()+"/"+reversetime[i]);
            if (reversetime[i]>0){

                reversetime[i]-=1;
                if (reversetime[i]%10==0){
                    if (reversetime[i]==90){
                        numericMap[enemies.get(i).numMapPos[1]][enemies.get(i).numMapPos[0]]=0;
                    }
                    numericMap[enemies.get(i).numMapPos[1]][enemies.get(i).numMapPos[0]]=0;
                    enemies.get(i).walk();
                    numericMap[enemies.get(i).numMapPos[1]][enemies.get(i).numMapPos[0]]=1;
                    if (enemies.get(i).getNumMapPos(0)==hero.getNumMapPos(0) && enemies.get(i).getNumMapPos(1)==hero.getNumMapPos(1)){
                        damageHero(enemies.get(i).damage);
                    }
                    //System.out.println("Zostavajuci cas["+i+"]: "+reversetime[i]);
                }
            }else if (reversetime[i]==0){
                enemies.get(i).direction=Direction.NO;
                //System.out.println("Minul sa cas["+i+"]. Hero direction: "+game.getScreen().getLevel().getEnemy1()[i].direction);
                //System.out.println("Enemy1["+i+"]-> x: "+game.getScreen().getLevel().getEnemy1()[i].x+" y: "+game.getScreen().getLevel().getEnemy1()[i].y);
                numericMap[enemies.get(i).numMapPos[1]][enemies.get(i).numMapPos[0]]=1;
                //System.out.println("Nastavujem numericMap["+game.getScreen().getLevel().getEnemy1()[i].numMapPos[1]+"]["+game.getScreen().getLevel().getEnemy1()[i].numMapPos[0]+"] => 1");
                reversetime[i]++;
            }
            i++;
        }
    } //enemy pohyb

    private void every500(){
       // time=0; //nulovanie casu kvoli prevencii preteceniu premennej
        //System.out.println(time);
        //System.out.println(" HeroX: " + game.getScreen().getHero().getX() + " HeroY: "+ game.getScreen().getHero().getY() +".");
        //System.out.println(" MatrixX: " + game.getScreen().getHero().getNumMap(0) + " MatrixY: "+ game.getScreen().getHero().getNumMap(1) );
        //System.out.println(game.getVolume());
                    /*System.out.println(game.getScreen().getLevel().getEnemy1().x);
                    System.out.println(game.getScreen().getLevel().getEnemy1().y);*/


        for (int i=0;i<totalEnemyCount;i++){
            if (enemies.get(i).getLife()>1){
                if (enemies.get(i).doYouWantwalk(getHero().getX(),getHero().getY())==1){
                        /*System.out.println("-------------------------------");
                        System.out.println("Enemy1["+i+"]-> x: "+game.getScreen().getLevel().getEnemy1()[i].x+" y: "+game.getScreen().getLevel().getEnemy1()[i].y);
                        System.out.println("Enemy["+i+"] pohyb...");
                        System.out.println(game.getScreen().getLevel().getEnemy1()[i].direction);*/
                    reversetime[i] = 100;
                }else{
                    //System.out.println("Enemy1["+i+"] Pohyb nechce");
                }
            }

        }
    } //pytanie sa enemy na pohyb

    public void checkHealPotion(){
        for (int i=0;i<healPotionCount;i++){
            if (healPotion[i].isVisible()){
                if (hero.getNumMapPos(0)==healPotion[i].getNumMapPos(0) && hero.getNumMapPos(1)==healPotion[i].getNumMapPos(1)){
                    healPotion[i].setVisible(false);
                    hero.setLife(hero.getLife()+30);
                    healPotion[i].getMediaPlayerHealPotion().stop();
                    healPotion[i].getMediaPlayerHealPotion().play();
                }
            }
        }

    } //hlada sa heal potion a v pripade kolizie sa vezme

    public Scene getSceneMenu() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public Hero getHero() {
        return hero;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public Enemy[] getEnemy1() {
        return enemy1;
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public int getTotalEnemyCount() {
        return totalEnemyCount;
    }

    public String getLevelName() {
        return levelName;
    }

    public void setLevelName(String levelName) {
        this.levelName = levelName;
    }

    public int getEnemy1Cout() {
        return enemy1Cout;
    }

    public int getEnemy2Cout() {
        return enemy2Cout;
    }
}
