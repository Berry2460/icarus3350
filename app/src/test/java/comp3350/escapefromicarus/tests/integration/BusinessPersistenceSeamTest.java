package comp3350.escapefromicarus.tests.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.business.LevelGeneration;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessStub;

public class BusinessPersistenceSeamTest {

    private DataAccess db;

    @Before
    public void setup() {

        db = new DataAccessStub();
    }

    @Test
    public void testLevelGenerationSeam() {

        Level level = new Level();

        //do without db and get null exception
        try {
            LevelGeneration.generate(1, level, null);
            fail();
        }
        catch(NullPointerException e) {
            assertEquals(e.getClass(), NullPointerException.class);
        }

        //did not open db, still should get errors
        try {
            LevelGeneration.generate(1, level, db);
            fail();
        }
        catch(NullPointerException e) {
            assertEquals(e.getClass(), NullPointerException.class);
        }

        //open db
        db.open("Stub");
        try {
            LevelGeneration.generate(1, level, db);
        }
        catch(NullPointerException e) {
            fail();
        }
    }

    @Test
    public void testLevelMonsterPlace() {

        Level level = new Level();
        LevelGeneration.initLevel(TextureType.GRASS, true, level);

        //do without db and get null exception
        try {
            LevelGeneration.placeRandomMonster(level, null, 1, 1, 1);
            fail();
        }
        catch(NullPointerException e) {
            assertEquals(e.getClass(), NullPointerException.class);
        }

        //did not open db, still should get errors
        try {
            LevelGeneration.placeRandomMonster(level, db, 1, 1, 1);
            fail();
        }
        catch(NullPointerException e) {
            assertEquals(e.getClass(), NullPointerException.class);
        }

        //open db
        db.open("Stub");

        try {
            LevelGeneration.placeRandomMonster(level, db, 1, 1, 1);
        }
        catch(NullPointerException e) {
           fail();
        }
    }
}
