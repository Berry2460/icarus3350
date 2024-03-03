package comp3350.escapefromicarus.objects;

import java.util.Random;

import comp3350.escapefromicarus.business.GameLogic;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.presentation.SoundEffect;
import comp3350.escapefromicarus.presentation.SoundPlayer;

public class Enemy extends Character {

    private static final int VISION_RANGE = 4; // how far away enemy can see the player
    private static final int WANDER_RANGE = 3; // how far away enemy can move at once
    private static final int DIRECTION_MODIFIER = 2;

    private String type;
    private float attackCooldown;
    private DataAccess db;

    public Enemy(DataAccess db, String type) {

        super(GameLogic.getTextureType(type), (int)(db.getLife(type) * (((float)(db.getDifficulty() - 1) / 4.0f) + 1)), db.getSpeed(type));
        this.db = db;
        this.type = type;
        this.attackCooldown = 0.0f;
        this.soundFX = getSoundEffect();
    }

    public void reduceCooldown(float amount) {

        if (this.attackCooldown >= 0.0f){
            this.attackCooldown -= amount;
        }
    }

    public void doRoutine(int playerX, int playerY, int seed) {

        if (!this.isMoving()) {
            if (this.type.equals("levelBoss") || //boss has infinite vision
                    Math.abs(this.getTileX() - playerX) + Math.abs(this.getTileY() - playerY) <= VISION_RANGE) {
                //we see the player
                setDestination(playerX, playerY);
            }
            else {
                //randomly move
                Random random = new Random(seed);

                //We multiply by 2, so the wander ranger can be both positive and negative, which
                //the enemies can move in all directions.
                int randomX = this.getTileX() + WANDER_RANGE - random.nextInt(DIRECTION_MODIFIER * WANDER_RANGE);
                int randomY = this.getTileY() + WANDER_RANGE - random.nextInt(DIRECTION_MODIFIER * WANDER_RANGE);
                setDestination(randomX, randomY);
            }
        }
    }

    private SoundEffect getSoundEffect() {

        SoundEffect result = null;

        if(this.type.equals("slime")) {
            result = SoundEffect.SLIME_FX;
        }
        else if(this.type.equals("skeleton")) {
            result = SoundEffect.SKELETON_FX;
        }
        else if(this.type.equals("levelBoss")) {
            result = SoundEffect.LEVEL_BOSS_FX;
        }
        return result;
    }

    @Override
    public void takeDamage(int amount) {

        SoundPlayer.setEffect(getSoundEffect());
        removeLife(amount - db.getDefense(this.type));
    }

    @Override
    public void attackCharacter(Character toAttack) {

        if (this.attackCooldown <= 0.0f) {
            toAttack.takeDamage(db.getAttack(this.type));
            this.attackCooldown = 0.9f;
        }
    }

    @Override
    public boolean collision(Actor collidedWith) {

        boolean out = false;
        if (collidedWith != null) {
            if (collidedWith instanceof Player
                    && level.getTilemap()[getFinalDestY() / Tile.TILE_SIZE][getFinalDestX() / Tile.TILE_SIZE].getOccupyingActor() == collidedWith) {
                attackCharacter((Character) collidedWith);
                out = true;
            }
            if (collidedWith instanceof HealthPickup) {
                out = true;
            }
        }
        return out;
    }
}
