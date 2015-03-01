package net.utlabs.utgame.ui;

import net.utlabs.utgame.*;
import org.lwjgl.util.glu.Sphere;

/**
 * Created by mas.dicicco on 3/1/2015.
 */
public class UiGame extends Ui {

    public Sphere leSphere;
    Room room;
    Player player;
    int rot = 0;

    public UiGame(Ui parent) {
        super(parent);
    }

    @Override
    public void init(Game game) {
        leSphere = new Sphere();
    }

    @Override
    public void update(int delta) {
    }

    @Override
    public void render(int delta) {
    }

    @Override
    public void keyEvent(int key, char ch, boolean down, long duration) {
    }
}
