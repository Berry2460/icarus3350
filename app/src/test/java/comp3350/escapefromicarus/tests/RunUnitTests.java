package comp3350.escapefromicarus.tests;//In TESTS, WE DID NOT USE THE "THIS" KEYWORD.

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.escapefromicarus.tests.businessTests.LevelGenerationTests;
import comp3350.escapefromicarus.tests.objectTests.HealthPickupTests;
import comp3350.escapefromicarus.tests.objectTests.MovementTests;
import comp3350.escapefromicarus.tests.businessTests.WorldLogicTests;


import comp3350.escapefromicarus.tests.objectTests.CharacterTests;
import comp3350.escapefromicarus.tests.objectTests.EnemyTests;
import comp3350.escapefromicarus.tests.objectTests.LevelTests;
import comp3350.escapefromicarus.tests.objectTests.PlayerTests;
import comp3350.escapefromicarus.tests.objectTests.TileTests;
import comp3350.escapefromicarus.tests.objectTests.WorldTests;
import comp3350.escapefromicarus.tests.persistenceTests.DataAccessTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({HealthPickupTests.class, LevelGenerationTests.class, MovementTests.class, WorldLogicTests.class, CharacterTests.class, EnemyTests.class, LevelTests.class, PlayerTests.class, TileTests.class, WorldTests.class ,
DataAccessTests.class})
public class RunUnitTests {
    // runs all tests in suite
}
