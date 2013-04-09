package biz.c24.io.fixml.sample.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 09/04/2013
 */
public class FileUtils {

    public static File locateClasspathResource(final String resourceName) {

        isTrue(StringUtils.isNotEmpty(resourceName), "Mandatory argument 'resourceName' missing.");
        try {
            return new ClassPathResource(resourceName).getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readFileAsString(final File file) {

        notNull(file, "Mandatory argument 'file' missing.");
        try {
            byte[] buffer = new byte[(int) file.length()];
            BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
            bufferedInputStream.read(buffer);
            return new String(buffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String readClasspathResourceAsString(final String resourceName) {

        isTrue(StringUtils.isNotEmpty(resourceName), "Mandatory argument 'resourceName' missing.");
        try {
            File resource = new ClassPathResource(resourceName).getFile();
            return readFileAsString(resource);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void writeFileFromString(final String string, final File file) {

        notNull(string, "Mandatory argument 'string' missing.");
        notNull(file, "Mandatory argument 'file' missing.");
        try {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file, false));
            outputStream.write(string.getBytes());
            outputStream.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File [] getRawFileList(final String baseDirectory, final boolean stripPrefix) {

        notNull(baseDirectory, "Mandatory argument 'baseDirectory' missing.");
        String location = baseDirectory;
        if (stripPrefix)
            location = baseDirectory.substring(baseDirectory.lastIndexOf(":") + 1, baseDirectory.length());
        File dir = locateClasspathResource(location);
        return dir.listFiles();
    }
}
