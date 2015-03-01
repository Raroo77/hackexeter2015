package net.utlabs.utgame;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;

//NOTE:
//THIS CLASS WAS MADE BEFORE HACK EXETER
public class Texture {

    public static boolean mGenerateMipMaps = true;
    public static int mPreferredMag = GL11.GL_LINEAR_MIPMAP_LINEAR;
    public static int mPreferredMin = GL11.GL_LINEAR_MIPMAP_LINEAR;
    private static final HashMap<String, Texture> TEXTURE_BANK = new HashMap<>();

    public static Texture getTexture(String texture) {
        return TEXTURE_BANK.get(texture);
    }

    public static void bindTexture(int id) {
        bindTexture(id, mPreferredMag, mPreferredMin);
    }

    public static void bindTexture(int id, int mag, int min) {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, mag);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, min);
        GL11.glTexEnvf(GL11.GL_TEXTURE_ENV, GL11.GL_TEXTURE_ENV_MODE, GL11.GL_MODULATE);
    }

    public static void unbindTexture() {
        bindTexture(0);
    }

    public static Texture loadTexture(File src) {
        try (FileInputStream in = new FileInputStream(src)) {
            BufferedImage image = ImageIO.read(in);
            boolean alpha = image.getColorModel().hasAlpha();
            ByteBuffer buffer = getImageData(image);
            int id = genTexture(GL11.GL_RGBA, image.getWidth(), image.getHeight(), alpha? GL11.GL_RGBA:GL11.GL_RGB, buffer);
            return TEXTURE_BANK.put(src.getName(), new Texture(id, image.getWidth(), image.getHeight()));
        } catch (Exception e) {
            new Exception("Problem loading texture: " + src, e).printStackTrace();
        }
        return null;
    }

    public static ByteBuffer getImageData(BufferedImage image) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
        boolean alpha = image.getColorModel().hasAlpha();
        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * (alpha? 4:3));
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) (pixel >> 16 & 0xFF));
                buffer.put((byte) (pixel >> 8 & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                if (alpha) {
                    buffer.put((byte) (pixel >> 24 & 0xFF));
                }
            }
        }
        buffer.flip();
        return buffer;
    }

    public static int genTexture(int internalFormat, int w, int h, int format, ByteBuffer buffer) {
        int id = GL11.glGenTextures();
        bindTexture(id);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalFormat, w, h, 0, format, GL11.GL_UNSIGNED_BYTE, buffer);
        if (mGenerateMipMaps) {
            GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        }
        unbindTexture();
        return id;
    }

    public final int mId;
    public final int mWidth;
    public final int mHeight;

    private Texture(int id, int width, int height) {
        mId = id;
        mWidth = width;
        mHeight = height;
    }

    public void drawCenteredModalRect(int ux, int uy, int uw, int uh, double x, double y, double z, double w, double h) {
        drawModalRect(ux, uy, uw, uh, x - w / 2, y + h / 2, z, w, h);
    }

    public void drawModalRect(int ux, int uy, int uw, int uh, double x, double y, double z, double w, double h) {
        double lx = (double) ux / mWidth;
        double rx = (double) (ux + uw) / mWidth;
        double ty = (double) uy / mHeight;
        double by = (double) (uy + uh) / mHeight;
        bindTexture(mId);
        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        GL11.glTexCoord2d(lx, by);
        GL11.glVertex3d(x, y - h, z);
        GL11.glTexCoord2d(rx, by);
        GL11.glVertex3d(x + w, y - h, z);
        GL11.glTexCoord2d(lx, ty);
        GL11.glVertex3d(x, y, z);
        GL11.glTexCoord2d(rx, ty);
        GL11.glVertex3d(x + w, y, z);
        GL11.glEnd();
        unbindTexture();
    }
}
