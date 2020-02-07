public abstract class Renderer extends Component
{
    public Material material;

    public abstract Intersection hit(Ray ray);
}
