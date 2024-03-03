package comp3350.escapefromicarus.tests.businessTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.WorldLogic;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.World;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class WorldLogicTests {
    private World world;
    private DataAccess dataAccess;

    @Before
    public void setUp() {

        // Start each test with a new World
        world = new World();
        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
    }

    @Test
    public void testTryNextLevel() {

        // no level exists so there is no next level
        assertFalse(WorldLogic.tryNextLevel(world));

        // add level and player
        WorldLogic.addLevel(world, dataAccess);
        world.addPlayer(dataAccess);

        // Ensure we always start with Level 0
        assertEquals(0, world.getLevelID());

        // player starts at level start
        Player player = world.getPlayer();
        Level currLevel = world.getCurrentLevel();
        assertTrue(player.getTileX() == currLevel.getStartX() && player.getTileY() == currLevel.getStartY());

        // now a level exists and as long as the player doesn't proceed past it, it will return true
        assertTrue(WorldLogic.tryNextLevel(world));
    }

    @Test
    public void testMultipleLevels() {

        // no level exists so there is no next level
        assertFalse(WorldLogic.tryNextLevel(world));
        assertEquals(0, world.getLevelCount());

        // add level and player
        WorldLogic.addLevel(world, dataAccess);
        assertEquals(1, world.getLevelCount());

        WorldLogic.addLevel(world, dataAccess);
        assertEquals(2, world.getLevelCount());

        WorldLogic.addLevel(world, dataAccess);
        assertEquals(3, world.getLevelCount());

        WorldLogic.addLevel(world, dataAccess);
        assertEquals(4, world.getLevelCount());

        // Ensure that the player was successfully added to the world
        assertTrue(world.addPlayer(dataAccess));
        assertEquals(4, world.getLevelCount());

        // Ensure we always start with Level 0
        assertEquals(0, world.getLevelID());

        // player starts at level start
        Player player = world.getPlayer();
        Level currLevel = world.getCurrentLevel();

        assertTrue(player.getTileX() == currLevel.getStartX() &&
                player.getTileY() == currLevel.getStartY());

        // now a level exists and as long as the player doesn't proceed past it, it will return true
        assertTrue(WorldLogic.tryNextLevel(world));
    }

    @Test
    public void testNoLevels() {

        assertEquals(0, world.getLevelCount());

        // Ensure that trying to add a player to a world that does not exist fails gracefully
        assertFalse(world.addPlayer(dataAccess));
    }

    @Test
    public void testLevelConnection() {

        // add level and player
        world = new World(); //reset the world
        WorldLogic.addLevel(world, dataAccess);
        world.addPlayer(dataAccess);

        //add another level and make sure number of levels incremented
        WorldLogic.addLevel(world, dataAccess);
        assertEquals(2, world.getLevelCount());

        //move the player to the door
        Player player = world.getPlayer();
        Level currLevel = world.getCurrentLevel();
        int endX = currLevel.getEndX();
        int endY = currLevel.getEndY();
        player.teleport(endX, endY);

        //check current level is 0
        assertEquals(0, world.getLevelID());
        WorldLogic.tryNextLevel(world);
    }
}
