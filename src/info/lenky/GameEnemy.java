package info.lenky;

import android.graphics.Bitmap;
import info.lenky.GameTank;

public class GameEnemy extends GameTank{
    
    public GameEnemy(Bitmap bmpEnemy, int x, int y) {
        super(bmpEnemy);
        this.x = x;
        this.y = y;
        this.live = true;
        this.direction = GameSetting.directionDown;
    }

}
