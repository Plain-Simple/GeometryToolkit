package plainsimple.geometrytoolkit;

import c10n.C10N;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Constructor {
    /* used to access C10N messages */
    private static final Messages msg = C10N.get(Messages.class);
    /* constructor args */
    private ArrayList<String> args = new ArrayList<>();
    /* name of object to construct */
    private String name = "";
    /* constructed object */
    private Object constructed_object = new Object();
    /* message to print */
    private String message = "";
    /* used for accessing CLI methods */
    private CLI cli = new CLI();
    /* used for accessing methods */
    private StoredObjects stored_objects = new StoredObjects();
    public Constructor(ArrayList<String> arguments) {
      /* collect constructor args in new list */
        name = arguments.get(arguments.indexOf("=") - 1);
        for (int i = arguments.indexOf("=") + 1; i < arguments.size(); i++) {
            args.add(arguments.get(i));
        }
    }
    public Constructor(String object_name, ArrayList<String> arguments) {
        name = object_name;
        args = arguments;
    }
    public Object getObject() {
        return constructed_object;
    }
    public String getMessage() {
        return message;
    }
    public String getName() {
        return name;
    }
    /* returns whether command is a constructor or not */
    public boolean isConstructor(ArrayList<String> args) {
        return (args.contains("="));
    }
    /* attempts to create object and returns whether it was created successfully */
    public boolean create() {
        if(validName(name)) {
            try {
                Object object_1 = stored_objects.get(args.get(0));
                Class object_class = object_1.getClass();
                if (Vector3D.class == object_class) { // todo: error handling
                    constructed_object = cli.handleVector3D(object_1, args);
                } else if (Vector2D.class == object_class) {
                    constructed_object = cli.handleVector2D(object_1, args);
                } else if (Point3D.class == object_class) {
                    constructed_object = cli.handlePoint3D(object_1, args);
                } else if (Point2D.class == object_class) {
                    constructed_object = cli.handlePoint2D(object_1, args);
                } else if (Plane3D.class == object_class) {

                } else if (Line3D.class == object_class) {

                }
                GeometryObject this_object = new GeometryObject(constructed_object);
                this_object.setName(name);
                message = (name + msg.arrow() + this_object.toString());
                return true;
            } catch (NullPointerException e) { // todo: error messages
                message = (msg.error_creating_object() + msg.double_quote() + name
                        + msg.double_quote());
                return false;
            }
        } else
           return false;
    }
    /* checks to make sure name for new object is valid */
    private boolean validName(String name) {
        /* rules: must contain letters only */
        Pattern pattern = Pattern.compile("[^a-zA-Z]");
        Matcher matcher = pattern.matcher(name);
        return (!matcher.find()); /* if found, return false. Otherwise true */
    }
}
