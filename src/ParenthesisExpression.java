/**
 * @author Aidan Burns  12/6/2020
 * This project does ParenthesisExpression on the IntelliJ IDEA
 */

import java.util.*;

public class ParenthesisExpression implements Expression {
    private Expression expression;

    /**
     *
     * @param str a string to be made into a Parenthesis Expression
     * @param parser 
     * @throws ExpressionParseException if the expression is bad not formatted correctly, it throws an error
     */
    public ParenthesisExpression(String str, SimpleExpressionParser parser) throws ExpressionParseException {
        expression = parser.parse(str.substring(1,str.length()-1));
    }

    /**
     *
     * @param x the given value of x
     * @return the value of the expression
     */
    @Override
    public double evaluate(double x) {
        return expression.evaluate(x);
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
        stringBuilder.append(breaks+"()" + "\n");
        expression.convertToString(stringBuilder, indentLevel + 1);
    }
}
