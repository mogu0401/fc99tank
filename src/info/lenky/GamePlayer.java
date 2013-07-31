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
	public int xCount, yCount;
	public int direction;
	//两张图片交叉显示，形成动感
	private boolean oneTwo;
	private Bitmap bmpPlayer;

	public GamePlayer(Bitmap bmpPlayer) {
	    this.live = false;
	    this.level = 0;
	    this.direction = GameSetting.directionUp;
	    this.oneTwo = false;
	    this.xCount = 0;
	    this.yCount = 0;
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
        src.bottom = src.top + GameSetting.playerWidth;
        src.left = GameSetting.playerWidth * level;
        if (this.oneTwo) {
            src.left += GameSetting.playerWidth;
        }
        this.oneTwo = !this.oneTwo;
        src.right = src.left + GameSetting.playerWidth;
  
        if (x < 0)
            x = 0;
        else if (x > GameGround.groundScreenWidth)
            x = GameGround.groundScreenWidth - GameGround.tileScreenWidth;
        dst.left = x;
        dst.right = dst.left + GameGround.tileScreenWidth;
        
        if (y < 0)
            y = 0;
        else if (y > GameGround.groundScreenHeight)
            y = GameGround.groundScreenHeight - GameGround.tileScreenHeight;
        dst.top = y;
        dst.bottom = dst.top + GameGround.tileScreenHeight;
        
        canvas.drawBitmap(this.bmpPlayer, src, dst, paint);
        src = null;
        dst = null;
    }
	
	public void logic() {
	    if (this.xCount > 0) {
	        this.xCount -= GameSetting.stepSpeed;
	        if (this.direction == GameSetting.directionLeft)
	            this.x -= GameSetting.stepSpeed;
	        else if (this.direction == GameSetting.directionRight)
	            this.x += GameSetting.stepSpeed;
	    }
	    
	    if (this.yCount > 0) {
            this.yCount -= GameSetting.stepSpeed;
            if (this.direction == GameSetting.directionUp)
                this.y -= GameSetting.stepSpeed;
            else if (this.direction == GameSetting.directionDown)
                this.y += GameSetting.stepSpeed;
        }
	}
}
