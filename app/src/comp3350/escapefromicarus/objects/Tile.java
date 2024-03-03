package comp3350.escapefromicarus.objects;

public class Tile {

    private boolean isWalkable;
    private Actor occupyingActor;
    private TextureType floor;

    public static final int TILE_SIZE = 128;

    public Tile(TextureType newFloor, boolean walkable) {

        this.floor = newFloor;
        this.isWalkable = walkable;
        this.occupyingActor = null;
    }

    public void setWalkable(boolean walkable) {

        this.isWalkable = walkable;
    }

    public void setOccupyingActor(Actor actor) {

        this.occupyingActor = actor;
    }

    public TextureType getFloor() {

        return this.floor;
    }

    public void setFloor(TextureType newFloor) {

        this.floor = newFloor;
    }

    public Actor getOccupyingActor() {

        return this.occupyingActor;
    }

    public boolean isWalkable() {

        return this.isWalkable;
    }
}
