package info.lenky;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;
import info.lenky.GameTank;

public class GamePlayer extends GameTank{
	public boolean xCount, yCount;

	public GamePlayer(Bitmap bmpPlayer) {
	    super(bmpPlayer);
	    this.xCount = false;
	    this.yCount = false;
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
