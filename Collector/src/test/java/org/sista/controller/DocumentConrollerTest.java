package org.sista.controller;

import org.junit.Before;
import org.junit.Test;
import org.siesta.model.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 *
 */
public class DocumentConrollerTest {
    private static final String REST_SERVICE_URI = "http://localhost:9000";
    private static final String DOCUMENT_ID = "5dd10405-e6e5-4bf9-8256-d75b45bb0596";
    HttpEntity<String> request = new HttpEntity<>(getHeaders());
    Document newDocument;
    private HttpHeaders headers;
    private RestTemplate restTemplate =  new RestTemplate();

    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Before
    public void setup() throws Exception {
        headers = getHeaders();
        newDocument = new Document();
        newDocument.setContent("new content.");
        newDocument.setTitle("new title");
        newDocument.setName("new name");
    }

    @Test
    public void testDocuments(){
        ResponseEntity<List<Document>> response = restTemplate.exchange(REST_SERVICE_URI+"/documents/", HttpMethod.GET, request, new ParameterizedTypeReference<List<Document>>(){});
        assertEquals(200, response.getStatusCodeValue());
        assertTrue( response.getBody().size()>0);
    }

    @Test
    public void testAddDocument(){ // test if crete id
        Document newDocument = new Document();
        newDocument.setContent("new content.");
        newDocument.setTitle("new title");
        newDocument.setName("new name");

        HttpEntity<Document> request = new HttpEntity<>( newDocument , getHeaders());
        ResponseEntity<Document> document= restTemplate.exchange(REST_SERVICE_URI+"/documents", HttpMethod.POST, request, new ParameterizedTypeReference<Document>(){});
        assertEquals(201, document.getStatusCodeValue());
        assertNotNull("Service must create new random id. ", document.getBody().getId());
    }

    @Test
    public void testUpdateDocument(){

        HttpEntity<Document> request = new HttpEntity<>( newDocument , getHeaders());
        Document savedDocument = restTemplate.exchange(REST_SERVICE_URI+"/documents", HttpMethod.POST, request, new ParameterizedTypeReference<Document>(){}).getBody();
        savedDocument.setTitle("Special Title");
        request = new HttpEntity<>( savedDocument, getHeaders());

        ResponseEntity<Boolean> resp = restTemplate.exchange(REST_SERVICE_URI+"/documents/", HttpMethod.PUT, request, new ParameterizedTypeReference<Boolean>(){});
        assertEquals(200, resp.getStatusCodeValue());
    }

    @Test
    public void testGetConcreteDoc(){
        HttpEntity<Document> request = new HttpEntity<>(  getHeaders());
        List<Document> response = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+DOCUMENT_ID, HttpMethod.GET, request, new ParameterizedTypeReference<List<Document>>(){}).getBody();
        assertNotNull(response.get(0));
    }

}
