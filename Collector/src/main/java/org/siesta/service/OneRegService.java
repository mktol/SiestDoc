package org.siesta.service;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;

/**
 * This class
 */
public class OneRegService {
    public static final String REST_SERVICE_URI = "http://localhost:8080/rest";


    private static String name; //TODO should be encrypted and stored to special file or db
    private static String password;
    public void registOneService(String name, String password){
        this.name = name;
        this.password = password;
    }

    private static HttpHeaders getHeaders() {
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
        ResponseEntity<List<Document>> response = restTemplate.exchange(REST_SERVICE_URI+"/documents/", GET,  request, new ParameterizedTypeReference<List<Document>>() {
        });
        List<Document> docs = response.getBody();
        docs.forEach(System.out::println);


        return false;

    }

    public static void main(String[] args) {
        OneRegService connct = new OneRegService();
        connct.registOneService("admin", "admin");
        connct.connect();
    }

}
