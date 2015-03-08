package plainsimple.geometrytoolkit;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CLI {
    private ArrayList<Vector3D> vector_3ds = new ArrayList<>();
    private ArrayList<Point3D> point_3ds = new ArrayList<>();
    private ArrayList<Line3D> line_3ds = new ArrayList<>();
    private ArrayList<Plane3D> plane_3ds = new ArrayList<>();
    private Pattern user_command_pattern = Pattern.compile("([^\\s\"\']+)|\"([^\"]*)\"|\'([^\']*)\'");
    public static void main(String args[]) {
        CLI cli = new CLI();
        cli.startCLI();
    }
    public void startCLI() {
        Println("Plain+Simple 3d Geometry Toolkit\n");
        while(true) {
            String user_input = getInput();
            if(user_input.isEmpty())
                Println("No input entered.");
            else
                processInput(user_input);
        }
    }
    public String getInput() {
        Println("Waiting on command...");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public void processInput(String user_input) {
        ArrayList<String> arguments = parseInput(user_input);
        if(arguments.get(0).equals("new")) {
            boolean create_success;
            switch(arguments.get(1)) {
                case ("Vector3D"):
                    create_success = createNewVector3D(arguments);
                    break;
            }
        } else if(arguments.get(0).equals("add")) {
            Vector3D sum = getVector(arguments.get(1)).addVector(getVector(arguments.get(2)));
            Println("Sum is " + sum.getVectorAsString());
        }
    }
    public ArrayList<String> parseInput(String user_input) {
        ArrayList<String> arguments = new ArrayList<String>();
        Matcher matcher = user_command_pattern.matcher(user_input);
        boolean found = false;
        while(matcher.find()) {
            if(matcher.group(1) != null) { /* if argument is space-separated */
                arguments.add(matcher.group(1));
            } else if(matcher.group(2) != null) { /* if argument is enclosed in double-quotes */
                arguments.add(matcher.group(2));
            } else if(matcher.group(3) != null) { /* if argument is enclosed in single-quotes */
                arguments.add(matcher.group(3));
            }
            found = true;
        }
        return arguments;
    }
    public boolean createNewVector3D(ArrayList<String> args) {
        try {
            Vector3D new_vector3d = new Vector3D(args.get(2));
            double x = Double.parseDouble(args.get(3));
            double y = Double.parseDouble(args.get(4));
            double z = Double.parseDouble(args.get(5));
            new_vector3d.setCoordinates(x, y, z);
            vector_3ds.add(new_vector3d);
            Println("Vector " + new_vector3d.getName() + " created: "
                    + new_vector3d.getVectorAsString());
            return true;
        }catch(Exception e) {
            Println("Error occurred");
            return false;
        }
    }
    public Vector3D getVector(String vector_name) {
        for(int i = 0; i < vector_3ds.size(); i++) {
            if(vector_3ds.get(i).getName().equals(vector_name))
                return vector_3ds.get(i);
        }
        return null;
    }
    public void Println(String s) {
        System.out.println(s);
    }
}
