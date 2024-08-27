package Skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import objects.Hero;

public class Warp extends Skill {
    Texture texture;
    float range = 200;
    Vector2 direction;
    public Warp(Hero hero) {
        super("Warp", "Teleports player in target direction", false, 1, 5, hero, false);
        texture = new Texture("badlogic.jpg");
    }

    @Override
    public void useSkill() {
        if (canUseSkill()) {
            direction = hero.moveInfo.direction;
            if (direction == null) {
                return;
            }
            if (direction.len() > 0) {
                direction.nor().scl(range);
            }
            hero.position.add(direction);
            System.out.println("Warping to target location!");
            startCooldown();
            useMana();
        } else {
            System.out.println("Warp is on cooldown!");
            System.out.println(remainingCooldown);
        }
    }
}
