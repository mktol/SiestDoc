package org.siesta.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siesta.ApplicationCollector;
import org.siesta.model.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Created by mtol on 21.12.2016.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationCollector.class)
public class CachedDocumentServiceTest {

    @Autowired
    private CachedDocumentService cachedDocumentService;
    @Test
    public void getAll() throws Exception {
        List<Document> docs =cachedDocumentService.getAll();
        assertNotNull(docs);
    }

}