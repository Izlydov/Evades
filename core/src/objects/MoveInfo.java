package objects;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;

// Класс для хранения информации о перемещении
public class MoveInfo {
    public boolean isWDown;
    public boolean isADown;
    public boolean isSDown;
    public boolean isDDown;
    public boolean isMouseOn;
    public Vector2 mPosition;
    public Vector2 direction;

    // Этот класс пока не используется
    // Этот класс пока не используется
    // Этот класс пока не используется
    public MoveInfo(boolean isWDown, boolean isADown, boolean isSDown, boolean isDDown, boolean isMouseOn, Vector2 mPosition, Vector2 direction) {
        this.isWDown = isWDown;
        this.isADown = isADown;
        this.isSDown = isSDown;
        this.isDDown = isDDown;
        this.isMouseOn = isMouseOn;
        this.mPosition = mPosition;
        this.direction = direction;
    }
}
