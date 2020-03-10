public class Vector3
{
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    public Vector3(Vector3 source)
    {
        this.x = source.x;
        this.y = source.y;
        this.z = source.z;
    }

    public Vector3 normalize()
    {
        double magnitude = this.magnitude();
        return new Vector3(this.x / magnitude, this.y / magnitude, this.z / magnitude);
    }

    public Vector3 absolute()
    {
        return new Vector3(Math.abs(this.x), Math.abs(this.y), Math.abs(this.z));
    }

    public double magnitude()
    {
        return Math.sqrt(dot(this, this));
    }

    public String toString()
    {
        return ("Vector3("+this.x+", "+this.y+", "+this.z+")");
    }


    public static Vector3 add(Vector3 first, Vector3 last)
    {
        return new Vector3(first.x + last.x, first.y + last.y, first.z + last.z);
    }

    public static Vector3 sub(Vector3 first, Vector3 last)
    {
        return new Vector3(first.x - last.x, first.y - last.y, first.z - last.z);
    }

    public static Vector3 mul(Vector3 first, Vector3 last)
    {
        return new Vector3(first.x * last.x, first.y * last.y, first.z * last.z);
    }

    public static Vector3 div(Vector3 first, Vector3 last)
    {
        return new Vector3(first.x / last.x, first.y / last.y, first.z / last.z);
    }

    public static Vector3 mul(Vector3 vector, double scale)
    {
        return new Vector3(vector.x * scale, vector.y * scale, vector.z * scale);
    }

    public static Vector3 div(Vector3 vector, double scale)
    {
        return new Vector3(vector.x / scale, vector.y / scale, vector.z / scale);
    }

    public static double dot(Vector3 first, Vector3 last)
    {
        return ((first.x * last.x) + (first.y * last.y) + (first.z * last.z));
    }

    public static Vector3 crs(Vector3 first, Vector3 last)
    {
        return new Vector3((first.y * last.z - first.z * last.y), (first.z * last.x - first.x * last.z), (first.x * last.y - first.y * last.x));
    }
}
