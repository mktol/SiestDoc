package org.siesta.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class
 */
public class DocumentUtil {

    private static String FIND_REPO_NAME = "_(.+)";
    private static String FIND_REPO_ID = "([a-zA-Z-0-9]{36}+)_";
    private static Pattern patternFindName = Pattern.compile(FIND_REPO_NAME);
    private static Pattern patternFindID = Pattern.compile(FIND_REPO_ID);



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
