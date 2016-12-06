package org.siesta.service;

import org.siesta.model.DocumentRepoOne;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.function.Function;

/**
 * This class
 */
public class ConnectionFunction implements Function<Future<List<DocumentRepoOne>>,  List<DocumentRepoOne>> {
    @Override
    public List<DocumentRepoOne> apply(Future<List<DocumentRepoOne>> listFuture) {
        try {
            return listFuture.get();
        } catch (Exception ex) {
            return new ArrayList<DocumentRepoOne>();
        }
    }
}
