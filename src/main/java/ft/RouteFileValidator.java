package ft;

import org.apache.commons.io.LineIterator;

import java.io.File;

import static ft.LineIteratorProvider.getLineIterator;

public abstract class RouteFileValidator {
    public static RouteFileValidationResult validate(File routeFile) {
        if (isEmpty(routeFile)) {
            return getInvalidResult("The file is empty");
        }
        if (!containsSeed(routeFile)) {
            return getInvalidResult("No seed found");
        }
        if (containsOnlyOneLine(routeFile)) {
            return getInvalidResult("No routes found");
        }
        return new RouteFileValidationResult();
    }

    private static RouteFileValidationResult getInvalidResult(String message) {
        RouteFileValidationResult result = new RouteFileValidationResult();
        result.setIsValid(false);
        result.setErrorMessage(message);
        return result;
    }

    private static boolean containsOnlyOneLine(File routeFile) {
        LineIterator it = getLineIterator(routeFile);
        it.nextLine();
        return !it.hasNext();
    }

    private static boolean isEmpty(File routeFile) {
        LineIterator it = getLineIterator(routeFile);
        return !it.hasNext();
    }

    private static boolean containsSeed(File routeFile) {
        LineIterator it = getLineIterator(routeFile);
        return it.nextLine().contains("# seed");
    }
}
