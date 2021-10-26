/**
 * @author Aidan Burns  12/6/2020
 * This project does VariableExpression on the IntelliJ IDEA
 */

import java.util.*;

public class VariableExpression implements Expression {

    /**
     *
     * @param x the given value of x
     * @return the value of the expression
     */
    @Override
    public double evaluate(double x) {
        return x;
    }

    /**
     *
     * @param stringBuilder the StringBuilder to use for building the String representation
     * @param indentLevel the indentation level (number of tabs from the left margin) at which to start
     */
    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        String indents = "";
        for (int i = 0; i < indentLevel; i++) {
            indents = indents + "\t";
        }
        stringBuilder.append(indents + "x" + "\n");
    }
}
