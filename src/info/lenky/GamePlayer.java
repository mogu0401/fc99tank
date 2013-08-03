package info.lenky;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.KeyEvent;

public class GamePlayer extends GameTank{
    
    private PlayerBullet playerBullet;
    private boolean xCount, yCount;
    //需要保证游戏玩家的坦克位置总是与地图上的方格对齐，
    //所以当坦克在原方向尚未移到对齐位置时，临时用这个变
    //量保存拐弯方向，也就是是坦克即将改变的下一步方向。
    private int turnRound;

    public GamePlayer(Bitmap bmpPlayer) {
        super(bmpPlayer);
        this.xCount = false;
        this.yCount = false;
        this.playerBullet = new PlayerBullet();
        this.turnRound = this.direction;
    }
    
	public void setxCount(boolean xCount) {
        this.xCount = xCount;
    }

    public void setyCount(boolean yCount) {
        this.yCount = yCount;
    }

    public void setTurnRound(int direction) {
        this.turnRound = direction;
    }

	public void fire() {
	    int initX, initY;
	    
	    //TODO: 暂时同时只允许发射一颗子弹
	    if (this.playerBullet.isLive())
	        return;
	    
	    switch (direction) {
	        case GameSetting.directionUp:
	            initX = x + GameGround.tileScreenWidth / 2;
	            initY = y;
	            break;
            case GameSetting.directionRight:
                initX = x + GameGround.tileScreenWidth;
                initY = y + GameGround.tileScreenHeight / 2;
                break;
            case GameSetting.directionDown:
                initX = x + GameGround.tileScreenWidth / 2;
                initY = y + GameGround.tileScreenHeight;
                break;
            case GameSetting.directionLeft:
                initX = x;
                initY = y + GameGround.tileScreenHeight / 2;
                break;
            default:
                //Cannot be here
                initX = initY = -100;
                return;
	    }

	    this.playerBullet.setBulletLive(initX, initY, direction, 
	        GameSetting.speedUp[level] * GameSetting.bulletMultiSpeed);
	}
	
	public void draw(Canvas canvas, Paint paint) {
        this.playerBullet.draw(canvas, paint);

        super.draw(canvas, paint);
    }

	public void logic() {
	    int adjustPosition;
	    int tempSpeedUp;
	    
	    this.playerBullet.logic();
	    
	    if (this.turnRound != this.direction || (this.xCount == false && this.yCount == false)) {
            if (this.direction == GameSetting.directionLeft) {
                if ((adjustPosition = x % (GameGround.tileScreenWidth / 2)) > 0) {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveLeft(tempSpeedUp);
                } else 
                    this.direction = this.turnRound;
            } else if (this.direction == GameSetting.directionRight) {
                if ((adjustPosition = (GameGround.tileScreenWidth / 2) - (x % (GameGround.tileScreenWidth / 2))) 
                    < (GameGround.tileScreenWidth / 2)) 
                {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveRight(tempSpeedUp);
                } else
                    this.direction = this.turnRound;
            } else if (this.direction == GameSetting.directionUp) {
                if ((adjustPosition = y % (GameGround.tileScreenHeight / 2)) > 0) {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveUp(tempSpeedUp);
                } else
                    this.direction = this.turnRound;
            } else if (this.direction == GameSetting.directionDown) {
                if ((adjustPosition = (GameGround.tileScreenHeight / 2) - (y % (GameGround.tileScreenHeight / 2)))
                    < (GameGround.tileScreenHeight / 2))
                {
                    if (adjustPosition > GameSetting.speedUp[level])
                        tempSpeedUp = GameSetting.speedUp[level];
                    else
                        tempSpeedUp = adjustPosition;
                    this.moveDown(tempSpeedUp);
                } else
                    this.direction = this.turnRound;
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
