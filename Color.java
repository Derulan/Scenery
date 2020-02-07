public class Color
{
    public int r;
    public int g;
    public int b;

    public Color()
    {
        this.r = 0;
        this.g = 0;
        this.b = 0;
    }

    public Color(int r, int g, int b)
    {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color(Color color)
    {
        this.r = color.r;
        this.g = color.g;
        this.b = color.b;
    }

    public int value()
    {
        return ((((this.r << 8) + this.g) << 8) + this.b);
    }

    public static Color mul(Color color, double scale)
    {
        return new Color((int)(color.r * scale), (int)(color.g * scale), (int)(color.b * scale));
    }


}
