import java.util.ArrayList;
public class Scene
{
    public static ArrayList<Scene> scenes;

    static
    {
        scenes = new ArrayList<Scene>();
    }

    public ArrayList<SceneObject> sceneObjects;

    public ArrayList<Light> lights;

    public double ambientLight;

    public final SkyboxRenderer renderer;

    public Scene()
    {
        Scene.scenes.add(this);
        this.sceneObjects = new ArrayList<SceneObject>();
        this.lights = new ArrayList<Light>();
        this.renderer = new SkyboxRenderer();
        this.renderer.material = new Material(new Color(0,0,0), SkyboxBasicShader.class);
    }

}
