package plainsimple.geometrytoolkit;

import java.io.*;
import java.util.Enumeration;
import java.util.Hashtable;
import c10n.C10N;

public class StoredObjects { // todo: constructor that takes filename?
    /* stores user objects */
    private  Hashtable<String, Object> user_objects = new Hashtable<>();
    /* gets object from hashtable. If object does not exist, tries to construct it */
    /* used to access C10N messages */
    private static final Messages msg = C10N.get(Messages.class);
    public Object get(String object_name) {
        GeometryObject this_object = new GeometryObject(object_name);
        if(this_object.isObject())
            return  this_object.getObject();
        try {
            Object object = user_objects.get(object_name);
            return object;
        } catch(NullPointerException e) {} /* object not found */
        return null; /* MUST BE HANDLED!!! */
    }
    /* puts object in user_objects hashtable */
    public void put(String key, Object object) {
        user_objects.put(key, object);
    }
    /* returns String listing all objects (for list() command) */
    public String list() {
        String result = "Stored Objects\n";
        Enumeration<String> objects = user_objects.keys();
        while(objects.hasMoreElements()) {
            String object_name = objects.nextElement();
            result += object_name + " -> " +
                    (new GeometryObject(user_objects.get(object_name))).toString() + "\n";
        }
        return result;
    }
    /* returns String listing all objects of object_type */
    public String list(String object_type) {
        String result = "";
        Class list_class;
        switch (object_type) {
            case "Vector3D":
                list_class = Vector3D.class;
                result += "Stored Vector3D Objects\n";
                break;
            case "Vector2D":
                list_class = Vector2D.class;
                result += "Stored Vector2D Objects\n";
                break;
            case "Point3D":
                list_class = Point3D.class;
                result += "Stored Point3D Objects\n";
                break;
            case "Point2D":
                list_class = Point2D.class;
                result += "Stored Point2D Objects\n";
                break;
            case "Plane3D":
                list_class = Plane3D.class;
                result += "Stored Plane3D Objects\n";
                break;
            case "Line3D":
                list_class = Line3D.class;
                result += "Stored Line3D Objects\n";
                break;
            default:
                list_class = Double.class; // todo: finish this. Default should give an error - unrecognized class
        }
        Enumeration<String> objects = user_objects.keys();
        while(objects.hasMoreElements()) {
            String object_name = objects.nextElement();
            if(user_objects.get(object_name).getClass().equals(list_class))
                result += object_name + " -> " +
                    (new GeometryObject(user_objects.get(object_name))).toString() + "\n";
        }
        return result;
    }
    /* removes all objects from list */
    public void clear() {
        user_objects.clear();
    }
    /* removes specified object from list */
    public boolean remove(String object_name) {
        try {
            user_objects.remove(object_name); // todo: remove auto-update?
            return true;
        }catch(NullPointerException e) { /* object_name = null */
            return false;
        }
    }
    /* returns whether hashtable contains key */
    public boolean containsKey(String key) {
        return user_objects.contains(key);
    }
    /* loads objects from file */
    public void loadObjects(String file_name) {
        try {
            FileReader file = new FileReader(file_name);
            BufferedReader read_objects = new BufferedReader(file);
            String line = "";
            while ((line = read_objects.readLine()) != null) {
                try { /* read in format "object_name: StringRepresentation" */
                    String object_name = line.substring(0, line.indexOf(":"));
                    Object object = get(line.substring(line.indexOf(":") + 2));
                    put(object_name, object);
                } catch (StringIndexOutOfBoundsException e) { /* no value defined */
                }
            }
        } catch (IOException e) {} // todo: error-catching?
    }
    /* writes objects to file */
    public void writeObjects(String file_name) {
        try {
            FileWriter file = new FileWriter(file_name);
            BufferedWriter write_objects = new BufferedWriter(file);
            Enumeration<String> objects = user_objects.keys();
            while(objects.hasMoreElements()) {
                String object_name = objects.nextElement();
                /* write in format "object_name: StringRepresentation" */
                write_objects.write(object_name + ": " +
                        (new GeometryObject(user_objects.get(object_name))).toString());
                write_objects.newLine();
            }
            write_objects.close();
        } catch (IOException e) { } // todo: error catching?
    }
}
