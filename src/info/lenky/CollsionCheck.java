package info.lenky;
import android.graphics.Rect;

public class CollsionCheck {

    public static boolean isCollsion(int x, int y, Rect rect) {
        
        if (x >= rect.left && x <= rect.right && y >= rect.top && y <= rect.bottom)
            return true;
        
        return false;
    }
}
