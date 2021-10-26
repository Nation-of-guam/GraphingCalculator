import java.util.*;

/**
 * Given https://paste.ofcode.org/aSkYkGjZSfKJrRerYSiXBB by @jrwhitehill
 * @author sciab 12/06/2020
 * This class is an implementation of an ExpressionParser that is used for my CS2103 project
 */


public class SimpleExpressionParser implements ExpressionParser {
	/*
	 *Used to check if a character is one of + - * / ^
	 * OperatorTable Table:
	 * + == 1 && - == 1
	 * * == 2 && / == 2
	 * ^ == 3
	 */
	private final  Map<Character, Integer> operatorMap = new HashMap<Character, Integer>(7);
	//A list of operation in order that they should be checked for
	private final char[] pemdas = new char[]{'+','-','*','/','^'};

	// Used to check if operatorMap is populated
	private boolean isPopulated;



	/*
	 * Grammar:
	 * S -> A | P
	 * A -> A+M | A-M | M
	 * M -> M*E | M/E | E
	 * E -> P^E | P
	 * P -> (S) | L | V
	 * L -> <float>
	 * V -> x
	 *
	 */

	/**
	 *
	 * @param str the string to parse into an expression tree
	 * @return an Expression object representing str
	 * @throws ExpressionParseException
	 */
	public Expression parse (String str) throws ExpressionParseException {
		if (!isPopulated){
			operatorMap.put('+', new Integer(0));
			operatorMap.put('-', new Integer(0));
			operatorMap.put('*', new Integer(1));
			operatorMap.put('/', new Integer(1));
			operatorMap.put('^', new Integer(2));
			isPopulated = true;
		}

		str = str.replaceAll(" ", "");
		str = str.replaceAll("÷", "/");

		if (str.length() == 0){
			throw new ExpressionParseException("Cannot parse expression: " + str);
		} else if (operatorMap.get(str.charAt(0)) != null && str.charAt(0) != '-'){
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}

		final Expression expression = parseStartExpression(str);
		if (expression == null) {
			throw new ExpressionParseException("Cannot parse expression: " + str);
		}
		return expression;
	}

	/**
	 *
	 * @param str the string to parse into an expression tree
	 * @return an Expression object representing str
	 * @throws ExpressionParseException
	 */
	private Expression parseStartExpression (String str) throws ExpressionParseException {
		Expression expression = null;
		final int stringLength = str.length();

		if (stringLength > 1){
			expression = parseParenthesis(str, stringLength);
			for (int i = 0; i < pemdas.length && expression == null; i++){
				expression = parseForLoop(str, stringLength, pemdas[i]);
			}
		}

		if (expression == null){
			expression = parseLiteralExpression(str);
			if (expression == null){
				expression = parseVariableExpression(str);
			}
		}

		return expression;
	}

	/**
	 * Checks to see if the whole expression is a set of parenthesis
	 * Correct examples:
	 * "(2*x+1)"
	 * "((x^3))"
	 * Incorrect examples:
	 * "((2*x+1)"
	 * ")4*x("
	 * @param str the string to check if it is a parenthesis object
	 * @param stringLength the length of the string
	 * @return a parenthesis object representing str, null if str is not a parenthesis expression
	 * @throws ExpressionParseException
	 */
	private Expression parseParenthesis (String str, int stringLength) throws ExpressionParseException {
		final int[] parenthesisCount = new int[]{0,0}; //index 0 is count of left parenthesis, index 1 is count of right parenthesis
		for (int i = 0; i < stringLength; i++){
			final char curChar = str.charAt(i);
			if (curChar == '('){
				parenthesisCount[0]++;
			} else if (curChar == ')'){
				parenthesisCount[1]++;
			} else if (parenthesisCount[0] == parenthesisCount[1]){
				return null;
			}
		}


		return new ParenthesisExpression(str, this);
	}

	/**
	 *
	 * @param str the string to check for the indicated character
	 * @param stringLength the length of the string
	 * @param charToCheck the character to compare against
	 * @return the type of expression that is first in the string of the indicated type, null if it
	 * @throws ExpressionParseException
	 */
	private Expression parseForLoop (String str, int stringLength, char charToCheck) throws ExpressionParseException {
		Expression toReturn = null;

		final int[] parenthesisCount = new int[]{0,0}; //index 0 is count of left parenthesis, index 1 is count of right parenthesis
		for (int i = 0; i < stringLength && toReturn == null; i++){
			final char curChar = str.charAt(i);
			if (curChar == '(' || curChar == ')' || parenthesisCount[0] == parenthesisCount[1] ){
				if (curChar == '('){ // if it is a parenthesis
					parenthesisCount[0]++;
				} else if (curChar == ')'){
					parenthesisCount[1]++;
				} else if (operatorMap.get(curChar) != null && curChar == charToCheck && i != 0) { // if not a parenthesis, and not inside of a parenthesis, and is the operator selected by operatorNumber
					toReturn = parseExpressionType(str, i, curChar);
				}
			}
		}
		return toReturn;
	}


	/**
	 *
	 * @param str the string of the expression
	 * @param curIndex the index of the character that is checked
	 * @param curChar the character at curIndex
	 * @return The expression that represents the string
	 */
	private Expression parseExpressionType (String str, int curIndex, char curChar) throws ExpressionParseException {
		Expression toReturn = null;
		final Integer expressionValue = operatorMap.get(curChar);

		switch (expressionValue.intValue()){
			case 0:
				toReturn = new AdditiveExpression(str, curIndex, this);
				break;
			case 1:
				toReturn = new MultiplicitaveExpression(str, curIndex, this);
				break;
			case 2:
				toReturn = new ExponentialExpression(str, curIndex, this);
				break;
			default:

				break;
		}
		return toReturn;
	}

	/**
	 * Consumes a string representing a variable, or a letter representing a constant (I.E: e, pi)
	 * @param str
	 * @return a VariableExpression
	 */
	private Expression parseVariableExpression (String str) {
		if (str.equals("x")) {
			return new VariableExpression();
		} else if (str.equals("e")){
			return parseLiteralExpression(String.valueOf(Math.exp(1)));
		} else if (str.equals("pi")){
			return parseLiteralExpression(String.valueOf(Math.PI));
		}
		return null;
	}


	/**
	 *
	 * @param str the string to be transformed into a literal
	 * @return a LiteralExpression that represents str, null if str is not a literal
	 */
	Expression parseLiteralExpression (String str) {
		// From https://stackoverflow.com/questions/3543729/how-to-check-that-a-string-is-parseable-to-a-double/22936891:
		final String Digits     = "(\\p{Digit}+)";
		final String HexDigits  = "(\\p{XDigit}+)";
		// an exponent is 'e' or 'E' followed by an optionally 
		// signed decimal integer.
		final String Exp        = "[eE][+-]?"+Digits;
		final String fpRegex    =
				("[\\x00-\\x20]*"+ // Optional leading "whitespace"
						"[+-]?(" +         // Optional sign character
						"NaN|" +           // "NaN" string
						"Infinity|" +      // "Infinity" string

						// A decimal floating-point string representing a finite positive
						// number without a leading sign has at most five basic pieces:
						// Digits . Digits ExponentPart FloatTypeSuffix
						//
						// Since this method allows integer-only strings as input
						// in addition to strings of floating-point literals, the
						// two sub-patterns below are simplifications of the grammar
						// productions from the Java Language Specification, 2nd
						// edition, section 3.10.2.

						// Digits ._opt Digits_opt ExponentPart_opt FloatTypeSuffix_opt
						"((("+Digits+"(\\.)?("+Digits+"?)("+Exp+")?)|"+

						// . Digits ExponentPart_opt FloatTypeSuffix_opt
						"(\\.("+Digits+")("+Exp+")?)|"+

						// Hexadecimal strings
						"((" +
						// 0[xX] HexDigits ._opt BinaryExponent FloatTypeSuffix_opt
						"(0[xX]" + HexDigits + "(\\.)?)|" +

						// 0[xX] HexDigits_opt . HexDigits BinaryExponent FloatTypeSuffix_opt
						"(0[xX]" + HexDigits + "?(\\.)" + HexDigits + ")" +

						")[pP][+-]?" + Digits + "))" +
						"[fFdD]?))" +
						"[\\x00-\\x20]*");// Optional trailing "whitespace"

		if (str.matches(fpRegex)) {
			return new LiteralExpression(str);
		}
		return null;
	}
}
