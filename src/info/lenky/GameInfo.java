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
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Rect;

public class GameInfo {
    
    private Bitmap bmpInfo;
    private int gapScreenX, gapScreenY;
    private int gapScreenWidth, gapScreenHeight;
    private final int maxTankCount = 20;
    private int tankCount;
    
    public GameInfo(Bitmap bmpInfo) {
        this.gapScreenX = GameGround.groundScreenWidth;
        this.gapScreenWidth = GameGround.tileScreenWidth * GameSetting.tileCountGap;
        this.gapScreenY = 0;
        this.gapScreenHeight = GameGround.tileScreenHeight * GameSetting.tileCountRow;
        this.bmpInfo = bmpInfo;
        this.tankCount = this.maxTankCount;
    }

    public void draw(Canvas canvas, Paint paint) {
        int lineHeight;
        int lineNum;
        int leftAlign = this.gapScreenWidth / 2;
        Rect src = new Rect();
        Rect dst = new Rect();
        
        Paint tmpPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        tmpPaint.setColor(GameSetting.backGroundColor);
        
        RectF outerRect = new RectF(this.gapScreenX, this.gapScreenY, 
                MainSurfaceView.screenWidth, MainSurfaceView.screenHeight);
        canvas.drawRoundRect(outerRect, GameSetting.rightRoundRectRadiusRat, 
            GameSetting.rightRoundRectRadiusRat, tmpPaint);
        
        //整个右边被分为20行
        lineHeight = this.gapScreenHeight / 20;

        tmpPaint.setColor(Color.BLACK);
        tmpPaint.setTextAlign(Paint.Align.CENTER);
        
        lineNum = 0;
        canvas.drawText("I P: 0", this.gapScreenX + leftAlign, 
            this.gapScreenY + lineHeight * (lineNum + 1), paint);
        //lineNum ++;
        //canvas.drawText("II P: 0", this.gapScreenX + this.gapScreenWidth / 2, 
        //    this.gapScreenY + lineHeight * (lineNum + 1), paint);

        lineNum ++;
        src.top = 18;
        src.bottom = 40;
        src.left = 22;
        src.right = 44;
        
        dst.left = this.gapScreenX + leftAlign;
        dst.right = dst.left + (lineHeight - 2);
        dst.top = this.gapScreenY + lineNum * lineHeight;
        dst.bottom = dst.top + lineHeight - 2;
        
        canvas.drawBitmap(this.bmpInfo, src, dst, paint);
        canvas.drawText("4", dst.right + 5, 
                this.gapScreenY + lineHeight * (lineNum + 1) - 2, paint);
        
        lineNum ++;
        src.top = 0;
        src.bottom = 40;
        src.left = 0;
        src.right = 22;
        
        dst.left = this.gapScreenX + leftAlign;
        dst.right = dst.left + (lineHeight - 2);
        dst.top = this.gapScreenY + lineNum * lineHeight;
        dst.bottom = dst.top + lineHeight;
        
        canvas.drawBitmap(this.bmpInfo, src, dst, paint);
        canvas.drawText("0", dst.right + 5, 
                this.gapScreenY + lineHeight * (lineNum + 1) - 2, paint);
        
        lineNum ++;
        //这几个值需符合资源图片info.png内的值
        src.top = 20;
        src.bottom = 40;
        src.left = 44;
        src.right = 66;
        
        for (int i = 0; i < this.tankCount; i ++) {
            if (i % 2 == 0) {
                dst.left = this.gapScreenX + leftAlign - 5;
                dst.right = dst.left + (lineHeight - 2);
                dst.top = this.gapScreenY + (lineNum + i / 2) * lineHeight;
                dst.bottom = dst.top + lineHeight;
            } else {
                dst.left = this.gapScreenX + leftAlign + (lineHeight - 2) + 5;
                dst.right = dst.left + (lineHeight - 2);
                dst.top = this.gapScreenY + (lineNum + i / 2) * lineHeight;
                dst.bottom = dst.top + lineHeight;
            }
            canvas.drawBitmap(this.bmpInfo, src, dst, paint);
        }

        tmpPaint = null;
    }
}
