package simtek.gameplanner.model;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Arena {

    int id;
    private String name;
    private String location;
    private int rentCost;
    private int capacity;


    public Arena(int id, String name, String location) {
        System.out.println("arena");
        this.name = name;
        this.location = location;
        this.rentCost = 5000;
        this.capacity = 1000;
    }
    public int getRentCost(){
        return rentCost;
    }

    public int getCapacity(){
        return capacity;
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
