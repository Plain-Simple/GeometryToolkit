package plainsimple.geometrytoolkit;

import c10n.C10N;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class CLI {
  /* used to access C10N messages */
  private static final Messages msg = C10N.get(Messages.class);
  /* stores all user-created objects */
  private StoredObjects user_objects = new StoredObjects();
    /* used for printing help */
    private static final Help help = new Help();
  /* regex pattern used to parse user input */
  private final Pattern user_command_pattern =
    Pattern.compile("([^\\s\"\']+)|\"([^\"]*)\"|\'([^\']*)\'");

  public void startCLI() {
    Println(msg.program_full_name());
    String user_input;
    do {
      user_input = getInput();
      if(user_input.isEmpty()) {
        Println(msg.no_input());
      } else {
        processInput(user_input);
      }
    } while (!user_input.equals("exit"));
  }
  private String getInput() {
    Println(msg.command_waiting());
    final Scanner scanner = new Scanner(
      System.in); //TODO: is it efficient to make a new scanner each time?
    return scanner.nextLine();
  }
  private void processInput(String user_input) {
    ArrayList<String> arguments = parseInput(user_input);
    Println("Debugging: arguments are " + arguments);
    switch (arguments.get(0)) {
    case "help":
    case "Help":
      help.handleHelp(arguments);
      break;
    case "list": // todo: list function (0.3)
      break;
    default:
      loadObject(arguments);
    }
  }
  private void loadObject(ArrayList<String> arguments) {
    if (arguments.contains("=")) {
        Println("Constructor detected");
        Constructor new_object = new Constructor(arguments);
        if(new_object.create()) {
            Println("Object created successfully");
            user_objects.put(new_object.getName(), new_object.getObject());
        }
        Println(new_object.getMessage());
    } else {
      try { /* user is referencing an object - look in list of objects */
        Object referenced_object = user_objects.get(arguments.get(0));
        /* get class of object so it can be converted to proper object */
        Class object_class = referenced_object.getClass();
        Object returned_object = msg.generic_error(); /* temporary */
        try {
          if (object_class.equals(Vector3D.class)) {
            returned_object = handleVector3D((Vector3D) referenced_object, arguments, false);
          } else if (object_class.equals(Vector2D.class)) {
            Vector2D vector_1 = (Vector2D) referenced_object;
          }
        } catch(IllegalArgumentException e) {
        }
        Println((String) returned_object);
      } catch (NullPointerException e) { /* arguments.get(0) not a valid object */
        Println(msg.variable_error(arguments.get(0)));
      }
    }
  }

  private ArrayList<String> parseInput(String user_input) {
    ArrayList<String> arguments = new ArrayList<>();
    Matcher matcher = user_command_pattern.matcher(user_input);
    while(matcher.find()) {
      if(null != matcher.group(1)) { /* argument is space-separated */
        arguments.add(matcher.group(1));
      } else if(null != matcher.group(2)) { /* argument is enclosed in double-quotes */
        arguments.add(matcher.group(2));
      } else if(null != matcher.group(3)) { /* argument is enclosed in single-quotes */
        arguments.add(matcher.group(3));
      }
    }
    return arguments;
  }
  /* handles commands involving Vector3D objects */
  public Object handleVector3D(Object vector_3d, ArrayList<String> args,
                                boolean constructor) {
    /* if constructor == true, need to pass a Vector3D object. Otherwise pass String */
    // todo: try-catch IllegalArgumentException. Allow constructor Point3D->Point3D. Implement remaining functions
      Vector3D vector_1 = (Vector3D) vector_3d;
    if(1 == args.size()) { /* syntax is <Objectname> */
      if(constructor) {
        try {
          Vector3D vector_2 = (Vector3D) user_objects.get(args.get(0));
          return vector_2;
        } catch(NullPointerException e) {
          /* return Error: "args.get(0)" is not defined */
          return msg.error() + msg.double_quote() + msg.variable() +
                 msg.double_quote() + args.get(0) + msg.var_does_not_exist();
        } catch(IllegalArgumentException e) {
          return getTypeError(args.get(0), new String[] {msg.vector() + msg.three_d()});
        }
      }
      if(args.get(0).startsWith("|")
          && args.get(0).endsWith("|")) { /* magnitude calculation */
        double magnitude = vector_1.getMagnitude();
        return(msg.magnitude_symbol() + vector_1.getName() + msg.magnitude_symbol() +
               msg.equal_sign() + magnitude);
      } else { /* print String representation */
        return(msg.vector() + "\"" + vector_1.getName() + "\" " +
               vector_1.getComponentForm());
      }
    }
    if(3 == args.size()) { /* syntax is <Objectname> <operator> <Objectname> */
      String operator = args.get(1);
      Vector3D vector_2 = new Vector3D();
      switch(operator) { /* all these operations require a second Vector3D */
      case "+":
      case "-":
      case "//":
      case "|_":
      case "==":
        vector_2 = getVector3D(user_objects.get(args.get(2)));
        break;
          default: // todo: error message: invalid operator

      }
      switch (operator) {
      case "+": /* vector addition */
        return constructor ? vector_1.addVector(vector_2) :
               vector_1.addVector(vector_2).getComponentForm();
      case "-": /* vector subtraction */
        return constructor ? vector_1.addVector(vector_2.multiplyScalar(
            -1)) : vector_1.addVector(vector_2.multiplyScalar(-1)).getComponentForm();
      case "*":
        Object object_2 = user_objects.get(args.get(2));
        if(object_2.getClass().equals(Vector3D.class)) { /* vector dot product */
          return constructor ? vector_1.dot((Vector3D) object_2) : vector_1.dot((Vector3D) object_2);
        } else if(object_2.getClass().equals(Double.class)) { /* scalar multiplication */
          return constructor ? vector_1.multiplyScalar((double) object_2) :
                  vector_1.multiplyScalar((double) object_2).getComponentForm();
        } else {
          return getTypeError(args.get(2), new String[] {msg.type_double(),
                              msg.type_int(), msg.vector() + msg.three_d()
                                                        });
        }
      case "/": /* scalar division */
        object_2 = user_objects.get(args.get(2));
        if(object_2.getClass().equals(Double.class)) { /* scalar division by double */
          double d = (double) object_2;
          return constructor ? vector_1.multiplyScalar(1 / d) :
                  vector_1.multiplyScalar(1 / d).getComponentForm();
        } else {
          return getTypeError(args.get(2), new String[] {msg.type_double(), msg.type_int()});
        }
      case "//": /* are vectors parallel */
        if(vector_1.isParallel(vector_2)) {
          return constructor ? true : vector_1.getName() + msg.parallel() +
                 vector_2.getName();
        } else {
          return constructor ? false : vector_1.getName() + msg.not_parallel() +
                 vector_2.getName();
        }
      case "|_": /* are vectors perpendicular */
        if(vector_1.isPerpendicular(vector_2)) {
          return constructor ? true : vector_1.getName() + msg.perpendicular() +
                 vector_2.getName();
        } else {
          return constructor ? false : vector_1.getName() + msg.not_perpendicular() +
                 vector_2.getName();
        }
      case "==": /* are vectors equal */
        if(vector_1.equals(vector_2)) {
          return constructor ? true : vector_1.getName() + msg.equal_to() +
                 vector_2.getName();
        } else {
          return constructor ? true : vector_1.getName() + msg.not_equal_to() + vector_2.getName();
          }
      default: /* return "Argument 'argument' was not recognized" */
        return msg.argument() + msg.space() + msg.double_quote() + args.get(
                 1) + msg.not_recognized();
      }
    }
    return msg.generic_error();
  }

  /* returns "Error: function cannot be performed. "function" must be of type ... " */
  private String getTypeError(String function, String[] required_types) {
    //TODO: convert to fancy C10N thingy
    String result = msg.error() + msg.cant_do_function() + msg.double_quote() +
                    function +
                    msg.double_quote() + msg.must_be_type() + required_types[0];
    for(int i = 1; i < required_types.length; i++) {
      result += msg.or() + required_types[i];
    }
    return result;
  }
  /* returns object as a Vector3D */
  private Vector3D getVector3D(Object o) {
      return (Vector3D) o;
  }
  private void Println(String s) {
    System.out.println(s);
  }
}
