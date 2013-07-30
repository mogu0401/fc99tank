package info.lenky;

public class GameSetting {
    //整个手机屏幕被均等的划分为13+2列，其中左13列是游戏地图，右2列用于显示游戏信息
	public static final int tileCountCol = 13;
    public static final int tileCountGap = 2;
    //整个手机屏幕被均等的划分为10行
	public static final int tileCountRow = 10;
	//用于显示游戏信息的右2列空间外围圆角矩形的圆角角度值
	public static final float rightRoundRectRadiusRat = 5f;

	//资源图片tile.png内图像得像素值
	public static final int tileWidth = 16;
	//资源图片player1.png和player2.png内图像得像素值
	public static final int playerWidth = 16;
	//资源图片bullet.png内图像得像素值
	public static final int bulletWidth = 6;
	
	//资源图片tile.png内各个图像的索引
	//NothingIndex表示不在对应位置画图，而直接显示黑色背景
	public static final int NothingIndex = 999;
    //Player1Index表示玩家1
    public static final int Player1Index = 800;
    //Player2Index
    public static final int Player2Index = 801;
	public static final int brickIndex = 0;
	public static final int rockIndex = 1;
	public static final int forestIndex = 2;
	public static final int riverIndex = 3;
	public static final int riverIndex2 = 4;
	public static final int eagleIndex = 5;
	
	//绘画tile全部
	public static final int totalTile = 0;
	//绘画tile的左上部分
	public static final int leftTopQuarterTile = 1;
    //绘画tile的右上部分
	public static final int rightTopQuarterTile = 2;
    //绘画tile的左下部分
	public static final int leftBottomQuarterTile = 3;
    //绘画tile的右下部分
	public static final int rigthBottomQuarterTile = 4;

	//玩家坦克的移动方向，这几个值需要和资源图片
	//player1.png和player2.png内坦克图像的指向一致
    public static final int directionUp = 0;
    public static final int directionRight = 1;
    public static final int directionDown = 2;
    public static final int directionLeft = 3;
    //玩家坦克一次移动的屏幕像素数，三个元素分别代表四个级别
    //即：没有吃星星，吃了1个星星，吃了2个星星，吃了3个星星
    public static final int stepSpeed[] = {5, 6, 7, 8};
    
    
}
