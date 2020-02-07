import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class Engine
{
    public static void main(String[] args)
    {
        File image = new File("Renders\\Image.png");
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        int width = 640;
        int height = 480;

        Scene local = new Scene();

        Vector3 light = (new Vector3(0, -1, 0.5)).normalize();



        SceneObject tobj = new SceneObject(local, "tobjign", new Vector3());
        SphereRenderer rend = tobj.AddComponent(SphereRenderer.class);
        rend.radius = 60.0;
        rend.material.color = new Color(255, 255, 255);

        SceneObject pog = new SceneObject(local, "Yeeters", new Vector3(0,0,90));
        SphereRenderer rend2 = pog.AddComponent(SphereRenderer.class);
        rend2.radius = 30.0;
        rend2.material.color = new Color(0, 125, 255);

        SceneObject epic = new SceneObject(local, "epicer", new Vector3(0,0,135));
        SphereRenderer rend3 = epic.AddComponent(SphereRenderer.class);
        rend3.radius = 15.0;
        rend3.material.color = new Color(20, 20, 20);



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
                    Intersection foremost = intersections.get(0);
                    double lighting = Vector3.dot(light, foremost.normal);
                    lighting = ((lighting > 0) ? lighting : 0);
                    lighting += 0.1;
                    lighting = ((lighting < 1) ? lighting : 1);
                    Color shade = Color.mul(intersections.get(0).source.material.color, lighting);
                    buffer.setRGB(x, y, shade.value());
                }
                else buffer.setRGB(x, y, (new Color((int)(((double)x/(double)width)*(255.0)),(int)(((double)y/(double)height)*(255.0)),125)).value());
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
