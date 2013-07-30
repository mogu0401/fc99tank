package info.lenky;

import java.util.Random;
import java.util.Vector;

import info.lenky.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;

public class MainSurfaceView extends SurfaceView implements Callback, Runnable {
    private SurfaceHolder sfh;
    private Paint paint;
    private Thread th;
    private Canvas canvas;
    
    public static int screenWidth, screenHeight;
    
    private Resources res = this.getResources();
    private Bitmap bmpTile;
    private Bitmap bmpPlayer1;
    private Bitmap bmpPlayer2;

    private GameGround gameGround;
    public static GamePlayer gamePlayer1;
    public static GamePlayer gamePlayer2;

    public MainSurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setColor(Color.WHITE);
        paint.setAntiAlias(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置背景常亮
        this.setKeepScreenOn(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screenWidth = this.getWidth();
        screenHeight = this.getHeight();
        initGame();

        th = new Thread(this);
        th.start();
    }

    private void initGame() {
        bmpPlayer1 = BitmapFactory.decodeResource(res, R.drawable.player1);
        gamePlayer1 = new GamePlayer(bmpPlayer1);
        bmpPlayer2 = BitmapFactory.decodeResource(res, R.drawable.player2);
        gamePlayer2 = new GamePlayer(bmpPlayer2);
        bmpTile = BitmapFactory.decodeResource(res, R.drawable.tile);
        gameGround = new GameGround(bmpTile);
        gameGround.loadMapData(R.raw.map_1);
    }

    public void drawView() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                //canvas.drawColor(Color.BLACK);
                
                gameGround.draw(canvas, paint);
                gamePlayer1.draw(canvas, paint);
                gamePlayer2.draw(canvas, paint);
                
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }
    
    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();
            drawView();
            long end = System.currentTimeMillis();
            try {
                if (end - start < 50) {
                    Thread.sleep(50 - (end - start));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}