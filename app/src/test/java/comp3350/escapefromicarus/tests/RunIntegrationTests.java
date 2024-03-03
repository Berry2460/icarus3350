package comp3350.escapefromicarus.tests;//In TESTS, WE DID NOT USE THE "THIS" KEYWORD.

import org.junit.runners.Suite;
import org.junit.runner.RunWith;

import comp3350.escapefromicarus.tests.integration.*;

@RunWith(Suite.class)
@Suite.SuiteClasses({BusinessPersistenceSeamTest.class, DataAccessHSQLDBSeamTest.class})
public class RunIntegrationTests {
    // runs all tests in suite
}