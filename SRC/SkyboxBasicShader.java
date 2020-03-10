public class SkyboxBasicShader extends Shader
{
    public static Color frag(Intersection intersection, final Light[] lights, double ambience)
    {
        return intersection.source.material.color;
    }
}