package java;

import org.siesta.model.Document;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Class for testing crud query. Not unit testing
 */
public class RestTest {


    private static Document document = new Document();

    private static RestTemplate restTemplate = new RestTemplate();
//    private static Document getConcreteDoc()
    private static void  initDocuments(){
        document.setComments(new ArrayList<>());
        document.setDocId(UUID.randomUUID().toString());
        document.setContent("balbalbala");
        document.setName("test name");
        document.setTitle("title 3003030");
        System.out.println("Lets update document.");
    }

    private static void addDocument()  {
        HttpEntity<Document> request = new HttpEntity<>( document , getHeaders());
//        restTemplate.postForEntity(REST_SERVICE_URI+"/documents", document, Document.class);
        document= restTemplate.exchange("http://localhost:9000/documents", HttpMethod.POST, request, new ParameterizedTypeReference<Document>(){}).getBody();
        System.out.println(document);
    }

    private static void updateDocument(){

        HttpEntity<Document> request = new HttpEntity<>( document , getHeaders());
        document.setTitle("update title");
        document.setContent("Updated content");
        Boolean document1= restTemplate.exchange("http://localhost:9000/documents", HttpMethod.PUT, request, new ParameterizedTypeReference<Boolean>(){}).getBody();
        System.out.println(document1);
    }

    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static void main(String[] args) {
        initDocuments();
        try {
            addDocument();
            updateDocument();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
