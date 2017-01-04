package org.siesta.error;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom exception, that can contains list of exceptions. Use for handling rest connections exceptions.
 */
public class AsyncHandleException extends RuntimeException {
    List<Exception> exceptionsList = new ArrayList<>();

    public List<Exception> getExceptionsList() {
        return exceptionsList;
    }
    public void add(Exception exception){
        exceptionsList.add(exception);
    }
}
