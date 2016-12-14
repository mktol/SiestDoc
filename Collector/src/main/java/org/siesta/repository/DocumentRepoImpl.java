package org.siesta.repository;

import org.siesta.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Repository for custom  actions by EntityManager  with Document entity
 */
@Repository
public class DocumentRepoImpl {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public List<Document> merge(List<Document> documents){
        List<Document> docs = new ArrayList<>();
        for (Document document : documents) {
            docs.add(entityManager.merge(document));
        }
        return docs;
    }
}
