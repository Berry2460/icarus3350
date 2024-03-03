package comp3350.escapefromicarus.objects;

import java.util.ArrayList;

import comp3350.escapefromicarus.persistence.DataAccess;

public class World {

    private int level;
    private ArrayList<Level> levelStack;
    private Player player;
    public static final int MAX_LEVELS = 5;

    public World() {

        this.level = 0;
        this.levelStack = new ArrayList<>();
        this.player = null;
    }

    public boolean addPlayer(DataAccess dataAccess) {

        boolean success = false;
        if (this.levelStack.size() > 0) {
            this.player = new Player(TextureType.PLAYER, dataAccess);
            this.player.place(this.getCurrentLevel(), this.getCurrentLevel().getStartX(), this.getCurrentLevel().getStartY());
            success = true;
        }
        return success;
    }

    public void setLevelID(int newID) {

        if(newID < this.levelStack.size()) {
            this.level = newID;
        }
    }

    public int getLevelCount() {

        return this.levelStack.size();
    }

    public void addLevel(Level level) {

        if(level != null) {
            this.levelStack.add(level);
        }
    }

    public Level getCurrentLevel() {

        Level result = null;
        if (this.levelStack.size() > 0){
            result = this.levelStack.get(level);
        }
        return result;
    }

    public int getLevelID() {

        return this.level;
    }

    public Player getPlayer() {

        return this.player;
    }
}
