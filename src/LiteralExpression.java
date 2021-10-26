/**
 * @author Aidan Burns  12/6/2020
 * This project does LiteralExpression on the IntelliJ IDEA
 */

import java.util.*;

public class LiteralExpression implements Expression {
    private double value;

    /**
     * @param str the string of the value of a literal
     */
    public LiteralExpression(String str){
        try {
            value = Double.parseDouble(str);
        } catch (NumberFormatException nfe){
        }

    }

    /**
     *
     * @param x the given value of x
     * @return the value of the literal
     */
    @Override
    public double evaluate(double x) {
        return value;
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
        stringBuilder.append(indents + value + "\n");
    }
}
