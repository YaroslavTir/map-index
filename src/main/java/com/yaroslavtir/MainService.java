package com.yaroslavtir;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.hibernate.search.mapper.orm.Search;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MainService {

    @PersistenceContext
    private EntityManager entityManager;

    @EventListener
    public void reindex(ContextRefreshedEvent ctxStartEvt) {
        populate();
        Search.session(entityManager)
                .massIndexer(MainEntity.class)
                .start();
    }

    private void populate() {
        entityManager.persist(new MainEntity(1L, 3));
        entityManager.persist(new MainEntity(2L, 2));
        entityManager.persist(new MainEntity(3L, 4));
        entityManager.persist(new MainEntity(4L, 1));
    }

}
