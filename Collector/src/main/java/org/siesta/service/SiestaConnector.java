package org.siesta.service;

import org.siesta.error.RepoConnectionException;
import org.siesta.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class SiestaConnector {
    private final Logger logger = LoggerFactory.getLogger(SiestaConnector.class);

    private String name;
    private RestTemplate restTemplate = new RestTemplate();
    private String url;

    private static HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public List<Document> getAll() {
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        ResponseEntity<List<Document>> response = restTemplate.exchange(url + "/documents/", HttpMethod.GET, request, new ParameterizedTypeReference<List<Document>>() {
        });

        List<Document> documents =response.getBody();
        changeDocumentId(documents);
        return documents;
    }

    public Document getDocumentById(String documentId) {
        HttpEntity<Document> request = new HttpEntity<>(getHeaders());
        List<Document> response = restTemplate.exchange(url + "/documents/" + documentId, HttpMethod.GET, request, new ParameterizedTypeReference<List<Document>>() {
        }).getBody();
        changeDocumentId(response);
        return response.get(0);
    }


    public boolean updateDocument(Document document) {
        try {

            HttpEntity<Document> request = new HttpEntity<>(document, getHeaders());
            String docId = document.getDocId();
            ResponseEntity<Document> resp = restTemplate.exchange(url + "/documents/" + docId, HttpMethod.PUT, request, new ParameterizedTypeReference<Document>() {
            });
            return resp.getStatusCodeValue() == 200;
        } catch (Exception connectionException) {
            handleResourceAccessException(connectionException);
        }
        return false;
    }

    private void handleResourceAccessException(Exception exception) { //TODO handle All exceptions by List
        if (exception.getCause() instanceof ResourceAccessException) { //TODO Вертати чесний результат
            logger.error("Repository " + name + " is not available.");
            throw new RepoConnectionException(name);
        }
        logger.error("Problem with communication with repository " + name, exception);
    }

    public boolean deleteDocument(String docId) {
        try {
            HttpEntity<Document> request = new HttpEntity<>(getHeaders());
            return restTemplate.exchange(url + "/documents/" + docId, HttpMethod.DELETE, request, new ParameterizedTypeReference<Boolean>() {
            }).getBody();
        } catch (Exception connectionException) {
            handleResourceAccessException(connectionException);
        }
        return false;
    }

    public Document addDocument(Document document) {

        if (document.getDocId() == null) {
            document.setDocId(UUID.randomUUID().toString());
        }
        HttpEntity<Document> request = new HttpEntity<>(document, getHeaders());

        Document res = null;
        try {
            res = restTemplate.exchange(url + "/documents", HttpMethod.POST, request, new ParameterizedTypeReference<Document>() {
            }).getBody();
        } catch (Exception e) {
            handleResourceAccessException(e);
        }

        changeDocumentId(res);
        return res;
    }

    public boolean isConnectionALive() {
        try {
            HttpEntity<Document> request = new HttpEntity<>(getHeaders());
            restTemplate.exchange(url + "/isalive", HttpMethod.GET, request, new ParameterizedTypeReference<Boolean>() {
            });
            return true;
        } catch (Exception ex) {
            logger.warn("problem with connection to repo = " + name, ex);
            return false;
        }
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

    private void changeDocumentId(List<Document> documents) {
        for (Document document : documents) {
            changeDocumentId(document);
        }
    }

    private void changeDocumentId(Document document) {
        if(document!=null) {
            String docId = document.getDocId();
            String newDocID = DocumentUtil.createId(docId, name);
            document.setDocId(newDocID);
        }
    }
}
