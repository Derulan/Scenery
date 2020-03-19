import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Arrays;
import java.util.HashMap;
import java.io.*;
import java.nio.file.*;

public class PinaCollada
{
    private static PinaCollada instance = null;

    private static boolean singleton = true;

    private static String directory = "Assets";

    public static HashMap<String, Mesh> imported_meshes;

    private static HashMap<String, Material[]> imported_materials;

    private static boolean Contains(int[][] array, int[] value)
    {
        for (int i = 0; i < array.length; i++)
        {
            if(Arrays.equals(array[i], value))
            {
                return true;
            }
        }
        return false;
    }


    private static String PathCombine(String[] paths)
    {
        File file = new File(paths[0]);
        for(int i = 1; i < paths.length; i++) file = new File(file, paths[i]);
        return file.getPath();
    }

    private static String FileReadAllText(String path)
    {   
        Scanner scanner = null;
        try
        {
            scanner = ((new Scanner(new File(path))).useDelimiter("\\Z"));
        }
        catch(Exception error)
        {
            System.out.println("[PINACOLLADA] File with path '"+path+"' could not be found.");
        }
        if(scanner != null)
        {
            String data = scanner.next();
            scanner.close();
            return data;
        }
        return "";
    }

    private static int[] GenerateRange(int floor, int ceiling)
    {
        ArrayList<Integer> range = new ArrayList<Integer>();
        for (int i = floor; i < ceiling; i++)
        {
            range.add(i);
        }
        int[] finale = range.stream().mapToInt(i->i).toArray();
        return finale;
    }

    private static String[] CutUp(String data, String separator) 
    {
        String[] split = data.split(Pattern.quote(separator));
        return split;
    }

    private static String GetPerfectFileName(String raw_input)
    {
        String start = raw_input;
        String separator = File.separator;
        if(start.contains(".")) start = CutUp(start,".")[0];
        if(start.contains(separator)) start = CutUp(start, separator)[CutUp(start, separator).length-1];
        String perfect = start;
        return perfect;
    }

    private static String CleanEscape(String data)
    {
        data = data.replace("\n"," ");
        data = data.replace("\t"," ");
        while(data.contains("  "))
        {
            data.replace("  ", " ");
        }
        return data;
    }
    
    private static String BasicCut(String data, String tag, String id)
    {
        int start = data.indexOf("<"+tag+" id=\"" + id + "\">") + ("<" + tag + " id=\"" + id + "\">").length();
        String finale = data.substring(start, data.length());
        return finale;
    }

    
    private static String GetCut(String data, String start, String end)
    {
        int index = data.indexOf(start)+start.length();
        String finale = data.substring(index, data.indexOf(end));
        return finale;
    }
    
    private static String GetSection(String data, String tag, String id, String param, String close) 
    {
        if(param == null) param = " id=\"";
        if(close == null) close = "\"";
        int step1 = data.indexOf("<"+tag+param+id+close) + ("<"+tag).length();
        String step2 = data.substring(step1, data.indexOf("</" + tag + ">")+1);
        data = step2;
        int step3 = data.indexOf(">") + 1;
        String finale = data.substring(step3, (data.length()-1));
        return (String)finale;

    }

    
    private static Vector2[] GetVector2(String data)
    {
        String[] data_parts = data.split(" ");
        Vector2[] finale = new Vector2[data_parts.length/2];
        for (int i = 0; i < (data_parts.length / 2); i++)
        {
            finale[i] = (new Vector2
            (
                Double.valueOf(data_parts[(i * 2)]),
                Double.valueOf(data_parts[(i * 2)+1])
            ));
        }
        return finale;
    }

    private static Vector3[] GetVector3(String data)
    {
        String[] data_parts = data.split(" ");
        Vector3[] finale = new Vector3[(data_parts.length/3)];
        for (int i = 0; i < (data_parts.length / 3); i++)
        {
            finale[i] = (new Vector3
            (
                Double.valueOf((data_parts[(i * 3)])),
                Double.valueOf((data_parts[(i * 3) + 1])),
                Double.valueOf((data_parts[(i * 3) + 2]))
            ));
        }
        return finale;
    }

    

    private static int[] GetInt(String data)
    {
        String[] data_parts = data.split(" ");
        int[] finale = new int[data_parts.length];
        for (int i = 0; i < (data_parts.length); i++)
        {
            finale[i] = Integer.valueOf(data_parts[i]);
        }
        return finale;
    }


    
    private static String GetModelData(String filename)
    {
        return (FileReadAllText(PathCombine(new String[]{directory,"Models",filename})));
    }

    /*
    private static Material[] ImportMaterials(String file) 
    {
        Material[] object_materials = null;
        if(GetModelData(file).contains("library_effects"))
        {
            String[] effect_library = CutUp(GetSection(GetModelData(file), "library_effects", "", "", ""), "effect");
            ArrayList<String[]> effect_arrays = new ArrayList<String[]>();
            int material_count = 0;
            foreach(String group : effect_library) 
            {
                if(group.Contains("color"))
                {
                    material_count++;
                }
            }
            object_materials = new Material[material_count];
            int fake_iterator = 0;
            foreach(String group : effect_library) 
            {
                if(group.Contains("color"))
                {
                    object_materials[fake_iterator] = new Material(Shader.Find("Standard"));
                    if(group.Contains("diffuse"))
                    {
                        Vector4 raw_color = GetVector4(GetSection(GetSection(group, "diffuse","","",""), "color", "diffuse", "sid=\""))[0];
                        object_materials[fake_iterator].color = new Color(raw);
                        object_materials[fake_iterator].Setdouble("_Glossiness", 0f);
                    }
                    fake_iterator++;
                }
            }   
        }
        if(object_materials == null)
        {
            object_materials = new Material[1];
            object_materials[0] = new Material(Shader.Find("Standard"));
        }

        return object_materials;
    }
    */
    
    private static Mesh ImportMesh(String file)
    {
        boolean uses_uvs = GetModelData(file).contains("map-0");
        String mesh_name = GetCut(GetModelData(file), "<geometry id=\"", "-mesh");
        int parameters = (uses_uvs ? 3 : 2);

        //Getting basic geometry
        Vector3[] unique_vert = GetVector3(GetSection(BasicCut(GetModelData(file), "source", mesh_name+"-mesh-positions"), "float_array", mesh_name+"-mesh-positions-array", null, null));
        Vector3[] unique_norm = GetVector3(GetSection(BasicCut(GetModelData(file), "source", mesh_name+"-mesh-normals"), "float_array", mesh_name+"-mesh-normals-array", null, null));
        Vector2[] unique_text = new Vector2[0];
        if(uses_uvs) unique_text = GetVector2(GetSection(BasicCut(GetModelData(file), "source", mesh_name+"-mesh-map-0"), "float_array", mesh_name+"-mesh-map-0-array", null, null));


        String[] triangle_groups = CutUp(GetModelData(file), "triangles");
        ArrayList<int[]> triangle_arrays = new ArrayList<int[]>();
        for (int i = 0; i< triangle_groups.length; i++) 
        { 
            if(((i > 0) && (i < triangle_groups.length - 1)) && triangle_groups[i].contains("semantic"))
            {
                triangle_arrays.add(GetInt(GetSection(triangle_groups[i], "p", "", "", "")));
            }
        }
        int[] triangle_ranges = new int[triangle_arrays.size()];
        for (int w = 0; w < triangle_arrays.size(); w++)
        {
            triangle_ranges[w] = (triangle_arrays.get(w).length / parameters);
        }
        ArrayList<Integer> index_list = new ArrayList<Integer>();
        for(int[] group : triangle_arrays) 
        {
            for(int integer : group)
            {
                index_list.add(integer);
            }
        }
        int[] all_indexes = index_list.stream().mapToInt(i->i).toArray();
        int[] vert_indexes = new int[all_indexes.length / parameters];
        int[] norm_indexes = new int[all_indexes.length / parameters];
        int[] text_indexes = new int[all_indexes.length / parameters];
        for (int j = 0; j < (all_indexes.length/parameters); j++)
        {
            vert_indexes[j] = all_indexes[(j * parameters)];
            norm_indexes[j] = all_indexes[(j * parameters) +1];
            if(uses_uvs) text_indexes[j] = all_indexes[(j * parameters) +2];
        }
        int[][] nvt_buffer = new int[vert_indexes.length][];
        int[][] nvt_triplets = new int[vert_indexes.length][];
        for (int k = 0; k < vert_indexes.length; k++)
        {
            if(uses_uvs) 
            {
                nvt_buffer[k] = new int[] { vert_indexes[k], norm_indexes[k], text_indexes[k]};
            } 
            else 
            {
                nvt_buffer[k] = new int[] { vert_indexes[k], norm_indexes[k]};
            }                    
        }
        int custom_iterator = 0;
        for (int l = 0; l < nvt_buffer.length; l++)
        {
            if (!Contains(nvt_triplets, nvt_buffer[l]))
            {
                nvt_triplets[custom_iterator] = nvt_buffer[l];
                custom_iterator++;
            }
        }
        Vector3[] vertices = new Vector3[nvt_triplets.length];
        Vector3[] normals = new Vector3[nvt_triplets.length];
        Vector2[] textures = new Vector2[nvt_triplets.length];

        for (int n = 0; n < nvt_triplets.length; n++)
        {
            vertices[n] = unique_vert[nvt_triplets[n][0]];
            normals[n] = unique_norm[nvt_triplets[n][1]];
            if(uses_uvs) textures[n] = unique_text[nvt_triplets[n][2]];
        }

        Mesh mesh = new Mesh();
        mesh.subMeshCount = triangle_ranges.length;
        mesh.Clear();
        mesh.vertices = vertices;
        mesh.normals = normals;
        if(uses_uvs) mesh.textures = textures;
        int number_cache = 0;
        for (int o = 0; o < triangle_ranges.length; o++)
        {
            mesh.SetTriangles(GenerateRange(number_cache, number_cache + triangle_ranges[o]), o);
            number_cache += triangle_ranges[o];
        }

        return mesh;
    }
    
    public PinaCollada()
    {
        if(PinaCollada.singleton && PinaCollada.instance == null)
        {
            PinaCollada.singleton = false;
            PinaCollada.instance = this;
            Path testdir = Paths.get(directory);
            if(Files.exists(testdir))
            {
                imported_meshes = new HashMap<String, Mesh>();

                File[] files = null;
                testdir = Paths.get(PathCombine(new String[]{directory,"Models"}));
                if(Files.exists(testdir))
                {

                    File dir = new File(PathCombine(new String[]{directory,"Models"}));
                    files = dir.listFiles();
                    for (int j = 0; j < files.length; j++)
                    {
                        if (files[j].getName().endsWith(".dae"))
                        {
                            imported_meshes.put(PinaCollada.GetPerfectFileName(files[j].getName()), PinaCollada.ImportMesh(files[j].getName()));
                        }
                    }
                    /*
                    for (int j = 0; j < files.length; j++)
                    {
                        if (files[j].getName().endsWith(".dae"))
                        {
                            imported_meshes.put(PinaCollada.GetPerfectFileName(files[j].getName()), PinaCollada.ImportMaterials(files[j].getName()));
                        }
                    }
                    */
                }
            }
        }
    }
}