package simtek.gameplanner.model;

import java.util.ArrayList;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Game {

    private int id;
    private Arena arena;
    private ArrayList<Official> officials;

    public Game() {
        System.out.println("I AM A GAME!");
        officials = new ArrayList<Official>();
    }

    public int getId() {
        return this.id;
    }

    public void addOfficial(Official o) {
        officials.add(o);
    }

    public Official getOfficial(int index) {
        return officials.get(index);
    }
}
