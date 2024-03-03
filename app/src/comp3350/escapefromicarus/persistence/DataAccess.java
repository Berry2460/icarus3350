package comp3350.escapefromicarus.persistence;

public interface DataAccess {

	void open(String string);

	void close();

	int getLife(String character);

	float getSpeed(String character);

	int getAttack(String character);

	int getDefense(String character);

	String getTexture(String TextureName);

	String getAudio(String audioName);

	int updateLife(String character, int amount);

	int newGame();

	float setVolume(float level);

	float getVolume();

	int setLevel(int level);

	int getLevel();

	int getSeed();

	int setSeed(int value);

	int saveExists();

	int isGameMuted();

	int setGameMute(int value);

	int isMusicMuted();

	int setMusicMute(int value);

	int getMaxLevel();

	int setMaxLevel(int value);

	int getDifficulty();

	void setDifficulty(int value);

}
