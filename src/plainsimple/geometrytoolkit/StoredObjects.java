package plainsimple.geometrytoolkit;

import java.util.Enumeration;
import java.util.Hashtable;
import c10n.C10N;

public class StoredObjects {
    /* stores user objects */
    private  Hashtable<String, Object> user_objects = new Hashtable<>();
    /* gets object from hashtable. If object does not exist, tries to construct it */
    /* used to access C10N messages */
    private static final Messages msg = C10N.get(Messages.class);
    public Object get(String object_name) { // todo: isVector3D method, isVector2D method, etc., for constructing simple objects
        /* account for possibility that user has entered |vector| */
        if(object_name.startsWith("|") && object_name.endsWith("|")) { // todo: make sure it is a vector
            object_name = object_name.substring(1, object_name.length() - 1);
        }
        if(object_name.startsWith("<") && object_name.endsWith(">")) {
            return new Vector3D().constructFromString("", object_name);
        }
        try { /* try parsing to double */
            double d = Double.parseDouble(object_name);
            return d;
        } catch(IllegalArgumentException ex) {}
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
        String result = "";
        for(Enumeration<String> objects = user_objects.keys(); objects.hasMoreElements();)
            result += objects.nextElement() + " -> " + user_objects.get(objects.nextElement());
        return result;
    }
    /* returns String listing all objects of object_type */
    public String list(String object_type) {
        try{ // todo: testing!
            Class specified_class = Class.forName(object_type);
            String result = "";
            for(Enumeration<String> objects = user_objects.keys(); objects.hasMoreElements();) {
                if(user_objects.get(objects.nextElement()).getClass().equals(specified_class))
                    result += objects.nextElement() + " -> " + user_objects.get(objects.nextElement()).toString(); // todo: fix this!!!
            }
            return result;
        }catch(ClassNotFoundException e) {
            return msg.class_not_recognized(object_type);
        }
    }
    /* removes specified object from list */
    public boolean remove(String object_name) {
        try {
            user_objects.remove(object_name);
            return true;
        }catch(NullPointerException e) { /* object_name = null */
            return false;
        }
    }
    /* returns whether hashtable contains key */
    public boolean containsKey(String key) {
        return user_objects.contains(key);
    }
}
