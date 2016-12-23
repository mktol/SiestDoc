package org.siesta.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.siesta.ApplicationCollector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ApplicationCollector.class)
public class HandleDocumentServiceTest {

    @Autowired
    private HandleDocumentService handleDocumentService;

    @Test
    public void deleteDocument() throws Exception {

    }

    @Test
    public void addDocument() throws Exception {

    }

    @Test
    public void connectorsChecker() throws Exception {

    }

}