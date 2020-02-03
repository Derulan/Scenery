import java.util.ArrayList;
import java.lang.reflect.*;

public class SceneObject
{
    public String name;

    public Vector3 transform;

    private ArrayList<Component> components;

    @SuppressWarnings("unchecked")
    public <T extends Component> T GetComponent(Class<T> type)
    {
        for(int i = 0; i < this.components.size(); i++)
        {
            if(this.components.get(i).getClass() == type) return (T)this.components.get(i);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> void AddComponent(Class<T> type)
    {
        try
        {
            T component = type.getDeclaredConstructor().newInstance();
            this.components.add(component);
        }
        catch(Exception error)
        {
            System.out.println("[ERROR] Component with type " + type.getName()+" could not be added to SceneObject "+name+".");
        }
    }
}
