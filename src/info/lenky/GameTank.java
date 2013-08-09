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

public class GameTank {
    protected boolean live;
    protected int level;
    //levelY取0或者1，仅为enemy坦克时有效，因为enemy坦克有两排
    private int levelY;
    protected int x, y;
    
    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    protected int direction;
    //两张图片交叉显示，形成动感
    private boolean oneTwo;
    private Bitmap bmpPlayer;

    public GameTank(Bitmap bmpPlayer) {
        this.live = false;
        this.level = 0;
        this.levelY = 0;
        this.direction = GameSetting.directionUp;
        this.oneTwo = false;
        this.bmpPlayer = bmpPlayer;
    }

    public int getDirection() {
        return direction;
    }

    public void draw(Canvas canvas, Paint paint) {
        if (!live)
            return;
        
        drawTank(canvas, paint);
    }
    
    private void drawTank(Canvas canvas, Paint paint) 
    {
        Rect src = new Rect();
        Rect dst = new Rect();
        
        src.top = (GameSetting.tankWidth * 4) * levelY + GameSetting.tankWidth * direction;
        src.bottom = src.top + GameSetting.tankWidth;
        src.left = GameSetting.tankWidth * level;
        if (this.oneTwo) {
            src.left += GameSetting.tankWidth;
        }
        this.oneTwo = !this.oneTwo;
        src.right = src.left + GameSetting.tankWidth;
  
        dst.left = x;
        dst.right = dst.left + GameGround.tileScreenWidth;
        dst.top = y;
        dst.bottom = dst.top + GameGround.tileScreenHeight;
        
        canvas.drawBitmap(this.bmpPlayer, src, dst, paint);
        src = null;
        dst = null;
    }
    
    protected void moveLeft(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x - moveStep;
        tY1 = y;
        tX2 = x - moveStep;
        //减去1是因为像素位置应该是一个半闭半开范围，即[0, GameGround.tileScreenHeight)
        tY2 = y + GameGround.tileScreenHeight - 1; 
        if (MainSurfaceView.gameGround.pixelFallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.pixelFallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.x -= moveStep;
        }
    }
    
    protected void moveRight(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x + GameGround.tileScreenWidth - 1 + moveStep;
        tY1 = y;
        tX2 = x + GameGround.tileScreenWidth - 1 + moveStep;
        tY2 = y + GameGround.tileScreenHeight - 1;
        if (MainSurfaceView.gameGround.pixelFallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.pixelFallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.x += moveStep;
        }
    }
    
    protected void moveUp(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x;
        tY1 = y - moveStep;
        tX2 = x + GameGround.tileScreenWidth - 1;
        tY2 = y - moveStep;
        if (MainSurfaceView.gameGround.pixelFallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.pixelFallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.y -= moveStep;
        }
    }
    
    protected void moveDown(int moveStep) {
        int tX1, tY1, tX2, tY2;
        tX1 = x;
        tY1 = y + GameGround.tileScreenHeight - 1 + moveStep;
        tX2 = x + GameGround.tileScreenWidth - 1;
        tY2 = y + GameGround.tileScreenHeight - 1 + moveStep;
        if (MainSurfaceView.gameGround.pixelFallOnWhere(tX1, tY1) == GameSetting.NothingIndex &&
            MainSurfaceView.gameGround.pixelFallOnWhere(tX2, tY2) == GameSetting.NothingIndex)
        {
            this.y += moveStep;
        }
    }
    
    
}
