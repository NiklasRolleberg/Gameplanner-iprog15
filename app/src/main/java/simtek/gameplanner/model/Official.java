package simtek.gameplanner.model;

/**
 * Created by Niklas on 2015-03-08.
 */
public class Official {
    private int id;
    private String name;
    private String age;
    private String resArea; //ev. int?
    private String licenseType;
    private String image;

    public Official(int id, String name, String age, String resArea, String licenseType, String image) {
        //System.out.println("I AM OFFICIAL!!!");
        this.id = id;
        this.name = name;
        this.age = age;
        this.resArea = resArea;
        this.licenseType = licenseType;
        this.image = image;
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

    public String getImage() { return image; }

    public String getAge() { return age; }
}
