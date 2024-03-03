package comp3350.escapefromicarus.business;

import comp3350.escapefromicarus.objects.Enemy;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.objects.World;
import comp3350.escapefromicarus.presentation.MyGame;
import comp3350.escapefromicarus.presentation.ScreenType;

public abstract class GameLogic {

    public static void playerDeathCheck(MyGame mainApplication, Player player) {

        if (player.isDead()) {
            mainApplication.changeScreen(ScreenType.GAME_OVER_SCREEN);
        }
    }

    public static void doEnemyLogic(Enemy enemy, int playerX, int playerY, float timeDelta) {

        enemy.reduceCooldown(timeDelta);
        enemy.doRoutine(playerX, playerY, (int) (Math.random() * Level.MAX_SEED));
    }

    public static boolean checkNextLevel(World world) {

        return !WorldLogic.tryNextLevel(world);
    }

    public static TextureType getTextureType(String texture) {

        TextureType result = null;

        if (texture.equals("slime")) {
            result = TextureType.SLIME;
        }
        else if (texture.equals("skeleton")) {
            result = TextureType.SKELETON;
        }
        else if (texture.equals("levelBoss")) {
            result = TextureType.LEVEL_BOSS;
        }
        return result;
    }
}
