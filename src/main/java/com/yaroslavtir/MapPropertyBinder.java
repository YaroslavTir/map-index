package com.yaroslavtir;

import java.util.Map;
import org.hibernate.search.engine.backend.document.model.dsl.IndexSchemaElement;
import org.hibernate.search.engine.backend.document.model.dsl.IndexSchemaObjectField;
import org.hibernate.search.mapper.pojo.bridge.binding.PropertyBindingContext;
import org.hibernate.search.mapper.pojo.bridge.mapping.programmatic.PropertyBinder;

public class MapPropertyBinder implements PropertyBinder {

    @Override
    public void bind(PropertyBindingContext context) {

        context.dependencies().useRootOnly();

        IndexSchemaElement schemaElement = context.indexSchemaElement();

        IndexSchemaObjectField mapField = schemaElement.objectField("mapField");
        mapField.fieldTemplate(
                "mapTemplate",
                fieldTypeFactory -> fieldTypeFactory.asString().analyzer("default")
        );
        final MapPropertyBridge bridge = new MapPropertyBridge(mapField.toReference());
        context.bridge(Map.class, bridge);
    }
}
