package net.jurka.arrow.util;


public class Util {

    /**
     * Formats time from milliseconds to HH:MM:SS format
     * @param startTime long
     * @return String time
     */
    public static String formatTime(long startTime) {
        // Taken from ShantayX all credits to author
        final long runTimeMilliseconds = System.currentTimeMillis() - startTime;
        final long secondsTotal = runTimeMilliseconds / 1000;
        final long minutesTotal = secondsTotal / 60;
        final long hoursTotal = minutesTotal / 60;
        int currentTimeHMS[] = new int[3];

        currentTimeHMS[2] = (int) (secondsTotal % 60);
        currentTimeHMS[1]  = (int) (minutesTotal % 60);
        currentTimeHMS[0] = (int) (hoursTotal % 500);

        return String.format("%02d:%02d:%02d", currentTimeHMS[0], currentTimeHMS[1], currentTimeHMS[2]);
    }

    /**
     * Recursive implementation, invokes itself for each factor of a thousand, increasing the class on each invokation.
     * From http://stackoverflow.com/questions/4753251/how-to-go-about-formatting-1200-to-1-2k-in-java
     * @param n the number to format
     * @param iteration in fact this is the class from the array c
     * @return a String representing the number n formatted in a cool looking way.
     */
    public static String coolFormat(double n, int iteration) {
        char[] c = new char[]{'k', 'm', 'b', 't'};

        double d = ((long) n / 100) / 10.0;
        boolean isRound = (d * 10) %10 == 0;//true if the decimal part is equal to 0 (then it's trimmed anyway)
        return (d < 1000? //this determines the class, i.e. 'k', 'm' etc
                ((d > 99.9 || isRound || (!isRound && d > 9.99)? //this decides whether to trim the decimals
                        (int) d * 10 / 10 : d + "" // (int) d * 10 / 10 drops the decimal
                ) + "" + c[iteration])
                : coolFormat(d, iteration+1));

    }
}