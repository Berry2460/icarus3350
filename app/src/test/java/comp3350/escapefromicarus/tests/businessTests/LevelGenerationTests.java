package comp3350.escapefromicarus.tests.businessTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.LevelGeneration;
import comp3350.escapefromicarus.business.WorldLogic;
import comp3350.escapefromicarus.objects.Enemy;
import comp3350.escapefromicarus.objects.HealthPickup;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.objects.Tile;
import comp3350.escapefromicarus.objects.World;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class LevelGenerationTests {

    //Change LevelGeneration so that initLevel takes the tilemap's length
    private Level levelOne;
    private Level levelTwo;
    private World world;
    private DataAccess dataAccess;
    Tile[][] tilemap; //the map of the level
    private final int SEED = 100;

    @Before
    public void setup() {

        levelOne = new Level();
        levelTwo = new Level();
        world = new World();
        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
    }

    @Test
    public void testLevelGenerate() {

        int numWalls = 0; //for testing level has walls. Should be more than one
        int numEnemies = 0; //for testing level has enemies
        int numPickups = 0; // for testing level has pickUps

        //make level using generate
        //Test if level is generated correctly (more than 1 tile)
        LevelGeneration.generate(SEED, levelOne, dataAccess);
        tilemap = levelOne.getTilemap();
        for(int i = 0; i < Level.MAP_SIZE; i++) {
            for (int j = 0; j < Level.MAP_SIZE; j++) {

                if(tilemap[i][j].getFloor() == TextureType.LEFT_WALL ||
                   tilemap[i][j].getFloor() == TextureType.RIGHT_WALL ||
                   tilemap[i][j].getFloor() == TextureType.SIDE_WALL ||
                   tilemap[i][j].getFloor() == TextureType.BOTTOM_LEFT_WALL ||
                   tilemap[i][j].getFloor() == TextureType.BOTTOM_RIGHT_WALL ||
                   tilemap[i][j].getFloor() == TextureType.TOP_WALL ||
                   tilemap[i][j].getFloor() == TextureType.COMBINED_WALL) {
                    numWalls++;
                    //make sure walls aren't walkable
                    assertFalse(tilemap[i][j].isWalkable());
                }

                if(tilemap[i][j].getOccupyingActor() != null) {
                    //check if we can walk on top of enemy and Healthpicksups

                    //check if enemy
                    if(tilemap[i][j].getOccupyingActor() instanceof Enemy) {
                        numEnemies++;
                        assertFalse(tilemap[i][j].isWalkable());
                    }

                    //check if health pickup
                    if(tilemap[i][j].getOccupyingActor() instanceof HealthPickup) {
                        numPickups++;
                        assertFalse(tilemap[i][j].isWalkable());
                    }
                }
            }
        }

        //Test level has walls
        assertTrue(numWalls > 0);
        //Test level has enemies
        assertTrue(numEnemies > 0);
        //Test level has health pickups
        assertTrue(numPickups > 0);
    }

    @Test
    public void testEdgeWalls() {

        LevelGeneration.generate(SEED, levelTwo, dataAccess);
        tilemap = levelTwo.getTilemap();
        //Test edges are not walkable
        for(int i = 0; i < Level.MAP_SIZE; i++) {
            //left edge walls
            assertFalse(tilemap[0][i].isWalkable());

            //right edge walls
            assertFalse(tilemap[Level.MAP_SIZE - 1][i].isWalkable());

            //top edge walls
            assertFalse(tilemap[i][Level.MAP_SIZE - 1].isWalkable());

            //bottom edge walls
            assertFalse(tilemap[i][0].isWalkable());
        }
    }

    @Test
    public void testLevelInitialization() {

        //initialize the level
        LevelGeneration.initLevel(TextureType.GRASS, false, levelOne);
        tilemap = levelOne.getTilemap();
        for(int i = 0; i < Level.MAP_SIZE; i++) {
            for(int j = 0; j < Level.MAP_SIZE; j++) {
                //test all the tiles are NOT walkable when first made
                assertFalse(tilemap[i][j].isWalkable());
                //test all the tiles have the correct texture
                assertSame(tilemap[i][j].getFloor(), TextureType.GRASS);
            }
        }
    }

    @Test
    public void testLevelConnection() {

        int numDoors = 0;

        WorldLogic.addLevel(world, dataAccess);

        //test Level One
        tilemap = world.getCurrentLevel().getTilemap();

        for(int i = 0; i < Level.MAP_SIZE; i++) {
            for(int j = 0; j < Level.MAP_SIZE; j++) {
                if(tilemap[i][j].getFloor() == TextureType.DOOR) {
                    //increment door count to check later
                    numDoors++;
                }
            }
        }

        //check there is only one door
        assertEquals(1, numDoors);
    }
}
