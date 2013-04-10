package biz.c24.io.fixml.sample.storage;

import biz.c24.io.api.data.ComplexDataObject;

/**
 * Created on behalf of C24 Technologies Ltd.
 *
 * @author mvickery
 * @since 08/04/2013
 */
public interface MongoDbCollectionWrapper<T extends ComplexDataObject> {
    public T store(final T complexDataObject);
    public T query(final String query);
}