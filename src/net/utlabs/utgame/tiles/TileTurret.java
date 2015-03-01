package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;

/**
 * Created by mas.dicicco on 2/28/2015.
 * Planned but unused tile
 */
@Deprecated
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
    public void update(int meta, int x, int y, Room room, Player player, int delta) {
        //TODO AIM AT PLAYER AND FIRE
    }

    public boolean collision(int x, int y, Player player){
        if(Math.pow(x*60-30-player.mPos.mX*50,2)+Math.pow(y*60-30-player.mPos.mY*50,2)<=900
                || Math.pow(x*60-30-player.mPos.mX*50,2)+Math.pow(y*60+30-player.mPos.mY*50,2)<=900
                || Math.pow(x*60+30-player.mPos.mX*50,2)+Math.pow(y*60-30-player.mPos.mY*50,2)<=900
                || Math.pow(x*60+30-player.mPos.mX*50,2)+Math.pow(y*60+30-player.mPos.mY*50,2)<=900
                || Math.pow(x*60-player.mPos.mX*50,2)+Math.pow(y*60-30-player.mPos.mY*50,2)<=900
                || Math.pow(x*60-player.mPos.mX*50,2)+Math.pow(y*60+30-player.mPos.mY*50,2)<=900
                || Math.pow(x*60-30-player.mPos.mX*50,2)+Math.pow(y*60-player.mPos.mY*50,2)<=900
                || Math.pow(x*60+30-player.mPos.mX*50,2)+Math.pow(y*60-player.mPos.mY*50,2)<=900){
            return true;
        }
        else return false;
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
    public void render(int meta, int x, int y, Room room, Player player, int delta) {

    }
}
