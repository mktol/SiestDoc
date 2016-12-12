package com.repo.one;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@ConfigurationProperties(value = "application.properties")
public class Application {

    @Value("${server.port}")
    private static String stringProp1;

    public static void main(String[] args) {
        try {
            InputStream inputStream = Application.class.getResourceAsStream("file.txt");
            byte[] bdata = FileCopyUtils.copyToByteArray(inputStream);
            String s = new String(bdata);
            System.out.println(s);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ApplicationContext ctx = SpringApplication.run(Application.class, args);
        System.out.println("Repository is started");
        System.out.println(stringProp1);
        try {
            String s = new String(Files.readAllBytes(Paths.get("\\src\\main\\resources")));
            System.out.println(s);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}