public abstract class Light extends Component
{
    public Color color;

    public double intensity;

    public abstract Vector3 direction(Vector3 other);
}