package com.repo.one.controller;

/**
 * This class
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
@ContextConfiguration*/

/*public class DocumentControllerTest {


    private static final String REST_SERVICE_URI = "http://localhost:8080/rest";
    private static final String DOCUMENT_ID = "5dd10405-e6e5-4bf9-8256-d75b45bb0596";
    private HttpHeaders headers;
    private RestTemplate restTemplate =  new RestTemplate();

    private static HttpHeaders getHeaders(){
        String plainCredentials="admin:admin";
        HttpHeaders headers = new HttpHeaders();
        String base64Credentials = new String(Base64.encodeBase64(plainCredentials.getBytes()));
        headers.add("Authorization", "Basic " + base64Credentials);
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        return headers;
    }

    @Before
    public void setup() throws Exception {
        headers = getHeaders();
    }

    @Test
    public void testDocuments(){
        HttpEntity<String> request = new HttpEntity<>(getHeaders());
        ResponseEntity<List<Document>> response = restTemplate.exchange(REST_SERVICE_URI+"/documents/", HttpMethod.GET, request, new ParameterizedTypeReference<List<Document>>(){});
        assertEquals(200, response.getStatusCodeValue());
        assertTrue( response.getBody().size()>0);
    }

    @Test
    public void testAddDocument(){ // test if crete id
        Document newDocument = new Document("test name", "test title", "test content");
        HttpEntity<Document> request = new HttpEntity<>( newDocument , getHeaders());
        ResponseEntity<Document> document= restTemplate.exchange(REST_SERVICE_URI+"/documents", HttpMethod.POST, request, new ParameterizedTypeReference<Document>(){});
        assertEquals(200, document.getStatusCodeValue());
        assertNotNull("Service must create new random id. ", document.getBody().getDocId());
    }

    @Test
    public void testUpdateDocument(){
        Document newDocument = new Document("test name", "test title", "test content");
        HttpEntity<Document> request = new HttpEntity<>( newDocument , getHeaders());
        Document savedDocument = restTemplate.exchange(REST_SERVICE_URI+"/documents", HttpMethod.POST, request, new ParameterizedTypeReference<Document>(){}).getBody();
        savedDocument.setTitle("Special Title");
        request = new HttpEntity<>( savedDocument , getHeaders());
        String savedDcoId = savedDocument.getDocId();
        ResponseEntity<Document> resp = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+savedDcoId, HttpMethod.PUT, request, new ParameterizedTypeReference<Document>(){});
        assertEquals(200, resp.getStatusCodeValue());
        assertEquals(savedDocument, resp.getBody());
    }

    @Test
    public void testGetConcreteDoc(){
        HttpEntity<Document> request = new HttpEntity<>(  getHeaders());
        List<Document> response = restTemplate.exchange(REST_SERVICE_URI+"/documents/"+DOCUMENT_ID, HttpMethod.GET, request, new ParameterizedTypeReference<List<Document>>(){}).getBody();
        assertNotNull(response.get(0));
    }
}}*/
