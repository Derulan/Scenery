public class WierdSkyboxShader extends Shader
{
    public static Color frag(Intersection intersection, final Light[] lights, double ambience)
    {
        return new Color(ColorSin(intersection.location.x*intersection.location.y-intersection.location.x*intersection.location.x), ColorSin(intersection.location.x*intersection.location.y), ColorSin(intersection.location.x*intersection.location.y-intersection.location.y*intersection.location.y));
    }

    private static int ColorSin(double x)
    {
        return (int)(45.0*((Math.sin(x*0.0001)+1)/2.0));
    }
}