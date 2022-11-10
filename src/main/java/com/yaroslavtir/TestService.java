package com.yaroslavtir;

import java.util.List;

import com.yaroslavtir.entity.ConceptEntity;
import com.yaroslavtir.entity.ValueEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RequiredArgsConstructor
@Service
public class TestService {

    private final MainService mainService;

    @EventListener
    public void test(ContextRefreshedEvent ctxStartEvt) throws InterruptedException {
        mainService.populate(List.of(
                new ConceptEntity(1L),
                new ConceptEntity(2L),
                new ConceptEntity(3L),
                new ConceptEntity(4L)
        ));

        mainService.populate(em -> List.of(
                new ValueEntity(1L, em.find(ConceptEntity.class, 1L), "field_1", 11),
                new ValueEntity(2L, em.find(ConceptEntity.class, 1L), "field_2", 12),
                new ValueEntity(3L, em.find(ConceptEntity.class, 1L), "field_3", 13),
                new ValueEntity(4L, em.find(ConceptEntity.class, 2L), "field_1", 21),
                new ValueEntity(5L, em.find(ConceptEntity.class, 2L), "field_2", 22),
                new ValueEntity(6L, em.find(ConceptEntity.class, 3L), "field_1", 31)
        ));
//        mainService.reindex();
        mainService.query();
    }


}
