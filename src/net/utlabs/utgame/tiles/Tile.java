package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;
import net.utlabs.utgame.Vector;

/**
 * Created by mas.dicicco on 2/28/2015
 * Abstract representation of all tiles which can exist in a room
 */
public abstract class Tile {
    public static final int SIZE = 60;

    /**
     * Assigns tiles integer values so we don't have to initialize like a thousand of them
     */
    public static Tile[] TILES = new Tile[] {
            new TileWall(0),
            new TileHazard(1),
            new TileTurret(2),
            new TileStart(3),
            new TileEnd(4),
            new TileSwitch(5),
            new TileGem(6)
    };

    /**
     * ID of the tile
     */
    public final int mId;

    /**
     * Constructs a Tile*
     *
     * @param id: ID number for a hazard
     */
    public Tile(int id) {
        mId = id;
    }

    /**
     * Updates
     *
     * @param meta: Metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    public abstract void update(int meta, int x, int y, Room room, Player player, int delta);

    /**
     * Sorry for this implementation, collisions are hard.
     * @param x
     * @param y
     * @param player
     * @return
     */

    public abstract boolean collision(int x, int y,Player player);

    /**
     *
     * @param r Takes values from (-pi, pi]
     * @return
     */
//    @Deprecated
//    public Vector collisionHelper(double r){
//        if(r>-Math.PI && r < -3*Math.PI/4){
//            return new Vector((float) -SIZE /2,(float) (-SIZE/2*Math.tan(Math.abs(Math.PI-Math.abs(r)))));
//        }
//        if(r>-Math.PI/4 && r<Math.PI/4){
//            return new Vector((float)SIZE/2, (float) (SIZE/2*Math.tan(r)));
//        }
//        if()
//    }

    /**
     * Draws
     *
     * @param meta: metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    public abstract void render(int meta, int x, int y, Room room, Player player, int delta);
}
