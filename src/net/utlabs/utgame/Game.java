package net.utlabs.utgame;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.io.File;

/**
 * Class that houses the main loop and other central game structures
 *
 * @author Alec
 */
public class Game {

    /**
     * Home directory of the game.
     */
    public static final File DIR = new File("").getAbsoluteFile();
    /**
     * File location of the Config file, generally "DIR/.config".
     */
    public static final File FL_CONFIG = new File(Game.DIR, ".config");
    /**
     * File location of the Log file, generally "DIR/.log".
     */
    public static final File FL_LOG = new File(Game.DIR, ".log");
    /**
     * The instance of the Game started in the main method.
     */
    private static Game mGame;

    public static void main(String[] args) {
        System.setProperty("org.lwjgl.librarypath", new File(DIR, "native").getAbsolutePath());
        mGame = new Game();
        mGame.start();
    }

    /**
     * @return The instance of Game set in motion by the main method, mGame
     */
    public static Game getInstance() {
        return mGame;
    }

    /**
     * Convenience method to set the size and fullscreen property of the window.
     *
     * @param width      desired width of the window
     * @param height     desired height of the window
     * @param fullscreen true for fullscreen, false for windowed
     *
     * @throws LWJGLException if no such DisplayMode exists, or something went wrong while setting it.
     */
    public static void setDisplayMode(int width, int height, boolean fullscreen) throws LWJGLException {
        for (DisplayMode mode : Display.getAvailableDisplayModes())
            if (mode.getWidth() == width && mode.getHeight() == height)
                if (!(fullscreen && !mode.isFullscreenCapable())) {
                    Display.setDisplayMode(mode);
                    Display.setFullscreen(fullscreen);
                    return;
                }
        throw new LWJGLException(String.format("Unable to set display mode %dx%d [%b]", width, height, fullscreen));
    }

    /**
     * Log for the Game object, located at FL_LOG
     */
    public Log mLog;
    /**
     * Config for the Game object, located at FL_CONFIG
     */
    public Config mConfig;

    /**
     * Start-up operations for the Game (Display creation, Keyboard and Mouse, etc.).
     */
    private void start() {
        mLog = new Log(2048, FL_LOG.getAbsolutePath(), true, true);
        mLog.d("Loading configuration from " + FL_CONFIG);
        try {
            mConfig = Config.loadConfig(FL_CONFIG);
            Config.saveConfig(mConfig, FL_CONFIG);
        } catch (Exception e) {
            mLog.e(e);
            System.exit(0);
        }
        mLog.d("Configuration loaded successfully");
        try {
            Display.setTitle("Untitled Game");
            setDisplayMode(mConfig.mWidth, mConfig.mHeight, mConfig.fullscreen);
            Display.create();
            Mouse.create();
            Keyboard.create();
        } catch (Exception e) {
            mLog.e(e);
            System.exit(0);
        }
        //TODO Replace with a real game loop
        while (!Display.isCloseRequested()) {
            Display.update();
        }
    }
}
