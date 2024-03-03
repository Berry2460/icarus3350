package comp3350.escapefromicarus.objects;

import comp3350.escapefromicarus.presentation.SoundEffect;
import comp3350.escapefromicarus.presentation.SoundPlayer;

public class HealthPickup extends Actor {

    private int lifeBonus;

    public HealthPickup(TextureType type, int amount) {

        super(type);
        this.lifeBonus = amount;
        this.soundFX = SoundEffect.HEALTH_PICKUP_FX;
    }

    public void pickedUp(Character toHeal) {

        removeActor();
        SoundPlayer.setEffect(SoundEffect.HEALTH_PICKUP_FX);
        toHeal.addLife(lifeBonus);
        toHeal.halt();
    }
}
