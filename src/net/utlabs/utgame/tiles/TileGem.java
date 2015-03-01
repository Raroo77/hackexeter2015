package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;

/**
 * Created by mas.dicicco on 2/28/2015.
 * This tile is a gem
 */
public class TileGem extends Tile {

    public int mPoints = 1;

    /**
     * Constructs a gem
     *
     * @param id: ID number
     */
    public TileGem(int id) {
        super(id);
    }

    /**
     * Gives a player points
     *
     * @param meta: Metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player player, int delta) {

    }

    /**
     * Draw
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
