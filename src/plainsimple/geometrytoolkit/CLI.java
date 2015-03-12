package plainsimple.geometrytoolkit;

import c10n.C10N;
import c10n.annotations.DefaultC10NAnnotations;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CLI {
    /* used to access C10N messages */
  private static final Messages msg = C10N.get(Messages.class);
    /* stores all user-created objects */
  private ArrayList<Object> user_objects = new ArrayList<>();
    /* regex pattern used to parse user input */
  private Pattern user_command_pattern =
    Pattern.compile("([^\\s\"\']+)|\"([^\"]*)\"|\'([^\']*)\'");
  public static void main(String args[]) {
    /* required for i18n */
    C10N.configure(new DefaultC10NAnnotations());
    CLI cli = new CLI();
    cli.startCLI();
  }
    private void startCLI() {
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
  private String getInput() {
    Println(msg.command_waiting());
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
  private void processInput(String user_input) {
    ArrayList<String> arguments = parseInput(user_input);
    Println(arguments.toString());
    switch(arguments.get(0)) {
    case "help": // todo: help function (0.2)
      break;
        case "list": // todo: list function (0.3)
            break;
    default:
      /* user is referencing an object - look in list of objects */
      Object referenced_object = getObject(arguments.get(0));
      if(referenced_object == null) { /* object does not exist - either a constructor or a user-error */
        /* command syntax: <Objectname> = new <Object> constructor */
        if(arguments.get(1).equals("=") && arguments.get(2).equals("new")) {
            ArrayList<String> constructor_args = new ArrayList<>();
            /* collect constructor args in new list */
            for(int i = 4; i < arguments.size(); i++) {
                constructor_args.add(arguments.get(i));
            }
            /* switch object type */
            switch(arguments.get(3)) {
                case "Vector3D": /* create new Vector3D */
                    constructVector3D(arguments.get(0), constructor_args);
                break;
            }
        } else {
            /* print error message: "Variable <name> does not exist or constructor is invalid" */
            Println(msg.variable() + msg.double_quote() + arguments.get(0) + msg.double_quote() +
                    msg.var_does_not_exist() + msg.invalid_constructor());
        }
      } else { /* specified object was found in arraylist */
        /* get class of object so it can be converted to proper object */
        Class object_class = referenced_object.getClass();
          Object returned_object;
        if(object_class.equals(Vector3D.class)) {
          Vector3D referenced_vector3d = (Vector3D) referenced_object;
          returned_object = handleVector3D(referenced_vector3d, arguments);
            if(returned_object.getClass().equals(Vector3D.class)) {
                Vector3D v = (Vector3D) returned_object;
                Println(v.getVectorAsString());
            } else if(returned_object.getClass().equals(String.class))
                Println((String) returned_object);
        } else if(object_class.equals(Vector2D.class)) {
          Vector2D vector_1 = (Vector2D) referenced_object;
        }
      }
    }
  }
  private ArrayList<String> parseInput(String user_input) {
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
  private boolean constructVector3D(String name, ArrayList<String> args) {
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
  /* looks for object in arraylist, returns if found */
  public Object getObject(String object_name) {
    for(int i = 0; i < user_objects.size(); i++) {
        String compare_name = getObjectName(user_objects.get(i));
      if(compare_name.equals(object_name))
          return user_objects.get(i);
      /* account for possibility that user has entered |vector| */
        else if(compare_name.startsWith("|") && compare_name.endsWith("|")) {
          /* parse out magnitude symbols */
          compare_name = compare_name.substring(1, compare_name.length() - 1);
          if(compare_name.equals(object_name))
              return user_objects.get(i);
      }
    }
    return null; /* MUST BE HANDLED!!! */
  }
    /* returns name of object */
   public String getObjectName(Object object) {
        Class object_class = object.getClass();
        /* unfortunately need to handle all classes here */
        if(object_class.equals(Vector3D.class)) {
            Vector3D v = (Vector3D) object;
            return v.getName();
        }  // todo: fill in for all classes
        return "this will need to be changed"; // just a placeholder for now
    }
    /* returns true if object's class  */
    public boolean classMatches(Object object, Class required_class) {
        return (object.getClass() == required_class);
    }
  /* handles commands involving Vector3D objects. Returns String to output or a new Vector3D object */
  private Object handleVector3D(Vector3D vector_1, ArrayList<String> args) { // todo: try-catch IllegalArgumentException
    /* syntax is <Objectname> */
    if(args.size() == 1) {
        /* magnitude calculation */
        if(args.get(0).startsWith("|") && args.get(0).endsWith("|")) {
            double magnitude = vector_1.getMagnitude();
            return(msg.magnitude_symbol() + vector_1.getName() + msg.magnitude_symbol() +
                msg.equal_sign() + magnitude);
        } else { /* print String representation */
            return(msg.vector() + "\"" + vector_1.getName() + "\" " +
                    vector_1.getVectorAsString());
        }
    } else if(args.size() == 3) { /* syntax is <Objectname> <operator> <Objectname> */
        String operator = args.get(1);
        switch (operator) {
            case "+": /* vector addition */
                Vector3D vector_2 = (Vector3D) getObject(args.get(2));
                return vector_1.addVector(vector_2);
                //break;
            case "-": /* vector subtraction */
                vector_2 = (Vector3D) getObject(args.get(2));
                return vector_1.addVector(vector_2.multiplyScalar(-1));
                //break;
            case "*":
                Object object_2 = getObject(args.get(2));
                if(object_2.getClass().equals(Vector3D.class)) { /* vector dot product */
                    vector_2 = (Vector3D) object_2;
                    return vector_1.dot(vector_2);
                } else if(object_2.getClass().equals(double.class)) { /* scalar multiplication by double */
                    double d = (double) object_2;
                    return vector_1.multiplyScalar(d);
                } else if(object_2.getClass().equals(int.class)) { /* scalar multiplication by int */
                    int i = (int) object_2;
                    return vector_1.multiplyScalar(i);
                }
                break;
            case "/": /* scalar division */
                object_2 = getObject(args.get(2));
                if(object_2.getClass().equals(double.class)) { /* scalar division by double */
                    double d = (double) object_2;
                    return vector_1.multiplyScalar(1 / d);
                } else if(object_2.getClass().equals(int.class)) { /* scalar division by double */
                    int i = (int) object_2;
                    return vector_1.multiplyScalar(1 / i);
                }
                break;
            case "//": /* are vectors parallel */
                vector_2 = (Vector3D) getObject(args.get(2));
                if(vector_1.isParallel(vector_2))
                    return vector_1.getName() + msg.parallel() + vector_2.getName();
                else
                    return vector_1.getName() + msg.not_parallel() + vector_2.getName();
                //break;
            case "|_": /* are vectors perpendicular */
                vector_2 = (Vector3D) getObject(args.get(2));
                if(vector_1.isPerpendicular(vector_2))
                    return vector_1.getName() + msg.perpendicular() + vector_2.getName();
                else
                    return vector_1.getName() + msg.not_perpendicular() + vector_2.getName();
               // break;
            case "==": /* are vectors equal */
                vector_2 = (Vector3D) getObject(args.get(2));
                if(vector_1.equals(vector_2)) {
                    return vector_1.getName() + msg.equal_to() + vector_2.getName();
                } else {
                    return vector_1.getName() + msg.not_equal_to() + vector_2.getName();
                }
               // break;
            default: /* return "Argument 'argument' was not recognized" */
                return msg.argument() + msg.space() + msg.double_quote() + args.get(1) + msg.not_recognized();
        }
    }
      return false;
  }
  private void Println(String s) {
    System.out.println(s);
  }
}
