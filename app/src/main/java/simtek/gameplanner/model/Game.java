package simtek.gameplanner.model;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Game {

    private int id;
    private Arena arena;
    private Official[] officials;
    private int visitors;
    private int turnout;
    private int ticketPrice;
    private int rentCost;


    private Team homeTeam;
    private Team awayTeam;

    private int year;
    private int month;
    private int day;

    private int hour;
    private int minute;

    public Game(int id, Arena arena, Team homeTeam, Team awayTeam, int year, int month, int day, int hour, int minute) {
        //System.out.println("I AM A GAME!");
        this.id = id;
        this.arena = arena;

        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;

        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;

        officials  = new Official[5];
    }
    public int getTicketPrice(){
        return ticketPrice;
    }
    public int getRentCost(){
        return rentCost;
    }
    public int getVisitors(){
        return visitors;
    }
    public int getTurnout(){
        return turnout;
    }
    public void setTicketPrice(int inPrice){
        ticketPrice = inPrice;
    }
    public void setVisitors(int inVisitors){
        visitors = inVisitors;
    }
    public void setTurnout(int inTurnout){
        turnout = inTurnout;
    }

    public int getId() {
        return this.id;
    }

    public void addOfficial(Official o, int index) {
        officials[index] = o;
    }

    public int getYear() {
        return year;
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
        return officials[index];
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

    public Arena getArena() {
        return this.arena;
    }

    public void setArena(Arena inArena){
        this.arena = inArena;
    }

    public int getNrOfOfficials() {
        int nr = 0;
        for(int i=0;i<5;i++)
            if (officials[i] != null) {
                nr++;
            }
        return nr;
    }
}
