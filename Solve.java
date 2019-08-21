import Jama.Matrix;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Scanner;

// Class that solves any system of linear equation or a singular equation.
public class Solve {
    private Matrix matrix;
    private double[] answerAsDoubleArray;
    private double[] unknowns ;
    private  String[] valuesAsArray;

    public Solve() {

        System.out.println("Is it a linear equation (0) or a system of linear equations (1) ?");
        Scanner input = new Scanner(System.in);
        int answer = input.nextInt();
        solution(answer);

    }

    public void solveForNxN() {

        System.out.print("Value of N+1 (N number of equations and unknowns) ? : ");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();

        double[][] tmpMatrix = new double[n][n];

        answerAsDoubleArray = new double[n];

       // unknowns
        unknowns = new double[n];

        /* Take the input of the user and saves the coefficients of the unknowns in tmpMatrix,
        while saving the value of the equation in valueAsDoubleArray.
         */
        System.out.println("Coefficients of unknowns and answers : ");
        input = new Scanner(System.in);
        for (int i = 0; i < n; i++) {
            String valuesAsString = input.nextLine();
            valuesAsArray = valuesAsString.split("\\s+");
            for (int j = 0; j < n ; j++) {
                // Saves coefficients as a double.
                tmpMatrix[i][j] = Double.parseDouble(valuesAsArray[j]);
            }

            /*System.out.println("D");
            input = new Scanner(System.in);
            valuesAsString = input.nextLine();*/

            // Saves value as a double.
            answerAsDoubleArray[i] = Double.parseDouble(valuesAsArray[n]);

        }

        matrix = new Matrix(tmpMatrix);
        Long startTime = System.nanoTime();

        //Inverse the matrix
        Matrix inverseMatrix = matrix.inverse();

        //Multiply the inverse matrix with the values of the equations and calculate it's sum ( matrix multiplication)
        for (int i = 0 ; i < n ; i++){
            for (int j = 0 ; j < n ; j++){
                unknowns[i] = unknowns[i] +  inverseMatrix.get(i,j) * answerAsDoubleArray[j];
            }
        }

        //Print answer
        printUnknownsNxN(n);

        Long endTime = System.nanoTime();
        System.out.println("Duration = " + (endTime-startTime) + " ns");



    }

    private void solveLinearEquation() {

            unknowns = new double[1];


            // Input for the coefficient of X
            System.out.println("X ");
            Scanner input = new Scanner(System.in);
            String valuesAsString = input.nextLine();

            // Saves coefficient as a double.
            Double coefficientAsDouble = Double.parseDouble(valuesAsString);

            // Input for the equation answer.
            System.out.println("D");
            input = new Scanner(System.in);
            valuesAsString = input.nextLine();

            // Saves value as a double.
            Double answerAsDouble = Double.parseDouble(valuesAsString);

            unknowns[0] = answerAsDouble / coefficientAsDouble;

        //Print answer
        printUnknownsLinearEquation();
    }

    // Checks if you want to solve a 3x3 matrix, a 2x2 matrix, a linear equation or if it's a invalid input.
    private void solution(int question) {
        switch (question) {
            case 0 :
                solveLinearEquation();
                break;
            case 1 :
                solveForNxN();
                break;
            default:
                System.out.println("Invalid input");

        }

    }

    // Method that prints the unknowns of a 3x3 matrix.
    private void printUnknownsNxN(int n){
        for (int i = 0 ; i < n ; i++){
            System.out.println("Unknown #" + (i+1) + " = " + unknowns[i]);
        }
    }


    private void printUnknownsLinearEquation(){

        NumberFormat formatter = new DecimalFormat("#0.000");
        System.out.print('\n');
        System.out.println("X = " + formatter.format(unknowns[0]));
    }



}
