import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.integration.*;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;


import java.util.Scanner;

// Class that permits the integration of any polynomial
public class Integrate {
    private UnivariateFunction fx;
    private int max,min;
    public Integrate() {

        // Input degree of the polynomial
        System.out.println("Degree of polynomial : ");
        Scanner input = new Scanner(System.in);
        int degree = input.nextInt();

        // Input it's coefficients, starting from the constant all the way to the nth degree.
        double[] coefficient = new double[degree + 1];
        for (int i = 0; i <= degree; i++) {
            System.out.print("Coefficient : ");
            input = new Scanner(System.in);
            coefficient[i] = input.nextDouble();
        }

        // Input range
        System.out.println("Min : ");
        input = new Scanner(System.in);
        min = input.nextInt();

        System.out.println("Max : ");
        input = new Scanner(System.in);
        max = input.nextInt();

        // Setup the polynomial.
        fx = new PolynomialFunction(coefficient);

        // Input the method of integration.
        System.out.println("Method : Simpson (0) Mid-point (1) Trapezoid (2) Romberg (3) Legendre-Gauss (4) ");
        input = new Scanner(System.in);
        int method = input.nextInt();
        integration(method);

    }

    // Prints out the approximate answer depending on the method of integration.
    private void integration(int method){
        switch (method){
            case 0 :
                SimpsonIntegrator simpsonIntegrator = new SimpsonIntegrator();
                System.out.println(fx);
                System.out.println("Simpson integration : " + simpsonIntegrator.integrate(1000000000,fx,min,max));
                break;

            case 1 :
                 MidPointIntegrator midPointIntegrator= new MidPointIntegrator();
                System.out.println(fx);
                System.out.println("Mid-point integration : " + midPointIntegrator.integrate(1000000000,fx,min,max));
                break;
            case 2 :
                TrapezoidIntegrator trapezoidIntegrator = new TrapezoidIntegrator();
                System.out.println(fx);
                System.out.println("Trapezoid integration : " + trapezoidIntegrator.integrate(1000000000,fx,min,max));
                break;
            case 3 :
                 RombergIntegrator rombergIntegrator = new RombergIntegrator();
                System.out.println(fx);
                 System.out.println("Romberg integration : " + rombergIntegrator.integrate(1000000000,fx,min,max));
                 break;
            case 4 :
                IterativeLegendreGaussIntegrator iterativeLegendreGaussIntegrator = new IterativeLegendreGaussIntegrator(10,1,100);
                System.out.println(fx);
                System.out.println("Legendre-Gauss integration : " + iterativeLegendreGaussIntegrator.integrate(1000000000,fx,min,max));
                break;
                default: System.out.println("Invalid input");
                break;
         }
    }
}
