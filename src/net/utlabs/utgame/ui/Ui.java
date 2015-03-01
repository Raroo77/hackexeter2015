package net.utlabs.utgame.ui;

import net.utlabs.utgame.Game;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public abstract class Ui {

    /**
     * Parent Ui*
     */
    public final Ui mParent;
    /**
     * List of components
     */
    protected final ArrayList<Component> mComponents;
    /**
     * Currently Selected Button
     */
    public int mCurrentButton = 0;
    /**
     * The game lol
     */
    public Game mGame;

    /**
     * Constructs UI
     *
     * @param parent: Parent UI
     */
    public Ui(Ui parent) {
        mParent = parent;
        mComponents = new ArrayList<>();
    }

    /**
     * Initializes* 
     * @param game: The game lol
     */
    public void init(Game game) {
        mGame = game;
        mComponents.clear();
    }

    /**
     * Updates 
     * @param delta: Timescale
     */
    public void update(int delta) {
        for (Component c : mComponents)
            c.update(delta);
    }

    /**
     * Renders 
     * @param delta: Timescale
     */
    public void render(int delta) {
        for (Component c : mComponents)
            c.render(delta);
    }

    /**
     * Keylistener 
     * @param key:The key pressed
     * @param ch: The character of the key pressed
     * @param down: Is is pressed
     * @param duration: How long is it pressed
     */
    public void keyEvent(int key, char ch, boolean down, long duration) {
        if (!down)
            return;
        switch (key) {
            case Keyboard.KEY_UP:
                mCurrentButton = (mCurrentButton + 1) % mComponents.size();
                break;
            case Keyboard.KEY_DOWN:
                if (mCurrentButton == 0) {
                    mCurrentButton = mComponents.size();
                }
                mCurrentButton = mCurrentButton - 1;

        }
        for (Component c : mComponents)
            if (c.keyEvent(key, ch, down, duration))
                return;
    }

    /**
     * Button Pressed
     *
     * @param id: Which button
     */
    public void childFired(int id) {
        System.out.println(id);

    }
}
