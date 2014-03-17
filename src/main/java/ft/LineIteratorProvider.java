package ft;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import java.io.File;
import java.io.IOException;

public abstract class LineIteratorProvider {

    public static LineIterator getLineIterator(File routeFile) {
        try {
            return FileUtils.lineIterator(routeFile, "UTF-8");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
