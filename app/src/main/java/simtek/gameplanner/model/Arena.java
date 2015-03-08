package simtek.gameplanner.model;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Arena {

    int id;
    private String name;
    private String location;

    public Arena(int id, String name, String location) {
        System.out.println("arena");
        this.name = name;
        this.location = location;
    }

    public int getID() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getLocation() {
        return this.location;
    }
}
