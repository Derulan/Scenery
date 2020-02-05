import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;
public class NewRender
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

        ArrayList<Renderer> renderers = new ArrayList<Renderer>();
        for(SceneObject obj : local.sceneObjects)
        {
            Renderer thisRenderer = obj.GetComponent(Renderer.class);
            if(thisRenderer != null) renderers.add(thisRenderer);
        }
        
        ArrayList<Double> depthMap = null;
        ArrayList<Intersection> allI = null;

        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                Ray ray = new Ray(new Vector3(x-width/2+.5, y-height/2+.5, 200.0), new Vector3(0.0, 0.0, -1.0));
                depthMap = new ArrayList<Double>();
                allI = new ArrayList<Intersection>();
                for(Renderer thisRenderer : renderers)
                {
                    Intersection thisIntersection = thisRenderer.hit(ray);
                    if(thisIntersection != null) allI.add(thisIntersection);
                }
                for(Intersection intersection : allI) depthMap.add(intersection.t);
                Collections.sort(depthMap);
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
        System.out.println("Main finished rendering.");
    }
}
