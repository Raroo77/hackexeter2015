package net.utlabs.utgame.ui;

import net.utlabs.utgame.*;

/**
 * Created by mas.dicicco on 3/1/2015.
 */
public class UiGame extends Ui {

    public Room mRoom;
    public Player mPlayer;

    public UiGame(Ui parent, Room room) {
        super(parent);
        mRoom = room;
    }

    @Override
    public void init(Game game) {
        super.init(game);
        mPlayer = new Player(game);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
    }

    @Override
    public void render(int delta) {
        super.render(delta);
    }

    @Override
    public void keyEvent(int key, char ch, boolean down, long duration) {
        super.keyEvent(key, ch, down, duration);
    }
}
