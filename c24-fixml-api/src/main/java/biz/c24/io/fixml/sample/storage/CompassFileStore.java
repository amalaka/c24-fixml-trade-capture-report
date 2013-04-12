package biz.c24.io.fixml.sample.storage;

import biz.c24.io.fixml.sample.util.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Enrico Musuruana
 * Date: 11/04/2013
 */
public class CompassFileStore
{
    private File validDir;
    private File invalidDir;

    private final String VALID = "valid-";
    private final String INVALID = "invalid-";
    private final String XML_FORMAT = ".xml";

    public CompassFileStore(File validDir, File invalidDir)
    {
        this.validDir = validDir;
        this.invalidDir = invalidDir;
    }

    public File storeValidFileAsXML(String message) throws IOException
    {
        return storeFile(validDir, message, true, XML_FORMAT);
    }

    public File storeInvalidFileAsXML(String message) throws IOException
    {
        return storeFile(invalidDir, message, false, XML_FORMAT);
    }

    private File storeFile(File directory, String message, boolean valid, String format)
    {
        if (!directory.exists())
            directory.mkdirs();

        int index = directory.list() == null? 0 : directory.list().length;
        File destination = new File(directory, (valid? VALID : INVALID) + index + format );

        FileUtils.writeFileFromString(message, destination);

        return destination;
    }
}
