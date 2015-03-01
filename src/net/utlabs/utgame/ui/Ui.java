package net.utlabs.utgame.ui;

import net.utlabs.utgame.Game;

import java.util.ArrayList;

public abstract class Ui {

    public final Ui mParent;
    public Game mGame;
    private final ArrayList<Component> mComponents;

    public Ui(Ui parent) {
        mParent = parent;
        mComponents = new ArrayList<>();
    }

    public void init(Game game) {
        mGame = game;
        mComponents.clear();
    }

    public void update(int delta) {
        for (Component c : mComponents)
            c.update(delta);
    }

    public void render(int delta) {
        for (Component c : mComponents)
            c.render(delta);
    }

    public void keyEvent(int key, char ch, boolean down, long duration) {
        for (Component c : mComponents)
            if (c.keyEvent(key, ch, down, duration))
                return;
    }

    public void childFired(int id) {}
}
