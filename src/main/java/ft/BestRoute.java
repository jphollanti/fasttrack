package ft;

import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.ArrayUtils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;

import static ft.LineCumulator.cumulate;
import static ft.LineIteratorProvider.getLineIterator;
import static ft.RouteFileValidator.validate;

/**
 * Provides the score for the most popular route by calculating the
 * score for each route. The algorithm starts from line one and descents
 * down line by line accumulating the highest value of the two parents
 * for each node.
 */
public abstract class BestRoute {

    public static void main(String[] args) {
        RouteFileValidationResult result = validate(new File(args[0]));
        if (!result.isValid()) {
            System.out.println(result.getErrorMessage());
            return ;
        }
        System.out.println(getScoreOfBestRoute(new File(args[0])));
    }

    public static int getScoreOfBestRoute(File routeFile) {
        LineIterator it = getLineIterator(routeFile);
        try {
            return getScoreOfBestRoute(it);
        } finally {
            LineIterator.closeQuietly(it);
        }
    }

    private static int getScoreOfBestRoute(LineIterator it) {
        skipLineWithSeed(it);
        int[] previousLine = getLine(it.nextLine());
        while (it.hasNext()) {
            int[] line = getLine(it.nextLine());
            cumulate(previousLine, line);
            previousLine = line;
        }
        return getMaxValue(previousLine);
    }

    protected static void skipLineWithSeed(LineIterator it) {
        it.nextLine();
    }

    private static int getMaxValue(int[] previousLine) {
        return Collections.max(Arrays.asList(ArrayUtils.toObject(previousLine))).intValue();
    }

    protected static int[] getLine(String lineAsString) {
        String[] valuesAsString = lineAsString.split(" ");
        int[] values = new int[valuesAsString.length];
        int i = 0;
        for (String stringValue : valuesAsString) {
            values[i] = Integer.parseInt(stringValue);
            i++;
        }
        return values;
    }
}
