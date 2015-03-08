package simtek.gameplanner.model;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Official {
    private int id;
    private String name;

    public Official(int id, String name) {
        //System.out.println("I AM OFFICIAL!!!");
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
