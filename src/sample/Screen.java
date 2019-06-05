package sample;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;

/*
* V triede Screen sa nastavuje Stage z parametru konštruktora
* Handler z parametru konštruktora
* Vytvára sa Level1 s parametrom Stage
* Vytvára sa Hero ktorý sa vykresľuje v Leveloch
*/
public class Screen {
    private Stage theStage;
    private Level level;
    private int f5 =0;
    private Hero hero;
    private AttacksBarScreen attacksBarScreens[]=new AttacksBarScreen[5];
    private  int pressedAttack=0;
    private XPProgressBar  progressBar;
    private boolean isOnPosition;
    private int enemy1Count, enemy2Count;




    Screen(Stage theStage){
        this.theStage=theStage;
        level = new Level(theStage, "level1",8,0,0);
        hero = level.getHero();
        enemy1Count = level.getEnemy1Cout();
        enemy2Count = level.getEnemy2Cout();
        isOnPosition = false;

        for (int i=0;i<attacksBarScreens.length;i++){
            attacksBarScreens[i]=new AttacksBarScreen(0,(int) level.canvas.getHeight()-20);
            attacksBarScreens[i].setX((int)((level.canvas.getWidth()/2)-(attacksBarScreens.length*attacksBarScreens[i].getW()/2)+(attacksBarScreens[i].getW()*i)));
        }
        progressBar = new XPProgressBar(level.canvas);
    }

    public void mouseRead(Scene theScene){
        theScene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        //System.out.println("MOUSE-> x: "+e.getX()+" y: "+e.getY());
                        hero.getMediaPlayer().play();
                        level.enter(pressedAttack);
                    }
                });
    }

    public ArrayList<String> readKeys(ArrayList<String> input){

        level.getSceneMenu().setOnKeyPressed(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e)
                    {
                        String code = e.getCode().toString();
                        //key=code;
                        // only add once... prevent duplicates
                        if ( !input.contains(code) )
                            input.add( code );
                            //System.out.println(code);

                    }
                });
        level.getSceneMenu().setOnKeyReleased(
                new EventHandler<KeyEvent>() {
                    public void handle(KeyEvent e)
                    {
                        if (input.contains("F5")){

                            f5++;
                            f5=f5%4;
                            System.out.println(f5);
                        }
                        String code = e.getCode().toString();
                        input.remove( code );
                        //hero.setDirection(Direction.NO);
                        //key="";
                    }
                });

        return input;
    }

    public void keyEvaluation(ArrayList<String> input){
        if(level.getTotalEnemyCount() > 0 || !isOnPosition){
                double x = hero.getX();
                double y = hero.getY();
                isOnPosition = false;


                //enemy[0] manipulacia
                if (input.contains("I")){
                    level.getEnemy1()[0].setDirection(Direction.NORTH);
                    level.getEnemy1()[0].walk();
                    }
                if (input.contains("J")){
                    level.getEnemy1()[0].setDirection(Direction.WEST);
                    level.getEnemy1()[0].walk();
                }
                if (input.contains("K")){
                    level.getEnemy1()[0].setDirection(Direction.SOUTH);
                    level.getEnemy1()[0].walk();
                }
                if (input.contains("L")){
                    level.getEnemy1()[0].setDirection(Direction.EAST);
                    level.getEnemy1()[0].walk();
                }


                if (input.contains("LEFT")|| input.contains("A")){
                    if(level.numericMap[hero.getNumMapPos(1)][hero.getNumMapPos(0)-1]!=1){
                        x-=5;
                        hero.setX(x);
                        level.checkHealPotion();
                    }

                    hero.motion = true;
                    hero.setDirection(Direction.WEST);
                }


                if (input.contains("RIGHT") || input.contains("D")){
                    if(hero.getX() < level.canvas.getWidth()-50){
                        if(level.numericMap[hero.getNumMapPos(1)][hero.getNumMapPos(0)+1]!=1){
                            x+=5;
                            hero.setX(x);
                            level.checkHealPotion();
                        }
                        hero.motion = true;
                        hero.setDirection(Direction.EAST);
                    }
                }

                if (input.contains("UP") || input.contains("W")){
                    if(level.numericMap[hero.getNumMapPos(1)-1][hero.getNumMapPos(0)]!=1){
                        y-=5;
                        hero.setY(y);
                        level.checkHealPotion();
                    }
                    hero.motion = true;
                    hero.setDirection(Direction.NORTH);
                }
                if (input.contains("DOWN") || input.contains("S")){
                    if(level.numericMap[hero.getNumMapPos(1)+1][hero.getNumMapPos(0)]!=1){
                        y+=5;
                        hero.setY(y);
                        level.checkHealPotion();
                    }
                    hero.motion = true;
                    hero.setDirection(Direction.SOUTH);
                }

                if (!input.contains("UP") && !input.contains("W")&&!input.contains("DOWN") && !input.contains("S")&&!input.contains("RIGHT") && !input.contains("A")&&!input.contains("LEFT") && !input.contains("D")){
                    hero.motion = false;
                }

                if (input.contains("ESCAPE") && theStage.getScene()== level.getSceneMenu()){
                    System.out.println("Menu");
                    input.remove( "ESCAPE" );
                    level.getMediaPlayer().pause();
                    Handler.game.getScreen().getTheStage().setScene(Handler.game.getMenuSettings().getScene());
                }

                if (input.contains("P")){
                    boolean pause = Handler.game.isPauseGame();
                    pause = !pause;
                    Handler.game.setPauseGame(pause);
                    Handler.game.setPlayMusic(false);
                    input.remove("P");
                }

                if(input.contains("ENTER") || input.contains("SPACE")){
                    hero.getMediaPlayer().play();
                    level.enter(pressedAttack);
                }

                if(input.contains("DIGIT1")){
                    pressedAttack=0;
                }
                if(input.contains("DIGIT2")){
                    if (hero.getUnlockedAttacks()>1){
                        pressedAttack=1;
                    }
                }
                if(input.contains("DIGIT3")){
                    if (hero.getUnlockedAttacks()>2){
                        pressedAttack=2;
                    }
                }
                if(input.contains("DIGIT4")){
                    if (hero.getUnlockedAttacks()>3){
                        pressedAttack=3;
                    }
                }
                if(input.contains("DIGIT5")){
                    if (hero.getUnlockedAttacks()>4){
                        pressedAttack=4;
                    }
                }

                hero.getMediaPlayer().setOnEndOfMedia(new Runnable() {
                    @Override
                    public void run() {
                        hero.getMediaPlayer().stop();
                    }
                }); //po dokoceni vfx sa MediaPlayer zastavi

                // VYKRESLOVANIE OBRAZOVKY
                level.drawLevel(f5);
                for (int i=0;i<attacksBarScreens.length;i++){
                    if (pressedAttack==i){
                        level.gc.setFill(Color.GREEN);
                    }else{
                        level.gc.setFill(Color.BLACK);
                    }
                    level.gc.fillRoundRect(attacksBarScreens[i].getX(),attacksBarScreens[i].getY(),attacksBarScreens[i].getW(),attacksBarScreens[i].getH(),20,20);
                    //TODO drawLevel obrazok do attackBaru
                }

                //VYKRESLOVANIE PRGRES BARU
                level.gc.setFill(progressBar.getProgresBarEmptyColor());
                level.gc.fillRect(progressBar.getX(),progressBar.getY(),progressBar.getW(),progressBar.getH());
                level.gc.setFill(Color.BLACK);

                level.gc.setFill(progressBar.getProgresBarFillColor());
                level.gc.fillRect(progressBar.getX(),progressBar.getY(),progressBar.fillSize(hero.getLevel(),hero.getExperience(),hero.getExpJump()),progressBar.getH());
                level.gc.setFill(Color.BLACK);

                level.gc.setLineWidth(progressBar.getStrokeWidth());
                level.gc.setStroke(progressBar.getProgresBarStrokeColor());
                level.gc.strokeRect(20, level.canvas.getHeight()-15, level.canvas.getWidth()-35,10);
                level.gc.setLineWidth(2);
                level.gc.setStroke(Color.BLACK);


                hero.setNumMapPos(0,(int)(hero.getX()/ level.tileset[0]));
                hero.setNumMapPos(1,(int)(hero.getY()/ level.tileset[1]));

                nextLevel();

        }else{
            loadNextLevel(level.getLevelName());
        }
    }

    public void keyPause(ArrayList<String> input,String in){
        if(input.contains(in)){
            boolean pause = Handler.game.isPauseGame();
            pause = !pause;
            Handler.game.setPauseGame(pause);
            Handler.game.setPlayMusic(true);
            input.remove(in);
        }
    }

    private void nextLevel(){
        double x = hero.getX();
        double y = hero.getY();
        //System.out.println(x+" "+y);

        if(level.getLevelName().endsWith("1")){
            if(level.getTotalEnemyCount()<=0 && x > level.canvas.getWidth()-60){
                if(y > 350 && y<420){
                    isOnPosition = true;
                }
            }else isOnPosition = false;
        }else if(level.getLevelName().endsWith("2")){
            if(level.getTotalEnemyCount()<=0 && y < 80){
                if(x > 1145 && y < 1200){
                    isOnPosition = true;
                }
            }else isOnPosition = false;
        }else if(level.getLevelName().endsWith("3")){
            if(level.getTotalEnemyCount()<=0 && y > level.canvas.getHeight()-60){
                if(x > 1195 && y < 1220){
                    isOnPosition = true;
                }
            }else isOnPosition = false;
        }
    }

    public Stage getTheStage() {
        return theStage;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    public Level getLevel() {
        return level;
    }

    private void loadNextLevel(String levelName){
        String number = levelName.substring(5);
        int levelInt = Integer.parseInt(number);

        if(levelInt < 3){
            int nextLevel = levelInt+1;
            String nextLevelName = "level";
            enemy1Count += 2;
            enemy2Count += 3;

            level.getMediaPlayer().stop();
            Group group = new Group();
            Scene scene = new Scene(group);
            level.setScene(scene);
            level = new Level(theStage,nextLevelName.concat(Integer.toString(nextLevel)),enemy1Count,enemy2Count,0);
            hero = level.getHero();
            theStage.setScene(scene);
            level.getMediaPlayer().setVolume(Handler.game.getVolume());
            level.getMediaPlayer().play();
            Handler.game.setScreen(this);
            Handler.game.getScreen().readKeys(Main.input);
            Handler.game.getScreen().getTheStage().setScene(level.getSceneMenu());
        }else{
            if(isOnPosition){
                level.getMediaPlayer().stop();
                Handler.game.getMenuSettings().getMediaPlayer().play();
                Handler.game.getScreen().getTheStage().setScene(Handler.game.getMenuSettings().getScene());
                isOnPosition = false;
                Handler.game.setWinGame(true);
            }
        }
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }
}
