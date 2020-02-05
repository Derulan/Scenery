import java.lang.reflect.Array;
import java.util.ArrayList;
public class Scene
{
    public static ArrayList<Scene> scenes;

    public ArrayList<SceneObject> sceneObjects;

    static
    {
        scenes = new ArrayList<Scene>();
    }

    public Scene()
    {
        Scene.scenes.add(this);
        this.sceneObjects = new ArrayList<SceneObject>();
    }
}
