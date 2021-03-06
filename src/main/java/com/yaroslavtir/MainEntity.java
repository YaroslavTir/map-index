package com.yaroslavtir;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.PropertyBinderRef;
import org.hibernate.search.mapper.pojo.extractor.mapping.annotation.ContainerExtract;
import org.hibernate.search.mapper.pojo.extractor.mapping.annotation.ContainerExtraction;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ObjectPath;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyBinding;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyValue;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Indexed
@Entity
public class MainEntity {

    @Id
    private Long id;

    private Integer count;

    @Transient
    @PropertyBinding(binder = @PropertyBinderRef(type = MapPropertyBinder.class))
    @IndexingDependency(derivedFrom = {
            @ObjectPath(@PropertyValue(propertyName = "count"))
    },
            extraction = @ContainerExtraction(extract = ContainerExtract.NO)
    )
    public Map<String, Integer> getMap() {

        return generateSomeValuesForMap();
    }

    private Map<String, Integer> generateSomeValuesForMap() {

        return IntStream.range(1, count).boxed()
                .collect(Collectors.toMap(
                        this::getFieldName,
                        Function.identity()
                ));
    }

    private String getFieldName(Integer index) {

        return String.format("field_%s", index);
    }

}
