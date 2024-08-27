package objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;

import java.util.Random;
import java.util.TimerTask;

import game.gdx.GameSettings;

public class Ball {
    public Vector2 position;
    public Vector2 velocity;
    private Vector2 originalVelocity;
    Random random = new Random();
    public float radius;

    public Ball(float radius, float maxVelocity, Area currentArea) {

        float vx = random.nextFloat(maxVelocity);
        float vy = maxVelocity - vx;


        this.radius = radius;
        float x = random.nextInt(currentArea.safeZone + (int) radius, currentArea.width - (int) radius - currentArea.safeZone);
        float y = random.nextFloat() * (currentArea.height - radius * 2) + radius;

        this.position = new Vector2(x, y);
        this.velocity = new Vector2(vx, vy);
        this.originalVelocity = new Vector2(velocity);
    }
    public void stun(float duration) {
        velocity.set(0, 0);

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                velocity.set(originalVelocity);
            }
        }, duration);
    }
}
