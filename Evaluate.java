import java.util.Scanner;
import java.util.Stack;

/* Class that permits the evaluation of expressions and returns the value */
public class Evaluate {
    private Scanner expression;

    public Evaluate() {
        System.out.println("Your expression (spaces in between each element) : ");
        expression = new Scanner(System.in);
        String exp = expression.nextLine();
        pedmas(exp);

    }

    private void pedmas(String exp) {
       Double fullAnswer = calculate(infixToPostFix(exp));
       System.out.println("= " + fullAnswer);

    }
    private String infixToPostFix(String exp){
        Stack<String> stack = new Stack<String>();
        String postFixExpression = "";
        String [] expAsArray = exp.split(" ");
        int numberOfNegation = 0;
        for (int i = 0; i < expAsArray.length ;i++){
            String value = expAsArray[i];
            //If it's a negation
            if (value.equals("-(")) {
                stack.push("(");
                numberOfNegation++;
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
                    postFixExpression = postFixExpression + "-1" + " *" + " ";
                    numberOfNegation--;
                }
                // removing the open parentheses from consideration
                stack.pop();
            }
            // if it's a digit or letter, you can place it immediately in the expression.
            else if (value.matches("-?[1-9]\\d*|0|-?[0-9]\\d*\\.[0-9]\\d*")){
                postFixExpression = postFixExpression +  value + " ";
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
        //System.out.println(postFixExpression);
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
