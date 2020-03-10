public abstract class Renderer extends Component
{
    public static final double discriminant = 10E-10;

    public Material material;

    public abstract Intersection hit(Ray ray);
}
