package comp3350.escapefromicarus.presentation;

public enum SoundEffect {

    SLIME_FX(0, "slimeHitFX"),
    SKELETON_FX(1, "skeletonHitFX"),
    LEVEL_BOSS_FX(2, "levelBossHitFX"),
    HEALTH_PICKUP_FX(3, "pickUpFX"),
    PLAYER_FX(4, "playerHitFX"),
    DEAD_FX(5, "deadFX"),
    DOOR_FX(6, "doorFX"),
    BUTTON_FX(7, "buttonFX");

    private int index;
    private String key;

    SoundEffect(int i, String directory) {

        this.index = i;
        this.key = directory;
    }

    public int getIndex() {

        return this.index;
    }

    public String getKey() {

        return this.key;
    }
}
