package comp3350.escapefromicarus.business;

import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.World;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.presentation.SoundEffect;
import comp3350.escapefromicarus.presentation.SoundPlayer;

public abstract class WorldLogic {

    public static void addLevel(World world, DataAccess db) {

        Level level = new Level();
        LevelGeneration.generate((int)(Math.random() * Level.MAX_SEED), level, db);
        world.addLevel(level);
    }

    public static boolean tryNextLevel(World world) {

        Player player = world.getPlayer();
        boolean results = true;

        if (player == null || world.getLevelCount() < 1) {
            results = false;
        }
        else {
            Level currLevel = world.getCurrentLevel();
            if (currLevel.getEndX() == player.getTileX() && currLevel.getEndY() == player.getTileY()) {
                if (world.getLevelID() < world.getLevelCount() - 1) {
                    //go to next level
                    SoundPlayer.setEffect(SoundEffect.DOOR_FX);
                    world.setLevelID(world.getLevelID() + 1);
                    Level nextLevel = world.getCurrentLevel();
                    player.changeLevel(nextLevel);
                }
                else {
                    results = false;
                }
            }
        }
        return results;
    }
}
