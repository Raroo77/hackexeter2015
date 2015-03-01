package net.utlabs.utgame.tiles;

/**
 * Created by mas.dicicco on 2/28/2015.
 * This tile starts the next level when a player reaches it
 */
public class TileEnd extends Tile {

    /**
     * The exit sometimes has to be opened by a switch
     */
    public boolean open=false;

    /**
     * Constructs an End Tile
     * @param id: ID number for wall
     */
    public TileEnd(int id){
        super(id);
    }

    /**
     * Updates
     * @param meta: Metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player player) {
        
    }

    /**
     * Draws based on openness
     * @param meta: metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void draw(int meta, int x, int y, Room room, Player player) {

    }
}
