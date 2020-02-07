public class Intersection implements Comparable<Intersection>
{
    public double t;
    public Vector3 location;
    public Vector3 normal;
    public Renderer source;

    public Intersection(double t, Vector3 location, Vector3 normal, Renderer source)
    {
        this.t = t;
        this.location = new Vector3(location);
        this.normal = new Vector3(normal);
        this.source = source;
    }

    @Override
    public int compareTo(Intersection other)
    {
        return Double.compare(this.t, other.t);
    }
}
