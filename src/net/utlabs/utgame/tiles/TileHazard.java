package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;

/**
 * Created by mas.dicicco on 2/28/2015.
 * Classic hazard block, like spikes or whatever
 */
public class TileHazard extends Tile {

    /**
     * Constructs a Hazard*
     *
     * @param id: ID number for a hazard
     */
    public TileHazard(int id) {
        super(id);
    }

    /**
     * Does nothing
     *
     * @param meta:      Metadata for the tile
     * @param x:         x coordinate
     * @param y:         y coordinate
     * @param room:      The room the tile is in
     * @param thePLayer: The player in the same room
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player thePLayer, int delta) {

    }

    /**
     * Draws
     *
     * @param meta: metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void draw(int meta, int x, int y, Room room, Player player, int delta) {

    }
}
