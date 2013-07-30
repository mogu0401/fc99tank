package info.lenky;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;

public class GamePlayer {
    public boolean live;
    public int level;
	public int x, y;
	public int direction;
	//两张图片交叉显示，形成动感
	private boolean oneTwo;
	private Bitmap bmpPlayer;

	public GamePlayer(Bitmap bmpPlayer) {
	    this.live = false;
	    this.level = 0;
	    this.direction = 0;
	    this.oneTwo = false;
		this.bmpPlayer = bmpPlayer;
	}

	public void draw(Canvas canvas, Paint paint) {
	    if (!live)
	        return;
	    
	    drawPlayer(canvas, paint);
	}
	
	private void drawPlayer(Canvas canvas, Paint paint) 
    {
	    Rect src = new Rect();
        Rect dst = new Rect();
        
        src.top = GameSetting.playerWidth * direction;
        src.bottom = GameSetting.playerWidth;
        src.left = GameSetting.playerWidth * level;
        if (this.oneTwo) {
            src.left += GameSetting.playerWidth;
        }
        this.oneTwo = !this.oneTwo;
        src.right = src.left + GameSetting.playerWidth;
  
        dst.left = x;
        dst.right = dst.left + GameGround.tileScreenWidth;
        dst.top = y;
        dst.bottom = dst.top + GameGround.tileScreenHeight;
        
        canvas.drawBitmap(this.bmpPlayer, src, dst, paint);
        src = null;
        dst = null;
    }
}
