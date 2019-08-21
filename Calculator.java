import java.util.Scanner;
/* Asks the user what he wants to calculate,graph etc.
 * Executes the command */
public class Calculator {
    private Scanner functionToBeCalled, expression;
    private final String[] functions = {"Evaluate (0) ", "Integrate (1) ", "Solve (2) ", "Plot (3) "};

    public Calculator() {
        System.out.println("Choose your function");
        String Instructions = "";
        for (int i = 0; i < functions.length; i++) {
            Instructions = Instructions + functions[i] + " ";
        }
        System.out.print(Instructions);
        for (int i = 0; i < 6; i++) {
            System.out.print("\n");
        }
        functionToBeCalled = new Scanner(System.in);
        int function_called = functionToBeCalled.nextInt();
        for (int i = 0; i < 6; i++) {
            System.out.print("\n");
        }
        execute(function_called);
    }
    private void execute(int function){
        switch(function){
            case 0 :
                Evaluate evaluation = new Evaluate();
                break;
            case 1 :
                Integrate intergration  = new Integrate();
                break;
            case 2 :
                Solve solve = new Solve();
                break;
            case 3 :
                Plot graph = new Plot();
                break;
            default :
                System.out.println("Invalid function");

        }


    }
}
