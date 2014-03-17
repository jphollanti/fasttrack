package ft;

import org.junit.Test;

import java.io.File;
import java.net.URISyntaxException;

import static org.junit.Assert.*;

public class RouteFileValidatorTest {

    @Test
    public void invalidatesRouteFileWithoutRoute() throws URISyntaxException {
        RouteFileValidationResult result = RouteFileValidator.validate(getEmptyRouteFile());
        assertNotNull(result);
        assertFalse(result.isValid());
        assertEquals("No routes found", result.getErrorMessage());
    }

    @Test
    public void invalidatesRouteFileWithoutSeedLine() throws URISyntaxException {
        RouteFileValidationResult result = RouteFileValidator.validate(getRouteFileWithoutSeedLine());
        assertFalse(result.isValid());
        assertEquals("No seed found", result.getErrorMessage());
    }

    @Test
    public void invalidatesEmptyFile() throws URISyntaxException {
        RouteFileValidationResult result = RouteFileValidator.validate(getEmptyFile());
        assertFalse(result.isValid());
        assertEquals("The file is empty", result.getErrorMessage());
    }

    @Test
    public void validatesOkFile() throws URISyntaxException {
        RouteFileValidationResult result = RouteFileValidator.validate(getShortRouteFile());
        assertTrue(result.isValid());
        assertNull(result.getErrorMessage());
    }

    private File getEmptyRouteFile() throws URISyntaxException {
        return getFile("/empty-route.txt");
    }

    private File getRouteFileWithoutSeedLine() throws URISyntaxException {
        return getFile("/route-file-without-seed.txt");
    }

    private File getEmptyFile() throws URISyntaxException {
        return getFile("/empty-file.txt");
    }

    private File getShortRouteFile() throws URISyntaxException {
        return getFile("/short-route.txt");
    }

    private File getFile(String fileName) throws URISyntaxException {
        return new File(getClass().getResource(fileName).toURI());
    }
}
