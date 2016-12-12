package com.repo.one.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

/**
 * Registration service. Make registration into connector
 */
@Service
public class AutoRegistrationService {
    @Value("${repo.name}")
    private String repoName;

    @Autowired
    ServletContext servletContext;

    @Value("${collector.reg.url}")
    private String regUrl;

    @PostConstruct
    public void init(){
        System.out.println(servletContext.getContextPath());
        System.out.println(servletContext.getServerInfo());
        System.out.println(servletContext.getVirtualServerName());
    };
}
