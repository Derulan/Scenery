public class DirectionalLight extends Light
{
    public Vector3 direction;

    public DirectionalLight()
    {
        this.direction = new Vector3(0.0, -1.0, 0.0);
        this.color = new Color(255, 255, 255);
        this.intensity = 1.0;
    }

    public void Initialize()
    {
        this.sceneObject.scene.lights.add(this);
    }

    public Vector3 direction(Vector3 other)
    {
        return direction.normalize();
    }

    public void Update(){}
    public void Start(){}
    
}
