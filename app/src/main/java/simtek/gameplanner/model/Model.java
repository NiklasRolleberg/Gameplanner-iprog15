package simtek.gameplanner.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Observable;

public class Model extends Observable {

    private ArrayList<Game> games;

    public Model() {
        System.out.println("MODEL LEVER!");
        games = new ArrayList<Game>();
    }

    public ArrayList<Game> getGames() {
        return this.games;
    }

    public void addGame(Game g) {
        games.add(g);
    }

    public void removeGame(Game g) {
        games.remove(g);
    }

}
