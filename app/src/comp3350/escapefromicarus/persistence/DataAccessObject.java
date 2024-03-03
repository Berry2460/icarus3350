package comp3350.escapefromicarus.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

// Implements SQL Database
public class DataAccessObject implements DataAccess {

	private Statement st0, st1;
	private Connection connection;
	private ResultSet rs0, rs2;

	private String dbName;
	private String dbType;

	private String cmdString;
	private static String EOF = "  ";

	private int playerLife = 12;
	private int playerAttack = 1;
	private int playerDefense = 0;
	private float playerSpeed = 1.7f;

	// HSQLDB would complain about duplicate keys and unique constraint violations if trying to insert into tables in the SC.script
	// So this is the gross solution we have to use, because it works.
	private String DB_SETUP =
					"INSERT INTO TEXTURES VALUES('bottomLeftWallStone','tileset/bottomLeftWallStone.png')\n" +
					"INSERT INTO TEXTURES VALUES('bottomRightWallStone','tileset/bottomRightWallStone.png')\n" +
					"INSERT INTO TEXTURES VALUES('combinedWallStone','tileset/combinedWallStone.png')\n" +
					"INSERT INTO TEXTURES VALUES('craftacular','font/craftacular-ui.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass','tileset/grass.png')\n" +
					"INSERT INTO TEXTURES VALUES('leftWallStone','tileset/leftWallStone.png')\n" +
					"INSERT INTO TEXTURES VALUES('lifeBar','UI/lifebar.png')\n" +
					"INSERT INTO TEXTURES VALUES('menuFont','font/menuFont.png')\n" +
					"INSERT INTO TEXTURES VALUES('pauseBtn','UI/pauseBtn.png')\n" +
					"INSERT INTO TEXTURES VALUES('music1','UI/music1.png')\n" +
					"INSERT INTO TEXTURES VALUES('musicMute','UI/musicMute.png')\n" +
					"INSERT INTO TEXTURES VALUES('Speaker1','UI/Speaker1.png')\n" +
					"INSERT INTO TEXTURES VALUES('SpeakerMute','UI/SpeakerMute.png')\n" +
					"INSERT INTO TEXTURES VALUES('copperhud','UI/copper_hud.png')\n" +
					"INSERT INTO TEXTURES VALUES('hudPanel','UI/hudPanel.png')\n" +
					"INSERT INTO TEXTURES VALUES('transparentBg','UI/transparentBackground.png')\n" +
					"INSERT INTO TEXTURES VALUES('playerFrame','UI/hudPlayerFrame.png')\n" +
					"INSERT INTO TEXTURES VALUES('blackBg','UI/blackBg.png')\n" +
					"INSERT INTO TEXTURES VALUES('forestBg','UI/environment_forestbackground.png')\n" +
					"INSERT INTO TEXTURES VALUES('rightWallStone','tileset/rightWallStone.png')\n" +
					"INSERT INTO TEXTURES VALUES('sideWallGrass','tileset/sideWallGrass.png')\n" +
					"INSERT INTO TEXTURES VALUES('sideWallStone','tileset/sideWallStone.png')\n" +
					"INSERT INTO TEXTURES VALUES('splashscreen','UI/splashscreen.png')\n" +
					"INSERT INTO TEXTURES VALUES('mainscreen','UI/mainscreen.png')\n" +
					"INSERT INTO TEXTURES VALUES('stone','tileset/stone.png')\n" +
					"INSERT INTO TEXTURES VALUES('theEnd','UI/TheEnd.png')\n" +
					"INSERT INTO TEXTURES VALUES('woodenDoor','tileset/woodenDoor.png')\n" +
					"INSERT INTO TEXTURES VALUES('player','characters/player.png')\n" +
					"INSERT INTO TEXTURES VALUES('slime','characters/slime.png')\n" +
					"INSERT INTO TEXTURES VALUES('skeleton','characters/skeleton.png')\n" +
					"INSERT INTO TEXTURES VALUES('levelBoss','characters/levelBoss.png')\n" +

					"INSERT INTO TEXTURES VALUES('grass0','tileset/grass0.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass1','tileset/grass1.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass2','tileset/grass2.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass3','tileset/grass3.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass4','tileset/grass4.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass5','tileset/grass5.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass6','tileset/grass6.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass7','tileset/grass7.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass8','tileset/grass8.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass9','tileset/grass9.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass10','tileset/grass10.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass11','tileset/grass11.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass12','tileset/grass12.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass13','tileset/grass13.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass14','tileset/grass14.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass15','tileset/grass15.png')\n" +
					"INSERT INTO TEXTURES VALUES('grass16','tileset/grass16.png')\n" +

					"INSERT INTO TEXTURES VALUES('blocker0','tileset/blocker0.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker1','tileset/blocker1.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker2','tileset/blocker2.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker3','tileset/blocker3.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker4','tileset/blocker4.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker5','tileset/blocker5.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker6','tileset/blocker6.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker7','tileset/blocker7.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker8','tileset/blocker8.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker9','tileset/blocker9.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker10','tileset/blocker10.png')\n" +
					"INSERT INTO TEXTURES VALUES('blocker11','tileset/blocker11.png')\n" +

					"INSERT INTO TEXTURES VALUES('heart','characters/heart.png')\n" +

					"INSERT INTO STATS VALUES('skeleton',4,1,0,'1.0')\n" +
					"INSERT INTO STATS VALUES('levelBoss',11,2,2,'1.8')\n" +
					"INSERT INTO STATS VALUES('player'," + this.playerLife + "," + this.playerAttack + "," + this.playerDefense + ",'"+ this.playerSpeed + "')\n" +
					"INSERT INTO STATS VALUES('slime',5,1,1,'0.9')\n" +

					"INSERT INTO AUDIO VALUES('gameOverInitialMusic','Music/gameOverInitialMusic.wav')\n" +
					"INSERT INTO AUDIO VALUES('gameOverLoopMusic','Music/gameOverLoopMusic.wav')\n" +
					"INSERT INTO AUDIO VALUES('levelMusic','Music/levelMusic.ogg')\n" +
					"INSERT INTO AUDIO VALUES('mainMenuMusic','Music/mainMenuMusic.ogg')\n" +
					"INSERT INTO AUDIO VALUES('victoryInitialMusic','Music/victoryInitialMusic.wav')\n" +
					"INSERT INTO AUDIO VALUES('victoryLoopMusic','Music/victoryLoopMusic.wav')\n" +
					"INSERT INTO AUDIO VALUES('pickUpFX','SoundEffect/pickUp.wav')\n" +
					"INSERT INTO AUDIO VALUES('skeletonHitFX','SoundEffect/skeletonHit.wav')\n" +
					"INSERT INTO AUDIO VALUES('levelBossHitFX','SoundEffect/bossHit.wav')\n" +
					"INSERT INTO AUDIO VALUES('slimeHitFX','SoundEffect/slimeHit.wav')\n" +
					"INSERT INTO AUDIO VALUES('playerHitFX','SoundEffect/playerHit.wav')\n" +
					"INSERT INTO AUDIO VALUES('deadFX','SoundEffect/dead.wav')\n" +
					"INSERT INTO AUDIO VALUES('doorFX','SoundEffect/door.mp3')\n" +
					"INSERT INTO AUDIO VALUES('buttonFX','SoundEffect/confirm.wav')\n" +

					"INSERT INTO SAVE VALUES('1','1','0','0',0,0,5,0)";

	public DataAccessObject(String dbName) {

		this.dbName = dbName;
	}

	public void open(String dbPath) {

		String url;
		try {
			System.out.println("\nCreating HSQLDB connection... ");
			// Setup for HSQL
			this.dbType = "HSQL";
			Class.forName("org.hsqldb.jdbcDriver").newInstance();
			url = "jdbc:hsqldb:file:" + dbPath; // stored on disk mode
			this.connection = DriverManager.getConnection(url, "SA", "");
			this.st0 = this.connection.createStatement();
			this.st1 = this.connection.createStatement();

			System.out.println("\nConnected to SQL database! \n");

			// If the DB is empty, create the database
			this.rs2 = this.st1.executeQuery("SELECT * FROM TEXTURES");
			if (!this.rs2.next()) {
				try{
					// Add the initial values to the DB
					this.rs2 = this.st1.executeQuery(DB_SETUP);
				}
				catch (Exception e) {
					processSQLError(e);
				}
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		System.out.println("Opened " +dbType +" database " +dbPath);

	}

	public void close() {

		try {	// commit all changes to the database
			this.cmdString = "shutdown compact";
			this.rs0 = this.st1.executeQuery(this.cmdString);
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.connection.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		System.out.println("Closed " + this.dbType + " database " + this.dbName);
	}

	public int getLife(String character) {

		String life = EOF;
		int result =  -1;

		try {
			this.cmdString = "SELECT LIFE FROM STATS WHERE CHARACTER_NAME = '" + character + "';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				life = this.rs2.getString("LIFE");
				result = Integer.parseInt(life);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public float getSpeed(String character) {

		String speed = EOF;
		float result = -1;

		try {
			this.cmdString = "SELECT SPEED FROM STATS WHERE CHARACTER_NAME = '" + character + "';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				speed = this.rs2.getString("SPEED");
				result = Float.parseFloat(speed);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int getAttack(String character) {

		String attack = EOF;
		int result = -1;

		try {
			this.cmdString = "SELECT ATTACK FROM STATS WHERE CHARACTER_NAME = '" + character + "';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				attack = this.rs2.getString("ATTACK");
				result = Integer.parseInt(attack);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public float getVolume() {

		String volume = EOF;
		float result = -1;

		try {
			this.cmdString = "SELECT VOLUME FROM SAVE;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				volume = this.rs2.getString("VOLUME");
				result = Float.parseFloat(volume);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int getDefense(String character) {

		String defense = EOF;
		int result = -1;

		try {
			this.cmdString = "SELECT DEFENSE FROM STATS WHERE CHARACTER_NAME = '" + character + "';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				defense = this.rs2.getString("DEFENSE");
				result = Integer.parseInt(defense);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public String getTexture(String textureName) {

		String filePath = EOF;
		String result = null;

		try {
			this.cmdString = "SELECT FILE_PATH FROM TEXTURES WHERE TEXTURE_NAME = '" + textureName + "';";
			this.rs0 = this.st0.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs0.next()) {
				filePath = this.rs0.getString("FILE_PATH");
				result = filePath;
			}
			this.rs0.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public String getAudio(String audioName) {

		String filePath = EOF;
		String result = null;

		try {
			this.cmdString = "SELECT FILE_PATH FROM AUDIO WHERE AUDIO_NAME = '" + audioName + "';";
			this.rs0 = this.st0.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs0.next()) {
				filePath = this.rs0.getString("FILE_PATH");
				result = filePath;
			}
			this.rs0.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	// returns the character's new max life if successful, -1 if the update fails
	public int updateLife(String character, int amount) {

		int updatedLife = -1;
		int currentLife = -1;

		try {
			currentLife = getLife(character);
			if(amount > 0) {
				updatedLife = currentLife + amount;
			}
			else {
				if((amount * -1) >= currentLife) {
					updatedLife = 1;
				}
				else {
					updatedLife = currentLife + amount;
				}
			}

			System.out.println("Updated health amount: " + amount);

			this.cmdString = "UPDATE STATS SET LIFE = '" + updatedLife +"' WHERE CHARACTER_NAME = '" + character + "';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			updateSaveExists(1);

		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {

				updatedLife = getLife(character);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return updatedLife;
	}

	// Resets the persistent values to default
	public int newGame() {

		int success = -1;

		try {
			this.cmdString = "UPDATE STATS SET LIFE = '" + this.playerLife +"' WHERE CHARACTER_NAME = 'player';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);

			this.cmdString = "UPDATE STATS SET ATTACK = '" + this.playerAttack +"' WHERE CHARACTER_NAME = 'player';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);

			this.cmdString = "UPDATE STATS SET DEFENSE = '" + this.playerDefense +"' WHERE CHARACTER_NAME = 'player';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);

			this.cmdString = "UPDATE STATS SET SPEED = '" + this.playerSpeed +"' WHERE CHARACTER_NAME = 'player';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);

			this.cmdString = "UPDATE SAVE SET MAX_LEVEL =  5;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);

			this.cmdString = "UPDATE SAVE SET CURRENT_LEVEL =  0;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);

			this.cmdString = "UPDATE SAVE SET DIFFICULTY =  1;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);

			this.cmdString = "UPDATE SAVE SET SEED =  0;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);


			updateSaveExists(0);
			success = 1;

		}
		catch (Exception e) {
			processSQLError(e);
		}

		return success;
	}


	// returns the volume level if successful, -1 if the update fails
	public float setVolume(float level) {

		float volumeLevel = -1;

		try {
			// Only update the volume if the level is within the defined range [0-1]
			if(level >= 0 && level <= 1) {
				this.cmdString = "UPDATE SAVE SET VOLUME = '" + level +"';";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				this.cmdString = "COMMIT";
				this.rs2 = this.st1.executeQuery(this.cmdString);
			}
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				this.cmdString = "SELECT VOLUME FROM STATS;";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				volumeLevel = Float.parseFloat(this.rs2.getString("VOLUME"));
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return volumeLevel;
	}

	// returns the level if successful, -1 if the update fails
	public int setLevel(int level) {

		int currentLevel = -1;

		try {
			// Only update the volume if the level is within the defined range [0-30]
			if(level >= 0 && level <= 30){
				this.cmdString = "UPDATE SAVE SET CURRENT_LEVEL = '" + level +"';";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				this.cmdString = "COMMIT";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				updateSaveExists(1);
				currentLevel = level;
			}
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return currentLevel;
	}

	public int getLevel() {

		String level = EOF;
		float queryResult = -1f;
		int result = -1;

		try {
			this.cmdString = "SELECT CURRENT_LEVEL FROM SAVE;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				level = this.rs2.getString("CURRENT_LEVEL");
				queryResult = Float.parseFloat(level);
				result = Math.round(queryResult);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}


	public int getSeed() {

		String seed = EOF;
		int result = -1;

		try {
			this.cmdString = "SELECT SEED FROM SAVE;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				seed = this.rs2.getString("SEED");
				result = Integer.parseInt(seed);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int setSeed(int value) {

		int result = -1;

		try {
			// If within seed range
			if(value >= 0 && value <= 1000000)
			this.cmdString = "UPDATE SAVE SET SEED = '" + value + "';";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			this.cmdString = "COMMIT";
			this.rs2 = this.st1.executeQuery(this.cmdString);
			updateSaveExists(1);
			result = value;

		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int saveExists() {

		String save = EOF;
		int result =  -1;

		try {
			this.cmdString = "SELECT SAVE_EXISTS FROM SAVE;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				save = this.rs2.getString("SAVE_EXISTS");
				result = Integer.parseInt(save);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}


	public int updateSaveExists(int value) {

		String save = EOF;
		int result = -1;

		try {
			if(value == 1 || value == 0){ // Treat as a boolean
				this.cmdString = "UPDATE SAVE SET SAVE_EXISTS = '" + value + "';";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				this.cmdString = "COMMIT";
				this.rs2 = this.st1.executeQuery(this.cmdString);
			}
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				save = this.rs2.getString("SAVE_EXISTS");
				result = Integer.parseInt(save);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int isGameMuted() {

		String muted = EOF;
		int result =  -1;

		try {
			this.cmdString = "SELECT GAME_MUTED FROM SAVE;";
			System.out.println(this.cmdString);
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				muted = this.rs2.getString("GAME_MUTED");
				result = Integer.parseInt(muted);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int setGameMute(int value) {

		String muted = EOF;
		int result = -1;

		try {
			if(value == 1 || value == 0){ // Treat as a boolean
				this.cmdString = "UPDATE SAVE SET GAME_MUTED = '" + value + "';";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				this.cmdString = "COMMIT";
				this.rs2 = this.st1.executeQuery(this.cmdString);
			}
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				muted = this.rs2.getString("GAME_MUTED");
				result = Integer.parseInt(muted);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int isMusicMuted() {

		String muted = EOF;
		int result =  -1;

		try {
			this.cmdString = "SELECT MUSIC_MUTED FROM SAVE;";
			System.out.println(this.cmdString);
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				muted = this.rs2.getString("MUSIC_MUTED");
				result = Integer.parseInt(muted);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int setMusicMute(int value) {

		String muted = EOF;
		int result = -1;

		try {
			if(value == 1 || value == 0){ // Treat as a boolean
				this.cmdString = "UPDATE SAVE SET MUSIC_MUTED = '" + value + "';";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				this.cmdString = "COMMIT";
				this.rs2 = this.st1.executeQuery(this.cmdString);
			}
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				muted = this.rs2.getString("MUSIC_MUTED");
				result = Integer.parseInt(muted);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public int getMaxLevel() {

		String queryResponse = EOF;
		int maxLevel =  -1;

		try {
			this.cmdString = "SELECT MAX_LEVEL FROM SAVE;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				queryResponse = this.rs2.getString("MAX_LEVEL");
				maxLevel = Integer.parseInt(queryResponse);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return maxLevel;
	}

	public int setMaxLevel(int value) {

		int maxLevel =  -1;

		try {
			// Must have at least 1 level
			if(value >= 1 ){
				this.cmdString = "UPDATE SAVE SET MAX_LEVEL = " + value + ";";
				System.out.println(this.cmdString);
				this.rs2 = this.st1.executeQuery(this.cmdString);
				this.cmdString = "COMMIT";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				maxLevel = value;
			}
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return maxLevel;
	}

	public void setDifficulty(int value) {

		try {
			if(value>= 0 && value <= 30){
				this.cmdString = "UPDATE SAVE SET DIFFICULTY = '" + value +"';";
				this.rs2 = this.st1.executeQuery(this.cmdString);
				this.cmdString = "COMMIT";
				this.rs2 = this.st1.executeQuery(this.cmdString);
			}
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
	}

	public int getDifficulty() {

		String difficulty = EOF;
		float queryResult = -1f;
		int result = -1;

		try {
			this.cmdString = "SELECT DIFFICULTY FROM SAVE;";
			this.rs2 = this.st1.executeQuery(this.cmdString);
		}
		catch (Exception e) {
			processSQLError(e);
		}
		try {
			while (this.rs2.next()) {
				difficulty = this.rs2.getString("DIFFICULTY");
				queryResult = Float.parseFloat(difficulty);
				result = Math.round(queryResult);
			}
			this.rs2.close();
		}
		catch (Exception e) {
			processSQLError(e);
		}
		return result;
	}

	public String processSQLError(Exception e) {

		String result = "*** SQL Error: " + e.getMessage();
		// Remember, this will NOT be seen by the user!
		e.printStackTrace();
		return result;
	}
}
