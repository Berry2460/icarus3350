package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.LevelGeneration;
import comp3350.escapefromicarus.objects.Enemy;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;


public class PlayerTests {

    private Player player;
    private Enemy enemyOne;
    private Enemy enemyTwo;
    private DataAccess dataAccess;

    private int playerHp;

    @Before
    public void setup() {

        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
        player = new Player(TextureType.PLAYER, dataAccess);
        enemyOne = new Enemy(dataAccess, "slime");
        enemyTwo = new Enemy(dataAccess, "slime");
        playerHp = player.getLife();
    }

    @Test
    public void testChangeLevel() {

        // setup level to test
        Level level = null;

        // if level is null throw error
        try {
            player.changeLevel(level);
            fail();
        }
        catch (NullPointerException e) {
            // test passed
        }

        level = new Level();
        Level nextLevel = new Level();
        LevelGeneration.initLevel(TextureType.GRASS, true, level);
        LevelGeneration.initLevel(TextureType.GRASS, true, nextLevel);

        // place player in first level
        player.place(level, level.getStartX(), level.getStartY());

        assertEquals(level.getStartX(), player.getTileX());
        assertEquals(level.getStartY(), player.getTileY());

        // add player to level to next level
        nextLevel.setLevelStartX(2);
        nextLevel.setLevelStartY(2);
        player.changeLevel(nextLevel);

        assertNotEquals(level.getStartX(), player.getTileX());
        assertNotEquals(level.getStartY(), player.getTileY());

        assertEquals(nextLevel.getStartX(), player.getTileX());
        assertEquals(nextLevel.getStartY(), player.getTileY());
    }

    @Test
    public void testPlayerAttack() {

        //test enemy lost some health with attack from player
        int enemyHealth = enemyOne.getLife();
        player.attackCharacter(enemyOne);
        assertEquals(enemyHealth - 1, enemyOne.getLife());

        //test other player is dead once attack is done
        assertFalse(enemyOne.isDead());

        //attack until dead
        for (int i = 1; i < enemyHealth; i++) {
            assertFalse(enemyOne.isDead());
            assertEquals(enemyHealth - i, enemyOne.getLife());
            player.attackCharacter(enemyOne);
        }
        assertTrue(enemyOne.isDead());
    }

    @Test
    public void testTakeDamage() {

        //test player gets take damage then they lose life
        assertEquals(playerHp, player.getLife());
        assertFalse(player.isDead());
        assertFalse(enemyTwo.isDead());

        enemyTwo.attackCharacter(player);
        assertEquals(playerHp - 1, player.getLife());

        enemyTwo.reduceCooldown(1000.0f);

        enemyTwo.attackCharacter(player);
        assertEquals(playerHp - 2, player.getLife());

        while (!player.isDead()){
            enemyTwo.reduceCooldown(1000.0f);
            enemyTwo.attackCharacter(player);
        }
        assertTrue(player.isDead());
        assertFalse(enemyTwo.isDead());
    }
}
