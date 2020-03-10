public class Transform
{
    private Vector3 position;

    private Vector3 scale;

    public Transform()
    {
        this.position = new Vector3();
        this.scale = new Vector3(1, 1, 1);
    }

    public Transform(Vector3 position, Vector3 scale)
    {
        this.position = new Vector3(position);
        this.scale = new Vector3(scale);
    }

    public void moveTo(Vector3 position)
    {
        this.position = new Vector3(position);
    }

    public void moveBy(Vector3 offset)
    {
        this.position = Vector3.add(this.position, offset);
    }

    public void scaleTo(Vector3 scale)
    {
        this.scale = new Vector3(scale);
    }

    public void scaleBy(Vector3 factor)
    {
        this.scale = Vector3.mul(this.scale, factor);
    }

    public Vector3 position()
    {
        return new Vector3(position);
    }

    public Vector3 scale()
    {
        return new Vector3(scale);
    }
}