package net.utlabs.utgame.ui;

import net.utlabs.utgame.*;
import org.lwjgl.opengl.GL11;

/**
 * Created by mas.dicicco on 3/1/2015.
 */
public class UiGame extends Ui {

    public Room mRoom;
    public Player mPlayer;
    public float mZoom;

    public UiGame(Ui parent, Room room) {
        super(parent);
        mRoom = room;
        mZoom = 1;
    }

    @Override
    public void init(Game game) {
        super.init(game);
        mPlayer = new Player(game);
    }

    @Override
    public void update(int delta) {
        super.update(delta);
        mRoom.update(delta, mPlayer);
        mPlayer.update(delta);
    }

    @Override
    public void render(int delta) {
        super.render(delta);
        GL11.glPushMatrix();
        GL11.glScalef(mZoom, mZoom, mZoom);
        mRoom.render(delta, mPlayer);
        mPlayer.render(delta);
        GL11.glPopMatrix();
    }

    @Override
    public void keyEvent(int key, char ch, boolean down, long duration) {
        super.keyEvent(key, ch, down, duration);
    }
}
