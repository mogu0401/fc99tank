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

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

public class GameGround {
	private int[] curtMap;
    private Bitmap bmpTile;
    public static int tileScreenWidth, tileScreenHeight;
    public static int groundScreenWidth, groundScreenHeight;
    
	public GameGround(Bitmap bmpTile) {
	    this.bmpTile = bmpTile;

        this.tileScreenWidth = MainSurfaceView.screenWidth / 
            (GameSetting.tileCountGap + GameSetting.tileCountCol);
        this.tileScreenHeight = MainSurfaceView.screenHeight / GameSetting.tileCountRow;
        
        this.tileScreenWidth = this.tileScreenWidth / 2 * 2;
        this.tileScreenHeight = this.tileScreenHeight / 2 * 2;
        
        this.groundScreenWidth = this.tileScreenWidth * GameSetting.tileCountCol;
        this.groundScreenHeight = this.tileScreenHeight * GameSetting.tileCountRow;
        
	}
	
	private void drawMap(Canvas canvas, Paint paint) {
		int row, col;
		int leftTop;

		for (row = 0; row < GameSetting.tileCountRow; row ++) {
			for (col = 0; col < GameSetting.tileCountCol; col ++) {
				leftTop = row * 2 * GameSetting.tileCountCol * 2 + col * 2;
				drawTile(canvas, paint, row, col, 
					curtMap[leftTop], curtMap[leftTop + 1],
					curtMap[leftTop + GameSetting.tileCountCol * 2],
					curtMap[leftTop + GameSetting.tileCountCol * 2 + 1]);
			}
		}
	}
	
	public boolean loadMapData(int fileResId) {
	    int halfRow, halfCol;
        int leftTop;
        
	    curtMap = GameData.readData(fileResId);
        if (curtMap == null)
            return false;
        
        for (halfRow = 0; halfRow < GameSetting.tileCountRow * 2 - 1; halfRow ++) {
            for (halfCol = 0; halfCol < GameSetting.tileCountCol * 2 - 1; halfCol ++) {
                leftTop = halfRow * GameSetting.tileCountCol * 2 + halfCol;
                
                if (curtMap[leftTop] == curtMap[leftTop + 1] &&
                    curtMap[leftTop] == curtMap[leftTop + GameSetting.tileCountCol * 2] &&
                    curtMap[leftTop] == curtMap[leftTop + GameSetting.tileCountCol * 2 + 1])
                {
                    //寻找玩家坦克初始位置
                    if (curtMap[leftTop] == GameSetting.Player1Index) {
                        MainSurfaceView.gamePlayer1.setLive(true);
                        MainSurfaceView.gamePlayer1.x = this.tileScreenWidth / 2 * halfCol;
                        MainSurfaceView.gamePlayer1.y = this.tileScreenHeight / 2 * halfRow;
                        //Reset Map Data
                        curtMap[leftTop] = curtMap[leftTop + 1] =
                            curtMap[leftTop + GameSetting.tileCountCol * 2] = 
                                curtMap[leftTop + GameSetting.tileCountCol * 2 + 1] = GameSetting.NothingIndex;
                    } else if (leftTop == GameSetting.Player2Index) {
                        //MainSurfaceView.gamePlayer2.live = true;
                        //MainSurfaceView.gamePlayer2.x = this.tileScreenWidth / 2 * halfCol;
                        //MainSurfaceView.gamePlayer2.y = this.tileScreenHeight / 2 * halfRow;
                        //Reset Map Data
                        curtMap[leftTop] = curtMap[leftTop + 1] =
                            curtMap[leftTop + GameSetting.tileCountCol * 2] = 
                                curtMap[leftTop + GameSetting.tileCountCol * 2 + 1] = GameSetting.NothingIndex;
                    }
                }
            }
        }
        
        return true;
	}

	public void draw(Canvas canvas, Paint paint) {
	    drawMap(canvas, paint);
	}
	
	public void drawTile(Canvas canvas, Paint paint, int row, int col, int leftTop, 
            int rightTop, int leftBottom, int rigthBottom) 
    {
        if (leftTop == rightTop && leftTop == leftBottom && leftBottom == rigthBottom) {
            this.drawTile(canvas, paint, row, col, leftTop, GameSetting.totalTile);
        } else {
            this.drawTile(canvas, paint, row, col, leftTop, GameSetting.leftTopQuarterTile);
            this.drawTile(canvas, paint, row, col, rightTop, GameSetting.rightTopQuarterTile);
            this.drawTile(canvas, paint, row, col, leftBottom, GameSetting.leftBottomQuarterTile);
            this.drawTile(canvas, paint, row, col, rigthBottom, GameSetting.rigthBottomQuarterTile);
        }
    }

    private void drawTile(Canvas canvas, Paint paint, int row, int col, int index, int type) 
    {
        if (index == GameSetting.NothingIndex || index == GameSetting.Player1Index || 
            index == GameSetting.Player2Index)
        {
            return;
        }
        Rect src = new Rect();
        Rect dst = new Rect();
        switch (type) {
            case GameSetting.totalTile:
                src.top = 0;
                src.bottom = GameSetting.tileWidth;
                src.left = GameSetting.tileWidth * index;
                src.right = src.left + GameSetting.tileWidth;
          
                dst.left = this.tileScreenWidth * col;
                dst.right = dst.left + this.tileScreenWidth;
                dst.top = this.tileScreenHeight * row;
                dst.bottom = dst.top + this.tileScreenHeight;
                break;
            case GameSetting.leftTopQuarterTile:
                src.top = 0;
                src.bottom = GameSetting.tileWidth/2;
                src.left = GameSetting.tileWidth * index;
                src.right = src.left + GameSetting.tileWidth/2;
          
                dst.left = this.tileScreenWidth * col;
                dst.right = dst.left + this.tileScreenWidth/2;
                dst.top = this.tileScreenHeight * row;
                dst.bottom = dst.top + this.tileScreenHeight/2;
                break;
            case GameSetting.rightTopQuarterTile:
                src.top = 0;
                src.bottom = GameSetting.tileWidth/2;
                src.left = GameSetting.tileWidth * index + GameSetting.tileWidth/2;
                src.right = src.left + GameSetting.tileWidth/2;
          
                dst.left = this.tileScreenWidth * col + this.tileScreenWidth/2;
                dst.right = dst.left + this.tileScreenWidth/2;
                dst.top = this.tileScreenHeight * row;
                dst.bottom = dst.top + this.tileScreenHeight/2;
                break;
            case GameSetting.leftBottomQuarterTile:
                src.top = GameSetting.tileWidth/2;
                src.bottom = GameSetting.tileWidth;
                src.left = GameSetting.tileWidth * index;
                src.right = src.left + GameSetting.tileWidth/2;
          
                dst.left = this.tileScreenWidth * col;
                dst.right = dst.left + this.tileScreenWidth/2;
                dst.top = this.tileScreenHeight * row + this.tileScreenHeight/2;
                dst.bottom = dst.top + this.tileScreenHeight/2;
                break;
            case GameSetting.rigthBottomQuarterTile:
                src.top = GameSetting.tileWidth/2;
                src.bottom = GameSetting.tileWidth;
                src.left = GameSetting.tileWidth * index + GameSetting.tileWidth/2;
                src.right = src.left + GameSetting.tileWidth/2;
          
                dst.left = this.tileScreenWidth * col + this.tileScreenWidth/2;
                dst.right = dst.left + this.tileScreenWidth/2;
                dst.top = this.tileScreenHeight * row + this.tileScreenHeight/2;;
                dst.bottom = dst.top + this.tileScreenHeight/2;
                break;
        }
        
        canvas.drawBitmap(this.bmpTile, src, dst, paint);
        src = null;
        dst = null;
    }
    
    //返回资源图片tile.png内各个图像的索引值，外部借此判断像素点落在哪里
    public int pixelFallOnWhere(int x, int y) {
        int index;
        int row, col;
        
        if (x < 0 || x >= this.groundScreenWidth || y < 0 || y >= this.groundScreenHeight)
            return GameSetting.outerWallIndex;
        
        col = x / (this.tileScreenWidth / 2);
        row = y / (this.tileScreenHeight / 2);
        
        index = row * GameSetting.tileCountCol * 2 + col;
        
        //if (index < 0 || index >= this.curtMap.length)
        //    return GameSetting.outerWallIndex;
        //else //直接这样，出现错误好及时暴露出来
            return this.curtMap[index];
    }
    
    public boolean coordinateIsNothing(int position[]) {
        return coordinateIsNothing(position[0], position[1]);
    }
    
    public boolean coordinateIsNothing(int halfRow, int halfCol) {
        
        int leftTop = halfRow * GameSetting.tileCountCol * 2 + halfCol;
        
        if (curtMap[leftTop] == GameSetting.NothingIndex && curtMap[leftTop] == curtMap[leftTop + 1] &&
            curtMap[leftTop] == curtMap[leftTop + GameSetting.tileCountCol * 2] &&
            curtMap[leftTop] == curtMap[leftTop + GameSetting.tileCountCol * 2 + 1])
        {
            return true;
        }
        return false;
    }
    
    public void updateTileMapByPosition(int position[], int data) {
        updateTileMapByPosition(position[0], position[1], data);
    }
    
    public void updateTileMapByPosition(int halfRow, int halfCol, int data) {
        int leftTop = halfRow * GameSetting.tileCountCol * 2 + halfCol;
        
        curtMap[leftTop] = curtMap[leftTop + 1] = 
            curtMap[leftTop + GameSetting.tileCountCol * 2] = 
                curtMap[leftTop + GameSetting.tileCountCol * 2 + 1] = data;
    }
    
    public void updateCurtMapByPixel(int x, int y, int data) {
        int index;
        int row, col;
        
        if (x < 0 || x >= this.groundScreenWidth || y < 0 || y >= this.groundScreenHeight)
            return;
        
        col = x / (this.tileScreenWidth / 2);
        row = y / (this.tileScreenHeight / 2);
        
        index = row * GameSetting.tileCountCol * 2 + col;
        
        //if (index >= 0 && index < this.curtMap.length)
            this.curtMap[index] = data;
    }
	
}
