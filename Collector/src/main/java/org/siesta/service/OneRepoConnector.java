package org.siesta.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.siesta.model.DocumentRepoOne;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

/**
 * This class
 */
@Component
public class OneRepoConnector implements RepoConnector{
//    public final String REST_SERVICE_URI ;
    public static final String REST_SERVICE_URI = "http://localhost:8080/rest";
    public String restServiceUrl;
    RestTemplate restTemplate = new RestTemplate();
    private String repoName = "defoult";
    private  String name; //TODO should be encrypted and stored to special file or db
    private  String password;

    public OneRepoConnector() {

    }

    public OneRepoConnector(String name, String password, String hostUrl) {
        this.name = name;
        this.password = password;
        restServiceUrl = "http://localhost:"+hostUrl+"/rest";
    }

    private  HttpHeaders getHeaders() {
        String plainCredentials = name+":"+password;
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public  boolean connect(){
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        try {
            ResponseEntity<List<DocumentRepoOne>> response = restTemplate.exchange(REST_SERVICE_URI + "/documents/", GET, request, new ParameterizedTypeReference<List<DocumentRepoOne>>() {
            });
            List<DocumentRepoOne> docs = response.getBody();
            docs.forEach(System.out::println);
        }catch (ResourceAccessException connectException){
            System.out.println(connectException.getMessage());
            return false;
        }
        return true;

    }

    HttpEntity<String> httpRequest(){
        return new HttpEntity<>(getHeaders());
    }

    @Override
    public String getPepoName() {
        return repoName;
    }

    public <T>T connect(String url, HttpMethod httpMethod , ParameterizedTypeReference<T> parametrizedType){
        try {
            ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, httpRequest(), parametrizedType);
            return response.getBody();
        }catch (Exception ex){
            System.err.println( "problem with connection to "+repoName+" repository. " + ex.getMessage());
            throw new RuntimeException( "problem with connection to "+repoName+" repository. " + ex.getMessage());
        }
    }



    public <T>T connect(String url, HttpMethod httpMethod , String jsonObject, ParameterizedTypeReference<T> parameterizedType){
        HttpHeaders headers = getHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, httpEntity, parameterizedType);
        return response.getBody();
    }

    @Override
    public void setRepoName(String repoName) {
        this.repoName = repoName;
    }

}
