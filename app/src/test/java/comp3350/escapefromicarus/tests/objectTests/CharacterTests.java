package comp3350.escapefromicarus.tests.objectTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.objects.Character;
import comp3350.escapefromicarus.objects.Enemy;
import comp3350.escapefromicarus.objects.Player;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class CharacterTests {

    private Character testCharacter;
    private Enemy enemy;
    private DataAccess dataAccess;

    @Before
    public void setup() {

        dataAccess = new DataAccessStub();
        dataAccess.open("Stub");
        testCharacter = new Player(TextureType.PLAYER, dataAccess);
        enemy = new Enemy(dataAccess, "slime"); // enemies have default health and cannot start with < 1
    }


    @Test
    public void testCombat() {

        Player dummy = new Player(TextureType.PLAYER, dataAccess);

        assertEquals(dataAccess.getLife("player"), testCharacter.getLife());

        //attack dummy so 1 life remains
        for (int i = 1; i < dataAccess.getLife("player"); i++) {
            testCharacter.attackCharacter(dummy);
            assertEquals(dataAccess.getLife("player") - i, dummy.getLife());
        }
        assertEquals(1, dummy.getLife());
        assertFalse(dummy.isDead());
        //after the last attack the dummy is dead
        testCharacter.attackCharacter(dummy);
        assertTrue(dummy.isDead());
        //attacker is still fine
        assertFalse(testCharacter.isDead());
    }
}
