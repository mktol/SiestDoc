package org.siesta.service;

import org.siesta.error.RepoConnectionException;
import org.siesta.model.DocumentRepoOne;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * This class
 */
//public class ConnectionFunction implements Function<Future<List<DocumentRepoOne>>,  List<DocumentRepoOne>> {
public class ConnectionFunction implements Function<Future<List<DocumentRepoOne>>,  List<DocumentRepoOne>> {
    @Override
    public List<DocumentRepoOne> apply(Future<List<DocumentRepoOne>> listFuture) {
        try {

            List<DocumentRepoOne> documentRepoOnes = listFuture.get(30, TimeUnit.SECONDS);
            return documentRepoOnes;
        }catch (RepoConnectionException eae){
            throw eae;
        }catch (Exception ex) {

            return new ArrayList<>();
        }
    }
}
