package comp3350.escapefromicarus.presentation;

public enum ScreenType {

    SPLASH_SCREEN(0),
    MENU_SCREEN(1),
    GAME_SCREEN(2),
    GAME_OVER_SCREEN(3),
    GAME_END_SCREEN(4),
    INSTRUCTIONS_SCREEN(5),
    CREDITS_SCREEN(6);

    private int index;

    ScreenType(int i) {

        this.index = i;
    }

    public int getIndex() {

        return this.index;
    }
}
