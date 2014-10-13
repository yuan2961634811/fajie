package com.glasgow.cs;

import com.mongodb.DB;
import com.mongodb.MongoClient;

public class Test { 
	public static void main(String[] args) {
		System.out.println("fdfa");
	      try{   
	 		 // To connect to mongodb server
	          MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
	          // Now connect to your databases
	          DB db = mongoClient.getDB( "test" );
	          
	 		 System.out.println("Connect to database successfully");
//	          boolean auth = db.authenticate(myUserName, myPassword);
//	 		 System.out.println("Authentication: "+auth);
	       }catch(Exception e){
	 	     System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	 	  }
	    }

	}


