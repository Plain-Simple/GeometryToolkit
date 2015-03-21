package plainsimple.geometrytoolkit;

import c10n.C10N;
import c10n.annotations.DefaultC10NAnnotations;

public class Main {
  public static void main(String[] args) {
    /* required for i18n */
    C10N.configure(new DefaultC10NAnnotations());
    CLI cli = new CLI();
    cli.startCLI();
  }
}
