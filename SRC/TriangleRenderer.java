public class TriangleRenderer extends Renderer
{
    public Vector3 vertex1;
    public Vector3 vertex2;
    public Vector3 vertex3;

    public TriangleRenderer(Vector3 vertex1, Vector3 vertex2, Vector3 vertex3, Material material)
    {
        this.vertex1 = new Vector3(vertex1);
        this.vertex2 = new Vector3(vertex2);
        this.vertex3 = new Vector3(vertex3);
        this.material = material;
    }

    public TriangleRenderer()
    {
        this.vertex1 = new Vector3();
        this.vertex2 = new Vector3();
        this.vertex3 = new Vector3();
        this.material = new Material(new Color(255, 255, 255));
    }

    public Intersection hit(Ray ray)
    {   
        Vector3 sceneObjectPosition = this.sceneObject.transform.position();
        Vector3 sceneObjectScale = this.sceneObject.transform.scale();
        Vector3 v1 = Vector3.add(Vector3.mul(this.vertex1, sceneObjectScale), sceneObjectPosition);
        Vector3 v2 = Vector3.add(Vector3.mul(this.vertex2, sceneObjectScale), sceneObjectPosition);
        Vector3 v3 = Vector3.add(Vector3.mul(this.vertex3, sceneObjectScale), sceneObjectPosition);
        Vector3 edge1 = Vector3.sub(v2, v1);
        Vector3 edge2 = Vector3.sub(v3, v1);
        Vector3 cross = Vector3.crs(ray.direction, edge2);

        double eval1 = Vector3.dot(edge1, cross);
        if(eval1 > -Renderer.discriminant && eval1 < Renderer.discriminant) return null;

        Vector3 moller = Vector3.sub(ray.origin, v1);
        double eval2 = ((1.0 / eval1) * Vector3.dot(moller, cross));
        if(eval2 < 0.0 || eval2 > 1.0) return null;

        Vector3 trumbore = Vector3.crs(moller, edge1);
        double eval3 = ((1.0 / eval1) * Vector3.dot(ray.direction, trumbore));
        if(eval3 < 0.0 || (eval2 + eval3) > 1.0) return null;

        double t = ((1.0 / eval1) * Vector3.dot(edge2, trumbore));
        if(t <= Renderer.discriminant) return null;

        Vector3 location = Vector3.add(ray.origin, Vector3.mul(ray.direction, t));
        Vector3 normal = Vector3.crs(edge1, edge2).normalize();

        return new Intersection(t, location, normal, this);

    }

    public String toString()
    {
        return ("Triangle(Vertex 1 = "+vertex1+", Vertex 2 = "+vertex2+"+Vertex 3 = "+vertex3+")");
    }

    public void Update(){}
    public void Start(){}
    public void Initialize(){}
}
