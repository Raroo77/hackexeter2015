package net.utlabs.utgame.ui;

import net.utlabs.utgame.*;

public class UiProgress extends Ui {

    private Room mRoom;
    private String mTarget;
    private Texture mTexture;
    private PBar mPBar;

    public UiProgress(Ui parent, String target) {
        super(parent);
        mTarget = target;
    }

    @Override
    public void init(Game game) {
        super.init(game);
        mRoom = new Room(game, mTarget);
        mTexture = Texture.getTexture("ProgressBar.png");
        mPBar = new PBar(this, 0, 50, 100, mWidth - 100, mTexture.mHeight, mTexture);
        Thread worker = new Thread() {
            @Override
            public void run() {
                doProcessing();
            }
        };
        worker.setDaemon(true);
        worker.start();
    }

    public void doProcessing() {
        //TODO Parallelize?
        try {
            mRoom.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
