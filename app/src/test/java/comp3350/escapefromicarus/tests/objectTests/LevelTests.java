package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.LevelGeneration;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.Tile;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class LevelTests {

    private Level level;
    private DataAccess dataAccess;

    @Before
    public void setup() {

        level = new Level();
        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
        LevelGeneration.generate(1, level, dataAccess);
    }

    @Test
    public void testTilemap() {

        // setup testing tilemaps
        Tile[][] levelTilemap = level.getTilemap();
        Tile[][] testTilemap = null;

        assertNotNull(levelTilemap);

        level.setTilemap(testTilemap); // should not change if null

        assertNotNull(level.getTilemap());
        assertSame(levelTilemap, level.getTilemap());

        testTilemap = new Tile[Level.MAP_SIZE/2][Level.MAP_SIZE/2];
        level.setTilemap(testTilemap);

        assertSame(testTilemap, level.getTilemap());
        assertNotSame(levelTilemap, level.getTilemap());
    }

    @Test
    public void testCoords() {

        // check coords start in bounds
        checkCoords();

        // try setting negative
        level.setLevelStartX(-2);
        level.setLevelStartY(-2);
        level.setLevelEndX(-2);
        level.setLevelEndY(-2);
        checkCoords();

        // try setting too large
        level.setLevelStartX(Level.MAP_SIZE + 10);
        level.setLevelStartY(Level.MAP_SIZE + 10);
        level.setLevelEndX(Level.MAP_SIZE + 10);
        level.setLevelEndY(Level.MAP_SIZE + 10);
        checkCoords();

        // try setting at map size
        level.setLevelStartX(Level.MAP_SIZE);
        level.setLevelStartY(Level.MAP_SIZE);
        level.setLevelEndX(Level.MAP_SIZE);
        level.setLevelEndY(Level.MAP_SIZE);
        checkCoords();

        // try setting 0
        level.setLevelStartX(0);
        level.setLevelStartY(0);
        level.setLevelEndX(0);
        level.setLevelEndY(0);
        checkCoords();

        // try setting valid values
        level.setLevelStartX(5);
        level.setLevelStartY(6);
        level.setLevelEndX(7);
        level.setLevelEndY(8);
        checkCoords();

        assertSame(5, level.getStartX());
        assertSame(6, level.getStartY());
        assertSame(7, level.getEndX());
        assertSame(8, level.getEndY());
    }

    @Test
    public void testLevelIsWalkable() {

        boolean isWalkable = false;
        for (int i = level.getEndY() - 1; i <= level.getEndY() + 1; i++) {
            for (int j = level.getEndX() - 1; j <= level.getEndX() + 1 ; j++) {
                if (!isWalkable && (level.getTilemap()[i][j].isWalkable() ||
                        (!level.getTilemap()[i][j].isWalkable() && level.getTilemap()[i][j].getOccupyingActor() != null))){
                    isWalkable = true;
                }
            }
        }
        assertTrue(isWalkable);
    }

    private void checkCoords() {

        assertTrue(level.getStartX() >= 0);
        assertTrue(level.getStartY() >= 0);
        assertTrue(level.getStartX() < Level.MAP_SIZE - 1);
        assertTrue(level.getStartY() < Level.MAP_SIZE - 1);

        assertTrue(level.getEndX() >= 0);
        assertTrue(level.getEndY() >= 0);
        assertTrue(level.getEndX() < Level.MAP_SIZE - 1);
        assertTrue(level.getEndY() < Level.MAP_SIZE - 1);
    }
}
