package net.utlabs.utgame;

import com.google.gson.annotations.SerializedName;
import net.utlabs.utgame.ui.Ui;
import net.utlabs.utgame.ui.UiMain;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.*;

import javax.swing.JOptionPane;
import java.io.File;
import java.lang.reflect.Field;

import static org.lwjgl.opengl.GL11.*;

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
     * Directory of the images.
     */
    public static final File DIR_IMG = new File(DIR, "img");
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
        try {
            mGame.start();
        } catch (Exception e) {
            mGame.mLog.e(e);
            showError();
        } finally {
            mGame.shutdown();
        }
    }

    /**
     * @return The instance of Game set in motion by the main method, mGame.
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
        if (!fullscreen)
            Display.setDisplayMode(new DisplayMode(width, height));
        else
            throw new LWJGLException(String.format("Unable to set fullscreen display mode %dx%d", width, height));
    }

    /**
     * Displays a nice dialog telling the user that the game screwed up and that they should check the log for more info.
     */
    public static void showError() {
        JOptionPane.showMessageDialog(null, "An error occurred, please read the log file for more information");
    }

    public int mWidth, mHeight;
    /**
     * Log for the Game object, located at FL_LOG.
     */
    public Log mLog;
    /**
     * Config for the Game object, located at FL_CONFIG.
     */
    public Config mConfig;
    /**
     * The current Ui menu
     */
    public Ui mUi;

    /**
     * Shuts down the Game in a nice manner.
     */
    public void shutdown() {
        mLog.i("Shutting down");
        Shaders.cleanup();
        Keyboard.destroy();
        Mouse.destroy();
        Display.destroy();
        mLog.close();
        System.exit(0);
    }

    public void changeMenu(Ui newMenu) {
        newMenu.init(this);
        mUi = newMenu;
    }

    /**
     * Start-up operations for the Game (Display creation, Keyboard and Mouse, etc.).
     */
    private void start() throws Exception {
        mLog = new Log(2048, FL_LOG.getAbsolutePath(), true, true);
        mLog.d("Loading configuration from " + FL_CONFIG);
        mConfig = Config.loadConfig(FL_CONFIG);
        Config.saveConfig(mConfig, FL_CONFIG);
        for (Field f : Config.class.getFields())
            mLog.d(f.getAnnotation(SerializedName.class).value() + ": " + f.get(mConfig));
        mLog.d("Configuration loaded successfully");
        mLog.d("Setting up Display");
        Display.setTitle("Untitled Game");
        setDisplayMode(mConfig.mWidth, mConfig.mHeight, mConfig.mFullscreen);
        Display.create();
        Keyboard.create();
        mLog.d("Setting up OpenGL");
        glViewport(0, 0, mWidth = Display.getWidth(), mHeight = Display.getHeight());
        glMatrixMode(GL_PROJECTION);
        glOrtho(0, mWidth, 0, mHeight, -100, 100);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        glShadeModel(GL_SMOOTH);
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        glClearDepth(1.0f);
        glEnable(GL_DEPTH_TEST);
        glDepthMask(true);
        glDepthRange(0, 1);
        glDepthFunc(GL_LESS);
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_LIGHTING);
        mLog.d("Loading shader programs");
        Shaders.init();
        GL20.glUseProgram(Shaders.mShaderProgram);
        mLog.d("Loading textures");
        File[] imgs = DIR_IMG.listFiles();
        if (imgs == null)
            throw new Exception("Unable to load images");
        for (File f : imgs) {
            mLog.d("Loading " + f);
            Texture.loadTexture(f);
        }
        mLog.d("Entering game loop");
        changeMenu(new UiMain());
        gameLoop();
    }

    private void gameLoop() throws Exception {
        long lastUpdate = System.currentTimeMillis();
        while (!Display.isCloseRequested()) {
            long updateStart = System.currentTimeMillis();
            int delta = (int) (updateStart - lastUpdate);
            Display.update();
            Keyboard.poll();
            update(delta);
            render(delta);
            lastUpdate = System.currentTimeMillis();
            Display.sync(mConfig.mFPS);
        }
    }

    private void update(int delta) throws Exception {
        while (Keyboard.next())
            mUi.keyEvent(Keyboard.getEventKey(), Keyboard.getEventCharacter(), Keyboard.getEventKeyState(), Keyboard.getEventNanoseconds());
        mUi.update(delta);
    }

    private void render(int delta) throws Exception {
        glClear(GL_DEPTH_BUFFER_BIT | GL_COLOR_BUFFER_BIT);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
        mUi.render(delta);
    }
}
