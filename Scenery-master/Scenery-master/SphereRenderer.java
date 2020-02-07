public class SphereRenderer extends Renderer
{
    public Vector3 center;
    public double radius;

    public SphereRenderer(Vector3 center, double radius, Material material)
    {
        this.center = new Vector3(center);
        this.radius = radius;
        this.material = material;
    }

    public SphereRenderer()
    {
        this.center = new Vector3();
        this.radius = 1.0;
        this.material = new Material();
    }

    public Intersection hit(Ray ray)
    {
        Vector3 centerWorld = Vector3.add(this.sceneObject.transform, this.center);
        double a = Vector3.dot(ray.direction, ray.direction);
        double b = (2*(Vector3.dot(Vector3.sub(ray.origin, centerWorld), ray.direction)));
        double c = Vector3.dot(Vector3.sub(ray.origin, centerWorld), Vector3.sub(ray.origin, centerWorld)) - (radius*radius);
        double d = (b*b)-(4*a*c);
        double t;
        if(d >= 0.0)
        {
            t = ((-b - Math.sqrt(d))/(2*a));
            if(t > (10E-9))
            {
                Vector3 location = Vector3.add(ray.origin, Vector3.mul(ray.direction, t));
                Vector3 normal = Vector3.sub(location, centerWorld).normalize();
                return new Intersection(t, location, normal, this);
            }
        }
        return null;
    }

    public String toString()
    {
        return ("Sphere(Center = "+center+", Radius = "+radius+")");
    }

    public void Update(){}
    public void Start(){}
}
