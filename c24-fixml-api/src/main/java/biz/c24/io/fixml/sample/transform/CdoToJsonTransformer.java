package biz.c24.io.fixml.sample.transform;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.JsonSink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.Transformer;

import java.io.IOException;
import java.io.StringWriter;

import static org.springframework.util.Assert.notNull;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 10/04/2013
 */
public class CdoToJsonTransformer {

    private final JsonSink jsonSink;

    @Autowired
    public CdoToJsonTransformer(final JsonSink jsonSink) {
        this.jsonSink = jsonSink;
    }

    @Transformer
    public String transform(final ComplexDataObject complexDataObject) throws IOException {

        notNull(complexDataObject, "Mandatory argument 'complexDataObject' missing.");
        StringWriter writer = new StringWriter();
        jsonSink.setWriter(writer);
        jsonSink.writeObject(complexDataObject);
        
        return writer.toString();
    }
}
