package info.lenky;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class GameJoyPad {
    
    private Bitmap bmpJoyPadLeft;
    private Bitmap bmpJoyPadUp;
    private Bitmap bmpJoyPadRight;
    private Bitmap bmpJoyPadDown;
    private Bitmap bmpJoyPadFire;

    //按左/上/右/下/fire记录屏幕位置
    private Rect[] screenRect; 
    
    public GameJoyPad(Bitmap bmpJoyPadLeft, Bitmap bmpJoyPadUp, 
        Bitmap bmpJoyPadRight, Bitmap bmpJoyPadDown, Bitmap bmpJoyPadFire) 
    {
        int centerX, centerY, offsetX, offsetY;
        int screenWidth, screenHight;
        
        this.bmpJoyPadLeft = bmpJoyPadLeft;
        this.bmpJoyPadUp = bmpJoyPadUp;
        this.bmpJoyPadRight = bmpJoyPadRight;
        this.bmpJoyPadDown = bmpJoyPadDown;
        this.bmpJoyPadFire = bmpJoyPadFire;

        this.screenRect = new Rect[5];
        centerX = new Float(GameSetting.joyPadDirectionCol * GameGround.tileScreenWidth).intValue();
        centerY = new Float(GameSetting.joyPadDirectionRow * GameGround.tileScreenHeight).intValue();
        offsetX = new Float(GameSetting.joyPadDirectionScreenGap / 2 * GameGround.tileScreenWidth).intValue();
        offsetY = new Float(GameSetting.joyPadDirectionScreenGap / 2 * GameGround.tileScreenHeight).intValue();
        screenWidth = new Float(GameSetting.joyPadDirectionScreen * GameGround.tileScreenWidth).intValue();
        screenHight = new Float(GameSetting.joyPadDirectionScreen * GameGround.tileScreenHeight).intValue();
        //左按钮
        this.screenRect[0] = new Rect();
        this.screenRect[0].left = centerX - offsetX;
        this.screenRect[0].top = centerY - screenHight / 2;
        this.screenRect[0].right = this.screenRect[0].left + screenWidth;
        this.screenRect[0].bottom = this.screenRect[0].top + screenHight;
        //上按钮
        this.screenRect[1] = new Rect();
        this.screenRect[1].left = centerX - screenWidth / 2;
        this.screenRect[1].top = centerY - offsetY;
        this.screenRect[1].right = this.screenRect[1].left + screenWidth;
        this.screenRect[1].bottom = this.screenRect[1].top + screenHight;
        //右按钮
        this.screenRect[2] = new Rect();
        this.screenRect[2].left = centerX + offsetX - screenWidth;
        this.screenRect[2].top = centerY - screenHight / 2;
        this.screenRect[2].right = this.screenRect[2].left + screenWidth;
        this.screenRect[2].bottom = this.screenRect[2].top + screenHight;
        //下按钮
        this.screenRect[3] = new Rect();
        this.screenRect[3].left = centerX - screenWidth / 2;
        this.screenRect[3].top = centerY + offsetY - screenHight;
        this.screenRect[3].right = this.screenRect[3].left + screenWidth;
        this.screenRect[3].bottom = this.screenRect[3].top + screenHight;

        centerX = new Float(GameSetting.joyPadFireCol * GameGround.tileScreenWidth).intValue();
        centerY = new Float(GameSetting.joyPadFireRow * GameGround.tileScreenHeight).intValue();
        screenWidth = new Float(GameSetting.joyPadFireScreen * GameGround.tileScreenWidth).intValue();
        screenHight = new Float(GameSetting.joyPadFireScreen * GameGround.tileScreenHeight).intValue();
        //fire按钮
        this.screenRect[4] = new Rect();
        this.screenRect[4].left = centerX - screenWidth / 2;
        this.screenRect[4].top = centerY - screenHight / 2;
        this.screenRect[4].right = this.screenRect[4].left + screenWidth;
        this.screenRect[4].bottom = this.screenRect[4].top + screenHight;
    }
    
    public void draw(Canvas canvas, Paint paint) {
        Rect src = new Rect();

        src.left = 0;
        src.top = 0;
        src.bottom = GameSetting.joyPadDirectionWidth;
        src.right = GameSetting.joyPadDirectionWidth;
        
        //TODO: 按钮按动效果
//        paint.setAlpha(100);
        canvas.drawBitmap(this.bmpJoyPadLeft, src, screenRect[0], paint);
        canvas.drawBitmap(this.bmpJoyPadUp, src, screenRect[1], paint);
        canvas.drawBitmap(this.bmpJoyPadRight, src, screenRect[2], paint);
        canvas.drawBitmap(this.bmpJoyPadDown, src, screenRect[3], paint);

        src.bottom = GameSetting.joyPadFireWidth;
        src.right = GameSetting.joyPadFireWidth;
        canvas.drawBitmap(this.bmpJoyPadFire, src, screenRect[4], paint);
        
//        paint.setAlpha(0);
        src = null;
    }
    
    public void logic() {
        int touchX, touchY;
        boolean down;
        
        if (MainSurfaceView.eventAction == GameSetting.actionAnyKeyDown) {
            for (int i = 0; i < MainSurfaceView.curtTouchPointCount; i ++) {
                touchX = MainSurfaceView.touchPoint[i][0];
                touchY = MainSurfaceView.touchPoint[i][1];
                down = (MainSurfaceView.touchPoint[i][2] == 1) ? true : false;
                if (CollsionCheck.isCollsion(touchX, touchY, screenRect[0])) {
                    if (MainSurfaceView.gamePlayer1.getDirection() != GameSetting.directionLeft) {
                        MainSurfaceView.gamePlayer1.setxCount(false);
                        MainSurfaceView.gamePlayer1.setyCount(false);
                        MainSurfaceView.gamePlayer1.setTurnRound(GameSetting.directionLeft);
                    }
                    if (down)
                        MainSurfaceView.gamePlayer1.setxCount(true);
                    else
                        MainSurfaceView.gamePlayer1.setxCount(false);
                } else if (CollsionCheck.isCollsion(touchX, touchY, screenRect[1])) {
                    if (MainSurfaceView.gamePlayer1.getDirection() != GameSetting.directionUp) {
                        MainSurfaceView.gamePlayer1.setxCount(false);
                        MainSurfaceView.gamePlayer1.setyCount(false);
                        MainSurfaceView.gamePlayer1.setTurnRound(GameSetting.directionUp);
                    }
                    if (down)
                        MainSurfaceView.gamePlayer1.setyCount(true);
                    else
                        MainSurfaceView.gamePlayer1.setyCount(false);
                } else if (CollsionCheck.isCollsion(touchX, touchY, screenRect[2])) {
                    if (MainSurfaceView.gamePlayer1.getDirection() != GameSetting.directionRight) {
                        MainSurfaceView.gamePlayer1.setxCount(false);
                        MainSurfaceView.gamePlayer1.setyCount(false);
                        MainSurfaceView.gamePlayer1.setTurnRound(GameSetting.directionRight);
                    }
                    if (down)
                        MainSurfaceView.gamePlayer1.setxCount(true);
                    else
                        MainSurfaceView.gamePlayer1.setxCount(false);
                }  else if (CollsionCheck.isCollsion(touchX, touchY, screenRect[3])) {
                    if (MainSurfaceView.gamePlayer1.getDirection() != GameSetting.directionDown) {
                        MainSurfaceView.gamePlayer1.setxCount(false);
                        MainSurfaceView.gamePlayer1.setyCount(false);
                        MainSurfaceView.gamePlayer1.setTurnRound(GameSetting.directionDown);
                    }
                    if (down)
                        MainSurfaceView.gamePlayer1.setyCount(true);
                    else
                        MainSurfaceView.gamePlayer1.setyCount(false);
                } else if (CollsionCheck.isCollsion(touchX, touchY, screenRect[4])) {
                    if (down)
                        MainSurfaceView.gamePlayer1.fire();                    
                }
            }
        } else if (MainSurfaceView.eventAction == GameSetting.actionAllKeyUp) {
            MainSurfaceView.gamePlayer1.setxCount(false);
            MainSurfaceView.gamePlayer1.setyCount(false);
        }
        
    }
}
