package comp3350.escapefromicarus.presentation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import comp3350.escapefromicarus.persistence.DataAccess;

public abstract class SoundPlayer {

    private static DataAccess dataAccess;
    private static Sound[] allSoundEffects;

    public static void initSounds(DataAccess db) {

        dataAccess = db;
        try {
            allSoundEffects = new Sound[]{
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.SLIME_FX.getKey()))),
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.SKELETON_FX.getKey()))),
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.LEVEL_BOSS_FX.getKey()))),
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.HEALTH_PICKUP_FX.getKey()))),
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.PLAYER_FX.getKey()))),
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.DEAD_FX.getKey()))),
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.DOOR_FX.getKey()))),
                    Gdx.audio.newSound(Gdx.files.internal(dataAccess.getAudio(SoundEffect.BUTTON_FX.getKey())))
            };
        }
        catch(Exception e){}
    }

    public static void setEffect(SoundEffect effectValue) {

        Sound effect;
        if(dataAccess != null && dataAccess.isGameMuted() != 1 && effectValue != null && allSoundEffects != null) {
            effect = allSoundEffects[effectValue.getIndex()];
            effect.play(dataAccess.getVolume());
        }
    }
}
