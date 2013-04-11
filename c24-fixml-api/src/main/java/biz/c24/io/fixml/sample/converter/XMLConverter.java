package biz.c24.io.fixml.sample.converter;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.XMLSink;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Enrico Musuruana
 * Date: 10/04/2013
 */
public class XMLConverter
{
    private static XMLSink sink = new XMLSink();

    public static String convert(ComplexDataObject cdo)
    {
        Writer writer = new StringWriter();
        String output = null;

        try {
            sink.setWriter(writer);
            sink.writeObject(cdo);

            output = writer.toString();
        } catch (IOException e) {
            System.out.println("An error occurred while converting the cdo to xml." + e);
        }

        return output;
    }
}
