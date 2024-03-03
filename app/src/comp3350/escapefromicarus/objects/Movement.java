package comp3350.escapefromicarus.objects;

public abstract class Movement extends Actor {

    private int finalDestX;
    private int finalDestY;
    private int nextMoveX;
    private int nextMoveY;
    private int deltaX;
    private int deltaY;

    private boolean isGliding;

    private float speed; //tiles per second

    private static final float DIV_OFFSET = 0.00000001f; // prevents dividing by 0

    public Movement(TextureType texture, float speed) {

        super(texture);
        this.speed = speed;
        this.isPlaced = false;
        this.isGliding = false;
    }

    public void setDestination(int xPos, int yPos) {

        if (this.isPlaced) {
            // snap destination to this.tilemap
            this.finalDestX = xPos * Tile.TILE_SIZE;
            this.finalDestY = yPos * Tile.TILE_SIZE;
            if (!this.isGliding) {
                this.isGliding = true;
                getNextMove();
            }
        }
    }

    private void getNextMove() {

        if (this.isPlaced) {
            if (this.isGliding && (int) this.pixelX == this.finalDestX
                    && (int) this.pixelY == this.finalDestY) {
                this.isGliding = false;
            }
            else {
                removeActor();
                //get next tile direction
                this.deltaX = Math.round((this.finalDestX - this.pixelX) / (Math.abs(this.finalDestX - this.pixelX + DIV_OFFSET)));
                this.deltaY = Math.round((this.finalDestY - this.pixelY) / (Math.abs(this.finalDestY - this.pixelY + DIV_OFFSET)));

                //collision
                Actor collided = this.tilemap[this.tileY + this.deltaY][this.tileX + this.deltaX].getOccupyingActor();
                if (collided != null) {
                    if (this instanceof Character) {
                        ((Character) this).collision(collided);
                    }
                    this.isGliding = false; //hit something
                }

                //collision and bounds check
                boolean isInBounds = (this.tileY + this.deltaY >= 0
                        && this.tileY + this.deltaY < this.tilemap.length - 1
                        && this.tileX + this.deltaX >= 0
                        && this.tileX + this.deltaX < this.tilemap[0].length - 1);

                if (isInBounds && this.tilemap[this.tileY + this.deltaY][this.tileX + this.deltaX].isWalkable()) {
                    //set destination to next tile
                    this.tileX += this.deltaX;
                    this.tileY += this.deltaY;
                    this.nextMoveX = this.tileX * Tile.TILE_SIZE;
                    this.nextMoveY = this.tileY * Tile.TILE_SIZE;
                }
                //redirect
                else if (isInBounds && this.isGliding) {
                    this.isGliding = false;
                    if (this.tilemap[this.tileY][this.tileX + this.deltaX].isWalkable()) {
                        setDestination(this.finalDestX / Tile.TILE_SIZE, this.tileY); //fix path along x axis
                    }
                    else if (this.tilemap[this.tileY + this.deltaY][this.tileX].isWalkable()) {
                        setDestination(this.tileX, this.finalDestY / Tile.TILE_SIZE); //fix path along y axis
                    }
                }
                placeActor();
            }
        }
    }

    public boolean isMoving() {

        return this.isGliding;
    }

    public void glide(float timeDelta) {

        if (this.isPlaced && this.isGliding) {
            // adjust speed to frame rate to prevent unexpected behaviour
            float trueSpeed = this.speed * Tile.TILE_SIZE * timeDelta;

            // apply speed and direction to move (Actor)this
            float xGlide = trueSpeed * this.deltaX + this.pixelX;
            float yGlide = trueSpeed * this.deltaY + this.pixelY;

            // check if destination reached
            if (Math.abs(this.nextMoveX - this.pixelX) <= trueSpeed
                    && Math.abs(this.nextMoveY - this.pixelY) <= trueSpeed) {
                // snap to tile and get next step
                this.setPixelPosition(this.tileX * Tile.TILE_SIZE, this.tileY * Tile.TILE_SIZE);
                getNextMove();
            }
            else {
                // glide to next tile
                this.setPixelPosition(xGlide, yGlide);
            }
        }
    }

    @Override
    public void place(Level level, int startX, int startY) {

        super.place(level, startX, startY);
        this.isGliding = false;
    }

    @Override
    public void teleport(int xPos, int yPos) {

        if (this.isPlaced && this.tilemap[yPos][xPos].isWalkable()) {
            this.isGliding = false;
            removeActor();
            //set new position
            this.setPixelPosition(xPos * Tile.TILE_SIZE, yPos * Tile.TILE_SIZE);
            this.tileX = (int) (this.pixelX / Tile.TILE_SIZE);
            this.tileY = (int) (this.pixelY / Tile.TILE_SIZE);
            placeActor();
        }
    }

    public void halt() {

        if (this.isPlaced) {
            removeActor();
            this.deltaX = 0;
            this.deltaY = 0;
            this.isGliding = false;
            this.setPixelPosition(getTileX() * Tile.TILE_SIZE, getTileY() * Tile.TILE_SIZE);
            placeActor();
        }
    }

    public int getFinalDestX() {

        return this.finalDestX;
    }

    public int getFinalDestY() {

        return this.finalDestY;
    }
}
