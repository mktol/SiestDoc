package com.repo.one.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.UUID;

/**
 * Registration service. Make registration into connector
 */
@Service
public class AutoRegistrationService {
    @Autowired
    ServletContext servletContext;
/*    @Value("${repo.name}")
    private String repoName;*/
    @Value("${collector.reg.url}")
    private String regUrl;
    @Value("${collector.unreg.url}")
    private String unregUrl;
    @Value("${repo.selfurl}")
    private String selfUrl;
    @Value("${server.port}")
    private String port;
    @Value("${spring.application.name}")
    private String appName;
    private RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        System.out.println(servletContext.getContextPath());
        System.out.println(servletContext.getServerInfo());
        System.out.println(servletContext.getVirtualServerName());
    }

    public boolean registrationIntoCollector() {
        String documentAccessUrl = selfUrl + port + "/" + appName+"/rest";
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        String repoName = UUID.randomUUID().toString().substring(10);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(regUrl)
                .queryParam("connectUrl", documentAccessUrl)
                .queryParam("name", repoName);
        HttpEntity<Boolean> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                request,
                Boolean.class);

        return response.getBody();
    }

   /* @PreDestroy
    public boolean cancelRegistration(){
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> request = new HttpEntity<>(headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(unregUrl)
                .queryParam("name", repoName);
        HttpEntity<Boolean> response = restTemplate.exchange(
                builder.build().encode().toUri(),
                HttpMethod.POST,
                request,
                Boolean.class);
        return response.getBody();
    }*/


}
