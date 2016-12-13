package org.siesta.service;

import org.siesta.model.Document;
import org.siesta.model.DocumentRepoOne;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * This class
 */
public class ConverterUtil { //TODO remove this

    private static String FIND_REPO_NAME = "_(.+)";
    private static String FIND_REPO_ID = "([a-zA-Z-0-9]{36}+)_";
    private static Pattern patternFindName = Pattern.compile(FIND_REPO_NAME);
    private static Pattern patternFindID = Pattern.compile(FIND_REPO_ID);

    public static Document convertToDocument(DocumentRepoOne doc) {
        if (doc != null) {
            Document document = new Document();
            document.setName(doc.getName());
            document.setContent(doc.getContent());
            document.setTitle(doc.getTitle());
            document.setDocId(doc.getId());
            return document;
        }
        throw new IllegalArgumentException("Input parameter cant be null. " + doc);
    }

    public static DocumentRepoOne convertToRepoOneDocument(Document document) {
        if (document != null) {
            DocumentRepoOne documentRepoOne = new DocumentRepoOne();
            documentRepoOne.setTitle(document.getTitle());
            documentRepoOne.setContent(document.getContent());
            documentRepoOne.setName(document.getName());
            String docId = getDocumentId(document.getDocId());
            if(docId.isEmpty()){
                docId = document.getDocId();
            }
            documentRepoOne.setId(docId);
            return documentRepoOne;
        }
        throw new IllegalArgumentException("Input parameter cant be null " + document);

    }

    public static List<Document> convertToDocumetnList(List<DocumentRepoOne> docs) {
        if (docs != null) {
            return docs.stream().map(ConverterUtil::convertToDocument).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Input parameter cant be null " + docs);
    }

    public static List<DocumentRepoOne> convertToDocumentRepoOne(List<Document> documents) {
        if (documents != null) {
            return documents.stream().map(ConverterUtil::convertToRepoOneDocument).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Input parameter cant be null " + documents);
    }

    public static String createId(String docId, String repoName) {
        return docId + "_" + repoName;
    }



    public static String getNameFromId(String generatedDocId) {
        Matcher matcher = patternFindName.matcher(generatedDocId);
        if (matcher.find())
            return matcher.group(1);
        return "";

    }

    public static String getDocumentId(String docId) {
        Matcher matcher = patternFindID.matcher(docId);
        if(matcher.find()){
            return matcher.group(1);
        }
        return "";
    }
}
