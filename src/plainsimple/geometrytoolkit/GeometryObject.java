/* methods for constructing objects from Strings as well as
getting Object names */
package plainsimple.geometrytoolkit;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeometryObject {
    /* stores constructed object, if any */
    private Object object = new Object();
    /* stores constructor */
    private String constructor = "";
    public GeometryObject(String s) {
        constructor = s;
    }
    public GeometryObject(Object o) { object = o; }
    /* returns stored object */
    public Object getObject() { return object; }
    /* checks whether s is syntax for a geometry object */
    public boolean isObject() {
        boolean valid_object;
        /* account for possibility that user has entered |vector| */
        if(constructor.startsWith("|") && constructor.endsWith("|")) { // todo: |<variablename>| must be recognized
            valid_object = constructVector(constructor.substring(1, constructor.length() - 1));
            if(valid_object)
                object = ((Vector3D) object).getMagnitude();
            return valid_object;
        } else if(constructor.startsWith("<") && constructor.endsWith(">")) {
            return constructVector(constructor);
        } else if(constructor.startsWith("(") && constructor.endsWith(")")) {
            return constructPoint(constructor);
        } else
            return constructDouble(constructor);
    }
    /* returns whether Vector was constructed successfully from String */
    public boolean constructVector(String args) {
        ArrayList<Double> coordinates = new ArrayList<>();
        Pattern parse_vector = Pattern.compile("\\D*(\\d+\\.*\\d*)\\D*");
        Matcher matcher = parse_vector.matcher(args);
        try {
            while (matcher.find()) {
                coordinates.add(Double.parseDouble(matcher.group(1)));
            }
            if(2 == coordinates.size()) {/* Vector2D */
                object = new Vector2D(coordinates.get(0), coordinates.get(1));
                return true;
            } else if(3 == coordinates.size()) {
                object = new Vector3D(coordinates.get(0), coordinates.get(1), coordinates.get(2));
                return true;
            }
            else
                return false;
        } catch(Exception e) {
            return false;
        }
    }
    /* returns whether Point was constructed successfully from String */
    public boolean constructPoint(String args) {
        ArrayList<Double> coordinates = new ArrayList<>();
        Pattern parse_vector = Pattern.compile("\\D*(\\d+\\.*\\d*)\\D*");
        Matcher matcher = parse_vector.matcher(args);
        try {
            while (matcher.find()) {
                coordinates.add(Double.parseDouble(matcher.group(1)));
            }
            if(2 == coordinates.size()) {/* Vector2D */
                object = new Point2D(coordinates.get(0), coordinates.get(1));
                return true;
            } else if(3 == coordinates.size()) {
                object = new Point3D(coordinates.get(0), coordinates.get(1), coordinates.get(2));
                return true;
            }
            else
                return false;
        } catch(Exception e) {
            return false;
        }
    }
    /* returns whether String was successfully parsed to Double */
    private boolean constructDouble(String args) {
        try { /* try parsing to double */
            double d = Double.parseDouble(constructor);
            object = d;
            return true;
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
    /* returns name of object */
    public String getName() {
        Class object_class = object.getClass();
        if(object_class.equals(Vector3D.class)) {
            return ((Vector3D) object).getName();
        } else if(object_class.equals(Vector2D.class)) {
            return ((Vector2D) object).getName();
        } else if(object_class.equals(Point3D.class)) {
            return ((Point3D) object).getName();
        } else if(object_class.equals(Point2D.class)) {
            return ((Point2D) object).getName();
        } else if(object_class.equals(Plane3D.class)) {
            return ((Plane3D) object).getName();
        } else if(object_class.equals(Line3D.class)) {
            return ((Line3D) object).getName();
        } else if(object_class.equals(String.class)) {
            return (String) object;
        } else if(object_class.equals(Double.class)) { // todo: Number Class
            return Double.toString((double) object);
        } else if (object_class.equals(Boolean.class)) {
            return Boolean.toString((boolean) object); // todo: Boolean Class (?)
        } else if(object_class.equals(Matrix.class)) {
            return ((Matrix) object).getName();
        }
        return ""; // todo: better way?
    }
    /* sets name of object */
    public void setName(String new_name) {
        Class object_class = object.getClass();
        if(object_class.equals(Vector3D.class)) {
            ((Vector3D) object).setName(new_name);
        } else if(object_class.equals(Vector2D.class)) {
            ((Vector2D) object).setName(new_name);
        } else if(object_class.equals(Point3D.class)) {
            ((Point3D) object).setName(new_name);
        } else if(object_class.equals(Point2D.class)) {
            ((Point2D) object).setName(new_name);
        } else if(object_class.equals(Plane3D.class)) {
            ((Plane3D) object).setName(new_name);
        } else if(object_class.equals(Line3D.class)) {
            ((Line3D) object).setName(new_name);
        } else if(object_class.equals(Matrix.class)) {
            ((Matrix) object).setName(new_name);
        }
    }
    /* returns String representation of object */
    @Override public String toString() {
        Class object_class = object.getClass();
        if(object_class.equals(Vector3D.class)) {
            return ((Vector3D) object).toString();
        } else if(object_class.equals(Vector2D.class)) {
            return ((Vector2D) object).toString();
        } else if(object_class.equals(Point3D.class)) {
            return ((Point3D) object).toString();
        } else if(object_class.equals(Point2D.class)) {
            return ((Point2D) object).toString();
        } else if(object_class.equals(Plane3D.class)) {
            return ((Plane3D) object).toString();
        } else if(object_class.equals(Line3D.class)) {
            return ((Line3D) object).getEquation();
        } else if(object_class.equals(Double.class)) {
            return Double.toString((Double) object);
        } else if(object_class.equals(double.class)) {
            return Double.toString((double) object);
        } else if(object_class.equals(String.class)) {
            return (String) object;
        } else if (object_class.equals(Boolean.class)) { // todo: test boolean vs. Boolean
            return Boolean.toString((boolean) object);
        } else {
        }
            return ""; // todo: better way?
    }

}
