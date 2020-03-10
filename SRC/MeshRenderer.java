import java.util.ArrayList;
import java.util.Collections;

public class MeshRenderer extends Renderer
{
    public Mesh mesh;

    public Material[] materials;

    private class TGPair implements Comparable<TGPair>
    {
        public double t;

        public int group;

        public TGPair(double t, int group)
        {
            this.t = t;
            this.group = group;
        }

        @Override
        public int compareTo(TGPair other)
        {
            return Double.compare(this.t, other.t);
        }
    }

    public Intersection hit(Ray ray)
    {
        Vector3 sceneObjectPosition = this.sceneObject.transform.position();
        Vector3 sceneObjectScale = this.sceneObject.transform.scale();
        ArrayList<Intersection> intersections = new ArrayList<Intersection>();
        ArrayList<TGPair> tgpairs = new ArrayList<TGPair>();
        for(int i = 0; i < this.mesh.triangles.length; i++)
        {
            int[] group = this.mesh.triangles[i];
            for(int j = 0; j < group.length; j += 3)
            {
                Vector3 v1 = Vector3.add(Vector3.mul(this.mesh.vertices[group[j]], sceneObjectScale), sceneObjectPosition);
                Vector3 v2 = Vector3.add(Vector3.mul(this.mesh.vertices[group[j + 1]], sceneObjectScale), sceneObjectPosition);
                Vector3 v3 = Vector3.add(Vector3.mul(this.mesh.vertices[group[j + 2]], sceneObjectScale), sceneObjectPosition);
                Vector3 edge1 = Vector3.sub(v2, v1);
                Vector3 edge2 = Vector3.sub(v3, v1);
                Vector3 cross = Vector3.crs(ray.direction, edge2);
        
                double eval1 = Vector3.dot(edge1, cross);
                if(eval1 > -Renderer.discriminant && eval1 < Renderer.discriminant) continue;
        
                Vector3 moller = Vector3.sub(ray.origin, v1);
                double eval2 = ((1.0 / eval1) * Vector3.dot(moller, cross));
                if(eval2 < 0.0 || eval2 > 1.0) continue;
        
                Vector3 trumbore = Vector3.crs(moller, edge1);
                double eval3 = ((1.0 / eval1) * Vector3.dot(ray.direction, trumbore));
                if(eval3 < 0.0 || (eval2 + eval3) > 1.0) continue;
        
                double t = ((1.0 / eval1) * Vector3.dot(edge2, trumbore));
                if(t <= Renderer.discriminant) continue;
        
                Vector3 location = Vector3.add(ray.origin, Vector3.mul(ray.direction, t));
                Vector3 normal = Vector3.crs(edge1, edge2).normalize();
                
                intersections.add(new Intersection(t, location, normal, this));
                tgpairs.add(new TGPair(t, i));
            }
        }
        if(intersections.size() > 0)
        {
            Collections.sort(intersections);
            Collections.sort(tgpairs);
            this.material = this.materials[tgpairs.get(0).group];
            return intersections.get(0);
        }
        return null;
    }

    public void Update(){}
    public void Start(){}
    public void Initialize(){}
}