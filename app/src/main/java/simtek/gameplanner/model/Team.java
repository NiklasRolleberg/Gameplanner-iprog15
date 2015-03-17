package simtek.gameplanner.model;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Team {

    private int id;
    private String name;

    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
