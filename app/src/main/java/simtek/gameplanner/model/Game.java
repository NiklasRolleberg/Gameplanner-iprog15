package simtek.gameplanner.model;

import java.util.ArrayList;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Game {

    private int id;
    private Arena arena;
    private ArrayList<Official> officials;

    private Team homeTeam;
    private Team awayTeam;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    public Game(int id, Arena arena, Team homeTeam, Team awayTeam, int year, int month, int day, int hour, int minute) {
        //System.out.println("I AM A GAME!");

        this.arena = arena;

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

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

    public int getYear() {
        return getYear();
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public Official getOfficial(int index) {
        return officials.get(index);
    }

    public void setOfficial(Official o, int index) {
        officials.set(index,o);
    }

    public Team getHomeTeam() {
        return this.homeTeam;
    }

    public Team getAwayTeam() {
        return this.awayTeam;
    }

    public void setHomeTeam(Team t) {
        homeTeam = t;
    }

    public void setAwayTeam(Team t) {
        awayTeam = t;
    }
}
