package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;
import net.utlabs.utgame.ui.UiGame;
import net.utlabs.utgame.ui.UiMain;
import net.utlabs.utgame.ui.UiProgress;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

/**
 * Created by mas.dicicco on 2/28/2015.
 * This tile starts the next level when a player reaches it
 */
public class TileEnd extends Tile {

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
        if (collision(x,y,player)) {
            room.mGame.changeMenu(new UiProgress(room.mGame.mUi.mParent, "Level02"));
        }
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
     * Draws based on openness
     *
     * @param meta: metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void render(int meta, int x, int y, Room room, Player player, int delta) {
        GL11.glColor3f(0, 1, 0);
        GL11.glRectf(x*60-30, y*60-30, x*60+30, y*60+30);

    }
}
