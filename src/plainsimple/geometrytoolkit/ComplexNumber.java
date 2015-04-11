package plainsimple.geometrytoolkit;

import c10n.C10N;

import java.util.concurrent.CompletionException;

public class ComplexNumber {
    /* in the form a+bi */
    private double a;
    private double b;
    private String name;
    /* used to access C10N messages */
    private static final Messages msg = C10N.get(Messages.class);
    public ComplexNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }
    /* returns a */
    public double a() { return a; }
    /* returns b */
    public double b() { return b; }
    /* returns whether complex number is "complex" or just a double.
    If complex, b != 0 */
    public boolean isComplex() {
        return (b != 0);
    }
    /* complex number addition */
    public ComplexNumber add(Object addend) throws NumberFormatException {
        if(addend.getClass() == ComplexNumber.class) {
            ComplexNumber c = (ComplexNumber) addend;
            return new ComplexNumber(a + c.a(), b + c.b());
        } else if(addend.getClass() == double.class) {
            double d = (double) addend;
            return new ComplexNumber(a + d, b);
        } else
            throw new NumberFormatException(msg.type_error(msg.plus_sign(), msg.complex_num(), msg.type_double()));
    }
    /* complex number subtraction */
    public ComplexNumber subtract(Object addend) throws NumberFormatException {
        if(addend.getClass() == ComplexNumber.class) {
            ComplexNumber c = (ComplexNumber) addend;
            return new ComplexNumber(a - c.a(), b - c.b());
        } else if(addend.getClass() == double.class) {
            double d = (double) addend;
            return new ComplexNumber(a - d, b);
        } else
            throw new NumberFormatException(msg.type_error(msg.minus_sign(), msg.complex_num(), msg.type_double()));
    }
    /* complex number multiplication */
    public ComplexNumber multiply(Object addend) throws NumberFormatException {
        if(addend.getClass() == ComplexNumber.class) {
            ComplexNumber c = (ComplexNumber) addend;
            /* (a + bi)(c + di) = ac - bd + (ad + bc)i */
            return new ComplexNumber(a * c.a() - b * c.b(), a * c.b() + b * c.a());
        } else if(addend.getClass() == double.class) {
            double d = (double) addend;
            return new ComplexNumber(a * d, b * d);
        } else
            throw new NumberFormatException(msg.type_error(msg.multiply_sign(), msg.complex_num(), msg.type_double()));
    }
    /* complex number division */
    public ComplexNumber divide(Object addend) throws NumberFormatException {
        if(addend.getClass() == ComplexNumber.class) {
            ComplexNumber c = (ComplexNumber) addend;
            /* (a + bi)/(c + di) = (ac + bd) / (c^2 + d^2) - (ad + bc) / (c^2 + d^2)i */
            return new ComplexNumber((a * c.a() + b * c.b()) / (c.a() * c.a() + c.b() * c.b()),
                    (-(a * c.b() + b * c.a())) / (c.a() * c.a() + c.b() * c.b()));
        } else if(addend.getClass() == double.class) {
            double d = (double) addend;
            return new ComplexNumber(a / d, b / d);
        } else
            throw new NumberFormatException(msg.type_error(msg.divide_sign(), msg.complex_num(), msg.type_double()));
    }
    @Override public boolean equals(Object o) {
        if (o == null)
            return false;
        else if (o == this)
            return true;
        else if (o.getClass() != ComplexNumber.class)
            return false;
        else {
            ComplexNumber c = (ComplexNumber) o;
            return ((a == c.a()) && (b == c.b()));
        }
    }
    @Override public String toString() {
        return (a != 0 ? a : "") + (b > 0 ? "+" + b + "i" : "") + (b < 0 ? "-" + b + "i" : "");
    }
}
