package biz.c24.io.fixml.sample.enricher;

import biz.c24.io.api.data.ComplexDataObject;
import org.fixprotocol.fixml44.FIXMLLocal;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: Enrico Musuruana
 * Date: 10/04/2013
 */
public class CdoEnricher
{
    public static ComplexDataObject enrich(FIXMLLocal message)
    {
        //todo: we could make something more interesting here...
        message.setAttr("v", "69");
        return message;
    }
}
