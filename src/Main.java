import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final int MAX_NUMBER = 3999;
    public static final int[] ARABIC =  {1, 4, 5, 9, 10, 40, 50, 90, 100, 400, 500, 900, 1000};
    public static final String[] ROMAN = {"I","IV","V","IX","X","XL","L","XC","C","CD","D","CM","M"};

    public static boolean isArabic(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            int i = Integer.parseInt(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean isRoman(String strNum) {
        if (strNum == null) {
            return false;
        }
        String[] letters = strNum.split("");
        for (String s : letters) {
            if (!List.of(ROMAN).contains(s)) {
                return false;
            }
        }
        return true;
    }

    public static void validateOperands(String[] operands) {
        if (operands.length != 3) {
            throw new UnsupportedOperationException("Wrong number of operands: " + operands.length);
        }
        if (! List.of("+", "-", "*", "/").contains(operands[1])) {
            throw new UnsupportedOperationException("Unsupported operation: " + operands[1]);
        }
        if (!(isArabic(operands[0]) && isArabic(operands[2])) &&
                !(isRoman(operands[0]) && isRoman(operands[2]))) {
            throw new UnsupportedOperationException("Operands should be numbers in one of the two formats");
        }
    }

    public static int calculate(String[] operands) {
        int a = toNumeric(operands[0]);
        int b = toNumeric(operands[2]);
        switch(operands[1]) {
            case "+":
                return a + b;
            case "-":
                return a - b;
            case "*":
                return a * b;
            case "/":
                return a / b;
            default:
                throw new UnsupportedOperationException("Unsupported operation: " + operands[1]);
        }
    }

    public static String arabicToRoman(int number) {
        if (number <= 0) {
            throw new RuntimeException("Negative Roman number");
        }
        if (number > MAX_NUMBER) {
            throw new RuntimeException("Too big number for Roman alphabet");
        }

        String result = "";
        int i = ARABIC.length - 1;
        while(number > 0) {
            if (number >= ARABIC[i]) {
                result += ROMAN[i];
                number -= ARABIC[i];
            } else {
                i--;
            }
        }
        return result;
    }

    public static int romanToArabic(String number) {
        int result = 0;
        int i = ARABIC.length - 1;
        int pos = 0;
        while (i >= 0 && pos < number.length()) {
            int end = pos + ROMAN[i].length() > number.length() ? number.length() : pos + ROMAN[i].length();
            if(number.substring(pos, end).equals(ROMAN[i])) {
                result += ARABIC[i];
                pos += ROMAN[i].length();
;            } else {
                i--;
            }
        }
        return result;
    }

    public static String formatResult(int number, boolean isRoman) {
        if (isRoman) {
            return arabicToRoman(number);
        } else {
            return Integer.toString(number);
        }
    }
    public static int toNumeric(String number) {
        if (isRoman(number)) {
            return romanToArabic(number);
        } else {
            return Integer.parseInt(number);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Enter new expression: ");
            try {
                String expression = scanner.nextLine();
                
                String[] operands = expression.split(" ");
                validateOperands(operands);
                int result = calculate(operands);
                String formatted = formatResult(result, isRoman(operands[0]));

                System.out.println(formatted);

            } catch (Exception e) {
                System.out.print("Error: ");
                System.out.println(e.getMessage());
            }

            while(true) {
                System.out.print("Do you want to continue? [y/n]: ");
                String answer = scanner.nextLine();
                if (answer.startsWith("n") || answer.startsWith("N")) {
                    System.out.println("Bye!");
                    return;
                } else if (answer.startsWith("y") || answer.startsWith("Y")) {
                    break;
                } else {
                    System.out.println("Sorry, didn't recognize your answer");
                }
            }
        }
    }
}