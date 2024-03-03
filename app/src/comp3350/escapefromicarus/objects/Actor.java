package comp3350.escapefromicarus.objects;

import comp3350.escapefromicarus.presentation.SoundEffect;

public abstract class Actor {

    protected int tileX;
    protected int tileY;
    protected float pixelX;
    protected float pixelY;
    protected Tile[][] tilemap;
    protected boolean isPlaced;
    protected Level level;
    protected SoundEffect soundFX;

    private final TextureType textureType;

    public Actor(TextureType texture) {

        this.textureType = texture;
        this.soundFX = null;
    }

    public TextureType getTextureType() {

        return this.textureType;
    }

    public float getPixelX() {

        return this.pixelX;
    }

    public float getPixelY() {

        return this.pixelY;
    }

    public int getTileX() {

        return this.tileX;
    }

    public int getTileY() {

        return this.tileY;
    }

    public void setPixelPosition(float x, float y) {

        this.pixelX = x;
        this.pixelY = y;
    }

    public void setLevel(Level level) {

        this.level = level;
        this.tilemap = level.getTilemap();
    }

    public void place(Level level, int startX, int startY) {

        this.isPlaced = true;
        this.level = level;
        this.tilemap = level.getTilemap();
        this.setPixelPosition(startX * Tile.TILE_SIZE, startY * Tile.TILE_SIZE);
        this.tileX = (int) this.pixelX / Tile.TILE_SIZE;
        this.tileY = (int) this.pixelY / Tile.TILE_SIZE;
        this.tilemap[this.tileY][this.tileX].setWalkable(false);
        this.tilemap[this.tileY][this.tileX].setOccupyingActor((Actor)this);
    }

    public void teleport(int xPos, int yPos) {

        if (this.isPlaced && this.tilemap[yPos][xPos].isWalkable()) {
            removeActor();
            //set new position
            this.setPixelPosition(xPos * Tile.TILE_SIZE, yPos * Tile.TILE_SIZE);
            this.tileX = (int) (this.pixelX / Tile.TILE_SIZE);
            this.tileY = (int) (this.pixelY / Tile.TILE_SIZE);
            placeActor();
        }
    }

    protected void removeActor() {

        if (isPlaced && this.tilemap[this.tileY][this.tileX].getOccupyingActor() == (Actor)this) {
            this.tilemap[this.tileY][this.tileX].setWalkable(true);
            this.tilemap[this.tileY][this.tileX].setOccupyingActor(null);
        }
    }

    protected void placeActor() {

        if (isPlaced) {
            this.tilemap[this.tileY][this.tileX].setWalkable(false);
            this.tilemap[this.tileY][this.tileX].setOccupyingActor((Actor)this);
        }
    }
}
