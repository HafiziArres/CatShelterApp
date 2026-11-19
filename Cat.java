public class Cat
{
    private String id;
    private String name;
    private String breed;
    private int age;            // in years
    private double weight;      // in kg
    private double adoptionFee; // in RM

    public Cat(String id, String name, String breed, int age, double weight, double adoptionFee)
    {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.weight = weight;
        this.adoptionFee = adoptionFee;
    }

    public String getId()          { return id; }
    public String getName()        { return name; }
    public String getBreed()       { return breed; }
    public int getAge()            { return age; }
    public double getWeight()      { return weight; }
    public double getAdoptionFee() { return adoptionFee; }

    // How one cat is shown on the screen
    public String toString()
    {
        return id + " | " + name + " | " + breed + " | " + age + " yrs | "
                + weight + " kg | RM " + adoptionFee;
    }
}
