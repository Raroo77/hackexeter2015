package net.utlabs.utgame.ui;

import net.utlabs.utgame.Texture;
import org.lwjgl.opengl.GL11;

public class PBar extends Component {

    private float mPercentage;
    private Texture mTexture;

    public PBar(Ui parent, int id, int x, int y, int width, int height, Texture t) {
        super(parent, id, x, y, width, height);
        mTexture = t;
    }

    public void render(int delta) {
        GL11.glPushMatrix();
        GL11.glTranslatef(mX, mY, 0);
        mTexture.drawModalRect(0, 0, (int) (mTexture.mWidth * mPercentage), mTexture.mHeight, mX, mY, 0, mWidth, mHeight);
        GL11.glEnd();
        GL11.glPopMatrix();
    }

    public void setPercentage(float percent) {
        mPercentage = percent;
    }
}
