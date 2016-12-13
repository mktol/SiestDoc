package org.siesta.service;

import org.siesta.model.Document;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Created by mtol on 13.12.2016.
 */
public class SiestaDocumentConnectFunction implements Function<Future<Document>,  Document> {
    @Override
    public Document apply(Future<Document> future) {
        try {
            Document document= future.get(30, TimeUnit.SECONDS);
            return document;
        }catch (Exception ex) {
//            RepoConnectionException eae = (RepoConnectionException) ex.getCause();
//            throw eae;
            return null;
        }
    }
}
