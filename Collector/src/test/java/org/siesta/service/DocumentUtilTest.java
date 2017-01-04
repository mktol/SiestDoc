package org.siesta.service;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by mtol on 13.12.2016.
 */
public class DocumentUtilTest {

    private String repoName = "testRepo";
    private String testDocId = "dbd9fc88-b1e4-4ea5-9d21-6d980c0aac45";
    private String generatedDocId = "dbd9fc88-b1e4-4ea5-9d21-6d980c0aac45_testRepo";

    @Test
    public void createId() throws Exception {
        System.out.println(testDocId);
        String expected = testDocId+"_"+repoName;
        String actual = DocumentUtil.createId(testDocId, repoName);
        assertEquals(expected, actual);
    }
    @Test
    public void getRepoNameFromDocIdTest(){
        String actual = DocumentUtil.getNameFromId(generatedDocId);
        assertEquals(repoName, actual);
    }

    @Test
    public void getRepoNameTest(){
        String actual = DocumentUtil.getNameFromId(testDocId);
        assertEquals("", actual);
    }
    @Test
    public void getDocumentId() throws Exception {
        String actual =  DocumentUtil.getDocumentId(generatedDocId);
        assertEquals(testDocId, actual);
    }

}