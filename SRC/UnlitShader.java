public class UnlitShader extends Shader
{
    public static Color frag(Intersection intersection, Light[] lights, double ambience)
    {
        return intersection.source.material.color;
    }
}