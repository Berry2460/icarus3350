package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.LevelGeneration;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.objects.World;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class WorldTests {

    private World world;
    private DataAccess dataAccess;

    @Before
    public void setup() {

        world = new World();
        dataAccess = new DataAccessStub();
    }

    @Test
    public void testWorldLevel() {

        int testID = 3;

        assertEquals(0, world.getLevelCount());

        // add player to world with no level
        assertFalse(world.addPlayer(dataAccess));

        // should not add null level
        Level testLevel = null;
        world.addLevel(testLevel);
        assertEquals(0, world.getLevelCount());

        // should add valid level
        testLevel = new Level();
        world.addLevel(testLevel);
        assertEquals(1, world.getLevelCount());
        assertEquals(Level.class, world.getCurrentLevel().getClass());
        assertSame(testLevel, world.getCurrentLevel());

        assertEquals(0, world.getLevelID()); // level should be at index 0 in levelStack

        // id should be in level size range or not update
        world.setLevelID(testID);
        assertEquals(0, world.getLevelID());

        world.addLevel(testLevel);
        world.addLevel(testLevel);
        world.addLevel(testLevel);

        world.setLevelID(testID);
        assertEquals(testID, world.getLevelID());
    }

    @Test
    public void testWorldPlayer() {

        // player does not exist yet
        Player testPlayer = world.getPlayer();
        assertNull(testPlayer);

        // add player to world with no level
        assertFalse(world.addPlayer(dataAccess));

        Level testLevel = new Level();
        LevelGeneration.initLevel(TextureType.GRASS, true, testLevel);

        world.addLevel(testLevel);
        world.addPlayer(dataAccess);
        testPlayer = world.getPlayer();
        assertNotNull(testPlayer);
    }
}
