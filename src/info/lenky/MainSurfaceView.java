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
    public static int touchX, touchY;
    
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
        this.eventAction = GameSetting.actionNull;
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
        
        this.gameGround.updateCurtMap(GameSetting.enemyInitPostion[position], GameSetting.EnemyIndex);
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
        switch (event.getAction()) {
        case MotionEvent.ACTION_DOWN:
            this.eventAction = GameSetting.actionDown;
            break;
        case MotionEvent.ACTION_UP:
            this.eventAction = GameSetting.actionUp;
            break;
        }
        this.touchX = (int) event.getRawX();
        this.touchY = (int) event.getRawY();
        
        return true;
    }
    
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        
    }
    
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}