package Skills;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import objects.Hero;

public abstract class Skill {
    String name;
    Texture texture;
    String info;
    boolean isPassive;
    float cooldown; // Time in seconds for cooldown
    float remainingCooldown = 0; // Time remaining on the cooldown
    int manaCost;
    Hero hero;
    boolean hasAura;

    // Constructor
    public Skill(String name, String info, boolean isPassive, float cooldown, int manaCost, Hero hero, boolean hasAura) {
        this.name = name;
        this.info = info;
        this.isPassive = isPassive;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.hero = hero;
        this.hasAura = hasAura;
    }

    // Update the cooldown (this would be called in your game loop)
    public void update(float delta) {
        if (remainingCooldown > 0) {
            remainingCooldown -= delta;
        }
    }

    // Abstract method that each skill will implement
    public abstract void useSkill();

    // Method to check if the skill is ready to be used
    public boolean canUseSkill() {
        return remainingCooldown <= 0 && manaCost <= hero.mana;
    }
    public boolean hasAura() {
        return hasAura;
    }

    // Reset cooldown when the skill is used
    protected void startCooldown() {
        remainingCooldown = cooldown;
    }
    protected void useMana() {
        hero.mana -= manaCost;
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getInfo() {
        return info;
    }

    public Texture getTexture() {
        return texture;
    }

}
