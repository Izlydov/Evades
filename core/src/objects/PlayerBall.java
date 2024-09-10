package objects;

import com.badlogic.gdx.math.Vector2;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.SpringLayout;

public class PlayerBall {
    public float radius;
    public Vector2 velocity;
    public Vector2 position;
    public float maxSpeed;
    public float minSpeed = maxSpeed / 5f;
    String name;
    public int areaId = 1;
    public float xp;
    public int level;
    public float mana;
    public float maxMana;
    public boolean isGod = false;
    public boolean isSlow = false;
    public boolean isMouseOn = false;
    public boolean isMoving;
    public MoveInfo moveInfo;
    public Area area;
    public int maxAreaReached;
    public int levelPoints;
    public float regen;

    public PlayerBall(Area currentArea){
        xp = 0;
        level = 0;
        mana = 30;
        maxMana = 30;
        maxSpeed = 10;
        radius = 20;
        maxAreaReached = 1;
        regen = 1;

        Random random = new Random();
        float x = random.nextInt(currentArea.safeZone - (int) radius - (int) radius) + (int) radius;
        float y = random.nextFloat() * (currentArea.height - radius * 2) + radius;

        this.position = new Vector2(x, y);
        this.velocity = new Vector2(0, 0);
    }
    public void setPosition(Vector2 position) {
        this.position = position;
    }
    public void toggleMovement() {
        isMouseOn = !isMouseOn;
    }
    public void toggleGodMode(){
        isGod = !isGod;
    }
}
