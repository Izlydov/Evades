package objects;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.graphics.Color;
import java.util.Random;

public class Pellet {
    public int xp;
    public Color color;
    public Vector2 position;
    Random random = new Random();
    public int radius = 10;

    public Pellet(Area currentArea){
        float x = random.nextInt(currentArea.safeZone + (int) radius, currentArea.width - (int) radius - currentArea.safeZone);
        float y = random.nextFloat() * (currentArea.height - radius * 2) + radius;

        position = new Vector2(x, y);

        switch ((int)(x + y) % 10){
            case 1:
            case 2:
                color = Color.LIME;
                break;
            case 3:
            case 4:
                color = Color.ORANGE;
                break;
            case 8:
                color = Color.VIOLET;
                break;
            case 5:
            case 6:
                color = Color.YELLOW;
                break;
            case 9:
                color = Color.PINK;
                break;
            case 0:
            case 10:
                color = Color.LIME;
                break;
            case 7:
                color = Color.PURPLE;
        }
    }

}
