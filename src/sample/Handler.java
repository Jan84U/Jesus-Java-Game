package sample;

/*
*Pomocou triedy Handler môžeme
* staticky pristupovať k triede Game
*/

import javafx.scene.paint.Paint;

public class Handler {
    public static Game game;

    public Handler(Game game){
        this.game = game;
    }

}
