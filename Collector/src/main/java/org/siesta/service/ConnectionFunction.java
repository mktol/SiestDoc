package org.siesta.service;

import org.siesta.model.DocumentRepoOne;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * This maintains connection to repository
 */

public class ConnectionFunction implements Function<Future<List<DocumentRepoOne>>,  List<DocumentRepoOne>> {
    @Override
    public List<DocumentRepoOne> apply(Future<List<DocumentRepoOne>> listFuture) {
        try {
            List<DocumentRepoOne> documentRepoOnes = listFuture.get(30, TimeUnit.SECONDS);
            return documentRepoOnes;
        }catch (Exception ex) {
//            RepoConnectionException eae = (RepoConnectionException) ex.getCause();
//            throw eae;
            return new ArrayList<>();
        }
    }

}
