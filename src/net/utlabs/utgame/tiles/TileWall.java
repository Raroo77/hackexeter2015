package net.utlabs.utgame.tiles;

import net.utlabs.utgame.Player;
import net.utlabs.utgame.Room;
import org.lwjgl.opengl.GL11;

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
     *
     * @param id: ID number for wall
     */
    public TileWall(int id) {
        super(id);
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
     * Does nothing
     *
     * @param meta: Metadata for the tile
     * @param x:    x coordinate
     * @param y:    y coordinate
     * @param room: The room the tile is in
     */
    @Override
    public void update(int meta, int x, int y, Room room, Player player, int delta) {
        if(collision(x,y,player)){
            double theta = Math.toDegrees(Math.atan2((y - player.mPos.mY), (x - player.mPos.mX)));
            if((theta < 45 && theta > -45) || (theta > 135 || theta < -135))
                player.mVelocity.mX*=-1;
            else if ((theta > 45 && theta < 135)|| (theta < -45 || theta > -135))
                player.mVelocity.mY*=-1;
            else if(theta==45 || theta == 135 || theta == -135 || theta == -45)
                player.mVelocity.multiply(-1,player.mVelocity);
        }
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
        if (meta == 0)
            GL11.glColor3f(.5F, .5F, .5F);
        if (meta == 1)
            GL11.glColor3f(1, .5F, 0);
        if (meta == 2)
            GL11.glColor3f(0, 0, 1);
        GL11.glRectf(x * 60 - 30, y * 60 - 30, x * 60 + 30, y * 60 + 30);
    }
}
