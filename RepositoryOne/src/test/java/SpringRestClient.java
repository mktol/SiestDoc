import com.fasterxml.jackson.core.JsonProcessingException;
import com.repo.one.model.Document;
import org.apache.tomcat.util.codec.binary.Base64;
import org.junit.Assert;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * This class
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)*/
public class SpringRestClient {
    public static final String REST_SERVICE_URI = "http://localhost:8080/rest";

    private static HttpHeaders getHeaders(){
//        String plainCredentials="admin:admin";
//        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));

        HttpHeaders headers = new HttpHeaders();
//        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }


    public static List<Document> listAllDoc(){
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
        String docId2 = listAllDoc().get(1).getId();
        Document document = new Document(docId2, "custom NAME", "my title", "TEST CONTENT");
        HttpEntity<Document> request = new HttpEntity<>(document, getHeaders());


//        restTemplate.put(REST_SERVICE_URI+"documents/"+docId2, document);
        ResponseEntity<Document> resp = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+docId2, HttpMethod.PUT, request, new ParameterizedTypeReference<Document>(){});
        System.out.println(resp.getStatusCode());
        System.out.println(resp.getStatusCodeValue());
        System.out.println(resp.getHeaders());

    }

    private static void addDocument() throws JsonProcessingException {
        Document document = new Document( "custom NAME", "my title", "TEST CONTENT");
        System.out.println("Lets update document.");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<Document> request = new HttpEntity<>( document , getHeaders());
//        restTemplate.postForEntity(REST_SERVICE_URI+"/documents", document, Document.class);
        Document document1= restTemplate.exchange(REST_SERVICE_URI+"/documents", HttpMethod.POST, request, new ParameterizedTypeReference<Document>(){}).getBody();


    }

/*    public <T>T connect(String url, HttpMethod httpMethod , String jsonObject, ParameterizedTypeReference<T> parameterizedType){
        HttpHeaders headers = getHeaders();
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonObject, headers);
        ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, httpEntity, parameterizedType);
        return response.getBody();
    }*/
    public static void main(String[] args) throws JsonProcessingException {
        listAllDoc();
//        updateDocument();
        deleteDocument();
//        addDocument();
    }

/*    @Test*/
    public static void deleteDocument(){
        System.out.println("Lets delete document");
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        String docId2 = listAllDoc().get(1).getId();
        boolean resp = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+docId2, HttpMethod.DELETE, request, new ParameterizedTypeReference<Boolean>(){}).getBody();
        Assert.assertTrue(resp);
    }
}
