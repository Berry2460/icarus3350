package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.LevelGeneration;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.objects.Tile;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class MovementTests {

    private Player player;
    private Player playerTwo;
    private Tile[][] tilemap;
    private Tile[][] tilemapFail;
    private DataAccess dataAccess;

    @Before
    public void setup() {

        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
        Level level = new Level();
        LevelGeneration.initLevel(TextureType.GRASS, true, level);
        player = new Player(TextureType.PLAYER, dataAccess);
        player.place(level, 1, 1);
        tilemap = level.getTilemap();

        Level failLevel = new Level();
        LevelGeneration.initLevel(TextureType.TOP_WALL, false, failLevel);
        tilemapFail = failLevel.getTilemap();

        //set only 2 spots to walkable one for player and one for plain grass
        tilemapFail[5][5].setWalkable(true);
        tilemapFail[5][5].setFloor(TextureType.GRASS);
        tilemapFail[5][6].setWalkable(true);
        tilemapFail[5][6].setFloor(TextureType.GRASS);

        playerTwo = new Player(TextureType.PLAYER, dataAccess);
        playerTwo.place(failLevel, 5, 5);
    }

    @Test
    public void testPathfinding() {
        assertEquals(1, player.getTileX());
        assertEquals(1, player.getTileY());

        player.setDestination(8, 3);
        doGlides();
        assertEquals(8, player.getTileX());
        assertEquals(3, player.getTileY());

        player.setDestination(2, 1);
        doGlides();
        assertEquals(2, player.getTileX());
        assertEquals(1, player.getTileY());

        player.setDestination(2, 5);
        doGlides();
        assertEquals(2, player.getTileX());
        assertEquals(5, player.getTileY());

        player.setDestination(3, 3);
        doGlides();
        assertEquals(3, player.getTileX());
        assertEquals(3, player.getTileY());
    }

    private void doGlides() {

        while (player.isMoving()) {
            player.glide(1);
        }
    }

    @Test
    public void testGoToWall() {

        assertEquals(5, playerTwo.getTileX());
        assertEquals(5, playerTwo.getTileY());
        playerTwo.setDestination(5, 4);

        //check player hasn't moved
        assertEquals(5, playerTwo.getTileX());
        assertEquals(5, playerTwo.getTileY());
    }

    @Test
    public void testPlayerOnWall() {

        //try putting the player on the wall
        playerTwo.teleport(4, 5);
        assertEquals(5, playerTwo.getTileX());
        assertEquals(5, playerTwo.getTileY());
    }

    @Test
    public void testGlideTopRight() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(2, 2);
        player.glide(1);
        assertTrue(player.getTileX() > 1 && player.getTileY() > 1);
    }

    @Test
    public void testGlideTopLeft() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(0, 2);
        player.glide(1);
        assertTrue(player.getTileX() < 1 && player.getTileY() > 1);
    }

    @Test
    public void testGlideBottomLeft() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(0, 0);
        player.glide(1);
        assertTrue(player.getTileX() < 1 && player.getTileY() < 1);
    }

    @Test
    public void testGlideBottomRight() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(2, 0);
        player.glide(1);
        assertTrue(player.getTileX() > 1 && player.getTileY() < 1);
    }

    @Test
    public void testGlideBottom() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(1, 0);
        player.glide(1);
        assertTrue(player.getTileX() == 1 && player.getTileY() < 1);
    }

    @Test
    public void testGlideTop() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(1, 2);
        player.glide(1);
        assertTrue(player.getTileX() == 1 && player.getTileY() > 1);
    }

    @Test
    public void testGlideRight() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(2, 1);
        player.glide(1);
        assertTrue(player.getTileX() > 1 && player.getTileY() == 1);
    }

    @Test
    public void testGlideLeft() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(0, 1);
        player.glide(1);
        assertTrue(player.getTileX() < 1 && player.getTileY() == 1);
    }

    @Test
    public void testTeleport() {

        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.teleport(1, 1);
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);

        player.teleport(2, 2);
        assertTrue(player.getTileX() == 2 && player.getTileY() == 2);

        player.teleport(1, 2);
        assertTrue(player.getTileX() == 1 && player.getTileY() == 2);

        player.teleport(3, 0);
        assertTrue(player.getTileX() == 3 && player.getTileY() == 0);
    }

    @Test
    public void testRedirectXTopRight() {

        setRedirectX();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(2, 2);
        player.glide(1);
        assertTrue(player.getTileX() > 1 && player.getTileY() == 1);
    }

    @Test
    public void testRedirectXTopLeft() {

        setRedirectX();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(0, 2);
        player.glide(1);
        assertTrue(player.getTileX() < 1 && player.getTileY() == 1);
    }

    @Test
    public void testRedirectXBottomLeft() {

        setRedirectX();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(0, 0);
        player.glide(1);
        assertTrue(player.getTileX() < 1 && player.getTileY() == 1);
    }

    @Test
    public void testRedirectXBottomRight() {

        setRedirectX();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(2, 0);
        player.glide(1);
        assertTrue(player.getTileX() > 1 && player.getTileY() == 1);
    }

    @Test
    public void testRedirectYTopRight() {

        setRedirectY();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(20, 20);
        player.glide(1);
        assertTrue(player.getTileX() == 1 && player.getTileY() > 1);
    }

    @Test
    public void testRedirectYTopLeft() {

        setRedirectY();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(0, 2);
        player.glide(1);
        assertTrue(player.getTileX() == 1 && player.getTileY() > 1);
    }

    @Test
    public void testRedirectYBottomLeft() {

        setRedirectY();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(0, 0);
        player.glide(1);
        assertTrue(player.getTileX() == 1 && player.getTileY() < 1);
    }

    @Test
    public void testRedirectYBottomRight() {

        setRedirectY();
        assertTrue(player.getTileX() == 1 && player.getTileY() == 1);
        player.setDestination(2, 0);
        player.glide(1);
        assertTrue(player.getTileX() == 1 && player.getTileY() < 1);
    }

    private void setRedirectX() {

        tilemap[player.getTileY() + 1][player.getTileX() - 1].setWalkable(false);
        tilemap[player.getTileY() + 1][player.getTileX()].setWalkable(false);
        tilemap[player.getTileY() + 1][player.getTileX() + 1].setWalkable(false);

        tilemap[player.getTileY() - 1][player.getTileX() - 1].setWalkable(false);
        tilemap[player.getTileY() - 1][player.getTileX()].setWalkable(false);
        tilemap[player.getTileY() - 1][player.getTileX() + 1].setWalkable(false);
    }

    private void setRedirectY() {

        tilemap[player.getTileY() - 1][player.getTileX() + 1].setWalkable(false);
        tilemap[player.getTileY()][player.getTileX() + 1].setWalkable(false);
        tilemap[player.getTileY() + 1][player.getTileX() + 1].setWalkable(false);

        tilemap[player.getTileY() - 1][player.getTileX() - 1].setWalkable(false);
        tilemap[player.getTileY()][player.getTileX() - 1].setWalkable(false);
        tilemap[player.getTileY() + 1][player.getTileX() - 1].setWalkable(false);
    }
}