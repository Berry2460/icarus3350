package comp3350.escapefromicarus.objects;

public class Level {

    // level variables
    private int levelStartX;
    private int levelStartY;
    private int levelEndX;
    private int levelEndY;

    private Tile[][] tilemap; // custom tilemap

    public static final int MAP_SIZE = 32;
    public static final int MAX_SEED = 1000000;

    public Level() {

        this.tilemap = new Tile[MAP_SIZE][MAP_SIZE];
        this.levelStartX = 0;
        this.levelStartY = 0;
        this.levelEndX = 0;
        this.levelEndY = 0;
    }

    public void setTilemap(Tile[][] tilemap) {

        if(tilemap != null) {
            this.tilemap = tilemap;
        }
    }

    public Tile[][] getTilemap() {

        return this.tilemap;
    }

    public void setLevelStartX(int levelStartX) {

        if(inBounds(levelStartX)) {
            this.levelStartX = levelStartX;
        }
    }

    public void setLevelStartY(int levelStartY) {

        if(inBounds(levelStartY)) {
            this.levelStartY = levelStartY;
        }
    }

    public void setLevelEndX(int levelEndX) {

        if(inBounds(levelEndX)) {
            this.levelEndX = levelEndX;
        }
    }

    public void setLevelEndY(int levelEndY) {

        if(inBounds(levelEndY)) {
            this.levelEndY = levelEndY;
        }
    }

    public int getStartX() {

        return this.levelStartX;
    }

    public int getStartY() {

        return this.levelStartY;
    }

    public int getEndX() {

        return this.levelEndX;
    }

    public int getEndY() {

        return this.levelEndY;
    }

    private boolean inBounds(int point) {

        return (point >= 0 && point < MAP_SIZE);
    }
}
