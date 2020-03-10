public class Material
{
    public Color color;

    public Class<? extends Shader> shader;

    public Material()
    {
        this.color = new Color(255, 255, 255);
    }

    public Material(Color color)
    {
        this.color = new Color(color);
    }

    public Material(Color color, Class<? extends Shader> shader)
    {
        this.color = new Color(color);
        this.shader = shader;
    }

    public Material(Material material)
    {
        this.color = new Color(material.color);
    }
}
