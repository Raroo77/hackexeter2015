package net.utlabs.utgame;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by raroo on 3/1/15.
 */
public class Progress {

    final static public String[] TOTAL;
    public ArrayList<String> completion;

    static {
        File file = new File(Game.DIR, "map");
        File[] l = file.listFiles();
        String[] r = new String[l.length / 2];
        for (int i = 0; i < l.length; i++)
            if (l[i].getName().endsWith(".png"))
                r[i] = l[i].getName().substring(0, l[i].getName().length() - 4);
        TOTAL = r;
    }

    public void init() {

    }

    public void add(Room r) {
        File f = new File(Room.DIR_MAP, r.mMapName);
        completion.add(f.getName().substring(0, f.getName().length() - 4));
    }
}
