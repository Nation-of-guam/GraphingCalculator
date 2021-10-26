import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author sciab This class is just for testing the
 *
 */
//TODO: make these actual JunitTests
public class Testing {
    public static void main(String[] args) throws ExpressionParseException, FileNotFoundException {
        ExpressionParser parser = new SimpleExpressionParser();
        String str = "(1/x) - (x^2 + 1)";
        System.out.println("Should be -4.5: " + parser.parse(str).evaluate(2));

        str = "2.5*x^3";
        System.out.println("Should be -2.5: " + parser.parse(str).evaluate(-1));

        str = "2^x";
        System.out.println("Should be 16: " + parser.parse(str).evaluate(4));

        str = "6";
        System.out.println("Should be 6: " + parser.parse(str).evaluate(2));

        str = "x";
        System.out.println("Should be 3: " + parser.parse(str).evaluate(3));

        str = "3+-3";
        System.out.println("Should be 0: " + parser.parse(str).evaluate(3));

        str = "9/3*3";
        System.out.println("Should be 9: " + parser.parse(str).evaluate(3));

        str = "4-3*5";
        System.out.println("Should be -11: " + parser.parse(str).evaluate(3));

        str = "-1*x";
        System.out.println("Should be -3: " + parser.parse(str).evaluate(3));

        str = "((((3 + x) / 2 ^ (x + 2)) + -8))";
        str = parser.parse(str).convertToString(0);
        System.out.println();
        System.out.print(str);

        final Scanner s = new Scanner(new File("C:/Users/sciab/IdeaProjects/Desmos/500000.txt"), "ISO-8859-1");
        str = s.next();
        System.out.println("Should be 127: " + parser.parse(str).evaluate(3));
    }
}
