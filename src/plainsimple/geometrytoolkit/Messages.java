package plainsimple.geometrytoolkit;
import c10n.annotations.*;
public interface Messages {
  @En("Plain + Simple 3D Geometry Toolkit")
  String program_full_name();

  @En("No input entered")
  String no_input();

  @En("Waiting on command...")
  String command_waiting();

  @En("Vector ")
  String vector();

  @En(" created")
  String created();

  @En("Error occurred")
  String generic_error();

    @En("Error: ")
    String error();

    @En("Variable ")
    String variable();

    @En(" does not exist")
    String var_does_not_exist();

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

    @En("Argument ")
    String argument();

    @En(" was not recognized")
    String not_recognized();

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

    @En("3D")
    String three_d();

    @En(" or ")
    String or();

    @En(" / ")
    String divide_sign();

    @En("Error creating object ")
    String error_creating_object();

    @En("The GeometryToolkit allows users to create objects, store them as variables, and manipulate them.\n" +
            "Currently supported objects:\n" +
            "Vector3D - A three dimensional vector in component form <x,y,z>\n" +
            "Point3D  - A three dimensional point in the form (x,y,z)\n" +
            "Plane3D  - A three dimensional plane represented by a Cartesian Equation\n" +
            "Line3D   - A three dimensional line represented as (x,y,z) + t<x,y,z>\n" +
            "Variables can be constructed using <VariableName> = <Variable> <Operator> <Variable>\n" +
            "or by using the Object's specialized constructor. \n" +
            "The command \"construct <ObjectType>\" can guide you through the process of creating that specific object.\n" +
            "For more detailed information on specific objects and their constructors, use \"help <ObjectType>\"")
    String general_help();

    @En("THE VECTOR3D OBJECT\n" +
            "Constructors:\n" +
            "\"<name> = <x,y,z>\" creates a Vector3D variable named <name> with component form <x,y,z>\n\n" +
            "Supported Operators:\n" +
            " <Vector3D>                  Outputs Vector in component form\n" +
            "|<Vector3D|                  Magnitude calculation\n" +
            "<Vector3D> +/- <Vector3D>    Vector addition/subtraction\n" +
            "<Vector3D>  *  <Vector3D>    Vector dot product\n" +
            "<Vector3D>  *  < Number >    Scalar multiplication\n" +
            "<Vector3D>  /  < Number >    Scalar division\n"+
            "<Vector3D>  x  <Vector3D>    Vector cross product\n" +
            "<Vector3D>  // <Vector3D>    Returns TRUE if vectors are parallel, otherwise FALSE\n" +
            "<Vector3D>  |_ <Vector3D>    Returns TRUE if vectors are perpendicular, otherwise FALSE\n" +
            "<Vector3D>  == <Vector3D>    Returns TRUE if vectors are equivalent, otherwise FALSE")
    String vector3d_help();

}
