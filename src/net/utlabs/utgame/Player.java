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
    public double mMcrg;
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

    /**
     * Constructs a Player
     * @param game the instance of the Game the Player exists in
     */
    public Player(Game game){
        mGame = game;
        mMove = new Vector(0, 0);
        mLife = Integer.MAX_VALUE; //TODO change this shit.
        mCrg =  -1;
        //TODO decide value of mMcrg.
        mMass = 5;
        mScore = 0;
        //TODO implement starting force.
    }

    /**
     *
     * @param K
     * @return
     */
    public boolean inputHandler(int K){
        switch(K) {
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
    public boolean isDead(){
        if(mMcrg == 0)
            return true;
        return false;
    }
    public boolean isReallyDead(){
        if (mLife==0)
            return true;
        return false;
    }
    public void update(int delta) {
        if(this.isDead()){
            try {
                //mGame.mRoom.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
            mLife--;
        }
        //mGame.mRoom.mField[(int)mPos.mX][(int)mPos.mY].multiply(1/mMass,mForce);
        Vector accel = new Vector(); //Player's total acceleration
        mMove.add(mForce, accel);
        accel.multiply(0.7f, accel);
        mPos.add(accel.multiply(delta, accel),mPos);


    }

}
