package net.utlabs.utgame.tiles;

/**
 * Created by mas.dicicco on 2/28/2015.
 * This tile opens the exit and can affect other tiles
 */
public class TileSwitch extends Tile {

    public boolean mToggled=false;
    /**
     * Constructs a Switch
     * @param id: ID number
     */
    public TileSwitch(int id){
        super(id);
    }

    /**
     * Opens the exit and/or modifies a tile
     * @param meta: Metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player player) {
        
    }

    /**
     * Drawn based on on-ness
     * @param meta: metadata for the tile
     * @param x: x coordinate
     * @param y: y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void draw(int meta, int x, int y, Room room, Player player) {

    }
}
