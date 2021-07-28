package com.yaroslavtir;

import java.util.Map;
import org.hibernate.search.engine.backend.document.DocumentElement;
import org.hibernate.search.engine.backend.document.IndexObjectFieldReference;
import org.hibernate.search.mapper.pojo.bridge.PropertyBridge;
import org.hibernate.search.mapper.pojo.bridge.runtime.PropertyBridgeWriteContext;

public class MapPropertyBridge implements PropertyBridge<Map> {

    private final IndexObjectFieldReference mapFieldReference;

    public MapPropertyBridge(IndexObjectFieldReference mapFieldReference) {

        this.mapFieldReference = mapFieldReference;
    }

    @Override
    public void write(DocumentElement target, Map bridgedElement, PropertyBridgeWriteContext context) {

        Map<String, Integer> map = (Map<String, Integer>) bridgedElement;

        DocumentElement indexedUserMetadata = target.addObject(mapFieldReference);
        map.forEach((field, value) -> indexedUserMetadata.addValue(field, field));
    }
}
