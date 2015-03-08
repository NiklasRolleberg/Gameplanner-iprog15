package simtek.gameplanner.model;

import java.util.ArrayList;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Game {

    private int id;
    private Arena arena;
    private ArrayList<Official> officials;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    public Game(Arena arena, int year, int month, int day, int hour, int minute) {
        //System.out.println("I AM A GAME!");

        this.arena = arena;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;

        officials = new ArrayList<Official>(5);
    }

    public int getId() {
        return this.id;
    }

    public void addOfficial(Official o, int index) {
        officials.set(index, o);
    }

    public Official getOfficial(int index) {
        return officials.get(index);
    }
}
