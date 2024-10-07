package Skills;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import objects.Area;
import objects.AreaLoader;
import objects.Ball;
import objects.Hero;

public class Paralysis extends Skill{
    boolean isActive;
    float range;
    float duration;
    public Paralysis(Hero hero) {
        super("Paralysis", "Freezes enemies for 3 seconds", false, 1, 15, hero, true);
        duration = 2;
        range = 250;
    }
    @Override
    public void useSkill() {
        if (canUseSkill()) {
            if (!isActive){
                isActive = true;
            } else {
                isActive = false;
                for (Ball ball : hero.area.balls) {
                    float distance = hero.position.dst(ball.position);
                    if (distance - ball.radius <= range) {
                        ball.stun(duration);
                    }
                }
                startCooldown();
                useMana();
            }
        } else {
            System.out.println("Paralysis is on cooldown!");
            System.out.println(remainingCooldown);
        }
    }

    public void drawAura(ShapeRenderer shapeRenderer) {
        if (isActive) {
            shapeRenderer.setColor(new Color(0, 0, 0.8f, 0.2f));
            shapeRenderer.circle(hero.position.x, hero.position.y, range);
        }
    }
}
