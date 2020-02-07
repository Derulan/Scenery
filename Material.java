import java.awt.*;

public class Material
{
    public Color color;

    public Material()
    {
        this.color = new Color(255, 255, 255);
    }

    public Material(Color color)
    {
        this.color = new Color(color);
    }

    public Material(Material material)
    {
        this.color = new Color(material.color);
    }
}
