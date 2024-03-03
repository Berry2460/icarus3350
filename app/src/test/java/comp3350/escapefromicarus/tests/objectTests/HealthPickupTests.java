package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.objects.HealthPickup;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class HealthPickupTests {
    DataAccess db;
    Player player;
    HealthPickup pickup;

    @Before
    public void setup() {
        db = new DataAccessStub();
        db.open("Stub");
        player = new Player(TextureType.PLAYER, db);
    }

    @Test
    public void testFullHeal() {
        assertEquals(12, player.getLife());
        player.removeLife(5);
        assertEquals(7, player.getLife());

        pickup = new HealthPickup(TextureType.HEART, 5);
        pickup.pickedUp(player);

        assertEquals(12, player.getLife());
    }

    @Test
    public void testOverHeal() {
        assertEquals(12, player.getLife());
        player.removeLife(4);
        assertEquals(8, player.getLife());

        pickup = new HealthPickup(TextureType.HEART, 50);
        pickup.pickedUp(player);

        assertEquals(12, player.getLife());
    }

    @Test
    public void testDamageHeal() {
        assertEquals(12, player.getLife());
        player.removeLife(3);
        assertEquals(9, player.getLife());

        pickup = new HealthPickup(TextureType.HEART, -2);
        pickup.pickedUp(player);

        assertEquals(7, player.getLife());
    }

    @Test
    public void testHeal() {
        assertEquals(12, player.getLife());
        player.removeLife(8);
        assertEquals(4, player.getLife());

        pickup = new HealthPickup(TextureType.HEART, 5);
        pickup.pickedUp(player);

        assertEquals(9, player.getLife());
    }
}
