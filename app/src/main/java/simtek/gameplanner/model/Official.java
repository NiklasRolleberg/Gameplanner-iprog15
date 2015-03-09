package simtek.gameplanner.model;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Official {
    private int id;
    private String name;
    private String resArea; //ev. int?
    private String licenseType;


    public Official(int id, String name, String resArea, String licenseType) {
        //System.out.println("I AM OFFICIAL!!!");
        this.id = id;
        this.name = name;
        this.resArea = resArea;
        this.licenseType = licenseType;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getResArea()
    {
        return this.resArea;
    }

    public String getLicenseType()
    {
        return licenseType;
    }


}
