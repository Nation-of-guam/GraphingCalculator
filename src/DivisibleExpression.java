/**
 * @author Aidan Burns  12/1/2020
 * This project does MultiplicitaveExpression on the IntelliJ IDEA
 */

import java.util.*;

public class DivisibleExpression implements Expression {
    private Expression left, right;
    private boolean isMultiply;

    public DivisibleExpression(String str, int indexOfMultiplySign, SimpleExpressionParser parser){
        try {
            left = parser.parse(str.substring(0,indexOfMultiplySign));
            System.out.println(str.substring(0,indexOfMultiplySign));
            right = parser.parse(str.substring(indexOfMultiplySign + 1));
            if (str.charAt(indexOfMultiplySign) == '*'){
                isMultiply = true;
            } else {
                isMultiply = false;
            }
        } catch (ExpressionParseException e) {

        }
    }

    @Override
    public double evaluate(double x) {
        return isMultiply? (left.evaluate(x) * right.evaluate(x)) : (left.evaluate(x) / right.evaluate(x));
    }

    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        String breaks = "";
        for (int i = 0; i < indentLevel; i++){
            breaks = breaks + "\b";
        }
        left.convertToString(stringBuilder, indentLevel + 1);
        right.convertToString(stringBuilder, indentLevel + 1);
    }
}