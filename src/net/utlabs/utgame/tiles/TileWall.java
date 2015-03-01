package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Tile;

/**
 * Created by mas.dicicco on 2/28/2015.
 * The component of a map that is a wall that can hold charge and exerts a force on the player
 */
public class TileWall extends Tile {

    /**
     * charge can be positive (1), negative(-1), and neutral (0)
     */
    public int mCharge;

    /**
     * Constructs a wall
     * @param id: ID number for wall
     */
    public TileWall(int id){
        super(id);
    }

    /**
     * Does nothing 
     * @param meta: Metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player thePlayer) {
        
    }

    /**
     * Draws and colors based on charge
     * @param meta: metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void draw(int meta, int x, int y, Room room) {
        
    }
}
