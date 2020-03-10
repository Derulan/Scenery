import java.util.ArrayList;
import java.lang.reflect.Method;

public class SceneObject
{
    public String name;

    public final Transform transform;

    public Scene scene;

    private ArrayList<Component> components;

    public SceneObject(Scene scene, String name, Vector3 position)
    {
        this.name = name;
        this.transform = new Transform(position, new Vector3(1, 1, 1));
        this.components = new ArrayList<Component>();
        this.scene = scene;
        scene.sceneObjects.add(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T GetComponent(Class<T> type)
    {
        for(int i = 0; i < this.components.size(); i++)
        {
            if(this.components.get(i).getClass() == type || this.components.get(i).getClass().getSuperclass() == type) return (T)this.components.get(i);
        }
        return null;
    }

    public <T extends Component> T AddComponent(Class<T> type)
    {
        try
        {
            T component = type.getDeclaredConstructor().newInstance();
            component.sceneObject = this;
            this.components.add(component);
            Method initializer = type.getMethod("Initialize");
            if(initializer != null) initializer.invoke(component);
            return component;
        }
        catch(Exception error)
        {
            System.out.println("[ERROR] Component with type '" + type.getName()+"' could not be added to SceneObject "+name+".\n");
            error.printStackTrace();
        }
        return null;
    }
}
