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
    public static int curtTouchPointCount;
    public static int touchPoint[][];
    
    public static int eventAction;
    
    private Resources res = this.getResources();

    public static GameGround gameGround;
    public static GamePlayer gamePlayer1;
    //TODO: 连网游戏或一名AI玩家
    //public static GamePlayer gamePlayer2;
    public GameJoyPad gameJoyPad; 

    private Random random;
    private Bitmap bmpEnemy;
    private Vector<GameEnemy> vGameEnemy;
    
    public MainSurfaceView(Context context) {
        super(context);
        sfh = this.getHolder();
        sfh.addCallback(this);
        paint = new Paint();
        paint.setAntiAlias(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
        //设置背景常亮
        this.setKeepScreenOn(true);
        this.eventAction = GameSetting.actionAllKeyUp;
        
        this.curtTouchPointCount = 0;
        //三个元素分别为：x, y, down/up
        //down为1, up为0
        this.touchPoint = new int[GameSetting.maxTouchPoint][3];
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
        gamePlayer1 = new GamePlayer(BitmapFactory.decodeResource(res, R.drawable.player1));
        //gamePlayer2 = new GamePlayer(BitmapFactory.decodeResource(res, R.drawable.player2));
        gameGround = new GameGround(BitmapFactory.decodeResource(res, R.drawable.tile));
        gameJoyPad = new GameJoyPad(BitmapFactory.decodeResource(res, R.drawable.joypadleft),
            BitmapFactory.decodeResource(res, R.drawable.joypadup),
            BitmapFactory.decodeResource(res, R.drawable.joypadright),
            BitmapFactory.decodeResource(res, R.drawable.joypaddown),
            BitmapFactory.decodeResource(res, R.drawable.joypadfire));

        gameGround.loadMapData(R.raw.map_1);
        
        bmpEnemy = BitmapFactory.decodeResource(res, R.drawable.enemy);
        vGameEnemy = new Vector<GameEnemy>();
        //实例随机库
        random = new Random();
        
        GameBullet.bmpBullet = BitmapFactory.decodeResource(res, R.drawable.bullet);
    }

    public void drawView() {
        try {
            canvas = sfh.lockCanvas();
            if (canvas != null) {
                canvas.drawColor(Color.BLACK);
                
                gameGround.draw(canvas, paint);
                gamePlayer1.draw(canvas, paint);
                //gamePlayer2.draw(canvas, paint);
                gameJoyPad.draw(canvas, paint);
                
                for (int i = 0; i < vGameEnemy.size(); i++) {
                    GameEnemy gameEnemy = vGameEnemy.elementAt(i);
                    gameEnemy.draw(canvas, paint);
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
        } finally {
            if (canvas != null)
                sfh.unlockCanvasAndPost(canvas);
        }
    }
    
    private void createNewEnemy(int position) {
        int x, y;
        x = this.gameGround.tileScreenWidth / 2 * GameSetting.enemyInitPostion[position][1];
        y = this.gameGround.tileScreenHeight / 2 * GameSetting.enemyInitPostion[position][0];
        
        vGameEnemy.addElement(new GameEnemy(bmpEnemy, x, y));
        
        this.gameGround.updateTileMapByPosition(GameSetting.enemyInitPostion[position], GameSetting.EnemyIndex);
    }
    
    public void logic() {
        int position;
        
        if (vGameEnemy.size() < GameSetting.curtMaxEnemy) {
            position = random.nextInt(1000) % 3;
            if (this.gameGround.coordinateIsNothing(GameSetting.enemyInitPostion[position]))
                createNewEnemy(position);
        }
    }
    
    private void gameLogic() {
        this.logic();
        gamePlayer1.logic();
        //gamePlayer2.logic();
        gameJoyPad.logic();
    }
    
    @Override
    public void run() {
        while (true) {
            long start = System.currentTimeMillis();
            drawView();
            gameLogic();
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
        int pointCount = event.getPointerCount();
        int del = -1;
        
        switch (event.getAction()) {
        case MotionEvent.ACTION_POINTER_UP:
        //我的手机上捕获到的是这几个被Deprecated的值，所以这里也列进来
        case MotionEvent.ACTION_POINTER_2_UP:
            del = event.getActionIndex();
        case MotionEvent.ACTION_POINTER_DOWN:
        case MotionEvent.ACTION_POINTER_2_DOWN:
        case MotionEvent.ACTION_DOWN:
            this.eventAction = GameSetting.actionAnyKeyDown;
            break;
        
        case MotionEvent.ACTION_UP:
            this.eventAction = GameSetting.actionAllKeyUp;
            break;
        }
        
        if (pointCount > GameSetting.maxTouchPoint)
            pointCount = GameSetting.maxTouchPoint;

        this.curtTouchPointCount = pointCount;
        for (int i = 0; i < pointCount; i ++) {
            this.touchPoint[i][0] = (int) event.getX(i);
            this.touchPoint[i][1] = (int) event.getY(i);
            
            if (del == i) {
                this.touchPoint[i][2] = 0;
            } else {
                this.touchPoint[i][2] = 1;
            }
        }

        return true;
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}