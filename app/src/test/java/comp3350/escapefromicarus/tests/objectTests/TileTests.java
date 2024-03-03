package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.objects.Actor;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.objects.Tile;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class TileTests {

    private Tile[][] tilemap;
    private DataAccess dataAccess;

    @Before
    public void setup() {

        dataAccess = new DataAccessStub();
        // Initialize a 2 x 2 tilemap for testing
        // Any input for tile is valid, no need to check constructor arguments
        tilemap = new Tile[2][2];
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                tilemap[i][j] = new Tile(TextureType.GRASS, true);
            }
        }
    }

    @Test
    public void testWalkable() {

        // Ensure that all set tiles are walkable.
        assertTrue(tilemap[0][0].isWalkable());
        assertTrue(tilemap[0][1].isWalkable());
        assertTrue(tilemap[1][0].isWalkable());
        assertTrue(tilemap[1][1].isWalkable());

        // Ensure that setting tile to not walkable works as intended.
        tilemap[1][0].setWalkable(false);
        assertFalse(tilemap[1][0].isWalkable());

        // Ensure that disabling walkable tile for [1][0] does not affect any other tiles.
        assertTrue(tilemap[0][0].isWalkable());
        assertTrue(tilemap[0][1].isWalkable());
        assertTrue(tilemap[1][1].isWalkable());
    }

    @Test
    public void testOccupyingActor() {

        // Ensure that a tile that does not contain an Actor reports that no Actor is present
        assertNull(tilemap[0][0].getOccupyingActor());
        assertNull(tilemap[0][1].getOccupyingActor());
        assertNull(tilemap[1][0].getOccupyingActor());
        assertNull(tilemap[1][1].getOccupyingActor());

        // Ensure that getOccupyingActor register that there is a Player on tile[0][0]
        Actor testActor = new Player(TextureType.PLAYER, dataAccess);
        tilemap[1][1].setOccupyingActor(testActor);
        assertEquals(testActor, tilemap[1][1].getOccupyingActor());

        // Ensure that tiles that do not contain an Actor do not report that the tile is occupied
        assertNull(tilemap[0][1].getOccupyingActor());
        assertNull(tilemap[1][0].getOccupyingActor());
        assertNull(tilemap[0][0].getOccupyingActor());

        //remove actor on [1][1]
        tilemap[1][1].setOccupyingActor(null);
        assertNull(tilemap[1][1].getOccupyingActor());

        //retest all are still null
        assertNull(tilemap[0][1].getOccupyingActor());
        assertNull(tilemap[1][0].getOccupyingActor());
        assertNull(tilemap[0][0].getOccupyingActor());
    }

    @Test
    public void testTextureTypes() {

        assertEquals(TextureType.GRASS, tilemap[1][1].getFloor());
        tilemap[1][1].setFloor(TextureType.COMBINED_WALL);
        assertNotEquals(TextureType.GRASS, tilemap[1][1].getFloor());
        assertEquals(TextureType.COMBINED_WALL, tilemap[1][1].getFloor());
    }
}
