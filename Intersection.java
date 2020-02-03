public class Intersection
{
    public double t;
    public Vector3 location;
    public Vector3 normal;

    public Intersection(double t, Vector3 location, Vector3 normal)
    {
        this.t = t;
        this.location = new Vector3(location);
        this.normal = new Vector3(normal);
    }
}
