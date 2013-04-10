package biz.c24.io.fixml.sample.transform;

import biz.c24.io.api.data.ComplexDataObject;
import biz.c24.io.api.presentation.JsonSource;
import org.fixprotocol.fixml44.FIXMLElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.Message;
import org.springframework.integration.annotation.Transformer;

import java.io.IOException;
import java.io.StringReader;

import static org.springframework.util.Assert.notNull;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 10/04/2013
 */
public class TradeCaptureReportJsonToCdoTransformer {
    
    private final JsonSource jsonSource;
    private final FIXMLElement fixmlElement;

    @Autowired
    public TradeCaptureReportJsonToCdoTransformer(final JsonSource jsonSource, final FIXMLElement fixmlElement) {
        
        notNull(jsonSource, "Mandatory argument 'jsonSource' missing.");
        notNull(fixmlElement, "Mandatory argument 'fixmlElement' missing.");
        this.jsonSource = jsonSource;
        this.fixmlElement = fixmlElement;
    }
    
    @Transformer
    public ComplexDataObject transform(final Message<String> message) throws IOException {
        
        notNull(message, "Mandatory argument 'message' missing.");
        StringReader reader = new StringReader(message.getPayload())
                ;
        jsonSource.setReader(reader);
        ComplexDataObject cdo = jsonSource.readObject(fixmlElement);
        
        return cdo;
    }
}
