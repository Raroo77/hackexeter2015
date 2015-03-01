package net.utlabs.utgame;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Shaders {

    public static final File DIR_SHADER = new File(Game.DIR, "shader");
    //Maybe put in an array, dunno if worth it
    public static final File FL_VERTEX = new File(DIR_SHADER, "vert.glsl");
    public static final File FL_FRAGMENT = new File(DIR_SHADER, "frag.glsl");
    public static final int mVertexShader;
    public static final int mFragmentShader;
    public static final int mShaderProgram;

    static {
        mVertexShader = GL20.glCreateShader(GL20.GL_VERTEX_SHADER);
        mFragmentShader = GL20.glCreateShader(GL20.GL_FRAGMENT_SHADER);
        mShaderProgram = GL20.glCreateProgram();
    }

    /**
     * Dummy method to trigger static block and load and compile the shader program.
     */
    public static void init() throws Exception {
        //Attach Sources
        GL20.glShaderSource(mVertexShader, readFile(FL_VERTEX));
        GL20.glShaderSource(mFragmentShader, readFile(FL_FRAGMENT));
        //Compile
        GL20.glCompileShader(mVertexShader);
        if (GL20.glGetShaderi(mVertexShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
            throw createExceptionFromInfoLog(mVertexShader);
        GL20.glCompileShader(mFragmentShader);
        if (GL20.glGetShaderi(mFragmentShader, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
            throw createExceptionFromInfoLog(mFragmentShader);
        //Attach each shader to program
        GL20.glAttachShader(mShaderProgram, mVertexShader);
        GL20.glAttachShader(mShaderProgram, mFragmentShader);
    }

    public static void cleanup() {
        GL20.glDeleteProgram(mShaderProgram);
        GL20.glDeleteShader(mVertexShader);
        GL20.glDeleteShader(mFragmentShader);
    }

    private static String readFile(File f) throws IOException {
        Scanner scanner = new Scanner(f);
        String src = "";
        while (scanner.hasNextLine())
            src += scanner.nextLine();
        return src;
    }

    private static Exception createExceptionFromInfoLog(int shader) {
        String log = GL20.glGetShaderInfoLog(shader, GL20.glGetShaderi(shader, GL20.GL_INFO_LOG_LENGTH));
        return new Exception("GL SHADER " + log);
    }
}
