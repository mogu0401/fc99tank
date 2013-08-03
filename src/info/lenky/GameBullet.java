package info.lenky;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class GameBullet {
    public static Bitmap bmpBullet;
    
    private boolean live;
    private int direction;
    //这个x与y是子弹中心位置
    private int x, y;
    
    private int speed;
    
    public GameBullet() {
        
    }
    
    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isLive() {
        return this.live;
    }
    
    public void setBulletLive(int x, int y, int direction, int speed) {
        this.live = true;
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.speed = speed;
    }
    
    public void logic() {
        if (!live)
            return;
        
        switch (direction) {
            case GameSetting.directionUp:
                y -= speed;
                break;
            case GameSetting.directionRight:
                x += speed;
                break;
            case GameSetting.directionDown:
                y += speed;
                break;
            case GameSetting.directionLeft:
                x -= speed;
                break;
        }
        
        if (MainSurfaceView.gameGround.pixelFallOnWhere(x, y) == GameSetting.outerWallIndex)
            this.live = false;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (!live)
            return;
        
        drawBullet(canvas, paint);
    }
    
    private void drawBullet(Canvas canvas, Paint paint) 
    {
        Rect src = new Rect();
        Rect dst = new Rect();
        
        src.top = 0;
        src.bottom = src.top + GameSetting.bulletWidth;
        src.left = GameSetting.bulletWidth * direction;
        src.right = src.left + GameSetting.bulletWidth;
  
        //TODO: 子弹暂没有做屏幕分辨率处理
        dst.left = x - GameSetting.bulletWidth / 2;
        dst.right = dst.left + GameSetting.bulletWidth;
        dst.top = y - GameSetting.bulletWidth / 2;
        dst.bottom = dst.top + GameSetting.bulletWidth;
        
        canvas.drawBitmap(this.bmpBullet, src, dst, paint);
        src = null;
        dst = null;
    }
}
