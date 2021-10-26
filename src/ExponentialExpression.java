/**
 * @author Aidan Burns  12/6/2020
 * This project does ExponentialExpression on the IntelliJ IDEA
 */

public class ExponentialExpression implements Expression {
    private Expression base, power;


    public ExponentialExpression(String str, int indexOfExponent, SimpleExpressionParser parser) throws ExpressionParseException {
        base = parser.parse(str.substring(0, indexOfExponent));
        power = parser.parse(str.substring(indexOfExponent + 1));
    }

    /**
     *
     * @param x the given value of x
     * @return the value of the expression
     */
    @Override
    public double evaluate(double x) {
        return  Math.pow(base.evaluate(x), power.evaluate(x));
    }

    @Override
    public void convertToString(StringBuilder stringBuilder, int indentLevel) {
        String breaks = "";
        for (int i = 0; i < indentLevel; i++){
            breaks = breaks + "\t";
        }
        stringBuilder.append(breaks + "^\n");
        base.convertToString(stringBuilder, indentLevel + 1);
        power.convertToString(stringBuilder, indentLevel + 1);
    }
}