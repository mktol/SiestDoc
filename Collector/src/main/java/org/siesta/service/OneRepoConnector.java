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
public class OneRepoConnector {
    public static final String REST_SERVICE_URI = "http://localhost:8080/rest";


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

    RestTemplate restTemplate = new RestTemplate();
    HttpEntity<String> httpRequest(){
        return new HttpEntity<>(getHeaders());
    }

    public <T>T connect(String url, HttpMethod httpMethod ,ParameterizedTypeReference<T> parameterizedType){
        ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, httpRequest(), parameterizedType);
        return response.getBody();
    }

/*


    public static void main(String[] args) {
        OneRepoConnector connct = new OneRepoConnector("admin", "admin");

        connct.connect(REST_SERVICE_URI + "/documents/", HttpMethod.GET, new ParameterizedTypeReference<List<DocumentRepoOne>>(){}).forEach(System.out::println);
        DocumentRepoOne  doc= connct.connect(REST_SERVICE_URI + "/documents/name9", HttpMethod.GET, new ParameterizedTypeReference<DocumentRepoOne>(){});
        System.out.println("======"+doc+"=======");

    }
*/

}
