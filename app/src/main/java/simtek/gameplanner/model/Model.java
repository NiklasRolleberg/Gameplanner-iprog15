package simtek.gameplanner.model;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Observable;

import simtek.gameplanner.R;

public class Model extends Observable {

    private ArrayList<Game> games;
    private ArrayList<Official> officials;
    private ArrayList<Arena> arenas;
    private ArrayList<Team> teams;

    public int nrGames = 0;

    public Model() {
        System.out.println("MODEL LEVER!");
        games = new ArrayList<Game>();

        //Create officials
        officials = new ArrayList<Official>();

        officials.add(new Official(1, "Pelle svanslös","Stockholm", "S"));
        officials.add(new Official(2, "Bengt-Eric Svensson","Luleå", "E"));
        officials.add(new Official(3, "Stig Ovesson","Gothenburg", "S"));
        officials.add(new Official(4, "Karl-Evert Karlsson","London", "J"));
        officials.add(new Official(5, "Jan Ivarsson","London", "E"));
        officials.add(new Official(6, "Jean-Pierre Baguette","London", "J"));
        officials.add(new Official(7, "Bert-Ola Surströmming","London", "S"));

        //create arenas
        arenas = new ArrayList<Arena>();
        arenas.add(new Arena(1,"Colosseum", "Rome", "colosseum"));
        arenas.add(new Arena(2,"Madison Square Garden", "London" ,"madisonsquaregarden"));
        arenas.add(new Arena(3,"Ericsson Globe", "Stockholm","ericssonglobe"));
        arenas.add(new Arena(4,"Scandinavium", "Gothenburg","scandinavium"));
        arenas.add(new Arena(5,"Göransson Arena", "Sandviken","goranssonarena"));
        arenas.add(new Arena(6,"Friends Arena", "Solna","friendsarena"));


        /*
        for(int i=0;i<10;i++) {
            arenas.add(new Arena(i,"Arena " + i, "Location " + i ));
        }*/

        //create teams
        teams = new ArrayList<Team>();

        teams.add(new Team(1, "Flyg"));
        teams.add(new Team(2, "Data"));
        teams.add(new Team(3, "Elektro"));
        teams.add(new Team(4, "Sam"));
        teams.add(new Team(5, "Media"));
        teams.add(new Team(5, "Kemi"));
        teams.add(new Team(6, "Maskin"));
        teams.add(new Team(7, "CL"));
        teams.add(new Team(8, "Energi och miljö"));
        teams.add(new Team(9, "Bergs"));



        /*
        for(int i=0;i<10;i++) {
            teams.add(new Team(i, "Team "+i));
        }*/

        //create a game
        Game G = new Game(0,arenas.get(0),teams.get(0),teams.get(1),2015,1,2,3,4);
        G.addOfficial(officials.get(0),0);
        G.addOfficial(officials.get(1),1);
        G.addOfficial(officials.get(2),2);
        games.add(G);
        nrGames ++;
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

    public Game getGame(int id) {
        for (int i=0;i<games.size();i++) {
            if(games.get(i).getId() == id) {
                return games.get(i);
            }
        }
        return null;
    }

    public Official getOfficial(int id) {
        for (int i=0;i<officials.size();i++) {
            if(officials.get(i).getId() == id) {
                return officials.get(i);
            }
        }
        return null;
    }

    public Arena getArena(int id) {
        for (int i=0;i<arenas.size();i++) {
            if(arenas.get(i).getId() == id) {
                return arenas.get(i);
            }
        }
        return null;
    }

    public void addGame(Game g) {
        //System.out.println("GAME ID, FOOL!!!" + g.getId());

        games.add(g);
    }

    public void removeGame(Game g) {
        games.remove(g);
    }

    public ArrayList<Team> getTeams() {
        return this.teams;
    }

}
