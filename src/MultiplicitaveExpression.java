/**
 * @author Aidan Burns  12/6/2020
 * This project does MultiplicitaveExpression on the IntelliJ IDEA
 */

import java.util.*;

public class MultiplicitaveExpression implements Expression {
    private Expression left, right;
    private boolean isMultiply;

    /**
     *
     * @param str the string representing an equation with a * or /
     * @param indexOfMultiplySign the index of / or * for this expression
     * @param parser a parser that can be used
     * @throws ExpressionParseException when it is not a valid Expression
     */
    public MultiplicitaveExpression(String str, int indexOfMultiplySign, SimpleExpressionParser parser) throws ExpressionParseException {
        left = parser.parse(str.substring(0,indexOfMultiplySign));
        right = parser.parse(str.substring(indexOfMultiplySign + 1));


        if (str.charAt(indexOfMultiplySign) == '*'){
            isMultiply = true;
        } else {
            isMultiply = false;
        }
    }

    /**
     *
     * @param x the given value of x
     * @return the value of the expression
     */
    @Override
    public double evaluate(double x) {
        return isMultiply? (left.evaluate(x) * right.evaluate(x)) : (left.evaluate(x) / right.evaluate(x));
    }

    /**
     *
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        String breaks = "";
        for (int i = 0; i < indentLevel; i++){
            breaks = breaks + "\t";
        }
        if (isMultiply){
            stringBuilder.append(breaks + "*\n" );
        } else {
            stringBuilder.append(breaks + "/\n" );
        }
        left.convertToString(stringBuilder, indentLevel + 1);
        right.convertToString(stringBuilder, indentLevel + 1);
    }
}