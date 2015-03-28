package plainsimple.geometrytoolkit;
import c10n.C10N;

import java.util.ArrayList;

public class Help {
    /* used to access C10N messages */
    private static final Messages msg = C10N.get(Messages.class);
    /* handles help commands */
    public String handleHelp(ArrayList<String> args) {
        if (1 == args.size()) { /* print general help */
            return generalHelp();
        } else if (2 == args.size()) { /* help <ObjectType> */
            switch (args.get(1)) {
                case "Vector3D":
                case "vector3d":
                case "Vector3d":
                    return vector3dHelp();
                case "Point3D":
                case "point3d":
                case "Point3d":
                    return point3dHelp();
                case "Point2D":
                case "point2d":
                case "Point2d":
                    return point2dHelp();
                default:
                    return (msg.variable_error(args.get(1)));
            }
        } else
            return msg.parameter_error("help", 2);
    }
    private String generalHelp() {
        return msg.general_help();
    }
    private String vector3dHelp() {
        return msg.vector3d_help();
    }
    private String point3dHelp() {
        return msg.point3d_help();
    }
    private String point2dHelp() { return msg.point2d_help(); }
}
