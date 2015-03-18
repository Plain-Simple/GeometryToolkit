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
      Println("Debugging: arguments are " + arguments.toString());
    switch(arguments.get(0)) {
    case "help": // todo: help function (0.2)
      break;
        case "list": // todo: list function (0.3)
            break;
    default:
        if(arguments.contains("=")) { /* command syntax: <Objectname> = constructor */
            ArrayList<String> constructor_args = new ArrayList<>();
            /* collect constructor args in new list */
            for(int i = arguments.indexOf("=") + 1; i < arguments.size(); i++)
                constructor_args.add(arguments.get(i));
            Println("Constructor detected: " + constructor_args.toString());
            Object object_2 = getObject(constructor_args.get(0));
            if(object_2 == null) {
                Println(getConstructorError(constructor_args.get(0)));
            } else {
                Vector3D vector3d_2 = getVector3D(object_2);
                Object returned_object = handleVector3D(vector3d_2, constructor_args, true);
                if(returned_object.getClass().equals(Vector3D.class)) {/* Vector3D created successfully */
                    Vector3D new_vector3d = getVector3D(returned_object);
                    new_vector3d.setName(arguments.get(0));
                    Println(msg.vector() + msg.double_quote() + new_vector3d.getName() + msg.double_quote() +
                            msg.created() + new_vector3d.getComponentForm());
                    user_objects.put(new_vector3d.getName(), new_vector3d);
                } else
                    Println(msg.error_creating_object() + msg.double_quote() + arguments.get(0)
                            + msg.double_quote());
            }
        }
      else {
            try { /* user is referencing an object - look in list of objects */
                Object referenced_object = user_objects.get(arguments.get(0));
          /* get class of object so it can be converted to proper object */
                Class object_class = referenced_object.getClass();
                Object returned_object = msg.generic_error(); /* temporary */
                if(object_class.equals(Vector3D.class)) { /* argument is a Vector3D */
                    Vector3D referenced_vector3d = getVector3D(referenced_object);
                    returned_object = handleVector3D(referenced_vector3d, arguments, false);
                } else if(object_class.equals(Vector2D.class)) {
                    Vector2D vector_1 = (Vector2D) referenced_object;
                }
                Println((String) returned_object);
            }catch(NullPointerException e) { // todo: fix so that it prints proper variable
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
  /* looks for object in arraylist, returns if found */
  public Object getObject(String object_name) {
      Println("Object name is " + object_name);
      /* account for possibility that user has entered |vector| */
      if(object_name.startsWith("|") && object_name.endsWith("|"))
          object_name.substring(1, object_name.length() - 1);
      else if(object_name.startsWith("<") && object_name.endsWith(">")) {
          return new Vector3D().constructFromString("", object_name);
      }
      try{ /* try parsing to double */
          double d = Double.parseDouble(object_name);
          return d;
      }catch(IllegalArgumentException ex) {}
      try {
        Object object = user_objects.get(object_name);
        return object;
      }catch(NullPointerException e) {} /* object not found */
      return null; /* MUST BE HANDLED!!! */
  }
  /* handles commands involving Vector3D objects */
  private Object handleVector3D(Vector3D vector_1, ArrayList<String> args, boolean constructor) {
      /* if constructor == true, need to pass a Vector3D object. Otherwise pass String */
  // todo: try-catch IllegalArgumentException
      if(args.size() == 1) { /* syntax is <Objectname> */
        if(constructor) {
            try {
                Vector3D vector_2 = (Vector3D) getObject(args.get(0));
                return vector_2;
            }catch(NullPointerException e) {
                /* return Error: "args.get(0)" is not defined */
                return msg.error() + msg.double_quote() + msg.variable() +
                        msg.double_quote() + args.get(0) + msg.var_does_not_exist();
            }catch(IllegalArgumentException e) {
                return getTypeError(args.get(0), new String[] {msg.vector() + msg.three_d()});
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
          Vector3D vector_2 = new Vector3D();
          switch(operator) { /* all these operations require a second Vector3D */
              case "+":
              case "-":
              case "//":
              case "|_":
              case "==":
                  vector_2 = getVector3D(getObject(args.get(2)));
                  break;
          }
        switch (operator) {
            case "+": /* vector addition */
                if(constructor)
                    return vector_1.addVector(vector_2);
                else
                    return vector_1.getName() + msg.plus_sign() +
                            vector_2.getName() + msg.equal_sign() +
                            vector_1.addVector(vector_2).getComponentForm();
            case "-": /* vector subtraction */
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
                        return vector_1.getName() + msg.multiply_sign() +
                                vector_2.getName() + msg.equal_sign() +
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
                    return getTypeError(args.get(2), new String[] {msg.type_double(),
                            msg.type_int(), msg.vector() + msg.three_d()});
                }
            case "/": /* scalar division */
                object_2 = getObject(args.get(2));
                if(object_2.getClass().equals(double.class)) { /* scalar division by double */
                    double d = (double) object_2;
                    if(constructor)
                        return vector_1.multiplyScalar(1 / d);
                    else
                        return vector_1.getName() + msg.divide_sign() + d +
                                msg.equal_sign() + vector_1.multiplyScalar(1 / d).getComponentForm();
                } else if(object_2.getClass().equals(int.class)) { /* scalar division by double */
                    int i = (int) object_2;
                    if(constructor)
                        return vector_1.multiplyScalar(1 / i);
                    else
                        return vector_1.getComponentForm() + msg.divide_sign() + i +
                                msg.equal_sign() + vector_1.multiplyScalar(1 / i).getComponentForm();
                } else {
                    return getTypeError(args.get(2), new String[] {msg.type_double(), msg.type_int()});
                }
            case "//": /* are vectors parallel */
                if(vector_1.isParallel(vector_2)) {
                    if(constructor)
                        return true;
                    else
                        return vector_1.getName() + msg.parallel() + vector_2.getName();
                } else {
                    if(constructor)
                        return false;
                    else
                        return vector_1.getName() + msg.not_parallel() + vector_2.getName();
                }
            case "|_": /* are vectors perpendicular */
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
                if(vector_1.equals(vector_2)) {
                    if(constructor)
                        return true;
                    else
                        return vector_1.getName() + msg.equal_to() + vector_2.getName();
                } else {
                    if(constructor)
                        return true;
                    else
                        return vector_1.getName() + msg.not_equal_to() + vector_2.getName();
                }
            default: /* return "Argument 'argument' was not recognized" */
                return msg.argument() + msg.space() + msg.double_quote() + args.get(1) + msg.not_recognized();
        }
    }
      return msg.generic_error();
  }
    /* returns "Error: function cannot be performed. "function" must be of type ... " */
    private String getTypeError(String function, String[] required_types) {
        String result = msg.error() + msg.cant_do_function() + msg.double_quote() + function +
                msg.double_quote() + msg.must_be_type() + required_types[0];
        for(int i = 1; i < required_types.length; i++) {
            result += msg.or() + required_types[i];
        }
        return result;
    }
    /* returns "Error: Variable <name> does not exist" */
    private String getConstructorError(String variable) {
        return msg.error() + msg.variable() + msg.double_quote() + variable + msg.double_quote() +
                msg.var_does_not_exist();
    }
    /* returns object as a Vector3D */
    private Vector3D getVector3D(Object o) {
        return (Vector3D) o;
    }
  private void Println(String s) {
    System.out.println(s);
  }
}
