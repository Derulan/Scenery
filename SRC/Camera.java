import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Camera extends Component
{
    private int width;
    private int height;
    private BufferedImage buffer;

    public Camera()
    {
        width = 100;
        height = 100;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public void resize(int width, int height)
    {
        this.width = width;
        this.height = height;
        buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
    }

    public Vector2 dimensions()
    {
        return new Vector2(this.width, this.height);
    }

    public BufferedImage render()
    {
        Scene local = this.sceneObject.scene;
        ArrayList<Renderer> renderers = new ArrayList<Renderer>();
        for(SceneObject obj : local.sceneObjects)
        {
            Renderer thisRenderer = obj.GetComponent(Renderer.class);
            if(thisRenderer != null) renderers.add(thisRenderer);
        }

        ArrayList<Intersection> intersections = null;

        for(int x = 0; x < this.width; x++)
        {
            for(int y = 0; y < this.height; y++)
            {
                Ray ray = new Ray(new Vector3(x-this.width/2+.5, y-this.height/2+.5, 200.0), new Vector3(0.0, 0.0, -1.0));
                Color shade = null;
                Method fragment = null;
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
                    try
                    {
                        Light[] lights = new Light[local.lights.size()];
                        lights = local.lights.toArray(lights);
                        fragment = foremost.source.material.shader.getMethod("frag", Intersection.class, lights.getClass(), double.class);
                        shade = (Color)fragment.invoke(null, foremost, lights, local.ambientLight);
                    }
                    catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException error)
                    {
                        shade = EngineConfig.ERROR_COLOR;
                        System.out.println("[ERROR] Shader '"+foremost.source.material.shader.getName()+"' could not be found.\n"+error.getMessage());
                    }
                    buffer.setRGB(x, this.height-y-1, shade.value());
                }
                else 
                {
                    
                    try
                    {
                        fragment = local.renderer.material.shader.getMethod("frag", Intersection.class, (new Light[0]).getClass(), double.class);
                        shade = (Color)fragment.invoke(null, new Intersection(0, new Vector3(x, y, 0), new Vector3(), local.renderer), null, 0.0);
                    }
                    catch(IllegalAccessException | InvocationTargetException | NoSuchMethodException error)
                    {
                        shade = EngineConfig.ERROR_COLOR;
                        System.out.println("[ERROR] Shader '"+local.renderer.material.shader.getName()+"' could not be found.\n"+error.getMessage());
                    }
                    buffer.setRGB(x, this.height-y-1, shade.value());
                }
            }
        }
        return this.buffer;
    }

    public void Update(){}
    public void Start(){}
    public void Initialize(){}
}
