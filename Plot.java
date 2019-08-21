import javax.swing.*;
import java.awt.*;
import java.util.Scanner;
import java.util.Stack;

/* Class that permits the input of any polynomial ( ideally between 0th degree and the 10th degree) and outputs the graphical representation of said     polynomial and its (x,y) coordinates for x in the range of all integers between -400 and 400 (inclusive)  */
public class Plot extends JFrame {
    private int xCoordinate;
    private Scanner expression;
    private String exp;
    public Plot() {
        System.out.println("P(x) = ");
        expression = new Scanner(System.in);
        exp = expression.nextLine();
        setTitle(exp);
        setSize(800,800);
        setVisible(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        xCoordinate = -400;
    }

    public void paint(Graphics g){
        g.setColor(Color.black);
        g.drawLine(0,400,800,400);
        g.drawLine(398,0,398,800);
        setForeground(Color.red);
        //offset origin point
        g.translate(396,398);
        g.setColor(Color.red);
        while (xCoordinate < 400){
            Double yCoordinate = -(calculate(infixToPostFix(exp)));
            if (yCoordinate.isNaN()) {
                xCoordinate++;
                continue;
            }
            else if (yCoordinate < -400 || yCoordinate > 400) {
                xCoordinate++;
                continue;
            }
            else {
                int yPosition = (int) Math.round(yCoordinate);
                g.fillOval(xCoordinate, yPosition, 8, 8);
                xCoordinate++;
            }
        }



    }
    private String infixToPostFix(String exp){
        Stack<String> stack = new Stack<String>();
        String postFixExpression = "";
        String [] expAsArray = exp.split(" ");
        int numberOfNegation = 0;
        for (int i = 0; i < expAsArray.length ;i++){
            String value = expAsArray[i];
            if (value.equals("-(")){
                numberOfNegation++;
                stack.push("(");
            }
            else if (value.equals("(")){
                stack.push(String.valueOf(expAsArray[i]));
            }
            else if (value.equals(")")){
                //placing the digits and operators in between parentheses first
                while (!stack.isEmpty() && !stack.peek().equals("(")){
                    postFixExpression = postFixExpression + stack.pop() + " ";
                }
                if (numberOfNegation > 0) {
                    postFixExpression = postFixExpression + "-1 " + "*" + " ";
                    numberOfNegation--;
                }
                // removing the open parentheses from consideration
                stack.pop();
            }
            // if it's a digit or letter, you can place it immediately in the expression.
            else if (value.matches("-?[1-9]\\d*|0")) {
                postFixExpression = postFixExpression +  value + " ";
            }
            else if (value.matches("-x")){
                postFixExpression = postFixExpression + String.valueOf(-xCoordinate) + " ";
            }
            else if (value.matches("x")){
                postFixExpression = postFixExpression + String.valueOf(xCoordinate) + " ";
            }
            // if it's a operand...
            else {
                /*While the stack isn't empty and the precedence of our current character is smaller then the precedence of the                         character at the top of the stack, you may remove the characters from the stacks and place it in the expression                     due to their precedence.*/
                while (!stack.isEmpty() && precedence(value) <= precedence(stack.peek())){
                    postFixExpression = postFixExpression +  stack.pop() + " ";
                }
                // place the operand of lower precedence in stack
                stack.push(String.valueOf(value));
            }
        }
        // Place the remaining operands in the expression
        while (!stack.isEmpty()){
            postFixExpression = postFixExpression + stack.pop() + " ";
        }
        return postFixExpression;
    }
    private int precedence(String op){
        switch (op) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":

                return 3;
            default: return -1;

        }
    }
    private Double calculate(String exp){
        Stack<String> stack = new Stack<>();
        String[] expAsArray = exp.split(" ");
        for (int i = 0; i < expAsArray.length; i++){
            String value = expAsArray[i];
            if (!value.equals("^") && !value.equals("/") && !value.equals( "*") && !value.equals("+") && !value.equals("-") ){
                stack.push(value);
            }
            else {
                String result = operation(stack.pop(),stack.pop(),value);
                stack.push(result);
            }

        }
        Double answer = Double.parseDouble(stack.pop());
        System.out.println("(" + xCoordinate + "," + answer + ")");
        return answer;
    }

    private String operation(String firstDigit,String secondDigit,String operation){
        switch (operation){
            case "+": return (String.valueOf(Double.parseDouble(firstDigit)+ Double.parseDouble(secondDigit)));
            case "-": return (String.valueOf(Double.parseDouble(secondDigit)- Double.parseDouble(firstDigit)));
            case "*": return (String.valueOf(Double.parseDouble(firstDigit)* Double.parseDouble(secondDigit)));
            case "/": return (String.valueOf(Double.parseDouble(secondDigit)/ Double.parseDouble(firstDigit)));
            case "^": return (String.valueOf(Math.pow(Double.parseDouble(secondDigit),Double.parseDouble(firstDigit))));
            default: return "0";
        }

    }
}
