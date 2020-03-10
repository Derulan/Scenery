public class LambertianShader extends Shader
{
    public static Color frag(Intersection intersection, Light[] lights, double ambience)
    {
        double value = ambience;
        for(int i = 0; i < lights.length; i++)
        {
            Vector3 direction = lights[i].direction(intersection.location);
            double oneValue = Vector3.dot(Vector3.mul(direction, -1.0), intersection.normal);
            oneValue *= lights[i].intensity;
            if(oneValue < 0.0) oneValue = 0.0;
            if(oneValue > 1.0) oneValue = 1.0;
            value += oneValue;
        }
        if(value < 0.0) value = 0.0;
        if(value > 1.0) value = 1.0;
        return Color.mul(intersection.source.material.color, value);
    }
}