package org.siesta.service;

import org.siesta.model.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by mtol on 13.12.2016.
 */
public class SiestaListConnectFunction implements Function<Future<List<Document>>,  List<Document>> {
    @Override
    public List<Document> apply(Future<List<Document>> listFuture) {
        try {
            List<Document> documentRepoOnes = listFuture.get(30, TimeUnit.SECONDS);
            return documentRepoOnes;
        }catch (Exception ex) {
//            RepoConnectionException eae = (RepoConnectionException) ex.getCause();
//            throw eae;
            return new ArrayList<>();
        }
    }
}
