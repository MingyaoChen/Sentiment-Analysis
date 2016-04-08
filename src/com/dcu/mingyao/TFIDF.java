package com.dcu.mingyao;

import java.util.*;

/*
* TFIDF.java
* @author Mingyao Chen 15210749

* The formula is:
* TF(t) = Number of times t appears in the document / Number of words in the document
* IDF(t) = log_e(Total number of documents / Number of Documents containing t)
* The TFIDF(t) score of the term t is the multiple of those two.
*/

public class TFIDF {
    /**
     * @param totalterms: Array of all the words of one document
     * @param termToCheck: term of which tf is to be calculated
     * @return tf value of term to check
     */
    public static double tfCaculator(String[] totalterms, String termToCheck) {
        double count = 0.0;
        double totalLengh = totalterms.length;
        for(String s: totalterms) {
            if(s.equalsIgnoreCase(termToCheck)) {
                count = count + 1.0;
            }
        }
        System.out.println(count );
        System.out.println(totalLengh);
        System.out.println(count / totalLengh);
        return count / totalterms.length;
    }

    /**
     * @params allterms: all the terms of all the documents
     * @params termToCheck
     * @return idf score
     */
    public static double idfCalculator(List<String[]> allTerms, String termToCheck) {
        double count = 0;
        double totalLength = allTerms.size();
        for (String[] ss: allTerms) {
            for (String s: ss) {
                if( s.equalsIgnoreCase(termToCheck)) {
                    count ++;
                    break;
                }
            }
        }
        return Math.log(totalLength / count);
    }
}
