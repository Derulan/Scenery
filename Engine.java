import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.*;
import javax.imageio.*;
public class Engine
{
    public static void main(String[] args)
    {
        File image = new File("Image.png");
        BufferedImage buffer = new BufferedImage(640, 480, BufferedImage.TYPE_INT_RGB);
        int green = (new Color(125, 255, 0)).getRGB();
        int width = 640;
        int height = 480;
        Vector3 light = new Vector3(0.5, -1.0, 1.0).normalize();
        SphereRenderer sphere = new SphereRenderer(new Vector3(), 120.0);
        SphereRenderer sphere2 = new SphereRenderer(new Vector3(60.0, -30.0 ,30.0), 120.0);

        for(int x = 0; x < width; x++)
        {
            for(int y = 0; y < height; y++)
            {
                Ray ray = new Ray(new Vector3(x-width/2+.5, y-height/2+.5, 200.0), new Vector3(0.0, 0.0, -1.0));
                Intersection hit = sphere.hit(ray);
                Intersection hit2 = null;
                int correct = 0;
                int correct2 = 0;

                if(hit!=null)
                {
                    double lighting = (Vector3.dot(hit.normal, light));
                    if(lighting<0.0) lighting = 0.0;
                    lighting+=0.2;
                    if(lighting>1.0) lighting = 1.0;
                    correct = (new Color((int)(lighting*125), 0, (int)(lighting*255))).getRGB();
                }
                if(hit2!=null)
                {
                    double lighting2 = (Vector3.dot(hit2.normal, light));
                    if(lighting2<0.0) lighting2 = 0.0;
                    lighting2+=0.2;
                    if(lighting2>1.0) lighting2 = 1.0;
                    correct2 = (new Color(0, (int)(lighting2*125), (int)(lighting2*255))).getRGB();
                }
                if(hit!=null&&hit2!=null)
                {
                    if(hit.t < hit2.t) buffer.setRGB(x, y, correct);
                    else buffer.setRGB(x, y, correct2);
                }
                else if(hit!=null) buffer.setRGB(x, y, correct);
                else if(hit2!=null) buffer.setRGB(x, y, correct2);
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
        System.out.println("Main finished rendering.");
    }
}
