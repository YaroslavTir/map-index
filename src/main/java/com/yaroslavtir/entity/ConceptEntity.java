package com.yaroslavtir.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.yaroslavtir.RelatedDistancePropertyBinder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.search.mapper.pojo.bridge.mapping.annotation.PropertyBinderRef;
import org.hibernate.search.mapper.pojo.extractor.mapping.annotation.ContainerExtract;
import org.hibernate.search.mapper.pojo.extractor.mapping.annotation.ContainerExtraction;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.AssociationInverseSide;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexingDependency;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.ObjectPath;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyBinding;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.PropertyValue;

@Getter
@Setter
@NoArgsConstructor
@Indexed
@Entity
public class ConceptEntity {

    @Id
    private Long id;

    @OneToMany(mappedBy = "concept")
//    @IndexedEmbedded
//    @AssociationInverseSide(inversePath = @ObjectPath(@PropertyValue(propertyName = "concept")))
    private List<ValueEntity> metadataValues = new ArrayList<>();

    public ConceptEntity(Long id) {
        this.id = id;
    }

    @Transient
    @PropertyBinding(binder = @PropertyBinderRef(type = RelatedDistancePropertyBinder.class))
    @IndexingDependency(
            derivedFrom = @ObjectPath(@PropertyValue(propertyName = "metadataValues")),
            extraction = @ContainerExtraction(extract = ContainerExtract.NO)
    )
    public Map<String, Integer> getExtraFields() {
        return metadataValues.stream().collect(
                Collectors.toMap(
                        ValueEntity::getName,
                        ValueEntity::getValue
                )
        );
    }
}
