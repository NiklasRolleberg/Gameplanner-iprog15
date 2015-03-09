package simtek.gameplanner.model;

import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private ArrayList<Game> games;
    private ArrayList<Official> officials;
    private ArrayList<Arena> arenas;
    private ArrayList<Team> teams;

    public Model() {
        System.out.println("MODEL LEVER!");
        games = new ArrayList<Game>();

        //Create officials
        officials = new ArrayList<Official>();
        for(int i=0;i<10;i++) {
            officials.add(new Official(i, "Official " + i, "Stockholm", "E"));
        }

        //create arenas
        arenas = new ArrayList<Arena>();
        for(int i=0;i<10;i++) {
            arenas.add(new Arena(i,"Arena " + i, "Location " + i ));
        }

        //create teams
        teams = new ArrayList<Team>();
        for(int i=0;i<10;i++) {
            teams.add(new Team(i, "Team "+i));
        }

        //create a game
        Game G = new Game(0,arenas.get(0),teams.get(0),teams.get(1),0,1,2,3,4);
        G.addOfficial(officials.get(0),0);
        G.addOfficial(officials.get(1),1);
        G.addOfficial(officials.get(2),2);
        games.add(G);
    }

    public ArrayList<Game> getGames() {
        return this.games;
    }

    public ArrayList<Official> getOfficials() {
        return this.officials;
    }

    public ArrayList<Arena> getArenas() {
        return this.arenas;
    }

    public void addGame(Game g) {
        games.add(g);
    }

    public void removeGame(Game g) {
        games.remove(g);
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }

}
