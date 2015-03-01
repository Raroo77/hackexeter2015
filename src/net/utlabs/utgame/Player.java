package net.utlabs.utgame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

/**
 * Created by raroo on 2/28/15.
 * Class representing an instance of the Player
 */
public class Player {

    /**
     * Game that Player exists within
     */
    public Game mGame;
    /**
     * Player's 'Charge'
     */
    public int mCrg;
    /**
     * Measure of Player's charge (when 0, mLife decrements)
     */
    public int mMcrg;
    /**
     * Player's life count
     */
    public int mLife;
    /**
     * Player's mass
     */
    public float mMass;
    /**
     * Player's score per room
     */
    public int mScore;
    /**
     * Electric force on Player (acceleration(ish))
     */
    public Vector mForce;
    /**
     * Player's acceleration
     */
    public Vector mAccel;
    /**
     * Player's velocity (acceleration)
     */
    public Vector mVelocity;
    /**
     * Player's position
     */
    public Vector mPos;

    public final Texture mTextureO;
    public final Texture mTextureB;

    /**
     * Constructs a Player
     *
     * @param game the instance of the Game the Player exists in
     */
    public Player(Game game) {
        mGame = game;
        mVelocity = new Vector(0, 0);
        mLife = Integer.MAX_VALUE; //TODO change this shit.
        mCrg = -1;
        mMcrg = 10000;
        mMass = 5;
        mScore = 0;
        mForce = new Vector();
        mVelocity = new Vector();
        mAccel = new Vector();
        mPos = new Vector();
        mTextureO = Texture.getTexture("PlayerO.png");
        mTextureB = Texture.getTexture("PlayerB.png");
    }

    public boolean keyEvent(int key, char ch, boolean down, long duration) {
        int sign = 0;
        if (down)
            sign = 1;
        else
            sign = -1;
        if (key == Keyboard.KEY_RIGHT)
            mForce.mX += 100 * sign;
        if (key == Keyboard.KEY_LEFT)
            mForce.mX -= 100 * sign;
        if (key == Keyboard.KEY_UP)
            mForce.mY += 100 * sign;
        if (key == Keyboard.KEY_DOWN)
            mForce.mY -= 100 * sign;
        if (key == Keyboard.KEY_SPACE && down)
            mCrg *= -1;
        return false;
    }

    public boolean isDead() {
        if (mMcrg == 0)
            return true;
        return false;
    }

    public boolean isReallyDead() {
        if (mLife == 0)
            return true;
        return false;
    }

    public void update(int delta) {
        if (this.isDead()) {
            mScore = 0;
            mLife--;
        }
        mMcrg -= Math.copySign(delta, -mMcrg);
        mForce.multiply(1F / mMass, mAccel);
        mVelocity.add(mAccel.multiply(delta / 1000F, new Vector()), mVelocity);
        mPos.add(mVelocity.multiply(delta / 1000F, new Vector()), mPos);
        mVelocity.multiply(0.97F, mVelocity);
    }

    public void render(int delta) {
        Texture t;
        if (mCrg > 0)
            t = mTextureB;
        else
            t = mTextureO;
        GL11.glColor3f(1, 1, 1);
        t.drawCenteredModalRect(0, 0, t.mWidth, t.mHeight, getPX(), getPY(), 0, t.mWidth / 4, t.mHeight / 4);
    }

    public int getPX() {
        return (int) (mPos.mX * 50);
    }

    public int getPY() {
        return (int) (mPos.mY * 50);
    }
}
