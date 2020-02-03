public class Vector2
{
    public double x;
    public double y;

    public Vector2(double x, double)
    {
        this.x = x;
        this.y = y;
    }

    public Vector2()
    {
        this.x = 0;
        this.y = 0;
    }

    public Vector2(Vector2 source)
    {
        this.x = source.x;
        this.y = source.y;
    }
}
