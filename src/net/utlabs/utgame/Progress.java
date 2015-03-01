package net.utlabs.utgame;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by raroo on 3/1/15.
 */
public class Progress {

    private static String[] TOTAL;

    public static void init() {
        File file = new File(Game.DIR, "map");
        File[] l = file.listFiles();
        if (l == null)
            throw new RuntimeException("Unable to load map names");
        TOTAL = new String[l.length / 2];
        for (int i = 0; i < TOTAL.length; i++)
            if (l[i].getName().endsWith(".png"))
                TOTAL[i] = l[i].getName().substring(0, l[i].getName().length() - 4);
    }

    public ArrayList<String> completion;

    public void add(Room r) {
        File f = new File(Room.DIR_MAP, r.mMapName);
        completion.add(f.getName().substring(0, f.getName().length() - 4));
    }
}
