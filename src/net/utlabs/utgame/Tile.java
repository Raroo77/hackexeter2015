package net.utlabs.utgame;

import net.utlabs.utgame.tiles.TileHazard;
import net.utlabs.utgame.tiles.TileTurret;
import net.utlabs.utgame.tiles.TileWall;

/**
 * Created by mas.dicicco on 2/28/2015.
 * Abstract representation of anything that can be in a map besides a Player
 * Integers are used to identify tiles instead of initializing like a thousand of them
 */

public abstract class Tile {

    /**
     * List of all tiles and their identifying number
     */
    public static Tile[] mComponents = new Tile[] {
            new TileWall(0), new TileHazard(1), new TileTurret(2)
    };

    /**
     * ID of component
     */
    private final int mId;

    /**
     * The map component is identified by the identification number 
     * @param id
     */
    public Tile(int id) {
        mId = id;
    }

    /**
     * Does nothing 
     * @param meta: Metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     * @param thePLayer: The player in the same room
     */
    public abstract void update(int meta, int x, int y, Room room, Player thePLayer);

    /**
     * Draws
     * @param meta: metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    public abstract void draw(int meta, int x, int y, Room room);
    
}
