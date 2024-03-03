package comp3350.escapefromicarus.presentation;

public enum DifficultyLevel {

    EASY(1),
    NORMAL(2),
    HARD(3);

    private int index;

    DifficultyLevel(int i) {

        this.index = i;
    }

    public int getIndex() {

        return this.index;
    }
}
