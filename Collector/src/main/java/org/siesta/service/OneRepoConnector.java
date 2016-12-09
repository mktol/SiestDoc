package org.siesta.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.siesta.error.DocumentNotFindException;
import org.siesta.error.RepoConnectionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

/**
 * This class
 */
@Component
public class OneRepoConnector implements RepoConnector{

//    public final String REST_SERVICE_URI ;
    public static final String REST_SERVICE_URI = "http://localhost:8080/rest"; // TODO make it flexible , Add context param (war name)
    private final Logger logger = LoggerFactory.getLogger(OneRepoConnector.class);
    RestTemplate restTemplate = new RestTemplate();
    private String url;
    private String repoName = "defoult"; // TODO repo name should be unique
    private  String name; //TODO should be encrypted and stored to special file or db
    private  String password;

    public OneRepoConnector() {

    }

    public OneRepoConnector(String name, String password) {
        this.name = name;
        this.password = password;
    }

    private  HttpHeaders getHeaders() {
        String plainCredentials = name+":"+password;
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }


    HttpEntity<String> httpRequest(){
        return new HttpEntity<>(getHeaders());
    }

    @Override
    public String getUrl() {
        return url;
    }

    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String getPepoName() {
        return repoName;
    }

    /**
     * Method connect to repository
     * @param url connection url
     * @param httpMethod http method
     * @param parametrizedType prarmeterized type reference
     * @param <T> type of returned response value
     * @return response
     */
    public <T>T connect(String url, HttpMethod httpMethod , ParameterizedTypeReference<T> parametrizedType){
        try {
            HttpEntity<String> request = new HttpEntity<>(getHeaders());
            ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, request, parametrizedType);
            return response.getBody();
        }
        catch (ResourceAccessException eae){
            throw new RepoConnectionException(repoName);
        }
        catch (Exception ex){
            logger.error( "problem with connection to "+repoName+" repository. " + ex.getMessage());
            throw new DocumentNotFindException( "problem with connection to "+repoName+" repository. " + ex.getMessage());
        }
    }


    /**

     * Method connect to repository
     * @param url connection url
     * @param httpMethod http method
     * @param requestObj object for sending in request body
     * @param parameterizedType
     * @param <T> type of returned response value
     * @return response
     */
    public <T>T connect(String url, HttpMethod httpMethod , T requestObj, ParameterizedTypeReference<T> parameterizedType){
        HttpHeaders headers = getHeaders();
        HttpEntity<T> httpEntity = new HttpEntity<>(requestObj, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, httpEntity, parameterizedType);
        return response.getBody();
    }

    @Override
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

}
