import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class Engine
{
    public static void main(String[] args)
    {
        File image = new File("Image.png");
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        int width = 640;
        int height = 480;

        Scene local = new Scene();

        SceneObject tobj = new SceneObject(local, "tobjign", new Vector3());
        SphereRenderer rend = tobj.AddComponent(SphereRenderer.class);
        rend.radius = 60.0;
        rend.material.color = new Color(255, 125, 0);

        SceneObject pog = new SceneObject(local, "Yeeters", new Vector3(0,0,90));
        SphereRenderer rend2 = pog.AddComponent(SphereRenderer.class);
        rend2.radius = 30.0;
        rend2.material.color = new Color(0, 125, 255);

        ArrayList<Renderer> renderers = new ArrayList<Renderer>();
        for(SceneObject obj : local.sceneObjects)
        {
            Renderer thisRenderer = obj.GetComponent(Renderer.class);
            if(thisRenderer != null) renderers.add(thisRenderer);
        }

        ArrayList<Intersection> intersections = null;

        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                Ray ray = new Ray(new Vector3(x-width/2+.5, y-height/2+.5, 200.0), new Vector3(0.0, 0.0, -1.0));
                intersections = new ArrayList<Intersection>();
                for(Renderer thisRenderer : renderers)
                {
                    Intersection thisIntersection = thisRenderer.hit(ray);
                    if(thisIntersection != null) intersections.add(thisIntersection);
                }
                if(intersections.size() > 0)
                {
                    Collections.sort(intersections);
                    buffer.setRGB(x, y, intersections.get(0).source.material.color.value());
                }
                else buffer.setRGB(x, y, 0);
            }
        }
        try
        {
            ImageIO.write(buffer, "PNG", image);
        }
        catch(Exception e)
        {
            System.out.println("Could not write to buffer");
        }
        System.out.println("Engine has finished rendering.");
    }
}
