package simtek.gameplanner.model;

import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Arena {

    private int id;
    private String name;
    private String location;
    private int rentCost;
    private int capacity;
    private String imageName;
    private int maxTicketPrice;


    public Arena(int id, String name, String location, String imageName) {
        //System.out.println("arena");
        this.id = id;
        this.name = name;
        this.location = location;
        this.capacity = 600+(int) (Math.random()*2000);
        this.rentCost = 4000+ (int) (Math.random()*capacity);
        this.imageName = imageName;
        this.maxTicketPrice = 10 + (int)(Math.random()*10);


    }

    public int getId() {
        return this.id;
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

    public String getImageName() {
        return this.imageName;
    }

    public int getmaxTicketPrice(){
        return maxTicketPrice;
    }
}
