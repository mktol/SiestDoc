package org.siesta.service;

import org.siesta.model.Document;
import org.siesta.model.DocumentRepoOne;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This class
 */
public class ConverterUtil {

    public static Document convertToDocument(DocumentRepoOne doc){
        if(doc!=null) {
            Document document = new Document();
            document.setName(doc.getName());
            document.setContent(doc.getContent());
            document.setTitle(doc.getTitle());
            document.setDocId(doc.getId());
            return document;
        }
        throw new IllegalArgumentException("Input parameter cant be null. "+ doc);
    }

    public static DocumentRepoOne convertToRepoOneDocument(Document document){
        if(document!=null){
            DocumentRepoOne documentRepoOne = new DocumentRepoOne();
            documentRepoOne.setTitle(document.getTitle());
            documentRepoOne.setContent(document.getContent());
            documentRepoOne.setName(document.getName());
            documentRepoOne.setId(document.getDocId());
            return documentRepoOne;
        }
        throw new IllegalArgumentException("Input parameter cant be null "+ document);

    }

    public static List<Document> convertToDocumetnList(List<DocumentRepoOne> docs){
        if(docs!=null){
            return docs.stream().map(ConverterUtil::convertToDocument).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Input parameter cant be null "+ docs);
    }

    public static List<DocumentRepoOne> convertToDocumentRepoOne(List<Document> documents){
        if(documents!=null){
            return documents.stream().map(ConverterUtil::convertToRepoOneDocument).collect(Collectors.toList());
        }
        throw new IllegalArgumentException("Input parameter cant be null "+ documents);
    }
}
