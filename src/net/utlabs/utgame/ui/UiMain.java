package net.utlabs.utgame.ui;

import net.utlabs.utgame.Game;
import net.utlabs.utgame.Texture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * Created by mas.dicicco on 3/1/2015.
 * MAIN MENU
 */
public class UiMain extends Ui {

    public Texture mSwitchTexture = Texture.getTexture("Switch.png");
    /**
     * Currently Selected Button
     */
    public int mCurrentButton = 3;
    double d;

    /**
     * Main Menu has no parent UI
     */
    public UiMain() {
        super(null);
    }

    public void init(Game game) {
        super.init(game);
        mComponents.add(new Button(this, 0, 0, 64, 256, 64, Texture.getTexture("Exit.png")));
        mComponents.add(new Button(this, 1, 0, 128, 256, 64, Texture.getTexture("Options.png")));
        mComponents.add(new Button(this, 2, 0, 196, 256, 64, Texture.getTexture("Credits.png")));
        mComponents.add(new Button(this, 3, 0, 256, 256, 64, Texture.getTexture("Play.png")));
    }

    @Override
    public void render(int delta) {
        GL11.glColor3f(1, 1, 1);
        GL11.glPushMatrix();
        GL11.glTranslated(mWidth / 2, mHeight * 2 / 3, 0);
        double scale = 0.05F * Math.sin((d += delta) / 1000F) + 1;
        GL11.glScaled(scale, scale, 0);
        GL11.glRotated(5 * Math.cos(d / 500F), 0, 0, 1);
        mSwitchTexture.drawCenteredModalRect(0, 0, mSwitchTexture.mWidth, mSwitchTexture.mHeight, 0, 0, 0, mSwitchTexture.mWidth, mSwitchTexture.mHeight);
        GL11.glPopMatrix();
        super.render(delta);
    }

    @Override
    public void keyEvent(int key, char ch, boolean down, long duration) {
        if (!down)
            return;
        int prev = mCurrentButton;
        int next = 0;
        switch (key) {
            case Keyboard.KEY_UP:
                next = -1;
                break;
            case Keyboard.KEY_DOWN:
                next = 1;
                break;
        }
        if (next != 0) {
            Button current = (Button) mComponents.get(mCurrentButton);
            current.mSelected = false;
            next += prev;
            if (next < 0)
                next += mComponents.size();
            else if (next >= mComponents.size())
                next -= mComponents.size();
            mCurrentButton = next;
            current = (Button) mComponents.get(mCurrentButton);
            current.mSelected = true;
        }
        super.keyEvent(key, ch, down, duration);
    }

    @Override
    public void childFired(int id) {
        if (id != mCurrentButton)
            return;
        switch (id) {
            case 0:
                mGame.shutdown();
                break;
            case 3:
                mGame.changeMenu(new UiProgress(this, "Level01"));
        }
    }
}
