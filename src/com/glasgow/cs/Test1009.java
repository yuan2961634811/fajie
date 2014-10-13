package com.glasgow.cs;

import org.bson.BasicBSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;


public class Test1009 {
	
	public static void main(String[] args) {
		MongoService userDAO = new MongoServiceImpl("test", "wt10");
//		MongoService userDAO = new MongoServiceImpl("1", "1");
		System.out.println("=============insert===============");
		DBObject obj = new BasicDBObject();
		obj.put("name", "原发杰");
		obj = userDAO.insert("test_id", obj);
		System.out.println("=============get===============");
		DBObject objGet = new BasicDBObject();
		objGet.put("name", "aaa");
		objGet = userDAO.getByObj(objGet);

		 
	    obj.put("name",new BasicBSONObject(){});
	    obj = userDAO.insert("test_id", obj);
		System.out.println("=============set===============");
		DBObject objAdd = new BasicDBObject();
		objAdd.put("age2", "ag2444");

		userDAO.update(obj, new BasicDBObject("$set", objAdd));

		System.out.println("=============集合===============");
		DBObject objArrays = new BasicDBObject();
		objArrays.put("arrays", new BasicDBObject("array1", "111"));
       
		userDAO.update(obj, new BasicDBObject("$push", objArrays));
//		
		BasicDBObject document = new BasicDBObject();
		document.put("database", "mkyongDB");
		document.put("table", "hosting");
		BasicDBObject documentDetail = new BasicDBObject();
		documentDetail.put("records", "99");
		documentDetail.put("index", "vps_index1");
		documentDetail.put("active", "true");
		document.put("detail", documentDetail);
		userDAO.insert("test_id", document);
//		userDAO.remove(new BasicDBObject("test_id", 1));
	}
}