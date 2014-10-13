package com.glasgow.cs;

import java.util.HashMap;
import java.awt.event.*;



import java.io.IOException;

import org.apache.hadoop.record.compiler.generated.Token;
import org.terrier.applications.TRECIndexing;
import org.terrier.indexing.tokenisation.Tokeniser;
import org.terrier.matching.ResultSet;
import org.terrier.querying.Manager;
import org.terrier.querying.SearchRequest;
import org.terrier.structures.Index;
import org.terrier.structures.MetaIndex;

public class TerrierUse {
	private static String indexPath = "//home/node1/Desktop/terrier-4.0/var/index";

	public static void main(String[] args) {
		System.out.println("dafaf");
//		System.setProperty("terrier.home", "/home/node1/Desktop/terrier-4.0");
//		// TRECIndexing MyIndex = new TRECIndexing(indexPath, "data");
//		// MyIndex.index();
//		// 451:what is a bengals cat
//		QueryIndex("451", "what is a bengals cat");

	}

	public static String QueryIndex(String queryId, String query) {


		// String queryId refer to query number
		// String query refere to query terms
		// ******************************************************************************
		StringBuilder sbuffer = new StringBuilder(); // string for store the
														// retrieval results
		Index index = Index.createIndex(indexPath, "data");
		int RESULTS_LENGTH = 0; // Number of retrieved documents
		// ***************************************************************************************
		// Please have a look on the terrier documentation about that.
		Manager m = new Manager(index);
	
		
	

		return null;
	}

}
