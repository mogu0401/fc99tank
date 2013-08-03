package info.lenky;

import android.graphics.Bitmap;
import info.lenky.GameTank;

public class GameEnemy extends GameTank{
    
    public GameEnemy(Bitmap bmpEnemy, int x, int y) {
        super(bmpEnemy);
        this.x = x;
        this.y = y;
        this.live = true;
        //TODO: 暂定初始时都是向下，后续可改
        this.direction = GameSetting.directionDown;
    }

}
