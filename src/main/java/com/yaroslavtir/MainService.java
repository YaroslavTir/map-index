package com.yaroslavtir;

import java.util.List;
import java.util.function.Function;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.yaroslavtir.entity.ConceptEntity;
import org.hibernate.search.engine.search.query.SearchResult;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MainService {

    @PersistenceContext
    private EntityManager entityManager;

    public void reindex() throws InterruptedException {
        Search.session(entityManager)
                .massIndexer(ConceptEntity.class)
                .startAndWait();

    }

    public void populate(List<?> entities) {
        entities.forEach(entityManager::persist);
    }

    public void populate(Function<EntityManager, List<?>> func) {
        func.apply(entityManager)
                .forEach(entityManager::persist);
    }

    public void query() {
        SearchSession searchSession = Search.session(entityManager);
        SearchResult<ConceptEntity> conceptEntitySearchResult = searchSession.search(ConceptEntity.class)
                .where(f -> f.match()
                        .field("fields.field_1")
                        .matching("11"))
                .fetchAll();
        System.out.println(conceptEntitySearchResult.hits().stream().count());
    }

}
