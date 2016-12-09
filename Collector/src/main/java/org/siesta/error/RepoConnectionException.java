package org.siesta.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Connection exception
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class RepoConnectionException extends RuntimeException {
    public RepoConnectionException(String repoName) {
        super("cant connect to repository "+repoName);
    }
}
