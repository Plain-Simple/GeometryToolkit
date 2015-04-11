package plainsimple.geometrytoolkit;
import c10n.annotations.*;
public interface Messages {
  @En("Plain + Simple 3D Geometry Toolkit")
  String program_full_name();

  @En("No input entered")
  String no_input();

  @En("\nWaiting on command...")
  String command_waiting();

  @En(" created")
  String created();

  @En("Error occurred")
  String generic_error();

  @En("Error: ")
  String error();

  @En("Variable ")
  String variable();

  @En("Error: Variable \"{0}\" does not exist")
  String var_does_not_exist(String var_name);

  @En(" or constructor is invalid")
  String invalid_constructor();

  @En("Type \"help\" for usage information")
  String type_help();

  @En("\"")
  String double_quote();

  @En(" ")
  String space();

  @En("|")
  String magnitude_symbol();

  @En(" = ")
  String equal_sign();

  @En(" is not parallel to ")
  String not_parallel();

  @En(" is parallel to ")
  String parallel();

  @En(" is not perpendicular to ")
  String not_perpendicular();

  @En(" is perpendicular to ")
  String perpendicular();

  @En(" is equal to ")
  String equal_to();

  @En(" is not equal to ")
  String not_equal_to();

  @En("Error: Argument \"{0}\" not recognized")
  String arg_not_recognized(String arg_name);

  @En(" -> ")
  String arrow();

  @En(" - ")
  String minus_sign();

  @En(" + ")
  String plus_sign();

  @En(" * ")
  String multiply_sign();

  @En(" function cannot be performed. ")
  String cant_do_function();

  @En(" must be of type ")
  String must_be_type();

  @En(" double")
  String type_double();

  @En(" int")
  String type_int();

  @En(" or ")
  String or ();

  @En(" / ")
  String divide_sign();

  @En("Error creating object ")
  String error_creating_object();

  @En("The GeometryToolkit allows users to create objects, store them as variables, and manipulate them.\n"
      +
      "Currently supported objects:\n" +
      "Vector3D - A three dimensional vector in component form <x,y,z>\n" +
      "Point3D  - A three dimensional point in the form (x,y,z)\n" +
      "Plane3D  - A three dimensional plane represented by a Cartesian Equation\n" +
      "Line3D   - A three dimensional line represented as (x,y,z) + t<x,y,z>\n" +
      "Variables can be constructed using <VariableName> = <Variable> <Operator> <Variable>\n"
      +
      "or by using the Object's specialized constructor. \n" +
      "The command \"construct <ObjectType>\" can guide you through the process of creating that specific object.\n"
      +
      "For more detailed information on specific objects and their constructors, use \"help <ObjectType>\"")
  String general_help();

  @En("THE VECTOR3D OBJECT\n" +
      "Constructors:\n" +
      "\"<name> = <x,y,z>\" creates a Vector3D variable named <name> with component form <x,y,z>\n\n"
      +
      "Supported Operators:\n" +
      " <Vector3D>                  Outputs Vector in component form\n" +
      "|<Vector3D>|                 Magnitude calculation\n" +
      "<Vector3D> +/- <Vector3D>    Vector addition/subtraction\n" +
      "<Vector3D>  *  <Vector3D>    Vector dot product\n" +
      "<Vector3D>  *  < Number >    Scalar multiplication\n" +
      "<Vector3D>  /  < Number >    Scalar division\n" +
      "<Vector3D>  x  <Vector3D>    Vector cross product\n" +
      "<Vector3D>  // <Vector3D>    Returns TRUE if vectors are parallel, otherwise FALSE\n"
      +
      "<Vector3D>  |_ <Vector3D>    Returns TRUE if vectors are perpendicular, otherwise FALSE\n"
      +
      "<Vector3D>  == <Vector3D>    Returns TRUE if vectors are equivalent, otherwise FALSE")
  String vector3d_help();

  @En("The POINT3D OBJECT\n" +
      "Constructors:\n" +
      "\"<name> = (x,y,z)\" creates a Point3D variable named <name> with coordinates x, y, z\n\n"
      +
      "Supported Operators:\n" +
      "<Point3D>                   Outputs Point in coordinate form\n" +
      "<Point3D>  -  <Point3D>     Distance between points\n" +
      "<Point3D>  == <Point3D>     Returns whether points are equal\n")
  String point3d_help();

  @En("The PLANE3D Object\n" +
      "Constructors:\n" +
      "\"<name> = <x,y,z>\"\n" +
      "<name> = " +
      "Supported Operators:\n" +
      "<Plane3D>                    Outputs Cartesian Equation of plane\n" +
      "<Plane3D>  -  <Plane3D>      Distance between planes\n" +
      "<Plane3D>  -  <Point3D>      Distance between point and plane\n" +
      "<Plane3D>  -   <Line3D>      Distance between line and plane\n" +
      "<Plane3D>  ct <Point3D>      Returns TRUE if Plane contains Point\n" +
      "<Plane3D>  ")

  String plane3d_help();

    @En("The LINE3D OBJECT\n" +
       "Constructors:\n" +
       "\"<name> = (x,y,z)+t<x,y,z> Creates a Line3D variable that ...\n\n" +
            "Supported Operators:\n")
    String line3d_help();

    @En("The POINT2D OBJECT\n" +
       "Constructors:\n" +
       "\"<name>\" = (x,y,z) \n" +
       "Supported Operators:\n" +
       "<Point2D>                   Outputs Coordinates of point\n" +
       "<Point2D>  -  <Point2D>     Distance between points\n" +
       "<Point2D>  == <Point2D>     Returns whether points are equal\n")
    String point2d_help();

  @En("Error: argument \"{0}\" was not recognized")
  String object_not_recognized(String object);

    @En("Error: \"{0}\" takes a maximum of {1} parameters")
    String max_parameter_error(String function, int max_parameters);

    @En("Error: \"{0}\" takes a minimum of {1} parameter(s)")
    String min_parameter_error(String function, int min_parameters);

    @En("Error: Class \"{0}\" not recognized")
    String class_not_recognized(String class_name);

    @En("Variable \"{0}\" successfully renamed to \"{1}\"")
    String rename_success(String original_name, String new_name);

    @En("Variable \"{0}\" successfully removed")
    String remove_success(String object_name);

    @En("All variables cleared")
    String clear_success();

    @En("Error: Input required")
    String input_required();

    @En("No objects to show")
    String no_objects();

    @En("Error: {0} index \"{1}\" is out of bounds")
    String index_out_of_bounds(String object, String index);

    @En("Matrix")
    String matrix();

    @En(",")
    String comma();

    @En("Size Error: Matrices cannot be {0}")
    String matrices_size_error(String function);

    @En("multiplied")
    String multiplied();

    @En("added")
    String added();

    @En("merged")
    String merged();

    @En("inverted")
    String inverted();

    @En("Error: Operator \"{0}\" accepts objects of type {1}")
    String type_error(String operator, String object_type);

    @En("Error: Operator \"{0}\" accepts objects of accept objects of type {1} or {2}")
    String type_error(String operator, String object_type1, String object_type2);

    @En("Error: Operator \"{0}\" accepts objects of type {1}, {2}, {3}, or {4}")
    String type_error(String operator, String object_type, String object_type2,
                      String object_type3, String object_type4);

    @En("Complex Number")
    String complex_num();

    @En("Vector2D")
    String vector_2d();

    @En("Vector3D")
    String vector_3d();

    @En("Point2D")
    String point_2d();

    @En("Point3D")
    String point_3d();

    @En("3D")
    String three_d();

    @En("2D")
    String two_d();
}
