package com.kved.main;


import java.util.List;

import net.sf.gsaapi.GSAClient;
import net.sf.gsaapi.GSAQuery;
import net.sf.gsaapi.GSAResponse;
import net.sf.gsaapi.GSAResult;
import net.sf.gsaapi.GSAQuery.GSAQueryTerm;

public class GoogleSearch {
   
    // target GSA's hostname
    private static final String HOSTNAME = "indexat.fr";
   
    // query string to search for
    private static final String QUERY_STRING = "java sdk";
   
    // The value for the frontend configured for the GSA
    // (If you dont know this, ask GSA admin for correct value for your target GSA.)
    private static final String SETTING_FRONTEND = "default_frontend";

    public static void main(String[] args) throws Exception {
        GSAClient client = new GSAClient(HOSTNAME); 
        GSAQuery query = new GSAQuery();
       
        // typical way to generate query term.
        GSAQueryTerm term = new GSAQueryTerm(QUERY_STRING);
        query.setQueryTerm(term);
        System.out.println("Searching for: "+query.getQueryString());
       
        // above 2 lines may be equivalently replaced by:
        // query.setAndQueryTerms(new String[]{QUERY_STRING});

        query.setFrontend(SETTING_FRONTEND); // required!
       
        GSAResponse response = client.getGSAResponse(query);
       
        System.out.println("Found " + response.getNumResults() + " results");
        List results = response.getResults();
        System.out.println("Showing top " + results.size() + " results");
        System.out.println("[To get more top N results, use query.setMaxResults(int)]");
        for (int i=0, iSize=results.size(); i<iSize; i++) {
            GSAResult result = (GSAResult) results.get(i);
            System.out.println("--------------------------------------------------");
            System.out.println(result.getRating() +"\t" + result.getTitle());
            System.out.println(result.getSummary());
            System.out.println(result.getUrl());
        }
    }
}