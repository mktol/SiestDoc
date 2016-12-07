import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.tomcat.util.codec.binary.Base64;
import org.siesta.model.Document;
import org.siesta.model.DocumentRepoOne;
import org.siesta.service.ConverterUtil;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import javax.print.Doc;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

/**
 * Created by mtol on 07.12.2016.
 */
public class RestTest {


//    private static Document getConcreteDoc()

    private static void addDocument() throws JsonProcessingException {
        Document document = new Document();
        document.setComments(new ArrayList<>());
        document.setDocId(UUID.randomUUID().toString());
        document.setContent("balbalbala");
        document.setName("test name");
        document.setTitle("title 3003030");
        System.out.println("Lets update document.");
        RestTemplate restTemplate = new RestTemplate();

        HttpEntity<Document> request = new HttpEntity<>( document , getHeaders());
//        restTemplate.postForEntity(REST_SERVICE_URI+"/documents", document, Document.class);
        DocumentRepoOne document1= restTemplate.exchange("http://localhost:9000/documents", HttpMethod.POST, request, new ParameterizedTypeReference<DocumentRepoOne>(){}).getBody();
        System.out.println(document1);
    }

    private static HttpHeaders getHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public static void main(String[] args) {
        try {
            addDocument();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
