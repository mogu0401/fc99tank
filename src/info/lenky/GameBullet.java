/***************************************************************************
 *   Copyright (C) 2013~2013 by Lenky0401                                  *
 *   Email: lenky0401@gmail.com                                            *
 *   WebSite: http://lenky.info/                                           *
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 *   This program is distributed in the hope that it will be useful,       *
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *   GNU General Public License for more details.                          *
 *                                                                         *
 *   You should have received a copy of the GNU General Public License     *
 *   along with this program; if not, write to the                         *
 *   Free Software Foundation, Inc.,                                       *
 *   51 Franklin St, Fifth Floor, Boston, MA 02110-1301, USA.              *
 ***************************************************************************/

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
    private boolean blowUp;
    
    public GameBullet() {
        this.speed = 0;
        this.blowUp = false;
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
    
    private void checkHitTarget() {
        int tX1, tY1, tX2, tY2;
        int tWhere1, tWhere2;
        
        switch (direction) {
        case GameSetting.directionUp:
            tX1 = x - GameSetting.bulletWidth / 2;
            tX2 = x + GameSetting.bulletWidth / 2 - 1;
            tY1 = tY2 = y - GameSetting.bulletWidth / 2;
            break;
        case GameSetting.directionRight:
            tX1 = tX2 = x + GameSetting.bulletWidth / 2;
            tY1 = y - GameSetting.bulletWidth / 2;
            tY2 = y + GameSetting.bulletWidth / 2 - 1;
            break;
        case GameSetting.directionDown:
            tX1 = x - GameSetting.bulletWidth / 2;
            tX2 = x + GameSetting.bulletWidth / 2 - 1;
            tY1 = tY2 = y + GameSetting.bulletWidth / 2;
            break;
        case GameSetting.directionLeft:
            tX1 = tX2 = x - GameSetting.bulletWidth / 2;
            tY1 = y - GameSetting.bulletWidth / 2;
            tY2 = y + GameSetting.bulletWidth / 2 - 1;
            break;
        default:
            //Cannot be here
            tX1 = tY1 = tX2 = tY2 = -1;
            return;
        }

        tWhere1 = MainSurfaceView.gameGround.pixelFallOnWhere(tX1, tY1);
        tWhere2 = MainSurfaceView.gameGround.pixelFallOnWhere(tX2, tY2);
        if (tWhere1 != GameSetting.NothingIndex || tWhere2 != GameSetting.NothingIndex) {
            this.live = false;
            this.blowUp = true;
            
            switch(tWhere1) {
            case GameSetting.brickIndex:
                MainSurfaceView.gameGround.updateCurtMapByPixel(tX1, tY1, GameSetting.NothingIndex);
                break;
            }

            switch(tWhere2) {
            case GameSetting.brickIndex:
                MainSurfaceView.gameGround.updateCurtMapByPixel(tX2, tY2, GameSetting.NothingIndex);
                break;
            }
        }
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
        
        checkHitTarget();        
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
