package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.LevelGeneration;
import comp3350.escapefromicarus.objects.Enemy;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class EnemyTests {

    private Enemy enemy;
    private Level level;
    private DataAccess dataAccess;

    @Before
    public void setup() {
        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
        enemy = new Enemy(dataAccess, "slime");
        level = new Level();
        LevelGeneration.initLevel(TextureType.GRASS, true, level);
        enemy.place(level, 5, 5);
    }

    @Test
    public void testWander() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(15, 15, 0); //set player coords outside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() != 5 && enemy.getTileY() != 5);
    }

    @Test
    public void testSeekTopRight() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(7, 7, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 6 && enemy.getTileY() == 6); //moves towards player
    }

    @Test
    public void testSeekTop() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(5, 7, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 6); //moves towards player
    }

    @Test
    public void testSeekTopLeft() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(3, 7, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 4 && enemy.getTileY() == 6); //moves towards player
    }

    @Test
    public void testSeekLeft() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(3, 5, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 4 && enemy.getTileY() == 5); //moves towards player
    }

    @Test
    public void testSeekRight() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(7, 5, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 6 && enemy.getTileY() == 5); //moves towards player
    }

    @Test
    public void testSeekBottom() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(5, 3, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 4); //moves towards player
    }

    @Test
    public void testSeekBottomLeft() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(3, 3, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 4 && enemy.getTileY() == 4); //moves towards player
    }

    @Test
    public void testSeekBottomRight() {

        assertTrue(enemy.getTileX() == 5 && enemy.getTileY() == 5);
        enemy.doRoutine(7, 3, 0); //set player coords inside of seek range
        enemy.glide(1);
        assertTrue(enemy.getTileX() == 6 && enemy.getTileY() == 4); //moves towards player
    }

    @Test public void testAttackCooldown() {

        Enemy dummy = new Enemy(dataAccess, "slime");
        dummy.place(level, 0, 0);

        int dummyHp = dummy.getLife();

        assertEquals(dummyHp, dummy.getLife());
        assertFalse(dummy.isDead());
        assertFalse(enemy.isDead());

        enemy.attackCharacter(dummy);
        assertEquals(dummyHp-1, dummy.getLife());

        //waits for cooldown, next attack doesnt work
        enemy.attackCharacter(dummy);
        assertEquals(dummyHp-1, dummy.getLife());

        //reset cooldown
        enemy.reduceCooldown(100.0f); //arbitrary large value to ensure reset, measured in seconds

        //now attack works again
        enemy.attackCharacter(dummy);
        assertEquals(dummyHp-2, dummy.getLife());
    }
}
