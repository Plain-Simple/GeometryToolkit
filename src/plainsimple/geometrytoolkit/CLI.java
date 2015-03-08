package plainsimple.geometrytoolkit;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class CLI {
    private ArrayList<Vector3D> vector_3ds = new ArrayList<>();
    private ArrayList<Point3D> point_3ds = new ArrayList<>();
    private ArrayList<Line3D> line_3ds = new ArrayList<>();
    private ArrayList<Plane3D> plane_3ds = new ArrayList<>();
    public static void main(String args[]) {
        CLI cli = new CLI();
        cli.startCLI();
    }
    public void startCLI() {
        Println("Plain+Simple 3d Geometry Toolkit\n");
        while(true) {
            try {
                String user_input = getInput();



            } catch(IndexOutOfBoundsException|NoSuchElementException e) { // doesn't seem to catch anything really
                Println("Error: No Command Entered.");
            }
        }
    }
    public String getInput() {
        Println("Waiting on command...");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    public void Println(String s) {
        System.out.println(s);
    }
}
