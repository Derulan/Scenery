public class PointLight extends Light
{
    public PointLight()
    {
        this.color = new Color(255, 255, 255);
        this.intensity = 1.0;
    }

    public void Initialize()
    {
        this.sceneObject.scene.lights.add(this);
    }

    public Vector3 direction(Vector3 other)
    {
        return Vector3.mul(Vector3.sub(this.sceneObject.transform.position(), other).normalize(), -1d);
    }

    public void Update(){}
    public void Start(){}
}
