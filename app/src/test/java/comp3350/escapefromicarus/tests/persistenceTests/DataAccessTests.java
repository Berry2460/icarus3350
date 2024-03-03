package comp3350.escapefromicarus.tests.persistenceTests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import comp3350.escapefromicarus.application.Main;
import comp3350.escapefromicarus.persistence.DataAccess;
import comp3350.escapefromicarus.persistence.DataAccessObject;

public class DataAccessTests {

	private DataAccess[] dataAccessArray;
	private static final int STUB = 0;
	private static final int HSQL = 1;

	@Before
	public void setUp() {

		dataAccessArray = new DataAccess[2];

		//Use the following statements to run with the stub database:
		dataAccessArray[STUB] = new DataAccessStub();
		dataAccessArray[STUB].open("Stub");

		dataAccessArray[HSQL] = new DataAccessObject(Main.dbName);
		dataAccessArray[HSQL].open(Main.getDBPathName());
	}

	@Test
	public void testDatabaseCreation() {

		for (DataAccess dataAccess : dataAccessArray) {
			assertNotNull(dataAccess);
		}
	}

	@Test
	public void testUpdateLife() {

		for (DataAccess dataAccess : dataAccessArray) {
			dataAccess.newGame();

			// Ensures that character life can be updated
			assertEquals(12, dataAccess.getLife("player"));
			dataAccess.updateLife("player",5);
			assertEquals(17, dataAccess.getLife("player"));

			// Ensure that max health can be reduced to 1
			assertEquals(16, dataAccess.updateLife("player", -1));
			assertEquals(1, dataAccess.updateLife("player", -15));

			// Ensure that player health can go as low as 1, and no less
			assertEquals(1, dataAccess.updateLife("player", -1));
			assertEquals(1, dataAccess.updateLife("player", -5));

			// Ensure player Max Life can be reset
			dataAccess.updateLife("player", 9);
			assertEquals(10, dataAccess.getLife("player"));

			// Ensure that updateLife reliably returns the updated value
			assertEquals(11, dataAccess.updateLife("player", 1));
			assertEquals(13, dataAccess.updateLife("player", 2));
			assertEquals(20, dataAccess.updateLife("player", 7));
			assertEquals(15, dataAccess.updateLife("player", -5));
			assertEquals(10, dataAccess.updateLife("player", -5));
			assertEquals(5, dataAccess.updateLife("player", -5));
			assertEquals(1, dataAccess.updateLife("player", -5));

			// Ensure Max Life has the expected value
			assertEquals(1, dataAccess.getLife("player"));

			// Ensure updating by 0 works as expected
			assertEquals(1, dataAccess.updateLife("player", 0));
			assertEquals(1, dataAccess.updateLife("player", 0));
			assertEquals(1, dataAccess.updateLife("player", 0));
			assertEquals(1, dataAccess.updateLife("player", 0));

			dataAccess.newGame();
		}
	}

	@Test
	public void testGetStats() {

		float delta = 0.00000001f; // needed for tests with floats
		for (DataAccess dataAccess : dataAccessArray) {
			dataAccess.newGame();

			// Ensure that valid stats requests return the expected value
			assertEquals(12, dataAccess.getLife("player"));
			assertEquals(5, dataAccess.getLife("slime"));

			assertEquals(1.7f, dataAccess.getSpeed("player"), delta);
			assertEquals(0.9f, dataAccess.getSpeed("slime"), delta);

			assertEquals(1, dataAccess.getAttack("player"));
			assertEquals(1, dataAccess.getAttack("slime"));

			assertEquals(0, dataAccess.getDefense("player"));
			assertEquals(1, dataAccess.getDefense("slime"));

			// Ensure that invalid stats requests are handled by returning -1
			assertEquals(-1, dataAccess.getLife("NOT VALID"));
			assertEquals(-1f, dataAccess.getSpeed("NOT VALID"), delta);
			assertEquals(-1, dataAccess.getAttack("NOT VALID"));
			assertEquals(-1, dataAccess.getDefense("NOT VALID"));

			// Test empty String and odd cases.
			assertEquals(-1, dataAccess.getDefense(""));
			assertEquals(-1, dataAccess.getDefense("."));

			// Test both integer and float return values
			assertEquals(-1, dataAccess.getAttack("\0"));
			assertEquals(-1f, dataAccess.getSpeed("\0"), delta);

			// Test each Stat method handles null values gracefully as expected
			assertEquals(-1, dataAccess.getLife(null));
			assertEquals(-1f, dataAccess.getSpeed(null), delta);
			assertEquals(-1, dataAccess.getAttack(null));
			assertEquals(-1, dataAccess.getDefense(null));
		}
	}

	@Test
	public void testGetTextures() {

		for (DataAccess dataAccess : dataAccessArray) {
			// Ensure that all textures have been loaded successfully
			assertEquals("tileset/stone.png", dataAccess.getTexture("stone"));
			assertEquals("tileset/woodenDoor.png", dataAccess.getTexture("woodenDoor"));
			assertEquals("characters/player.png", dataAccess.getTexture("player"));
			assertEquals("characters/slime.png", dataAccess.getTexture("slime"));
			assertEquals("UI/splashscreen.png", dataAccess.getTexture("splashscreen"));
			assertEquals("tileset/bottomLeftWallStone.png", dataAccess.getTexture("bottomLeftWallStone"));
			assertEquals("tileset/bottomRightWallStone.png", dataAccess.getTexture("bottomRightWallStone"));
			assertEquals("tileset/combinedWallStone.png", dataAccess.getTexture("combinedWallStone"));
			assertEquals("font/craftacular-ui.png", dataAccess.getTexture("craftacular"));
			assertEquals("tileset/grass.png", dataAccess.getTexture("grass"));
			assertEquals("tileset/leftWallStone.png", dataAccess.getTexture("leftWallStone"));
			assertEquals("UI/lifebar.png", dataAccess.getTexture("lifeBar"));
			assertEquals("font/menuFont.png", dataAccess.getTexture("menuFont"));
			assertEquals("UI/pauseBtn.png", dataAccess.getTexture("pauseBtn"));
			assertEquals("tileset/rightWallStone.png", dataAccess.getTexture("rightWallStone"));
			assertEquals("tileset/sideWallGrass.png", dataAccess.getTexture("sideWallGrass"));
			assertEquals("tileset/sideWallStone.png", dataAccess.getTexture("sideWallStone"));
			assertEquals("characters/slime.png", dataAccess.getTexture("slime"));
			assertEquals("UI/splashscreen.png", dataAccess.getTexture("splashscreen"));
			assertEquals("tileset/stone.png", dataAccess.getTexture("stone"));
			assertEquals("UI/TheEnd.png", dataAccess.getTexture("theEnd"));
			assertEquals("tileset/woodenDoor.png", dataAccess.getTexture("woodenDoor"));
			assertEquals("tileset/grass0.png", dataAccess.getTexture("grass0"));
			assertEquals("tileset/grass1.png", dataAccess.getTexture("grass1"));
			assertEquals("tileset/grass2.png", dataAccess.getTexture("grass2"));
			assertEquals("tileset/grass3.png", dataAccess.getTexture("grass3"));
			assertEquals("tileset/grass4.png", dataAccess.getTexture("grass4"));
			assertEquals("tileset/grass5.png", dataAccess.getTexture("grass5"));
			assertEquals("tileset/grass6.png", dataAccess.getTexture("grass6"));
			assertEquals("tileset/grass7.png", dataAccess.getTexture("grass7"));
			assertEquals("tileset/grass8.png", dataAccess.getTexture("grass8"));
			assertEquals("tileset/grass9.png", dataAccess.getTexture("grass9"));
			assertEquals("tileset/grass10.png", dataAccess.getTexture("grass10"));
			assertEquals("tileset/grass11.png", dataAccess.getTexture("grass11"));
			assertEquals("tileset/grass12.png", dataAccess.getTexture("grass12"));
			assertEquals("tileset/grass13.png", dataAccess.getTexture("grass13"));
			assertEquals("tileset/grass14.png", dataAccess.getTexture("grass14"));
			assertEquals("tileset/grass15.png", dataAccess.getTexture("grass15"));
			assertEquals("tileset/grass16.png", dataAccess.getTexture("grass16"));

			// Ensure that valid texture requests return the expected file path
			assertNull(dataAccess.getTexture("INVALID"));
			assertNull(dataAccess.getTexture("NOT VALID"));

			// Test empty String and odd cases.
			assertNull(dataAccess.getTexture(""));
			assertNull(dataAccess.getTexture("."));

			// Test both integer and float return values
			assertNull(dataAccess.getTexture("\0"));
			assertNull(dataAccess.getTexture("\0"));

			// Test each Stat method handles null values gracefully as expected
			assertNull(dataAccess.getTexture(null));
		}
	}

	public void testGetAudio() {

		for (DataAccess dataAccess : dataAccessArray) {
			//Ensure that valid texture requests return the expected file path
			assertEquals("Music/gameOverInitialMusic.wav", dataAccess.getAudio("gameOverInitialMusic"));
			assertEquals("Music/gameOverLoopMusic.wav", dataAccess.getAudio("gameOverLoopMusic"));
			assertEquals("Music/levelMusic.ogg", dataAccess.getAudio("levelMusic"));
			assertEquals("Music/mainMenuMusic.ogg", dataAccess.getAudio("mainMenuMusic"));
			assertEquals("Music/victoryInitialMusic.wav", dataAccess.getAudio("victoryInitialMusic"));
			assertEquals("Music/victoryLoopMusic.wav", dataAccess.getAudio("victoryLoopMusic"));

			// Ensure that valid texture requests return the expected file path
			assertNull(dataAccess.getAudio("INVALID"));
			assertNull(dataAccess.getAudio("NOT VALID"));

			// Test empty String and odd cases.
			assertNull(dataAccess.getAudio(""));
			assertNull(dataAccess.getAudio("."));

			// Test both integer and float return values
			assertNull(dataAccess.getAudio("\0"));
			assertNull(dataAccess.getAudio("\0"));

			// Test each Stat method handles null values gracefully as expected
			assertNull(dataAccess.getAudio(null));
		}
	}

	@Test
	public void testNewGame() {

		for (DataAccess dataAccess : dataAccessArray) {
			dataAccess.newGame();

			// Change db values linked to save files
			assertEquals(10, dataAccess.setMaxLevel(10));
			assertEquals(17, dataAccess.updateLife("player", 5));
			assertEquals(4, dataAccess.setLevel(4));
			assertEquals(100, dataAccess.setSeed(100));
			dataAccess.setDifficulty(3);
			assertEquals(3, dataAccess.getDifficulty());

			// Ensure that the values were successfully changes from their default values
			assertEquals(10, dataAccess.getMaxLevel());
			assertEquals(17, dataAccess.getLife("player"));
			assertEquals(4, dataAccess.getLevel());
			assertEquals(100, dataAccess.getSeed());
			assertEquals(3, dataAccess.getDifficulty());

			// Reset the DB
			dataAccess.newGame();

			// Ensure the DB is properly reset when calling newGame
			assertEquals(5, dataAccess.getMaxLevel());
			assertEquals(12, dataAccess.getLife("player"));
			assertEquals(0, dataAccess.getLevel());
			assertEquals(0, dataAccess.getSeed());
			assertEquals(1, dataAccess.getDifficulty());
		}
	}

	@Test
	public void testSetVolume() {

		float delta = 0.00000001f; // needed for tests with floats
		for (DataAccess dataAccess : dataAccessArray) {
			dataAccess.setVolume(1.0f);

			// Ensure the default volume is the expected value
			assertEquals(1.0f, dataAccess.getVolume(), delta);

			//Change the volume level to a typical volume range.
			dataAccess.setVolume(0.5f);

			// Ensure the volume was properly updated
			assertEquals(0.5f, dataAccess.getVolume(), delta);

			//Change the volume level to an edge case volume range.
			dataAccess.setVolume(0.0f);

			// Ensure the volume was properly updated
			assertEquals(0.0f, dataAccess.getVolume(), delta);

			//Change the volume level to an edge case volume range.
			dataAccess.setVolume(1.0f);

			// Ensure the volume was properly updated
			assertEquals(1.0f, dataAccess.getVolume(), delta);

			//Change the volume level to an out of range volume range.
			dataAccess.setVolume(1.1f);

			// Ensure the invalid volume request was ignored
			assertEquals(1.0f, dataAccess.getVolume(), delta);

			//Change the volume level to an out of range volume range.
			dataAccess.setVolume(-1f);

			// Ensure the invalid volume request was ignored
			assertEquals(1.0f, dataAccess.getVolume(), delta);

			// Reset volume to default level
			dataAccess.setVolume(1.0f);
		}
	}

	@Test
	public void testSetLevel() {

		for (DataAccess dataAccess : dataAccessArray) {
			// Ensure the default level is the expected value
			assertEquals(0, dataAccess.getLevel());

			//Change the level to a typical number
			dataAccess.setLevel(5);

			// Ensure the level was properly updated
			assertEquals(5, dataAccess.getLevel());

			//Change the level to an edge case.
			dataAccess.setLevel(0);

			// Ensure the level was properly updated
			assertEquals(0, dataAccess.getLevel());

			//Change the level to an out of range number.
			dataAccess.setLevel(-1);

			// Ensure the invalid level set request was ignored
			assertEquals(0, dataAccess.getLevel());
		}
	}

	@Test
	public void testSaveExists() {

		float delta = 0.00000001f; // needed for tests with floats
		for (DataAccess dataAccess : dataAccessArray) {
			// Wipe the save file
			dataAccess.newGame();

			// Ensure the default volume is the expected value
			assertEquals(0, dataAccess.saveExists());

			// Ensure that updating the player's health  triggers a save
			dataAccess.updateLife("player", 5);

			assertEquals(1.0f, dataAccess.saveExists(), delta);
			// Wipe the save file
			dataAccess.newGame();
		}
	}

	@Test
	public void testSetGameMute() {

		for (DataAccess dataAccess : dataAccessArray) {
			// Ensure that the game starts unmuted
			assertEquals(0, dataAccess.isGameMuted());

			// Mute the game
			dataAccess.setGameMute(1);

			// Ensure that the game is set to mute
			assertEquals(1, dataAccess.isGameMuted());

			// Unmute the game
			dataAccess.setGameMute(0);

			// Ensure that the game is unmuted
			assertEquals(0, dataAccess.isGameMuted());

			// Try setting game mute to an unsupported positive number
			dataAccess.setGameMute(2);

			// Ensure that the unsupported value was ignored
			assertEquals(0, dataAccess.isGameMuted());

			// Try setting game mute to an unsupported negative number
			dataAccess.setGameMute(-2);

			// Ensure that the unsupported value was ignored
			assertEquals(0, dataAccess.isGameMuted());
		}
	}

	@Test
	public void testSetMusicMute() {

		for (DataAccess dataAccess : dataAccessArray) {
			// Ensure that the game starts with music unmuted
			assertEquals(0, dataAccess.isMusicMuted());

			// Mute the music
			dataAccess.setMusicMute(1);

			// Ensure that the music is set to mute
			assertEquals(1, dataAccess.isMusicMuted());

			// Unmute the music
			dataAccess.setMusicMute(0);

			// Ensure that the music is unmuted
			assertEquals(0, dataAccess.isMusicMuted());

			// Try setting music mute to an unsupported positive number
			dataAccess.setMusicMute(2);

			// Ensure that the unsupported value was ignored
			assertEquals(0, dataAccess.isMusicMuted());

			// Try setting game mute to an unsupported negative number
			dataAccess.setMusicMute(-2);

			// Ensure that the unsupported value was ignored
			assertEquals(0, dataAccess.isMusicMuted());
		}
	}

	@Test
	public void testSetDifficulty() {

		for (DataAccess dataAccess : dataAccessArray) {
			// Ensure that the game starts with difficulty set to 1
			assertEquals(1, dataAccess.getDifficulty());

			// increase the game difficulty to 3
			dataAccess.setDifficulty(3);

			// Ensure that the game difficulty was increased
			assertEquals(3, dataAccess.getDifficulty());

			// Reset the game difficulty back to default value
			dataAccess.setDifficulty(1);

			// Ensure that the game difficulty was set to default value
			assertEquals(1, dataAccess.getDifficulty());
		}
	}
}
