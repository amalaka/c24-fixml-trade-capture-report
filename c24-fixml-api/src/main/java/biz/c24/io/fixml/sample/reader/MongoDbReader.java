package biz.c24.io.fixml.sample.reader;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.data.Element;
import biz.c24.io.api.presentation.JsonSource;
import biz.c24.io.spring.core.C24Model;
import com.mongodb.BasicDBObject;

import java.io.IOException;
import java.io.StringReader;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Enrico Musuruana
 * Date: 10/04/2013
 */
public class MongoDbReader
{
    private final String ELEMENT_NAME = "FIXML";

    private JsonSource source;
    private Element element;

    public MongoDbReader(JsonSource source, C24Model model)
    {
        this.source = source;
        this.element = model.getRootElement();
    }

    public ComplexDataObject parse(BasicDBObject mongoObject)
    {
        ComplexDataObject cdo = null;

        String context = mongoObject.get(ELEMENT_NAME).toString();
        source.setReader(new StringReader(context));

        try {
           cdo = source.readObject(element);
        } catch (IOException e) {
            System.out.println("An error occurred while parsing the message. "+e);
        }

        return cdo;
    }
}
