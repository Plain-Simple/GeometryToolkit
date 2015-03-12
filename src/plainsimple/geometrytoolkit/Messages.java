package plainsimple.geometrytoolkit;
import c10n.annotations.*;
public interface Messages {
  @En("Plain + Simple 3D Geometry Toolkit")
  String program_full_name();

  @En("No input entered")
  String no_input();

  @En("Waiting on command...")
  String command_waiting();

  @En("Vector")
  String vector();

  @En("created")
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

    @En("Argument")
    String argument();

    @En(" was not recognized")
    String not_recognized();
}
