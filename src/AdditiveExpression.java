/**
 * @author Aidan Burns  12/6/2020
 * This project does AdditiveExpression on the IntelliJ IDEA
 */

import java.util.*;

public class AdditiveExpression implements Expression {
    private Expression left, right;
    private boolean isPlus;

    /**
     *
     * @param str
     * @param indexOfPlus
     * @param parser
     * @throws ExpressionParseException
     */
    public AdditiveExpression(String str, int indexOfPlus, SimpleExpressionParser parser) throws ExpressionParseException {
        left = parser.parse(str.substring(0,indexOfPlus));
        right = parser.parse(str.substring(indexOfPlus + 1));


        if (str.charAt(indexOfPlus) == '+'){
            isPlus = true;
        } else {
            isPlus = false;
        }
    }

    /**
     *
     * @param x the given value of x
     * @return the value of the expression
     */
    @Override
    public double evaluate(double x) {
        return isPlus? left.evaluate(x) + right.evaluate(x) : left.evaluate(x) - right.evaluate(x);
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

        if (isPlus){
            stringBuilder.append(breaks + "+\n" );
        } else {
            stringBuilder.append(breaks + "-" );
        }
        left.convertToString(stringBuilder,  indentLevel + 1);
        right.convertToString(stringBuilder, indentLevel + 1);
    }
}
