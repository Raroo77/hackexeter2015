package net.utlabs.utgame.ui;

import net.utlabs.utgame.Game;
import net.utlabs.utgame.Texture;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * Created by mas.dicicco on 3/1/2015.
 * MAIN MENU
 */
public class UiMain extends Ui {

    public Texture mSwitchTexture = Texture.getTexture("Switch.png");
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
        GL11.glTranslated(Display.getWidth() / 2, Display.getHeight() * 2 / 3, 0);
        double scale = 0.05F * Math.sin((d += delta) / 1000F) + 1;
        GL11.glScaled(scale, scale, 0);
        GL11.glRotated(5 * Math.cos(d / 500F), 0, 0, 1);
        mSwitchTexture.drawCenteredModalRect(0, 0, mSwitchTexture.mWidth, mSwitchTexture.mHeight, 0, 0, 0, mSwitchTexture.mWidth, mSwitchTexture.mHeight);
        GL11.glPopMatrix();
        super.render(delta);
    }

    @Override
    public void childFired(int id) {
        switch (id) {
            case 0:
                mGame.shutdown();
                break;
        }
    }

}
