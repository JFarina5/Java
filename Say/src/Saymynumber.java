// By: John Farina
// Date: 04/28/2017

import java.util.Scanner;

public class Saymynumber {

    private final static long QUINTILLION = 1000000000000000000L;
    private final static long QUADRILLION = 1000000000000000L;
    private final static long TRILLION = 1000000000000L;
    private final static long BILLION = 1000000000L;
    private final static long MILLION = 1000000L;

    final static String[] singleDigits = { "", "one", "two", "three", "four", "five", "six", "seven ", "eight ",
            "nine " };

    final static String[] twoDigits = { "ten ", "eleven ", "twelve ", "thirteen ", "fourteen ", "fifteen ", "sixteen ",
            "seventeen ", "eighteen ", "nineteen " };

    final static String[] tens = { "", "", "twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty",
            "ninety " };

    /**
     * Method that produces a readable string representation
     * of a number between 0 and 19 digits. Negative numbers are also handled.
     *
     *  for eg. if n = -1234, return minus
     *         one thousand two hundred thirty one. return Zero if n = 0
     */
    public static String say (long n) {

        if (n == 0) {
            return "Zero";
        } else if (n < 0) {
            return "minus " + say(-n);
        } else if ((n / QUINTILLION) > 0) {

            return say(n / QUINTILLION) + "quintillion " + (n % QUINTILLION > 0 ? say(n % QUINTILLION) : "");

        } else if ((n / QUADRILLION) > 0) {

            return say(n / QUADRILLION) + "quadrillion " + (n % QUADRILLION > 0 ? say(n % QUADRILLION) : "");

        } else if ((n / TRILLION) > 0) {

            return say(n / TRILLION) + "trillion " + (n % TRILLION > 0 ? say(n % TRILLION) : "");

        } else if ((n / BILLION) > 0) {

            return say(n / BILLION) + "billion " + (n % BILLION > 0 ? say(n % BILLION) : "");

        } else if ((n / MILLION) > 0) {
            return say(n / MILLION) + "million " + (n % MILLION > 0 ? say(n % MILLION) : "");

        } else if ((n / 1000) > 0) {

            return say(n / 1000) + "thousand " + (n % 1000 > 0 ? say(n % 1000) : "");

        } else if ((n / 100) > 0) {

            return say(n / 100) + "hundred " + (n % 100 > 0 ? say(n % 100) : "");

        } else {

            if (n < 10) {
                return singleDigits[(int) n] + " ";

            } else if (n < 20) {
                return twoDigits[(int) n - 10];

            } else {
                return tens[(int) n / 10] + " " + singleDigits[(int) n % 10] + " ";
            }
        }
    }

    /**
     * Main method, handles user interaction and input validation
     *
     */
    public static void main (String[] args) {

        Scanner in = new Scanner(System.in);

        long number;

        while (true) {
            System.out.println("Enter an integer between 1 and 9.2 Quintillion: ");

            String input;
            if ((input = in.nextLine()).length() > 19) {
                System.out
                        .println("Sorry the input value is too long, Restrict your numbers to a maximum of 19 digits");
                continue;
            }

            try {
                if ((number = Long.parseLong(input)) > Long.MAX_VALUE || number < Long.MIN_VALUE)
                    continue;
            } catch (NumberFormatException e) {
                System.out.println("Input Error: please enter a valid number only between " + Long.MIN_VALUE + " and "
                        + Long.MAX_VALUE);
                continue;
            }
            break;
        }

        System.out.println(say(number));
    }
}
