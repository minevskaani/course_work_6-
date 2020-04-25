package io.raytracing.math;

// TODO
public class MathUtil {

    public final static double EQ_EPS = 1e-90;
    public final static double DOUBLE_PI = Math.PI * 2;

    public static boolean isZero(double x) {
        return x > -EQ_EPS && x < EQ_EPS;
    }

    public static double min(double a, double b) {
        return Double.min(a, b);
    }

    public static int solveQuadric(double t1, double t2, double[] params) {
        assert(params.length == 3);

        double a = params[0];
        double b = params[1];
        double c = params[2];

        double a2 = 2 * a;
        double b_a2 = -b / a2;
        double disc = b * b - 4 * a * c;

        if (isZero(disc)) {
            t1 = t2 = b_a2;

            return 1;
        } else if (disc > 0.0) {
            double sdisc_a2 = Math.sqrt(disc) / a2;

            t1 = b_a2 - sdisc_a2;
            t2 = b_a2 + sdisc_a2;

            return 2;
        }

        return 0;
    }

    public static int solveQuadric(double[] c, double[] s) {
        assert(c.length == 5);
        assert (s.length == 4);

        double p, q, D;

        // normal form: x^2 + px + q = 0

        p = c[1] / (2 * c[2]);
        q = c[0] / c[2];

        D = p * p - q;

        if (isZero(D)) {
            s[0] = - p;

            return 1;
        } else if (D > 0) {
            double sqrt_D = Math.sqrt(D);

            s[ 0 ] =   sqrt_D - p;
            s[ 1 ] = - sqrt_D - p;

            return 2;
        }

        return 0;
    }

    double checkValue(double x) {
        return 0;
    }
}
