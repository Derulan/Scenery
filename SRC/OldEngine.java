import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
import java.util.*;

public class OldEngine
{
    public static void main(String[] args)
    {
        File image = new File("..\\Renders\\Image.png");
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);

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

        SceneObject mc = new SceneObject(local, "Main Camera", new Vector3());
        Camera comp = mc.AddComponent(Camera.class);
        comp.resize(640, 480);

        buffer = comp.render();



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
