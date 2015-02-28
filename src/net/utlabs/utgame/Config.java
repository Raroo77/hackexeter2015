package net.utlabs.utgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.*;

/**
 * This class is a Java representation of the config file parsed with the external library google-gson.
 * Instances of this class are to be loaded with loadConfig()
 */
public class Config {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();

    /**
     * Reads JSON from a file and stores the values in a Config object. If the given file doesn't exist, then a new file
     * is created and default values (defined in the constructor for Config) are written to it.
     *
     * @param src the File that should be read for Config values
     *
     * @return the read Config, or the new one if the given file doesn't exist
     * @throws Exception if anything goes wrong during file reading (or writing, if it was necessary)
     */
    public static Config loadConfig(File src) throws Exception {
        final Config config;
        if (!src.exists())
            config = saveConfig(new Config(), src);
        else
            try (FileReader reader = new FileReader(src)) {
                config = GSON.fromJson(reader, Config.class);
            } catch (Exception e) {
                throw new Exception("Unable to read config " + src.getAbsolutePath(), e);
            }
        return config;
    }

    /**
     * Writes the JSON of a Config to a file.
     *
     * @param config the Config object whose values will be written
     * @param target the File to write the Config to
     *
     * @return The passed in Config, for convenience.
     * @throws Exception if anything goes wrong during file writing
     */
    public static Config saveConfig(Config config, File target) throws Exception {
        try (FileWriter writer = new FileWriter(target, false)) {
            GSON.toJson(config, writer);
        } catch (Exception e) {
            throw new Exception("Unable to write config " + target.getAbsolutePath(), e);
        }
        return config;
    }

    /**
     * Width of the game window.
     */
    @Expose
    @SerializedName("Width")
    public int mWidth;
    /**
     * Height of the game window.
     */
    @Expose
    @SerializedName("Height")
    public int mHeight;
    /**
     * Fullscreen state of the window.
     */
    @Expose
    @SerializedName("Fullscreen")
    public boolean fullscreen;

    /**
     * Constructor assigns default values to fields.
     */
    public Config() {
        mWidth = 800;
        mHeight = 600;
        fullscreen = false;
    }
}
