package org.siesta.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NO_CONTENT, reason = "no content error")
public class NotContentException extends RuntimeException {
    public NotContentException() {
        super();
    }

    public NotContentException(String message) {
        super(message);
    }
}
