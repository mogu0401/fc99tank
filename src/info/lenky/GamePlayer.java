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
	public boolean xCount, yCount;
	public int direction;
	//两张图片交叉显示，形成动感
	private boolean oneTwo;
	private Bitmap bmpPlayer;

	public GamePlayer(Bitmap bmpPlayer) {
	    this.live = false;
	    this.level = 0;
	    this.direction = GameSetting.directionUp;
	    this.oneTwo = false;
	    this.xCount = false;
	    this.yCount = false;
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
  
        dst.left = x;
        dst.right = dst.left + GameGround.tileScreenWidth;
        dst.top = y;
        dst.bottom = dst.top + GameGround.tileScreenHeight;
        
        canvas.drawBitmap(this.bmpPlayer, src, dst, paint);
        src = null;
        dst = null;
    }
	
    private void moveLeft(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x - moveStep;
        tY1 = y;
        tX2 = x - moveStep;
        //减去1是因为像素位置应该是一个半闭半开范围，即[0, GameGround.tileScreenHeight)
        tY2 = y + GameGround.tileScreenHeight - 1; 
        if (MainSurfaceView.gameGround.fallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.fallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.x -= moveStep;
        }
    }
    
    private void moveRight(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x + GameGround.tileScreenWidth - 1 + moveStep;
        tY1 = y;
        tX2 = x + GameGround.tileScreenWidth - 1 + moveStep;
        tY2 = y + GameGround.tileScreenHeight - 1;
        if (MainSurfaceView.gameGround.fallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.fallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.x += moveStep;
        }
    }
    
    private void moveUp(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x;
        tY1 = y - moveStep;
        tX2 = x + GameGround.tileScreenWidth - 1;
        tY2 = y - moveStep;
        if (MainSurfaceView.gameGround.fallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.fallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.y -= moveStep;
        }
    }
    
    private void moveDown(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x;
        tY1 = y + GameGround.tileScreenHeight - 1 + moveStep;
        tX2 = x + GameGround.tileScreenWidth - 1;
        tY2 = y + GameGround.tileScreenHeight - 1 + moveStep;
        if (MainSurfaceView.gameGround.fallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.fallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.y += moveStep;
        }
    }
    
	public void logic() {
	    int adjustPosition;
	    int tempSpeedUp;
	    
	    if (this.xCount == false && this.yCount == false) {
            if (this.direction == GameSetting.directionLeft) {
                if ((adjustPosition = x % (GameGround.tileScreenWidth / 2)) > 0) {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveLeft(tempSpeedUp);
                }
            } else if (this.direction == GameSetting.directionRight) {
                if ((adjustPosition = (GameGround.tileScreenWidth / 2) - (x % (GameGround.tileScreenWidth / 2))) 
                    < (GameGround.tileScreenWidth / 2)) 
                {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveRight(tempSpeedUp);
                }
            } else if (this.direction == GameSetting.directionUp) {
                if ((adjustPosition = y % (GameGround.tileScreenHeight / 2)) > 0) {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveUp(tempSpeedUp);
                }
            } else if (this.direction == GameSetting.directionDown) {
                if ((adjustPosition = (GameGround.tileScreenHeight / 2) - (y % (GameGround.tileScreenHeight / 2)))
                    < (GameGround.tileScreenHeight / 2))
                {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveDown(tempSpeedUp);
                }
            }
	    } else {
    	    if (this.xCount) {
    	        if (this.direction == GameSetting.directionLeft) {
    	            this.moveLeft(GameSetting.speedUp[level]);
    	        } else if (this.direction == GameSetting.directionRight) {
    	            this.moveRight(GameSetting.speedUp[level]);
    	        }
    	    }
    	    
    	    if (this.yCount) {
                if (this.direction == GameSetting.directionUp) {
                    this.moveUp(GameSetting.speedUp[level]);
                } else if (this.direction == GameSetting.directionDown) {
                    this.moveDown(GameSetting.speedUp[level]);
                }
            }
	    }
	}
}
