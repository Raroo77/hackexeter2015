package net.utlabs.utgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.utlabs.utgame.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by raroo on 2/28/15.
 * A representation of a Room that the Player moves around in
 */
public class Room {
    public static final File DIR_MAP=new File(Game.DIR, "map");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    /**
     * a method to map the JSONG data to a Metadata object
     * @param src The file to be loaded from
     * @return
     * @throws Exception
     */
    public static Metadata loadMetadata(File src) throws Exception {
        final Metadata meta;
        try (FileReader reader = new FileReader(src)) {
            meta = GSON.fromJson(reader, Metadata.class);
        } catch (Exception e) {
            throw new Exception("Unable to read map " + src.getAbsolutePath(), e);
        }
        return meta;
    }

    /**
     * Game that the room exists in
     */
    public Game mGame;
    /**
     * A vector field to compute Player movement
     */
    public Vector[][] mField;

    /**
     * An long array functioning as a wrapper to store block data and metadata
     */
    public long[][] mMap;

    /**
     * A string to store the name of the loaded map image
     */
    public String mMapName;

    /**
     * Constructs a room
     * @param game The game that this room exists in
     * @param mapName The name of the map image that is being loaded
     */
    public Room(Game game, String mapName) {
        mGame = game;
        mMapName = mapName;
    }

    /**
     * a method to map the JSONG data to a Metadata object
     *
     * @param src The file to be loaded from
     * @return
     * @throws Exception
     */
    public static Metadata loadMetadata(File src) throws Exception {
        final Metadata meta;
        try (FileReader reader = new FileReader(src)) {
            meta = GSON.fromJson(reader, Metadata.class);
        } catch (Exception e) {
            throw new Exception("Unable to read map " + src.getAbsolutePath(), e);
        }
        return meta;
    }

    /**
     * Starts the loading of a room
     * Reads the map image and then computes the vector field
     * @throws IOException
     */
    public void start() throws Exception {
        BufferedImage im = ImageIO.read(new File(DIR_MAP, mMapName + ".png"));
        Metadata met = loadMetadata(new File(DIR_MAP, mMapName + ".jsong"));
        mMap = new long[met.mapX][met.mapY];
        for(int i=0; i<mMap.length; i++)
            for(int j=0; j<mMap[0].length; j++)
                mMap[i][j]=getMapComponent(met.hashMap.get(Integer.toHexString(im.getRGB(i,j))));
    }

    public void update(int delta, Player player) {
        iter(delta, player, false);
    }

    public void render(int delta, Player player) {
        iter(delta, player, false);
    }

    /**
     * Decapsulates the long wrapper
     *
     * @param l
     *
     * @return
     */
    public int getMapComponent(long l) {
        return (int) l >> 32;
    }

    /**
     * Decapsulates the long wrapper
     * @param l
     * @return
     */
    public int getMapMetadata(long l){
        return (int) l;
    }

    private void iter(int delta, Player player, boolean draw) {
        for (int x = 0; x < mMap.length; x++)
            for (int y = 0; y < mMap[x].length; x++) {
                long l = mMap[x][y];
                int id = getMapComponent(l);
                int meta = getMapMetadata(l);
                if (draw)
                    Tile.TILES[id].draw(meta, x, y, this, player, delta);
                else
                    Tile.TILES[id].update(meta, x, y, this, player, delta);
            }
    }

    /**
     * A class used to interpret the JSON metadata
     */
    private static class Metadata{
        /**
         * The horizontal resolution of the map
         */
        public int mapX;
        /**
         * The vertical resolution of the map
         */
        public int mapY;
        public Map<String, Long> hashMap;
    }
}
