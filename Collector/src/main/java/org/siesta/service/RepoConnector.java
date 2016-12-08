package org.siesta.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;

/**
 * This class
 */
public interface RepoConnector {
    String getUrl();

    void setUrl(String url);

    String getPepoName();

    <T>T connect(String url, HttpMethod httpMethod , ParameterizedTypeReference<T> parameterizedType);

    <T>T connect(String url, HttpMethod httpMethod , T requestObj, ParameterizedTypeReference<T> parameterizedType);

    void setRepoName(String repoName);
}
