package net.utlabs.utgame.ui;

public class Component {

    public final int mId;
    public final Ui mParent;
    public int mX, mY;
    public int mWidth, mHeight;

    public Component(Ui parent, int id, int x, int y, int width, int height) {
        mParent = parent;
        mId = id;
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
    }

    public void update(int delta) {}

    public void render(int delta) {}

    public boolean keyEvent(int key, char ch, boolean down, long duration) {

        return false;
    }

    protected void fire() {
        mParent.childFired(mId);
    }
}
