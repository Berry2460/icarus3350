package comp3350.escapefromicarus.objects;

public abstract class Character extends Movement {

    // character stats
    private int life;
    private final int maxLife;

    public Character(TextureType texture, int life, float speed) {

        super(texture, speed);
        this.life = life;
        this.maxLife = life;
    }

    public boolean isDead() {

        return (this.life < 1);
    }

    public void kill() {

        this.removeActor();
    }

    public void removeLife(int amount) {

        this.life -= Math.max(amount, 1);
    }

    public void addLife(int amount) {

        this.life = Math.min(this.life + amount, this.maxLife);
    }

    public int getLife() {

        return this.life;
    }

    public int getMaxLife() {

        return this.maxLife;
    }

    public abstract void takeDamage(int amount);

    public abstract void attackCharacter(Character toAttack);

    public abstract boolean collision(Actor actor);
}
