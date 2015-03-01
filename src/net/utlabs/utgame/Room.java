package net.utlabs.utgame;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.utlabs.utgame.tiles.Tile;
import org.lwjgl.opengl.GL11;

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
    public static final int VECTOR_FIELD_RESOLUTION = 4;
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
    public Vector mSpawn;
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
                    String hex = Integer.toHexString(im.getRGB(i, j) & 0xFFFFFF);
                    if (!mMeta.hashMap.containsKey(hex))
                        mMap[i][j] = -1;
                    else {
                        long data = Long.valueOf(mMeta.hashMap.get(hex), 16);
                        if (data == 0x300000000L)
                            mSpawn = new Vector(i, j);
                        mMap[i][j] = getMapComponent(data);
                    }
                }

            mField = new Vector[mMap.length * VECTOR_FIELD_RESOLUTION][mMap[0].length * VECTOR_FIELD_RESOLUTION];
            for (int i = 0; i < mField.length; i++)
                for (int j = 0; j < mField[i].length; j++)
                    mField[i][j] = new Vector();
            for (int i = 0; i < mMap.length; i++)
                for (int j = 0; j < mMap[0].length; j++)
                    if (getMapComponent(mMap[i][j]) == 0)
                        for (int k = 0; k < mField.length; k++)
                            for (int l = 0; l < mField[0].length; l++) {
                                double theta = Math.atan2(l - (VECTOR_FIELD_RESOLUTION * j), k - (VECTOR_FIELD_RESOLUTION * i));
                                double mag = (getMapMetadata(mMap[i][j]) - 1) / (Math.pow(l - (VECTOR_FIELD_RESOLUTION * j), 2) + Math.pow(k - (VECTOR_FIELD_RESOLUTION * i), 2));
                                mField[k][l].add(Vector.fromPolar(theta, mag), mField[k][l]);
                            }
        }
        else {
            for (int i = 0; i < mMap.length; i++)
                for (int j = 0; j < mMap[0].length; j++)
                    mMap[i][j] = getMapComponent(Long.valueOf(mMeta.hashMap.get(Integer.toHexString(im.getRGB(i, j))), 16));
        }
    }

    public void update(int delta, Player player) {
        iter(delta, player, false);
        player.mForce.add(mField[pixelToVectorField(player.getPX())][pixelToVectorField(player.getPY())].multiply(player.mCrg, new Vector()), player.mForce);
    }

    public void render(int delta, Player player) {
        iter(delta, player, false);
        //int l = toPixelCoord((int) mSpawn.mX);
        //int r = toPixelCoord((int) (mSpawn.mX - 1));
        //int b = toPixelCoord((int) mSpawn.mY);
        //int t = toPixelCoord((int) (mSpawn.mY - 1));
        GL11.glBegin(GL11.GL_LINE_STRIP);
        for (int i = 0; i < mMap.length; i++)
            for (int j = 0; j < mMap[i].length; j++) {
                GL11.glVertex2i(toPixelCoord(i), toPixelCoord(j));
                GL11.glVertex2i(toPixelCoord(i + 1), toPixelCoord(j));
                GL11.glVertex2i(toPixelCoord(i), toPixelCoord(j + 1));
                GL11.glVertex2i(toPixelCoord(i + 1), toPixelCoord(j + 1));
            }

        GL11.glEnd();
        GL11.glBegin(GL11.GL_POINTS);
        for (int i = 0; i < mField.length; i++)
            for (int j = 0; j < mField[i].length; j++)
                GL11.glVertex2i(vectorFieldToPixel(i + .5f), vectorFieldToPixel(j + 0.5f));
        GL11.glEnd();
        //GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        //GL11.glVertex2i(l, b);
        //GL11.glVertex2i(r, b);
        //GL11.glVertex2i(l, t);
        //GL11.glVertex2i(r, t);
        //GL11.glEnd();
    }

    public int toPixelCoord(float gridCoord) {
        return (int) ((gridCoord) * 60);
    }

    public int pixelToGridCoord(int pixelCoord) {
        return pixelCoord / 60;
    }

    public int vectorFieldToPixel(float fieldCoord) {
        return (int) (fieldCoord / 4F * 60);
    }

    public int pixelToVectorField(int pixelCoord) {
        return (int) (pixelCoord / 60F * VECTOR_FIELD_RESOLUTION);
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
