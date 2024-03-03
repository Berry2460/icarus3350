package comp3350.escapefromicarus.objects;

import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.presentation.SoundEffect;
import comp3350.escapefromicarus.presentation.SoundPlayer;

public class Player extends Character {

    private int armor;
    private int damage;

    public Player(TextureType texture, DataAccess dataAccess) {

        super(texture, dataAccess.getLife("player"), dataAccess.getSpeed("player"));
        this.armor = dataAccess.getDefense("player");
        this.damage = dataAccess.getAttack("player");
        this.soundFX = SoundEffect.PLAYER_FX;
    }

    public void changeLevel(Level level) {

        this.removeActor();
        this.setLevel(level);
        this.teleport(level.getStartX(), level.getStartY());
    }

    @Override
    public void takeDamage(int amount) {

        SoundPlayer.setEffect(SoundEffect.PLAYER_FX);
        removeLife(amount - this.armor);
    }

    @Override
    public void attackCharacter(Character toAttack) {

        toAttack.takeDamage(this.damage);
    }

    @Override
    public boolean collision(Actor collidedWith) {

        boolean out = false;
        if (collidedWith != null){
            if (collidedWith instanceof Character
                    && this.level.getTilemap()[this.getFinalDestY() / Tile.TILE_SIZE][this.getFinalDestX() / Tile.TILE_SIZE].getOccupyingActor() == collidedWith) {
                attackCharacter((Character) collidedWith);
                out = true;
            }
            else if (collidedWith instanceof HealthPickup) {
                ((HealthPickup) collidedWith).pickedUp((Character) this);
                out = true;
            }
        }
        return out;
    }
}
