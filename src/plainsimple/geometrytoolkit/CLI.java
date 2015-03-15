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
  private Hashtable<String, Object> user_objects = new Hashtable<>();
    /* regex pattern used to parse user input */
  private Pattern user_command_pattern =
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
    } while (!Objects.equals(user_input, "exit"));
  }
  private String getInput() {
    Println(msg.command_waiting());
    Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
  private void processInput(String user_input) {
    ArrayList<String> arguments = parseInput(user_input);
    switch(arguments.get(0)) {
    case "help": // todo: help function (0.2)
      break;
        case "list": // todo: list function (0.3)
            break;
    default:
      try { /* user is referencing an object - look in list of objects */
          Object referenced_object = user_objects.get(arguments.get(0));
          /* get class of object so it can be converted to proper object */
          Class object_class = referenced_object.getClass();
          Object returned_object;
          if(object_class.equals(Vector3D.class)) { /* argument is a Vector3D */
              Vector3D referenced_vector3d = (Vector3D) referenced_object;
              returned_object = handleVector3D(referenced_vector3d, arguments, false);
              if(returned_object.getClass().equals(Vector3D.class)) { // should never happen
                  Vector3D v = (Vector3D) returned_object;
                  Println(v.getComponentForm());
              } else if(returned_object.getClass().equals(String.class))
                  Println((String) returned_object);
          } else if(object_class.equals(Vector2D.class)) {
              Vector2D vector_1 = (Vector2D) referenced_object;
          }
      }catch(NullPointerException e) {
      /* object not found - either a constructor or a user-error */
        /* command syntax: <Objectname> = constructor */
        if(arguments.contains("=")) {
            ArrayList<String> constructor_args = new ArrayList<>();
            /* collect constructor args in new list */
            for(int i = arguments.indexOf("=") + 1; i < arguments.size(); i++) {
                constructor_args.add(arguments.get(i));
            }
            Object object_2 = getObject(constructor_args.get(0));
            if(object_2 == null) {
                /* print error message: "Variable <name> does not exist or constructor is invalid" */
                Println(msg.variable() + msg.double_quote() + constructor_args.get(0) + msg.double_quote() +
                        msg.var_does_not_exist());
                break;
            } else {
                Vector3D vector3d_2 = getVector3D(object_2);
                Object returned_object = handleVector3D(vector3d_2, constructor_args, true);
                if(returned_object.getClass().equals(Vector3D.class)) {/* Vector3D created successfully */
                    Vector3D new_vector3d = getVector3D(returned_object);
                    Println(msg.vector() + msg.double_quote() + new_vector3d.getName() + msg.double_quote() +
                        msg.created() + new_vector3d.getComponentForm());
                    user_objects.put(new_vector3d.getName(), new_vector3d);
                } else
                    Println(msg.error_creating_object() + msg.double_quote() + arguments.get(0)
                            + msg.double_quote());
            }
        } else {
            /* print error message: "Variable <name> does not exist or constructor is invalid" */
            Println(msg.variable() + msg.double_quote() + arguments.get(0) + msg.double_quote() +
                    msg.var_does_not_exist() + msg.invalid_constructor());
        }
      }
    }
  }
  private ArrayList<String> parseInput(String user_input) {
    ArrayList<String> arguments = new ArrayList<>();
    Matcher matcher = user_command_pattern.matcher(user_input);
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
    }
    return arguments;
  }
  private Vector3D constructVector3D(String name, String args, boolean add_to_list) {
      /* syntax is "<x, y, z>" */
      ArrayList<Double> coordinates = new ArrayList<>();
      Pattern parse_vector = Pattern.compile("\\D*(\\d+\\.*\\d*)\\D*");
      Matcher matcher = parse_vector.matcher(args);
      try {
          while (matcher.find()) {
              Println("matcher = " + matcher.group(1));
              coordinates.add(Double.parseDouble(matcher.group(1)));
          }
          Vector3D new_vector3d = new Vector3D(name);
          new_vector3d.setCoordinates(coordinates.get(0), coordinates.get(1), coordinates.get(2));
          if(add_to_list) {
              Println(msg.vector() + msg.double_quote() + new_vector3d.getName() + msg.double_quote()
                      + msg.created() + ": " + new_vector3d.getComponentForm());
              user_objects.put(new_vector3d.getName(), new_vector3d);
          }
          return new_vector3d;
      }catch(Exception e) {
          Println(msg.generic_error());
          return null;
      }
  }
  /* looks for object in arraylist, returns if found */
  public Object getObject(String object_name) {
      /* account for possibility that user has entered |vector| */
      if(object_name.startsWith("|") && object_name.endsWith("|"))
          object_name.substring(1, object_name.length() - 1);
      else if(object_name.startsWith("<") && object_name.endsWith(">")) {
          Println("Vector3D detected");
          return (constructVector3D("", object_name, false));
      }
    try {
        Object object = user_objects.get(object_name);
        return object;
    }catch(NullPointerException e) { /* object not found */
        return null; /* MUST BE HANDLED!!! */
    }
  }
    /* returns name of object */
    public String getObjectName(Object object) {
        Class object_class = object.getClass();
        /* unfortunately need to handle all classes here */
        if(object_class.equals(Vector3D.class)) {
            Vector3D v = getVector3D(object);
            return v.getName();
        }  // todo: fill in for all classes
        return "this will need to be changed"; // just a placeholder for now
    }
    /* returns true if object's class  */
    public boolean classMatches(Object object, Class required_class) {
        return (object.getClass() == required_class);
    }
  /* handles commands involving Vector3D objects */
  private Object handleVector3D(Vector3D vector_1, ArrayList<String> args, boolean constructor) {
      /* if constructor == true, need to pass a Vector3D object. Otherwise pass String */
  // todo: try-catch IllegalArgumentException
      if(args.size() == 0)
          return vector_1;
    else if(args.size() == 1) { /* syntax is <Objectname> */
        if(constructor) {
            try {
                Vector3D vector_2 = (Vector3D) getObject(args.get(0));
                return vector_2;
            }catch(NullPointerException e) {
                /* return Error: "args.get(0)" is not defined */
                return msg.error() + msg.double_quote() + msg.variable() +
                        msg.double_quote() + args.get(0) + msg.var_does_not_exist();
            }catch(IllegalArgumentException e) {
                /* Print: error: "args.get(0)" is not of type Vector3D */ // todo: error messages functions
            }
        }
        if(args.get(0).startsWith("|") && args.get(0).endsWith("|")) { /* magnitude calculation */
            double magnitude = vector_1.getMagnitude();
            return(msg.magnitude_symbol() + vector_1.getName() + msg.magnitude_symbol() +
                msg.equal_sign() + magnitude);
        } else { /* print String representation */
            return(msg.vector() + "\"" + vector_1.getName() + "\" " +
                    vector_1.getComponentForm());
        }
    } else if(args.size() == 3) { /* syntax is <Objectname> <operator> <Objectname> */
        String operator = args.get(1);
        switch (operator) {
            case "+": /* vector addition */
                Vector3D vector_2 = getVector3D(args.get(2));
                if(constructor)
                    return vector_1.addVector(vector_2);
                else
                    return vector_1.getComponentForm() + msg.plus_sign() +
                            vector_2.getComponentForm() + msg.equal_sign() +
                            vector_1.addVector(vector_2).getComponentForm();
            case "-": /* vector subtraction */
                vector_2 = getVector3D(args.get(2));
                if(constructor)
                    return vector_1.addVector(vector_2.multiplyScalar(-1));
                else
                    return vector_1.getComponentForm() + msg.minus_sign() +
                            vector_2.multiplyScalar(-1).getComponentForm() + msg.equal_sign() +
                            vector_1.addVector(vector_2.multiplyScalar(-1)).getComponentForm();
            case "*":
                Object object_2 = getObject(args.get(2));
                if(object_2.getClass().equals(Vector3D.class)) { /* vector dot product */
                    vector_2 = getVector3D(object_2);
                    if(constructor)
                        return vector_1.dot(vector_2);
                    else
                        return vector_1.getComponentForm() + msg.multiply_sign() +
                                vector_2.getComponentForm() + msg.equal_sign() +
                                vector_1.dot(vector_2);
                } else if(object_2.getClass().equals(double.class)) { /* scalar multiplication by double */
                    double d = (double) object_2;
                    if(constructor)
                        return vector_1.multiplyScalar(d);
                    else
                        return vector_1.getComponentForm() + msg.multiply_sign() +
                                d + msg.equal_sign() + vector_1.multiplyScalar(d).getComponentForm();
                } else if(object_2.getClass().equals(int.class)) { /* scalar multiplication by int */
                    int i = (int) object_2;
                    if(constructor)
                        return vector_1.multiplyScalar(i);
                    else
                        return vector_1.getComponentForm() + msg.multiply_sign() + i +
                                msg.equal_sign() + vector_1.multiplyScalar(i).getComponentForm();
                } else {
                    return msg.error() + msg.cant_do_function() + msg.double_quote() + args.get(2) +
                            msg.double_quote() + msg.must_be_type() + msg.type_double() + msg.or() +
                            msg.type_int() + msg.or() + msg.vector() + msg.three_d();

                }
            case "/": /* scalar division */
                object_2 = getObject(args.get(2));
                if(object_2.getClass().equals(double.class)) { /* scalar division by double */
                    double d = (double) object_2;
                    if(constructor)
                        return vector_1.multiplyScalar(1 / d);
                    else
                        return vector_1.getComponentForm() + msg.divide_sign() + d +
                                msg.equal_sign() + vector_1.multiplyScalar(1 / d).getComponentForm();
                } else if(object_2.getClass().equals(int.class)) { /* scalar division by double */
                    int i = (int) object_2;
                    if(constructor)
                        return vector_1.multiplyScalar(1 / i);
                    else
                        return vector_1.getComponentForm() + msg.divide_sign() + i +
                                msg.equal_sign() + vector_1.multiplyScalar(1 / i).getComponentForm();
                } else {
                    return msg.error() + msg.cant_do_function() + msg.double_quote() + args.get(2) +
                            msg.double_quote() + msg.must_be_type() + msg.type_double() + msg.or() +
                            msg.type_int();
                }
            case "//": /* are vectors parallel */
                vector_2 = getVector3D(args.get(2));
                if(vector_1.isParallel(vector_2)) {
                    if(constructor)
                        return true;
                    else
                        return vector_1.getName() + msg.parallel() + vector_2.getName();
                }
                else {
                    if(constructor)
                        return false;
                    else
                        return vector_1.getName() + msg.not_parallel() + vector_2.getName();
                }
            case "|_": /* are vectors perpendicular */
                vector_2 = getVector3D(args.get(2));
                if(vector_1.isPerpendicular(vector_2)) {
                    if(constructor)
                        return true;
                    else
                        return vector_1.getName() + msg.perpendicular() + vector_2.getName();
                } else {
                    if(constructor)
                        return false;
                    else
                        return vector_1.getName() + msg.not_perpendicular() + vector_2.getName();
                }
            case "==": /* are vectors equal */
                vector_2 = getVector3D(args.get(2));
                if(vector_1.equals(vector_2)) {
                    if(constructor)
                        return true;
                    else
                        return vector_1.getName() + msg.equal_to() + vector_2.getName();
                } else {
                    if(constructor)
                        return false;
                    else
                        return vector_1.getName() + msg.not_equal_to() + vector_2.getName();
                }
            default: /* return "Argument 'argument' was not recognized" */
                return msg.argument() + msg.space() + msg.double_quote() + args.get(1) + msg.not_recognized();
        }
    }
      return msg.generic_error();
  }
    /* returns object as a Vector3D */
    private Vector3D getVector3D(Object o) {
        return (Vector3D) o;
    }
  private void Println(String s) {
    System.out.println(s);
  }
}
