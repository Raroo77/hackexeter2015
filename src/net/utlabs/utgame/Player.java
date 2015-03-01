package net.utlabs.utgame;

import org.lwjgl.input.Keyboard;

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
     * Player's input movement (acceleration)
     */
    public Vector mMove;
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
        mMove = new Vector(0, 0);
        mLife = Integer.MAX_VALUE; //TODO change this shit.
        mCrg = -1;
        mMcrg = 10000;
        mMass = 5;
        mScore = 0;
        mForce = new Vector();
        mMove = new Vector();
        mPos = new Vector();
        mTextureO = Texture.getTexture("PlayerO.png");
        mTextureB = Texture.getTexture("PlayerB.png");
    }

    /**
     * @param K
     *
     * @return
     */
    public boolean inputHandler(int K) {
        switch (K) {
            case Keyboard.KEY_RIGHT:
                mMove.mX += 1;
                return true;
            case Keyboard.KEY_LEFT:
                mMove.mX -= 1;
                return true;
            case Keyboard.KEY_UP:
                mMove.mY += 1;
                return true;
            case Keyboard.KEY_DOWN:
                mMove.mY -= 1;
                return true;
            case Keyboard.KEY_SPACE:
                mCrg *= -1;
                return true;
            default:
                return false;
        }
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
            try {
                //mGame.mRoom.start();
                mScore = 0;
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLife--;
        }
        mMcrg -= delta;

        Vector accel = new Vector(); //Player's total acceleration
        mMove.add(mForce, accel);
        accel.multiply(0.7f, accel);
        mPos.add(accel.multiply(delta, accel), mPos);
    }

    public void render(int delta) {
        Texture t;
        if (mCrg > 0)
            t = mTextureB;
        else
            t = mTextureO;
        t.drawCenteredModalRect(0, 0, t.mWidth, t.mHeight, mPos.mX, mPos.mY, 0, t.mWidth, t.mHeight);
    }
}
