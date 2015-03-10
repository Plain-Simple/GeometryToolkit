package plainsimple.geometrytoolkit;

import c10n.C10N;
import c10n.annotations.DefaultC10NAnnotations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CLI {
  private static final Messages msg = C10N.get(Messages.class);
  private ArrayList<Vector3D> vector_3ds = new ArrayList<>();
  private ArrayList<Point3D> point_3ds = new ArrayList<>();
  private ArrayList<Line3D> line_3ds = new ArrayList<>();
  private ArrayList<Plane3D> plane_3ds = new ArrayList<>();
  private ArrayList<Object> user_objects = new ArrayList<>();
  private Pattern user_command_pattern =
    Pattern.compile("([^\\s\"\']+)|\"([^\"]*)\"|\'([^\']*)\'");
  public static void main(String args[]) {
    /* required for i18n */
    C10N.configure(new DefaultC10NAnnotations());
    CLI cli = new CLI();
    cli.startCLI();
  }
  void startCLI() {
    Println(msg.program_full_name());
    String user_input;
    do {
      user_input = getInput();
      if(user_input.isEmpty()) {
        Println(msg.no_input());
      } else {
        processInput(user_input);
      }
    } while (!Objects.equals(user_input, "exit"));
  }
  String getInput() {
    Println(msg.command_waiting());
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
  void processInput(String user_input) {
    ArrayList<String> arguments = parseInput(user_input);
    Println(arguments.toString());
    switch(arguments.get(0)) {
    case "help": // todo: help function (0.2)
      break;
    default:
      /* user is referencing an object - look in list of objects */
      Object referenced_object = getObject(arguments.get(0));
      if(referenced_object ==
          null) { /* object does not exist - either a constructor or a user-error */
        /* command syntax: <Objectname> = new <Object> constructor */
        if(arguments.get(1).equals("=") && arguments.get(2).equals("new")) {
          switch(arguments.get(3)) {
          case "Vector3D": /* create new Vector3D */
            ArrayList<String> constructor_args = new ArrayList<>();
            /* collect constructor args in new list */
            for(int i = 4; i < arguments.size(); i++) {
              constructor_args.add(arguments.get(i));
            }
            constructVector3D(arguments.get(0), constructor_args);
          }
        }
      } else {
        /* get class of object so it can be converted to proper object */
        Class object_class = referenced_object.getClass();
        if(object_class.equals(Vector3D.class)) {
          Vector3D referenced_vector3d = (Vector3D) referenced_object;
          handleVector3D(referenced_vector3d, arguments);
        } else if(object_class.equals(Vector2D.class)) {
          Vector2D vector_1 = (Vector2D) referenced_object;
        }
      }
    }
  }
  ArrayList<String> parseInput(String user_input) {
    ArrayList<String> arguments = new ArrayList<>();
    Matcher matcher = user_command_pattern.matcher(user_input);
    boolean found = false;
    while(matcher.find()) {
      if(matcher.group(1) != null) { /* if argument is space-separated */
        arguments.add(matcher.group(1));
      } else if(matcher.group(2) !=
                null) { /* if argument is enclosed in double-quotes */
        arguments.add(matcher.group(2));
      } else if(matcher.group(3) !=
                null) { /* if argument is enclosed in single-quotes */
        arguments.add(matcher.group(3));
      }
      found = true;
    }
    return arguments;
  }
  boolean constructVector3D(String name, ArrayList<String> args) {
    try {
      Vector3D new_vector3d = new Vector3D(name);
      double x = Double.parseDouble(args.get(0));
      double y = Double.parseDouble(args.get(1));
      double z = Double.parseDouble(args.get(2));
      new_vector3d.setCoordinates(x, y, z);
      user_objects.add(new_vector3d);
      Println(msg.vector() + " " + new_vector3d.getName() + " " + msg.created() + ": "
              + new_vector3d.getVectorAsString());
      return true;
    } catch(Exception e) {
      Println(msg.generic_error());
      return false;
    }
  }
  /* returns specified object as an object (if object exists) */
  Object getObject(String object_name) {
    /* loop through objects. For each:
    1. Get object class
    2. Match with existing Object Classes
    3. Convert to correct class
    4. Check to see if name matches */
    for(int i = 0; i < user_objects.size(); i++) {
      Class object_class = user_objects.get(i).getClass();
      if(object_class.equals(Vector3D.class)) {
        Vector3D this_vector3d = (Vector3D) user_objects.get(i);
        if(this_vector3d.getName().equals(object_name)) {
          return this_vector3d;
        }
      } else if(object_class.equals(Vector2D.class)) {
        Vector2D this_vector2d = (Vector2D) user_objects.get(i);
        if(this_vector2d.getName().equals(object_name)) {
          return this_vector2d;
        }
      }
    }
    return null; /* MUST BE HANDLED!!! */
  }
  /* handles commands involving Vector3D objects */
  void handleVector3D(Vector3D vector_1, ArrayList<String> args) {
    /* syntax is <Objectname>. Print vector */
    if(args.size() == 1) {
      Println(msg.vector() + "\"" + vector_1.getName() + "\" " +
              vector_1.getVectorAsString());
    } else if(args.size() ==
              3) { /* syntax is <Objectname> <operator> <Objectname> */
    }
  }
  void Println(String s) {
    System.out.println(s);
  }
}
