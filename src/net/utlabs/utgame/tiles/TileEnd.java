package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;

/**
 * Created by mas.dicicco on 2/28/2015.
 * This tile starts the next level when a player reaches it
 */
public class TileEnd extends Tile {

    /**
     * The exit sometimes has to be opened by a switch
     */
    public boolean mOpen = false;

    /**
     * Constructs an End Tile
     *
     * @param id: ID number for wall
     */
    public TileEnd(int id) {
        super(id);
    }

    /**
     * Updates
     *
     * @param meta: Metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player player, int delta) {
        if (Math.abs(player.mPos.mX - 4 * x) <= 4 && Math.abs(player.mPos.mY - 4 * x) <= 4 && mOpen) {
            //TODO NEXT ROOM
        }
    }

    /**
     * Draws based on openness
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
