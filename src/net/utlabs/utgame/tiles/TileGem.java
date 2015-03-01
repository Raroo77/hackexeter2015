package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;
import net.utlabs.utgame.Texture;

/**
 * Created by mas.dicicco on 2/28/2015.
 * This tile is a gem
 */
public class TileGem extends Tile {
    Texture gemSprite = Texture.getTexture("Gem.png");

    public int mPoints = 1;

    /**
     * Constructs a gem
     *
     * @param id: ID number
     */
    public TileGem(int id) {
        super(id);
    }
    public boolean collision(int x, int y, Player player){
        if(Math.pow(x*60-player.mPos.mX*50,2)+Math.pow(y*60-player.mPos.mY*50,2)<=600){
            return true;
        }
        else return false;
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
        if (collision(x,y,player) && room.getMapMetadata(room.mMap[x][y])!=1){
            room.mMap[x][y]=0x6000000001l;
        }
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
    public void render(int meta, int x, int y, Room room, Player player, int delta) {
        if(room.getMapMetadata(room.mMap[x][y])!=1){
            gemSprite.drawCenteredModalRect(0,0,gemSprite.mWidth,gemSprite.mHeight,x,y,0,50,50);
        }
    }
}
