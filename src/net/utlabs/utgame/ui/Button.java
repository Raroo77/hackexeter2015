package net.utlabs.utgame.ui;

import net.utlabs.utgame.Texture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * Created by mas.dicicco on 3/1/2015.
 */
public class Button extends Component {

    private Texture mTexture;

    public Button(Ui parent, int id, int x, int y, int width, int height, Texture t) {
        super(parent, id, x, y, width, height);
        mTexture = t;
    }

    @Override
    public boolean keyEvent(int key, char ch, boolean down, long duration) {
        if (key == Keyboard.KEY_SPACE && mParent.mCurrentButton == mId) {
            fire();
            return true;
        }
        return false;
    }

    @Override
    public void render(int delta) {
        if (mParent.mCurrentButton == mId) {
            GL11.glColor3f(0, 1, 0);
        } else
            GL11.glColor3f(1, 1, 1);
        mTexture.drawModalRect(0, 0, mTexture.mWidth, mTexture.mHeight, mX, mY, 0, mTexture.mWidth, mTexture.mHeight);
    }

}
