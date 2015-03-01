package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;

/**
 * Created by mas.dicicco on 2/28/2015.
 */
public class TileTurret extends Tile {

    /**
     * Constructs a Turret
     *
     * @param id: ID number for wall
     */
    public TileTurret(int id) {
        super(id);
    }

    /**
     * Aims at player and fires
     *
     * @param meta: Metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player thePlayer) {
        //TODO AIM AT PLAYER AND FIRE
    }

    /**
     * Draws and colors based on charge
     *
     * @param meta: metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void draw(int meta, int x, int y, Room room, Player player) {

    }
}
