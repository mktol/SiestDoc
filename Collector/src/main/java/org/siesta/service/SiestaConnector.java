package org.siesta.service;

import org.siesta.model.Document;
import org.siesta.model.DocumentRepoOne;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Created by mtol on 13.12.2016.
 */
public class SiestaConnector {

    private String name;
    private RestTemplate restTemplate = new RestTemplate();
    private String url;

    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public List<Document> getAll(){
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        ResponseEntity<List<DocumentRepoOne>> response = restTemplate.exchange(url+"/documents/", HttpMethod.GET, request, new ParameterizedTypeReference<List<DocumentRepoOne>>(){});

        List<Document> documents = ConverterUtil.convertToDocumetnList(response.getBody());
        changeDocumentId(documents);
        return documents;
    }

    public Document getDocumentById(String documentId){
        HttpEntity<Document> request = new HttpEntity<>(  getHeaders());
        List<DocumentRepoOne> response = restTemplate.exchange(url+"/documents/"+documentId, HttpMethod.GET, request, new ParameterizedTypeReference<List<DocumentRepoOne>>(){}).getBody();

        List<Document> documents = ConverterUtil.convertToDocumetnList(response);
        changeDocumentId(documents);
        return documents.get(0);
    }



    public boolean updateDocument(Document document){
        DocumentRepoOne updatedDoc = ConverterUtil.convertToRepoOneDocument(document);
        HttpEntity<DocumentRepoOne> request = new HttpEntity<>( updatedDoc , getHeaders());
        String docId = updatedDoc.getId();
        ResponseEntity<DocumentRepoOne> resp = restTemplate.exchange(url+"/documents/"+docId, HttpMethod.PUT, request, new ParameterizedTypeReference<DocumentRepoOne>(){});
        return resp.getStatusCodeValue()==200;



    }

    public boolean deleteDocument(String docId){
        HttpEntity<Document> request = new HttpEntity<>(  getHeaders());
        boolean resp = restTemplate.exchange(url+"/documents/"+docId, HttpMethod.DELETE, request, new ParameterizedTypeReference<Boolean>(){}).getBody();
        return resp;
    }

    public Document addDocument(Document document){
        DocumentRepoOne newDoc = ConverterUtil.convertToRepoOneDocument(document);
        HttpEntity<DocumentRepoOne> request = new HttpEntity<>( newDoc, getHeaders());
        DocumentRepoOne document1= restTemplate.exchange(url+"/documents", HttpMethod.POST, request, new ParameterizedTypeReference<DocumentRepoOne>(){}).getBody();
        Document res = ConverterUtil.convertToDocument(document1);
        changeDocumentId(res);
        return res;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    private void changeDocumentId(List<Document> documents){
        for (Document document : documents) {
            changeDocumentId(document);
        }
    }

    private void changeDocumentId(Document document){
        String docId = document.getDocId();
        String newDocID = ConverterUtil.createId(docId, name);
        document.setDocId(newDocID);
    }
}
