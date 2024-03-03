package comp3350.escapefromicarus.tests.integration;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.application.Main;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.persistence.DataAccessObject;

public class DataAccessHSQLDBSeamTest {

    private DataAccess db;

    @Before
    public void setup() {

        db = new DataAccessObject(Main.dbName);
    }

    @Test
    public void testWorking() {

        // database is open, nothing should return -1
        db.open(Main.getDBPathName());

        assertNotEquals(-1, db.getAttack("player"));
        assertNotEquals(-1, db.getAttack("slime"));
        assertNotEquals(-1, db.getAttack("skeleton"));
        assertNotEquals(-1, db.getAttack("levelBoss"));

        assertNotEquals(-1, db.getDefense("player"));
        assertNotEquals(-1, db.getDefense("slime"));
        assertNotEquals(-1, db.getDefense("skeleton"));
        assertNotEquals(-1, db.getDefense("levelBoss"));

        assertNotEquals(-1, db.getLife("player"));
        assertNotEquals(-1, db.getLife("slime"));
        assertNotEquals(-1, db.getLife("skeleton"));
        assertNotEquals(-1, db.getLife("levelBoss"));

        assertNotEquals(-1, db.getSpeed("player"));
        assertNotEquals(-1, db.getSpeed("slime"));
        assertNotEquals(-1, db.getSpeed("skeleton"));
        assertNotEquals(-1, db.getSpeed("levelBoss"));

        db.close();
    }

    @Test
    public void testNotWorking() {

        // database is closed, everything should return -1

        float delta = 0.000001f; // needed for float comparison

        assertEquals(-1, db.getAttack("player"));
        assertEquals(-1, db.getAttack("slime"));
        assertEquals(-1, db.getAttack("skeleton"));
        assertEquals(-1, db.getAttack("levelBoss"));

        assertEquals(-1, db.getDefense("player"));
        assertEquals(-1, db.getDefense("slime"));
        assertEquals(-1, db.getDefense("skeleton"));
        assertEquals(-1, db.getDefense("levelBoss"));

        assertEquals(-1, db.getLife("player"));
        assertEquals(-1, db.getLife("slime"));
        assertEquals(-1, db.getLife("skeleton"));
        assertEquals(-1, db.getLife("levelBoss"));

        assertEquals(-1.0f, db.getSpeed("player"), delta);
        assertEquals(-1.0f, db.getSpeed("slime"), delta);
        assertEquals(-1.0f, db.getSpeed("skeleton"), delta);
        assertEquals(-1.0f, db.getSpeed("levelBoss"), delta);
    }
}
