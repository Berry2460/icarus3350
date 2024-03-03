package comp3350.escapefromicarus.business;

import java.util.Random;

import comp3350.escapefromicarus.objects.Enemy;
import comp3350.escapefromicarus.objects.HealthPickup;
import comp3350.escapefromicarus.objects.Level;
import comp3350.escapefromicarus.objects.TextureType;
import comp3350.escapefromicarus.objects.Tile;
import comp3350.escapefromicarus.persistence.DataAccess;

public abstract class LevelGeneration {

    private static final int BOUNDS_START = 0;
    private static final int BOUNDS_END = 1;
    private static final int BOUNDS_X = 0;
    private static final int BOUNDS_Y = 1;

    private static final int ROOM_MIN_SIZE = 3;
    private static final int ROOM_MAX_SIZE = 6;

    private static final int MAP_EDGE = Level.MAP_SIZE - 2;
    private static final int VERTICAL_HALL_MAX = 4;
    private static final int VERTICAL_HALL_MIN = 2;

    private static final int ROOM_BLOCKER_CHANCE = 2;
    private static final int HEALTH_PICKUP_CHANCE = 4;
    private static final int PICKUP_MAX = 8;

    private static final TextureType[] GRASS_CHOICES = {
            TextureType.GRASS1,
            TextureType.GRASS2,
            TextureType.GRASS3,
            TextureType.GRASS4,
            TextureType.GRASS5,
            TextureType.GRASS6,
            TextureType.GRASS7,
            TextureType.GRASS8,
            TextureType.GRASS9,
            TextureType.GRASS10,
            TextureType.GRASS11,
            TextureType.GRASS12,
            TextureType.GRASS13,
            TextureType.GRASS14,
            TextureType.GRASS15,
            TextureType.GRASS16
    };

    private static final TextureType[] BLOCKER_CHOICES = {
            TextureType.BLOCKER0,
            TextureType.BLOCKER1,
            TextureType.BLOCKER2,
            TextureType.BLOCKER3,
            TextureType.BLOCKER4,
            TextureType.BLOCKER5,
            TextureType.BLOCKER6,
            TextureType.BLOCKER7,
            TextureType.BLOCKER8,
            TextureType.BLOCKER9,
            TextureType.BLOCKER10,
            TextureType.BLOCKER11,
    };

    private static final String[] MONSTER_CHOICES = { "slime", "skeleton" };

    private static final int ATTEMPT_MULTIPLIER = 4;

    public static void generate(int seed, Level level, DataAccess db) {

        Random random = new Random(seed);
        initLevel(TextureType.TOP_WALL, false, level);

        int startX = 1;
        int startY = 1;
        boolean done = false;

        while (!done) {
            int roomStartX = startX;
            int roomStartY = startY;
            int roomEndX = roomStartX + random.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE) + ROOM_MIN_SIZE;
            int roomEndY = roomStartY + random.nextInt(ROOM_MAX_SIZE - ROOM_MIN_SIZE) + ROOM_MIN_SIZE;
            if (roomEndY >= MAP_EDGE) {
                //map is full of rooms
                done = true;
            }
            else if (roomEndX >= MAP_EDGE) {
                //move along Y axis for rooms
                startX = random.nextInt(ROOM_MIN_SIZE) + 1;
                startY += ROOM_MAX_SIZE + random.nextInt(ROOM_MIN_SIZE) + 1;
                if (startY >= MAP_EDGE) {
                    //map is full of rooms
                    done = true;
                }
            }
            else {
                //carve room
                int[][] roomBounds = { { roomStartX, roomStartY }, { roomEndX, roomEndY } };
                carveRoom(level, TextureType.GRASS, roomBounds, true);
                //add decorations in room (flip a coin)
                if (random.nextInt() % ROOM_BLOCKER_CHANCE == 0) {
                    int[][] roomDecorBounds = { { roomStartX + 1, roomStartY + 1 }, { roomEndX - 1, roomEndY - 1 } };
                    sprinkleDecorations(level, BLOCKER_CHOICES, random.nextInt(Level.MAX_SEED), random.nextInt(ROOM_MIN_SIZE), roomDecorBounds, false);
                }
                //add life pickups
                if (random.nextInt() % HEALTH_PICKUP_CHANCE == 0) {
                    placeRandomHealthPickup(level, random.nextInt(Level.MAX_SEED), roomBounds);
                }
                //next room along X axis
                startX = roomEndX + random.nextInt(ROOM_MAX_SIZE) + 1;
                if (startX <= MAP_EDGE) {
                    //connect adjacent rooms on X axis
                    int hallwayOffsetY = (roomEndY - roomStartY) / 2;
                    int[][] hallBounds = { { roomEndX, roomStartY + hallwayOffsetY }, { startX, roomStartY + hallwayOffsetY } };
                    carveHallway(level, TextureType.GRASS, hallBounds, true);
                }
                //add enemy
                placeRandomMonster(level, db, random.nextInt(Level.MAX_SEED), roomEndX, roomEndY);
            }
        }

        //carve vertical hallways
        int verticalHalls = (int) (Math.random() * (VERTICAL_HALL_MAX - VERTICAL_HALL_MIN)) + VERTICAL_HALL_MIN;

        for (int i = 0; i < verticalHalls; i++) {
            int hallStepX = i * (MAP_EDGE / verticalHalls) + (MAP_EDGE / verticalHalls) / 2;
            int[][] hallBounds = {{hallStepX, 1}, {hallStepX, MAP_EDGE}};
            carveHallway(level, TextureType.GRASS, hallBounds, true);
        }

        //add final decorations
        int decorAmount = random.nextInt(Level.MAP_SIZE / 2) + Level.MAP_SIZE / 2;
        int[][] mapBounds = {{0, 0}, {Level.MAP_SIZE, Level.MAP_SIZE}};
        sprinkleDecorations(level, GRASS_CHOICES, random.nextInt(Level.MAX_SEED), decorAmount, mapBounds, true);

        //add start and end
        placeStartEnd(level);

        //place level boss
        Enemy boss = new Enemy(db, "levelBoss");
        boss.place(level, level.getEndX(), level.getEndY());
    }

    public static void initLevel(TextureType texture, boolean walkable, Level level) {

        Tile[][] tilemap = level.getTilemap();

        for (int i = 0; i < Level.MAP_SIZE; i++) {
            for (int j = 0; j < Level.MAP_SIZE; j++) {
                tilemap[i][j] = new Tile(texture, walkable);
            }
        }
        level.setTilemap(tilemap);
    }

    private static void placeStartEnd(Level level) {

        Tile[][] tilemap = level.getTilemap();
        int levelStartY = 0;
        int levelStartX = 0;
        int levelEndY = 0;
        int levelEndX = 0;

        while (!tilemap[levelStartY][levelStartX].isWalkable()) {
            levelStartY = (int) (Math.random() * (Level.MAP_SIZE));
            levelStartX = (int) (Math.random() * (Level.MAP_SIZE));
        }

        while (!tilemap[levelEndY][levelEndX].isWalkable() || (levelEndX == levelStartX && levelEndY == levelStartY)) {
            levelEndY = (int) (Math.random() * (Level.MAP_SIZE));
            levelEndX = (int) (Math.random() * (Level.MAP_SIZE));
        }

        level.setLevelStartX(levelStartX);
        level.setLevelStartY(levelStartY);
        level.setLevelEndX(levelEndX);
        level.setLevelEndY(levelEndY);
        level.getTilemap()[level.getEndY()][level.getEndX()].setFloor(TextureType.DOOR);
        level.getTilemap()[level.getEndY()][level.getEndX()].setWalkable(true);
    }

    private static void carveRoom(Level level, TextureType texture, int[][] bounds, boolean walkable) {

        Tile[][] tilemap = level.getTilemap();

        fixBounds(bounds);
        //carve room
        for (int i = bounds[BOUNDS_START][BOUNDS_Y]; i <= bounds[BOUNDS_END][BOUNDS_Y]; i++) {
            for (int j = bounds[BOUNDS_START][BOUNDS_X]; j <= bounds[BOUNDS_END][BOUNDS_X]; j++) {
                if (tilemap[i][j].getOccupyingActor() == null) {
                    tilemap[i][j] = new Tile(texture, walkable);
                    fixCarveWall(level, j, i);
                }
            }
        }

        level.setTilemap(tilemap);
    }

    private static void carveHallway(Level level, TextureType floor, int[][] bounds, boolean walkable) {

        Tile[][] tilemap = level.getTilemap();

        fixBounds(bounds);
        //carve hall X
        for (int i = bounds[BOUNDS_START][BOUNDS_X]; i < bounds[BOUNDS_END][BOUNDS_X]; i++) {
            if (tilemap[bounds[BOUNDS_START][BOUNDS_Y]][i].getOccupyingActor() == null) {
                tilemap[bounds[BOUNDS_START][BOUNDS_Y]][i] = new Tile(floor, walkable);
            }
            fixCarveWall(level, i, bounds[BOUNDS_START][BOUNDS_Y]);
        }
        //carve hall Y
        for (int i = bounds[BOUNDS_START][BOUNDS_Y]; i < bounds[BOUNDS_END][BOUNDS_Y]; i++) {
            if (tilemap[i][bounds[BOUNDS_END][BOUNDS_X]].getOccupyingActor() == null) {
                tilemap[i][bounds[BOUNDS_END][BOUNDS_X]] = new Tile(floor, walkable);
            }
            fixCarveWall(level, bounds[BOUNDS_END][BOUNDS_X], i);
        }

        level.setTilemap(tilemap);
    }

    private static void fixCarveWall(Level level, int x, int y) {

        Tile[][] tilemap = level.getTilemap();

        //snaps the correct wall siding to tiles
        if (tilemap[y + 1][x].getFloor() == TextureType.TOP_WALL ||
                tilemap[y + 1][x].getFloor() == TextureType.RIGHT_WALL ||
                tilemap[y + 1][x].getFloor() == TextureType.LEFT_WALL) {
            tilemap[y + 1][x] = new Tile(TextureType.SIDE_WALL, false);
        }

        if (tilemap[y + 1][x].getFloor() == TextureType.SIDE_WALL &&
                tilemap[y + 1][x - 1].getFloor() == TextureType.GRASS) {
            tilemap[y + 1][x] = new Tile(TextureType.BOTTOM_LEFT_WALL, false);
        }

        if (tilemap[y][x + 1].getFloor() == TextureType.TOP_WALL) {
            tilemap[y][x + 1] = new Tile(TextureType.LEFT_WALL, false);
        }
        else if (tilemap[y][x + 1].getFloor() == TextureType.RIGHT_WALL) {
            tilemap[y][x + 1] = new Tile(TextureType.COMBINED_WALL, false);
        }
        else if (tilemap[y][x + 1].getFloor() == TextureType.SIDE_WALL) {
            tilemap[y][x + 1] = new Tile(TextureType.BOTTOM_LEFT_WALL, false);
        }

        if (tilemap[y][x - 1].getFloor() == TextureType.TOP_WALL) {
            tilemap[y][x - 1] = new Tile(TextureType.RIGHT_WALL, false);
        }
        else if (tilemap[y][x - 1].getFloor() == TextureType.LEFT_WALL) {
            tilemap[y][x - 1] = new Tile(TextureType.COMBINED_WALL, false);
        }
        else if (tilemap[y][x - 1].getFloor() == TextureType.SIDE_WALL) {
            tilemap[y][x - 1] = new Tile(TextureType.BOTTOM_RIGHT_WALL, false);
        }

        level.setTilemap(tilemap);
    }

    private static void fixBounds(int[][] bounds) {

        int tempStartX = bounds[BOUNDS_START][BOUNDS_X];
        int tempStartY = bounds[BOUNDS_START][BOUNDS_Y];
        //bounds
        bounds[BOUNDS_START][BOUNDS_X] = Math.min(bounds[BOUNDS_START][BOUNDS_X], bounds[BOUNDS_END][BOUNDS_X]);
        bounds[BOUNDS_START][BOUNDS_Y] = Math.min(bounds[BOUNDS_START][BOUNDS_Y], bounds[BOUNDS_END][BOUNDS_Y]);
        bounds[BOUNDS_END][BOUNDS_X] = Math.max(tempStartX, bounds[BOUNDS_END][BOUNDS_X]);
        bounds[BOUNDS_END][BOUNDS_Y] = Math.max(tempStartY, bounds[BOUNDS_END][BOUNDS_Y]);
    }

    public static void sprinkleDecorations(Level level, TextureType[] choices, int seed, int amount, int[][] bounds, boolean walkable) {

        fixBounds(bounds);
        int attempts = 0;
        int maxAttempts = amount * ATTEMPT_MULTIPLIER;
        int placed = 0;
        Random random = new Random(seed);
        Tile[][] tilemap = level.getTilemap();

        while (attempts < maxAttempts && placed < amount) {
            int x = random.nextInt(bounds[BOUNDS_END][BOUNDS_X] - bounds[BOUNDS_START][BOUNDS_X]) + bounds[BOUNDS_START][BOUNDS_X];
            int y = random.nextInt(bounds[BOUNDS_END][BOUNDS_Y] - bounds[BOUNDS_START][BOUNDS_Y]) + bounds[BOUNDS_START][BOUNDS_Y];
            attempts++;
            if (tilemap[y][x].getFloor() == TextureType.GRASS && tilemap[y][x].getOccupyingActor() == null) {
                tilemap[y][x].setFloor(choices[random.nextInt(choices.length)]);
                tilemap[y][x].setWalkable(walkable);
                placed++;
            }
        }
    }

    public static void placeRandomMonster(Level level, DataAccess db, int seed, int tileX, int tileY) {

        Random random = new Random(seed);
        Enemy newEnemy = new Enemy(db, MONSTER_CHOICES[random.nextInt(MONSTER_CHOICES.length)]);
        newEnemy.place(level, tileX, tileY);
    }

    public static void placeRandomHealthPickup(Level level, int seed, int[][] bounds) {

        fixBounds(bounds);
        Random random = new Random(seed);
        int x;
        int y;

        do {
            x = random.nextInt(bounds[BOUNDS_END][BOUNDS_X] - bounds[BOUNDS_START][BOUNDS_X]) + bounds[BOUNDS_START][BOUNDS_X];
            y = random.nextInt(bounds[BOUNDS_END][BOUNDS_Y] - bounds[BOUNDS_START][BOUNDS_Y]) + bounds[BOUNDS_START][BOUNDS_Y];
        } while(!level.getTilemap()[y][x].isWalkable() && level.getTilemap()[y][x].getOccupyingActor() == null);

        int amount = random.nextInt(PICKUP_MAX) + 1;
        HealthPickup pickup = new HealthPickup(TextureType.HEART, amount);
        pickup.place(level, x, y);
    }
}
