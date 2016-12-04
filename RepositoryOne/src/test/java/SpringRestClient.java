import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repo.one.model.Document;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.log.SystemLogHandler;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * This class
 */
public class SpringRestClient {
    public static final String REST_SERVICE_URI = "http://localhost:8080/rest";

    private static HttpHeaders getHeaders(){
        String plainCredentials="admin:admin";
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    private static List<Document> listAllDoc(){
        System.out.println("\nTest getDocks API---------------");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        List<Document> response = restTemplate.exchange(REST_SERVICE_URI+"/documents/", HttpMethod.GET, request, new ParameterizedTypeReference<List<Document>>(){}).getBody();
        response.forEach(System.out::println);
        return response;
    }

    //connector.connect(OneRepoConnector.REST_SERVICE_URI + "/documents/"+name+"/", HttpMethod.GET, new ParameterizedTypeReference<Document>() {

    private static void updateDocument() throws JsonProcessingException {
        System.out.println("Lets update document.");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        String docId2 = listAllDoc().get(1).getId();
        Document document = new Document(docId2, "custom NAME", "my title", "TEST CONTENT");
        ObjectMapper mapper = new ObjectMapper();
        String doc = mapper.writeValueAsString(document);

        restTemplate.put(REST_SERVICE_URI+"documents/"+docId2, document);
//        Document resp = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+docId2, HttpMethod.PUT, request, new ParameterizedTypeReference<Document>(){}).getBody();

    }

    private static void deleteDocument(){
        System.out.println("Lets delete document");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        String docId2 = listAllDoc().get(1).getId();
        boolean resp = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+docId2, HttpMethod.DELETE, request, new ParameterizedTypeReference<Boolean>(){}).getBody();
        System.out.println(resp);
    }

    public static void main(String[] args) throws JsonProcessingException {
        listAllDoc();
        updateDocument();
        deleteDocument();
    }
}
