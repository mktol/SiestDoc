package org.siesta.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * not fond document error handling
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "no such document")
public class DocumentNotFindException extends RuntimeException {
    public DocumentNotFindException(String docId) {
        super("could not find document. id = "+docId);
    }

    public DocumentNotFindException() {
        super();
    }
}
