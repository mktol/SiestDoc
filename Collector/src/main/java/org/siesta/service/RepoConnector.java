package org.siesta.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

/**
 * This class
 */
public interface RepoConnector {
    String getPepoName();

    <T>T connect(String url, HttpMethod httpMethod , ParameterizedTypeReference<T> parameterizedType);

    <T>T connect(String url, HttpMethod httpMethod , String jsonObject, ParameterizedTypeReference<T> parameterizedType);

    void setRepoName(String repoName);
}
