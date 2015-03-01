package net.utlabs.utgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.utlabs.utgame.tiles.Tile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Map;

/**
 * Created by raroo on 2/28/15.
 * A representation of a Room that the Player moves around in
 */
public class Room {

    public static final File DIR_MAP = new File(Game.DIR, "map");
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

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

    public Metadata mMeta;
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
    public boolean mStarted;

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
     * Starts the loading of a room
     * Reads the map image and then computes the vector field
     * @throws IOException
     */
    public void start() throws Exception {
        BufferedImage im = ImageIO.read(new File(DIR_MAP, mMapName + ".png"));
        if (!mStarted) {
            mMeta = loadMetadata(new File(DIR_MAP, mMapName + ".json"));
            mMap = new long[mMeta.mapX][mMeta.mapY];
            for (int i = 0; i < mMap.length; i++)
                for (int j = 0; j < mMap[0].length; j++) {
                    String hex = Integer.toHexString(im.getRGB(i, j));
                    if (!mMeta.hashMap.containsKey(hex))
                        mMap[i][j] = -1;
                    else
                        mMap[i][j] = getMapComponent(Long.valueOf(mMeta.hashMap.get(hex), 16));
                }

            mField = new Vector[mMap.length * 4][mMap[0].length * 4];
            for (int i = 0; i < mField.length; i++)
                for (int j = 0; j < mField[i].length; j++)
                    mField[i][j] = new Vector();
            for (int i = 0; i < mMap.length; i++)
                for (int j = 0; j < mMap[0].length; j++)
                    if (getMapComponent(mMap[i][j]) == 0)
                        for (int k = 0; k < mField.length; k++)
                            for (int l = 0; l < mField[0].length; l++)
                                mField[k][l].add(Vector.fromPolar(Math.atan2(l - (4 * j + 2), k - (4 * i + 2)), (getMapMetadata(mMap[i][j]) - 1) / (Math.pow(l - (4 * j + 2), 2) + Math.pow(k - (4 * i + 2), 2))), mField[k][l]);
        }
        else {
            for (int i = 0; i < mMap.length; i++)
                for (int j = 0; j < mMap[0].length; j++)
                    mMap[i][j] = getMapComponent(Long.valueOf(mMeta.hashMap.get(Integer.toHexString(im.getRGB(i, j))), 16));
        }
    }

    public void update(int delta, Player player) {
        iter(delta, player, false);
        mField[(int) player.mPos.mX][(int) player.mPos.mY].multiply(player.mCrg / player.mMass, player.mForce);
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
    public int getMapMetadata(long l) {
        return (int) l;
    }

    private void iter(int delta, Player player, boolean draw) {
        for (int x = 0; x < mMap.length; x++)
            for (int y = 0; y < mMap[x].length; y++) {
                long l = mMap[x][y];
                int id = getMapComponent(l);
                int meta = getMapMetadata(l);
                if (id > 0)
                    if (draw)
                        Tile.TILES[id].render(meta, x, y, this, player, delta);
                    else
                        Tile.TILES[id].update(meta, x, y, this, player, delta);
            }
    }

    /**
     * A class used to interpret the JSON metadata
     */
    private static class Metadata {
        /**
         * The horizontal resolution of the map
         */
        public int mapX;
        /**
         * The vertical resolution of the map
         */
        public int mapY;
        public Map<String, String> hashMap;
    }
}
