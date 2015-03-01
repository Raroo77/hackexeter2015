package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;

/**
 * Created by mas.dicicco on 2/28/2015.
 * This tile spawns the player on it
 */
public class TileStart extends Tile {

    /**
     * Constructs Tile* 
     * @param id: ID number
     */
    public TileStart(int id){
        super(id);
    }

    /**
     * Spawns the player
     * @param meta: Metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player player) {
        
    }

    /**
     * Invisible
     * @param meta: metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void draw(int meta, int x, int y, Room room, Player player) {

    }
}
