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
    Pattern.compile("([^\\s\\^\\*/!%\\+\\-[->][==]]+)|([\\^\\*/!%\\+\\-[->][==]])");

  public void startCLI() {
      user_objects.loadObjects("GeometryToolkit_SavedObjects");
      Println(msg.program_full_name());
    String user_input;
    do {
      Println(msg.command_waiting());
      user_input = getInput();
      if(user_input.isEmpty()) {
        Println(msg.no_input());
      } else {
        processInput(user_input);
      }
    } while (!user_input.equals("exit"));
  }
  private String getInput() {
    final Scanner scanner = new Scanner(System.in);
    return scanner.nextLine();
  }
  private void processInput(String user_input) {
    System.out.print(">> ");
    ArrayList<String> arguments = parseInput(user_input); // use hash = hash * 13 + (dept == null ? 0 : dept.hashCode());
    switch (arguments.get(0)) {
    case "help":
    case "Help":
      Println(help.handleHelp(arguments));
      break;
    case "list":
    case "List":
        if(1 == arguments.size()) /* list all objects */
            Println(user_objects.list());
        else if(2 == arguments.size()) /* list objects of specified type */
            Println(user_objects.list(arguments.get(1)));
        else /* too many args */
            Println(msg.max_parameter_error("list", 2));
      break;
    case "remove":
    case "Remove":
        if(1 == arguments.size()) { /* remove all objects */
            Println(user_objects.clear());
            user_objects.writeObjects("GeometryToolkit_SavedObjects");
        } else if(2 == arguments.size()) { /* remove specified object */
            Println(user_objects.remove(arguments.get(1)));
        } else /* too many args */
            Println(msg.max_parameter_error("remove", 2)); // todo: messages
        break;
    case "rename":
    case "Rename":
        if(3 == arguments.size()) {
            Println(user_objects.rename(arguments.get(1), arguments.get(2)));
        } else if(arguments.size() > 3) {
            Println(msg.max_parameter_error("rename", 3));
        } else {
            Println(msg.min_parameter_error("rename", 3));
        }
        break;
    case "New":
    case "new":
        if(2 == arguments.size()) {
            switch(arguments.get(1)) {
                case "Matrix":
                    MatrixConstructor();
            }
        } else if(arguments.size() > 2) {
            Println(msg.max_parameter_error("new", 2));
        } else {
            Println(msg.min_parameter_error("new", 2));
        }
    break;
    default:
      loadObject(arguments);
    }
  }
  private void loadObject(ArrayList<String> arguments) {
    if (arguments.contains("=")) {
        Constructor new_object = new Constructor(arguments);
        if(new_object.create()) {
            user_objects.put(new_object.getName(), new_object.getObject());
            user_objects.writeObjects("GeometryToolkit_SavedObjects"); /* update file */
        }
        Println(new_object.getMessage());
    } else {
      try { /* user is referencing an object - look in list of objects */
        Object referenced_object = user_objects.get(arguments.get(0));
        /* get class of object so it can be converted to proper object */
        Class object_class = referenced_object.getClass();
        Object output = msg.generic_error(); /* temporary */
        try {
          if (object_class.equals(Vector3D.class)) {
            output = handleVector3D(referenced_object, arguments);
          } else if (object_class.equals(Vector2D.class)) {
            output = handleVector2D(referenced_object, arguments);
          } else if(object_class.equals(Point3D.class)) {
            output = handlePoint3D(referenced_object, arguments);
          } else if(object_class.equals(Point2D.class)) {
            output = handlePoint2D(referenced_object, arguments);
          } else if(object_class.equals(Double.class)) {
              output = referenced_object;
          } else if(object_class.equals(GeometryObject.class)) {
              output = ((GeometryObject) referenced_object).toString();
          }
        } catch(IllegalArgumentException e) {
        }
        Println((new GeometryObject(output)).toString());
      } catch (NullPointerException e) { /* arguments.get(0) not a valid object */
        Println(msg.var_does_not_exist(arguments.get(0)));
      }
    }
  }

  private ArrayList<String> parseInput(String user_input) {
    ArrayList<String> arguments = new ArrayList<>();
    Matcher matcher = user_command_pattern.matcher(user_input);
    while(matcher.find()) {
      if(null != matcher.group(1)) { /* argument is space-separated */
        arguments.add(matcher.group(1));
      } else if(null != matcher.group(2)) {
        arguments.add(matcher.group(2));
      } else if(null != matcher.group(3)) {
        arguments.add(matcher.group(3));
      }
    }
    return arguments;
  }
  /* handles commands involving Vector3D objects */
  public Object handleVector3D(Object vector_3d, ArrayList<String> args) {
    /* if constructor == true, need to pass a Vector3D object. Otherwise pass String */
    // todo: try-catch IllegalArgumentException. Allow constructor Point3D->Point3D. Implement remaining functions
      Vector3D vector_1 = (Vector3D) vector_3d;
    if(1 == args.size()) /* syntax is <Objectname> */
          return user_objects.get(args.get(0));
    if(3 == args.size()) { /* syntax is <Objectname> <operator> <Objectname> */
      String operator = args.get(1);
      Object object_2 = user_objects.get(args.get(2));
      switch (operator) {
      case "+": /* vector addition */
        return vector_1.add(object_2);
      case "-": /* vector subtraction */
        return vector_1.subtract(object_2);
      case "*":
        if(object_2.getClass().equals(Vector3D.class)) { /* vector dot product */
          return vector_1.dot(object_2);
        } else if(object_2.getClass().equals(Double.class) || object_2.getClass().equals(double.class)) { /* scalar multiplication */
          return vector_1.multiply(object_2);
        } else {
          return getTypeError(args.get(2), new String[]{msg.type_double(),
                  msg.type_int(), msg.vector() + msg.three_d()});
        }
      case "x":
        return vector_1.cross(object_2);
      case "/": /* scalar division */
          return vector_1.divide(Double.parseDouble(args.get(2)));
      case "//": /* are vectors parallel */
        return  vector_1.isParallel(object_2);
      case "|_": /* are vectors perpendicular */
        return vector_1.isPerpendicular(object_2);
      case "==": /* are vectors equal */
        return vector_1.equals(object_2);
      case "->": /* constructor from vector to vector or vector to point */
          object_2 = user_objects.get(args.get(2));
          if(object_2.getClass().equals(Vector3D.class)) {
              return new Vector3D(vector_1, (Vector3D) object_2);
          } else if(object_2.getClass().equals(Point3D.class)) {
              return new Vector3D(vector_1, (Point3D) object_2);
          }
      default: /* return "Argument 'argument' was not recognized" */
        return msg.arg_not_recognized(args.get(1));
      }
    }
    return msg.generic_error();
  }
    public Object handleVector2D(Object vector_2d, ArrayList<String> args) {
    /* if constructor == true, need to pass a Vector2D object. Otherwise pass String */
        Vector2D vector_1 = (Vector2D) vector_2d;
        if(1 == args.size()) { /* syntax is <Objectname> */
                try {
                    Vector2D vector_2 = (Vector2D) user_objects.get(args.get(0));
                    return vector_2;
                } catch(NullPointerException e) {
          /* return Error: "args.get(0)" is not defined */
                    return msg.var_does_not_exist(args.get(0));
                } catch(IllegalArgumentException e) {
                    return getTypeError(args.get(0), new String[] {msg.vector() + msg.two_d()});
                }
        }
        if(3 == args.size()) { /* syntax is <Objectname> <operator> <Objectname> */
            String operator = args.get(1);
            Object object_2 = user_objects.get(args.get(2));
            Vector2D vector_2 = new Vector2D();
            Double d = new Double(0.0);
            switch(operator) { /* all these operations require a second Vector3D */
                case "+":
                case "-":
                case "//":
                case "|_":
                    vector_2 = (Vector2D) object_2;
                    break;
                case "/":
                    d = Double.parseDouble(args.get(2));
                    break; // return getTypeError(args.get(2), new String[] {msg.type_double(), msg.type_int()});
                default: // todo: error message: invalid operator

            }
            switch (operator) {
                case "+": /* vector addition */
                    return vector_1.addVector(vector_2);
                case "-": /* vector subtraction */
                    return vector_1.addVector(vector_2.multiplyScalar(-1));
                case "*":
                    if (object_2.getClass().equals(Vector2D.class)) { /* vector dot product */
                        return vector_1.dot((Vector2D) object_2);
                    } else if (object_2.getClass().equals(Double.class)) { /* scalar multiplication */
                        return vector_1.multiplyScalar((double) object_2);
                    } else {
                        return getTypeError(args.get(2), new String[]{msg.type_double(),
                                msg.type_int(), msg.vector() + msg.three_d()
                        });
                    }
                case "/": /* scalar division */
                    return vector_1.divideScalar(d);
                case "//": /* are vectors parallel */
                    return vector_1.isParallel(vector_2);
                case "|_": /* are vectors perpendicular */
                    return vector_1.isPerpendicular(vector_2);
                case "==": /* are vectors equal */
                    return vector_1.equals(object_2);
                case "->":
                    object_2 = user_objects.get(args.get(2));
                    if(object_2.getClass().equals(Vector2D.class)) {
                        return new Vector2D(vector_1, (Vector2D) object_2);
                    } else if(object_2.getClass().equals(Point2D.class)) {
                        return new Vector2D(vector_1, (Point2D) object_2);
                    }
                default: /* return "Argument 'argument' was not recognized" */
                    return msg.arg_not_recognized(args.get(1));
            }
        }
        return msg.generic_error();
    }
    public Object handlePoint3D(Object point_3d, ArrayList<String> args) {
        Point3D point_1 = (Point3D) point_3d;
        if (1 == args.size()) { /* syntax is <Objectname> */
                try {
                    Point3D point_2 = (Point3D) user_objects.get(args.get(0));
                    return point_2;
                } catch (NullPointerException e) {
          /* return Error: "args.get(0)" is not defined */
                    return msg.var_does_not_exist(args.get(0));
                } catch (IllegalArgumentException e) {
                    return getTypeError(args.get(0), new String[]{msg.point() + msg.three_d()});
                }
        }
        if (3 == args.size()) { /* syntax is <Objectname> <operator> <Objectname> */
            String operator = args.get(1);
            Point3D point_2 = new Point3D();
            switch (operator) { /* all these operations require a second Vector3D */
                case "-":
                case "==":
                    point_2 = (Point3D) user_objects.get(args.get(2));
                    break;
            }
            switch (operator) {
                case "-":
                    return point_1.getDistance(point_2);
                case "==":
                    return point_1.equals(point_2);
                case "->":
                    Object object_2 = user_objects.get(args.get(2));
                    if(object_2.getClass().equals(Vector3D.class)) {
                        return new Vector3D((Vector3D) object_2, point_1);
                    } else if(object_2.getClass().equals(Point3D.class)) {
                        return new Vector3D((Point3D) object_2, point_1);
                    }
                    break;
                default:
                    return msg.arg_not_recognized(args.get(1));
            }
        }
        return  msg.generic_error();
    }
    public Object handlePoint2D(Object point_2d, ArrayList<String> args) {
        Point2D point_1 = (Point2D) point_2d;
        if (1 == args.size()) { /* syntax is <Objectname> */
                try {
                    Point2D point_2 = (Point2D) user_objects.get(args.get(0));
                    return point_2;
                } catch (NullPointerException e) {
          /* return Error: "args.get(0)" is not defined */
                    return msg.var_does_not_exist(args.get(0));
                } catch (IllegalArgumentException e) {
                    return getTypeError(args.get(0), new String[]{msg.point() + msg.two_d()});
                }
        }
        if (3 == args.size()) { /* syntax is <Objectname> <operator> <Objectname> */
            String operator = args.get(1);
            Point2D point_2 = new Point2D();
            switch (operator) { /* all these operations require a second Vector3D */
                case "-":
                case "==":
                    point_2 = (Point2D) user_objects.get(args.get(2));
                    break;
            }
            switch (operator) {
                case "-":
                    return point_1.getDistance(point_2);
                case "==":
                    return point_1.equals(point_2);
                case "->":
                    Object object_2 = user_objects.get(args.get(2));
                    if(object_2.getClass().equals(Vector2D.class)) {
                        return new Vector2D((Vector2D) object_2, point_1);
                    } //else if(object_2.getClass().equals(Point2D.class)) {
                      //  return new Vector2D((Point2D) object_2, point_1);
                default:
                    return msg.arg_not_recognized(args.get(1));
            }
        }
        return  msg.generic_error();
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
  private void MatrixConstructor() {
      int row_counter = 1;
      boolean keepGoing = true;
      Println("Enter name of matrix to be created: ");
      String name = getInput();
      Matrix new_matrix = new Matrix(name);
      while(keepGoing) {
          try {
              Println("Enter row " + row_counter + " (space-separated):");
              ArrayList<String> next_row = parseInput(getInput());
              if (next_row.isEmpty()) {
                  keepGoing = false;
              } else {
                  RowVector args = new RowVector();
                  for (int i = 0; i < next_row.size(); i++)
                      args.addElement(Double.parseDouble(next_row.get(i)));
                  if (row_counter == 1) {
                      new_matrix.setSize(args.size());
                  }
                  new_matrix.addRow(args);
                  user_objects.put(name, new_matrix);
                  row_counter++;
                  keepGoing = true;
              }
          } catch(IndexOutOfBoundsException e) { // todo: C10N
              Println("Exception!");
              Println(e.getMessage());
          } catch(NumberFormatException e) {
              Println("All entries must be numbers!");
              Println(e.getMessage());
          }
          Println(new_matrix.toString());
      }
  }
  private void Println(String s) {
    System.out.println(s);
  }
}
