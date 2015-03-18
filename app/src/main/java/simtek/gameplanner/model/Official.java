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

    private float rating;
    private int xp;
    private String prefPos;

    public Official(int id, String name, String age, String resArea, String licenseType, String image) {
        //System.out.println("I AM OFFICIAL!!!");
        this.id = id;
        this.name = name;
        this.age = age;
        this.resArea = resArea;
        this.licenseType = licenseType;
        this.image = image;

        double xpe = (Math.random()*0.5) * Integer.parseInt(age);
        this.xp = (int) xpe;

        this.rating = (float) (Math.random() * 5);

        String[] pos = {"R","U","HL","L","BJ","BJ"};
        this.prefPos = pos[(int) Math.random() * 5];
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

    public int getYearsOfXp() {
        return this.xp;
    }

    public String getPrefPos() {
        return this.prefPos;
    }

    public float getRating() {
        return this.rating;
    }
}
