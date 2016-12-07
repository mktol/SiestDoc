package org.siesta.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * not fond error handling
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class DocumentNotFindException extends RuntimeException {
    public DocumentNotFindException(String docId) {
        super("could not find document. id = "+docId);
    }
}
