# Escape from Icarus

 ## How to play Escape From Icarus
 - When launching the game, the player will be greeted by the Escape From Icarus splashscreen.
 - The player will be presented with 4 buttons: One for "New Game"" to start a new save file, one to view "Instructions" on how to play the game, another for "Credits" to see the developers of the game and the other is to "Exit" the game.
 - When selecting "New Game" the player is prompted to choose a difficulty setting, "Easy", "Normal" and "Hard".
 - Easy Mode: Complete 5 levels to beat the game. Enemies have little health.
 - Normal Mode: Complete 10 levels to beat the game. Enemies have a moderate amount of health.
 - Hard Mode: Complete 15 levels to beat the game. Enemies have lots of health.
 - Beating the game will reward the player with a bonus of 2 max life points.
 - The player is represented by a sprite of a person with brown hair, with the player's current amount of "life" represented by a health bar above the player sprite.
  -There is also a Heads Up Display at the bottom left corner that displays the player's life.
 - Playing the game will start the player off in a randomly generated level. The player will start in a room with a grass floor surrounded by grey-stone walls.
 - Each level is full of many enemies, currently there are slime and skeleton enemies. Enemies also have a current "life" represented by a health bar about the enemy sprite.
 - To move the player, tap anywhere on the screen and the player will begin to move to the location that was tapped.
 - The player can attack the enemy by tapping on the enemy while standing next to the enemy.
 - Attacking the enemy and reducing it's health to 0 will cause the enemy to die, and it will be removed from the level.
 - Enemies will wander randomly around the level, and will pursuit the player if the player comes too close to the enemy.
 - Find the door that is randomly placed in each level to progress to the next level.
 - The game ended screen will have an image of "The End" with a button that leads to the Main Menu where the player can choose to play the game again of exit the application.

## Major Changes in features/behaviours from Iteration 2
- Level bosses have been added to the game! Good luck reaching the door ;)
- Skeletons have joined the fight along side the slime
- Health pickups! There is a small chance for a health pickup to be placed in rooms. Represented by a heart!
- Items and obstacles have been added to add some variety to the maps.
- Pretty floors! We have a new random grass tile system that adds decorations such as flowers and rocks to the ground.
- Sounds effects have been added for attacking enemies, player and enemy death, health pickups, door and menu interactions.
- Multiple difficulties available! Try the game on easy, normal or hard mode.
- Audio preferences. Mute all the game audio, or just the music.
- Save file! Player progress is saved each level - save and continue automatically! audio preferences are also saved.
- Get a maximum health boost of 2 life points when beating the game.
- Credits menu. See who developed the game.


## Major changes in structure of source code
- Removed Logic code from Actor, World, Enemy, Level and GameScreen
- Added Logic abstract classes
- Corrected structure of test packages
- Added HSQLDB. Replaced stub database with it (except for in tests)

## Acceptance Testing
Due to issues with libGDX and espresso, we could not implement automated acceptance testing for our project. However, we did write thorough scripts for acceptance testing and added them to the project in the same place that our acceptance tests would go.

## Outstanding Bugs/Quirks
- Player has an overlapping glitch where if it moves diagonally to any objects, the corners of the character overlap the object (specifically, the enemy and the door/portal).
- Music has static sound at the beginning when game is launched due to audio streaming issues. But on the actual hardware this was not an issue
- There is an exploit in our game, where if you progress a level and are low on health, you can exit the game and reopen to restart at the same level with full health. Persistent current player health would need to be added to fix

***https://github.com/YingLiang2/3350Project/tree/master***


## Libraries used

- LibGDX: ***https://libgdx.com***

## Licenses
- Boss Sprite https://elthen.itch.io/2d-pixel-art-shardsoul-slayer-sprites by Elthen's Pixel Art Shop
- Menu button sound effect https://obsydianx.itch.io/interface-sfx-pack-1 by ObsydianX
- Door sound effect https://leohpaz.itch.io/minifantasy-dungeon-sfx-pack by Krishna Palacio
- Sound effect https://coloralpha.itch.io/50-menu-interface-sfx by ColorAlpha
- Sound effects https://jdwasabi.itch.io/8-bit-16-bit-sound-effects-pack by JDWasabi
- HUD panel https://zed-hanok.itch.io/misc-hud-and-ui-graphics by Zed Hanok
- Artwork https://masayume.itch.io/pixel-art-fantasy-lands-1 by Masayume
- HUD elements https://cathean.itch.io/liteui-fantasy by Cathean
- Buttons and Fonts https://github.com/czyzby/gdx-skins/tree/master/craftacular by Raymond "Raeleus" Buckley
- Fonts https://somepx.itch.io/humble-fonts-free by Eeve Somepx
- Character Sprites, Grass, Walls, Door textures https://game-endeavor.itch.io/mystic-woods by Game Endeavor
- Game Over and Game Win music https://sonatina.itch.io/infinity-crystal by Sara Garrard
- Menu screen and Game Screen music https://sonatina.itch.io/letsadventure by Sara Garrard

## Team Members

- Ying
- Tanisha
- Ryan
- Tristan
- Agape

## Log Files

- Log files prior to Iteration 1 were kept using Trello: https://trello.com/escapefromicarus
- We are using Google Docs going forward to make exporting text files easier for future iteration submissions.

## Android Testing

- Our project has been tested in Android Studio, emulating a Nexus 7 tablet with API level 23. Addtionally, the app was tested on physical hardware, a Samsung Note 10+.

## Environment

- Android Studio and GitHub


# Application

## AndroidLauncher class:

Launches the game application.

Methods:

- onCreate(Bundle savedInstanceState) initializes the database, the game and starts the process.
- onDestroy() closes the application
- copyDatabaseToDevice() copies the database to the target device if the DB does not exist on the device 
- copyAssetsToDirectory(String[] assets, File directory) copies the assets that main the database to the device

## Main class

Launches the database.

Methods:

- main(String[] args) runs the database code when application first starts
- startUp() calls Services to create the database
- shutDown() calls Services to close the database
- getDBPathName() gets database pathname
- setDBPathName(String pathName) sets database pathname

## Services class

Connector to the database and Application

Methods:

- createDataAccess(String dbName) creates the database with string input
- getDataAccess(String dbName) makes database connection, returns a DataAccess object
- closeDataAccess() closes the database


# Business


## GameLogic

Methods:

- playerDeathCheck(MyGame mainApplication, Player player) checks if the player is dead
- doEnemyLogic(EnemyMovement enemyMover, Movement playerLocation, float deltaTime) triggers the enemy to begin their ai logic
- checkNextLevel(World world) Checks to see if there is another level to play (false with no levels or last level)
- getTextureType(String texture) returns the TextureType requested.

-- REMOVED
- doPlayerLogic(MyGame mainApplication, Movement characterMover, boolean isDead, float deltaTime) triggers the player to move towards a destination
- addPlayer(World world, int life, float speed) adds a player to the game world


## LevelGeneration

Methods:

 - generate(int seed, Level level) Generates the random level
 - initLevel(TextureType texture, boolean walkable, Level level) Sets up the level size
 - placeStartEnd(Level level) Sets up the starting point of where to create the map
 - carveRoom(TextureType texture, int[][] bounds, boolean walkable, Level level) Generates the walkable area for each room
 - carveHallway(TextureType floor, int[][] bounds, boolean walkable, Level level) Generates the hallways connecting rooms
 - fixCarveWall(int x, int y, Level level)
 - fixBounds(int[][] bounds)
 - sprinkleDecorations(Level level, TextureType[] choices, int seed, int amount, int[][] bounds, boolean walkable) adds decorations to the game level 
 - placeRandomMonster(Level level, DataAccess db, int seed, int tileX, int tileY)
 - placeRandomHealthPickup(Level level, int seed, int[][] bounds)

## WorldLogic

- addLevel(World world) Adds a a level to the stack of levels
- tryNextLevel(World world) Progresses the game to the next level


# Objects

## Actor class

An abstract class that has all the necessary functionality to make a fully functional NPC or Player from.

Methods:

 - getPixelX()
 - getPixelY()
 - getTileX()
 - getTileY()
 - setPixelPosition(float x, float y)
 - getTextureType() returns the type of texture for rendering purposes.
 - setLevel(Level level) sets the level that the player is on
 - place(Level level, int startX, int startY) places an actor on the level
 - teleport(int xPos, int yPos) Moved the actor to the desired location
 - removeActor() Removes the actor from the game
 - placeActor() Places the actor back in the level.
 
## Character class:

A subclass of Movement that holds basic stats information.

Methods:

- isDead() returns a boolean if life is below 1.
- kill() remove character from map.
- addLife(int amount) adds life to character (life cannot exceed max life)
- removeLife(int amonut) removes life
- getLife()
- getMaxLife()
- takeDamage(int amount) subtracts an amount off character life.
- attackCharacter(Character toAttack) sends attacks to the enemy.
- collision(Actor actor) detected collision


## Enemy class:

A subclass of Character that holds data specific for the enemy.

- reduceCooldown(float amount) allows the enemy to attack in intervals
- doRoutine(int playerX, int playerY, int seed) Enemy movement logic
- getSoundEffect() gets the enemy's sound effect
- takeDamage(int amount) 
- attackCharacter(Character toAttack)
- collision(Actor collidedWith) Collision logic

## HealthPickup class:

Methods:

- HealthPickup(TextureType type, int amount) Creates a health pickup with "amount" of health
- pickedUp(Character toHeal) Applies the healing item to the character that picked it up


## Level class:

Holds level tile map.

Methods:

 - setTilemap(Tile[][] tilemap)
 - getTilemap()
 - setLevelStartX(int levelStartX)
 - setLevelStartY(int levelStartY)
 - setLevelEndX(int levelEndX)
 - setLevelEndY(int levelEndY)
 - setLevelDepth(int levelDepth)
 - getStartX()
 - getStartY()
 - getEndX()
 - getEndY()
 - inBounds(int point)


## Movement

Methods:

- setDestination(int xPos, int yPos) sets a new destination, in tile coordinates, for the Actor and it will glide towards it.
- getNextMove() gets the player's next location and moves it to the location.
- removeActor() removed the Actor from the game
- placeActor() places an actor on the current tile
- getTileX() gets the x location of current tile
- getTileY() gets the y location of the current tile
- isMoving() returns true if the Actor is currently moving
- glide(float timeDelta) is called each frame and moves the Actor, if it is not already at its destination.
- teleport(int xPos, int yPos) teleports the Actor to a new tile location, coordinates are in tile coordinates, not pixels.
- halt() Stops actor movement
- setTilemap(Tile[][] levelMap) initializes the map of the current level


## Player class:

A subclass of Character that holds data, like inventory space, for the main player.

Methods:

- changeLevel(Level level) removes the player from the current level and moves it to the next one
- takeDamage(int amount)
- attackCharacter(Character toAttack)
- collision(Actor collidedWith) Collision logic

## Tile class:

A class that holds information about a tile in the game world.

- Note: any texture smaller or bigger than the TILE_SIZE will be cut to fit the TILE_SIZE.

Methods:

- setWalkable(boolean walkable) sets whether the tile is walkable or not.
- setOccupyingActor(Actor actor) sets what Actor (if any) is standing on the tile, 'null' is if nothing is on it.
- getFloor() returns a Sprite for the floor texture, the coordinates are arbitrary and are adjusted to the camera in the rendering pipeline.
- setFloor(Sprite newFloor) changes the floor texture.
- getOccupyingActor() gets what Actor is standing on the tile.
- isWalkable() gets a boolean if the tile is walkable or not.

## World class:

Holds all levels and entities.

Methods:

- addPlayer(DataAccess dataAccess)
- setLevelID(int newID)
- getLevelCount()
- getCurrentLevel()
- getLevelID()
- getPlayer()


# Persistence:

## DataAccess:

An interface that defines the methods supported by both the Stub and the SQL database.

Methods:

- open() Sets up the desired DB and adds the initial values to the DB.
- close() Closes the connection to the DB.
- getLife(String character) Returns the current life value of the Character as an int.
- getSpeed(String character) Returns the movement speed of the Character as a float.
- getAttack(String character) Returns the attack of the Character as an int.
- getDefense(String character) Returns the defense value of a given character
- getTexture(String textureName) gets the path of a given texture from the database as a String.
- getAudio(String audioName) Returns the file path to the requested audio
- updateLife(String character, int amount) Updated the maximum life value of a given character by amount
- newGame() Wipes the player's save file, resetting the DB to default values
- setVolume(float level) Sets the volume of the game audio
- getVolume() Gets the volume of the game audio
- setLevel(int level) Sets the current level of the game
- getLevel() gets the current level of the game
- getSeed() gets the level seed
- setSeed(int value) sets the level seed
- saveExists() checks to see if a save file exists
- isGameMuted() returns 1 if the game is muted, 0 if the game is unmuted 
- setGameMute(int value)  Pass 1 to mute the game, 0 to unmute the game
- isMusicMuted() returns 1 if the music is muted, 0 if the music is unmuted
- setMusicMute(int value)Pass 1 to mute the game music, 0 to unmute the game music
- getMaxLevel() gets the maximum level of the game, used for difficulty settings
- setMaxLevel(int value) sets the maximum level of the game
- getDifficulty() gets the difficulty level of the game
- setDifficulty(int value) sets the difficulty level of the game

## DataAccessObject:

A class that implements the DataAccess methods outlined in the DataAccess interface above using SQL

# Presentation:

## CreditsScreen class:

Methods:

- show()
- render(float delta) draws the level by calling other methods in the World class
- dispose() gets the current active screen and disposes it
- createLabels() creating the labels which will stylize our buttons, (position, colour, font, et)
- addButtons() adds the buttons onto the screen that are used to guide around the menu screen

## EndScreen class:

A class that holds information for the EndGame screen. Contains buttons to allow the user to go back to the main menu screen.

Methods:

- show()
- render(float delta) draws the level by calling other methods in the World class
- dispose() gets the current active screen and disposes it
- addButtons() adds the buttons onto the screen that are used to guide around the menu screen
- makeMusic() sets the endscreen music and plays the end screen music.

## GameOver class:

Methods:

- show()
- render(float delta) draws the level by calling other methods in the World class
- dispose() gets the current active screen and disposes it
- createLabels() creating the labels which will stylize our buttons, (position, colour, font, et)
- addButtons() adds the buttons to the game over screen
- makeMusic() sets the game over music and plays the game over music.

## GameScreen class:

A class that holds information for the gameplay screen. It generates the levels by calling methods in the World class.

Methods:

- show()
- render(float delta) draws the level by calling other methods in the World class
- dispose() gets the current active screen and disposes it
- updateCamera()
- doPlayerMovement() takes in the input of the players taps, and moves the character accordingly
- getTexture(TextureType)
- dispose() disposes everything for the GameScreen class.
- drawLevel() sets up the level with the walls and floors and incorporates the sprites into them.
- drawSprites(ArrayList <Actor> actorsToDraw) assigns the sprites onto the tiles, aka walls and floors.

## InstructionsScreen

Methods: 

- show()
- render(float delta) draws the level by calling other methods in the World class
- dispose() gets the current active screen and disposes it
- createLabels() creating the labels which will stylize our buttons, (position, colour, font, et)
- addButtons() adds buttons to the instruction screen which will guide us back to the main screen

## MenuScreen class:

A class that holds information for the main menu screen (before the actual gameplay starts). Contains buttons which allows the user to exit the game or play the game.

Methods:

- show()
- render(float delta) draws the level by calling other methods in the World class
- dispose() gets the current active screen and disposes it
- addButtons() adds buttons on the menu screen
- makeMusic() sets the menu screen music

## MyGame class:

A class that will call the other screens

Has ScreenType enum, the following screen types are:
- ScreenType.SPLASH_SCREEN
- ScreenType.MENU_SCREEN
- ScreenType.GAME_SCREEN
- ScreenType.GAME_END_SCREEN

Methods:

- create() sets the first screen that is viewed when the game is first launched
- dispose() gets the current active screen and disposes it
- changeScreen(ScreenType screen) called from other screen classes to switch screens
- makeNewGame(DifficultyLevel difficultylvl) makes a new game at the desired difficulty level
- getDifficulty() gets the difficulty of the game
- setMusic(Music music) changes the music to a different one
- getMusicVol() gets the current music volume
- changeVol(float newVol) changes volume of the music
- getMusicContinue() gets the boolean whether the current music should continue or not
- setMusicContinue(Boolean isItStart) sets whether the current music should continue to play or not
- loadMusic() loads the music assets into the asset manager
- unloadMusic() unloads the music assets from the asset manager
- getMusicManager() gets the asset manager to get load the music in the respective screen

## ScreenSetter

A class that holds the method for the default (basic) screen setup

Methods:

- doBasicRender(float delta, Stage stage, SpriteBatch batch, Texture BACKGROUND_IMAGE) draws the default (basic) screen with the backgrounds set according to the caller.

## SplashScreen class:

A class that holds the information for the splash screen (loading screen). Changes to main menu screen after a certain time

Methods:

- render(float delta) draws the splash screen and then switch to the main menu after a certain time
- dispose() disposes everything for the splashscreen
