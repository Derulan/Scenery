public class Mesh
{
    public Vector3[] vertices;
    public Vector3[] normals;
    public Vector2[] textures;
    public int[][] triangles;
    public int subMeshCount = 1;

    public void SetTriangles(int[] triangles, int group)
    {
        this.triangles[group] = triangles;
    }

    public void Clear()
    {
        this.vertices = null;
        this.normals = null;
        this.textures = null;
        this.triangles = new int[subMeshCount][];
    }
}