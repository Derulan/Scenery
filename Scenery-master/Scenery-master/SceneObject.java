import java.util.ArrayList;
import java.lang.reflect.*;

public class SceneObject
{
    public String name;

    public Vector3 transform;

    private ArrayList<Component> components;

    public SceneObject(Scene scene, String name, Vector3 transform)
    {
        this.name = name;
        this.transform = new Vector3(transform);
        this.components = new ArrayList<Component>();
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

    @SuppressWarnings("unchecked")
    public <T extends Component> T AddComponent(Class<T> type)
    {
        try
        {
            T component = type.getDeclaredConstructor().newInstance();
            component.sceneObject = this;
            this.components.add(component);
            return component;
        }
        catch(Exception error)
        {
            System.out.println("[ERROR] Component with type " + type.getName()+" could not be added to SceneObject "+name+".");
        }
        return null;
    }
}
