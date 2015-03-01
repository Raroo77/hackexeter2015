package net.utlabs.utgame.ui;

/**
 * Any Component of a Menu
 * Actually just buttons but whatever
 */
public class Component {

    /**
     * Component's index in the array of components
     */
    public final int mId;
    /**
     * Parent UI*
     */
    public final Ui mParent;
    /**
     * Coordinates of top left corner of component for drawing
     */
    public int mX, mY;
    /**
     * width and height of the components*
     */
    public int mWidth, mHeight;

    /**
     * Constructs Component*
     *
     * @param parent: parent
     * @param id:     index
     * @param x:      x coordinate
     * @param y:      y coordinate
     * @param width:  width of component
     * @param height: height of component
     */
    public Component(Ui parent, int id, int x, int y, int width, int height) {
        mParent = parent;
        mId = id;
        mX = x;
        mY = y;
        mWidth = width;
        mHeight = height;
    }

    /**
     * Update, obviously 
     * @param delta: timescale
     */
    public void update(int delta) {
    }

    /**
     * Render
     * @param delta:timescale
     */
    public void render(int delta) {
    }

    /**
     * Menu Navigation 
     * @param key:Key Pressed
     * @param ch:Character pressed
     * @param down:Is pressed
     * @param duration:How long is pressed
     * @return
     */
    public boolean keyEvent(int key, char ch, boolean down, long duration) {

        return false;
    }

    /**
     * Press button
     */
    protected void fire() {
        mParent.childFired(mId);
    }
}
