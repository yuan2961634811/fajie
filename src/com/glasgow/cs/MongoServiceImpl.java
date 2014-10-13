package com.glasgow.cs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;
import com.mongodb.WriteResult;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSFile;

public  class MongoServiceImpl implements MongoService{
	private String bucketName;
	private DB db;

	public MongoServiceImpl(String dbName, String bucketName) {
		this.bucketName = bucketName;
		Mongo mongo;
		try {
			mongo = new Mongo("localhost", 27017);
			this.db = mongo.getDB(dbName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ͨ��gridFS�ϴ�����
	 * @param obj Ŀ�����
	 * @param paramsMap ����map
	 * @return
	 * @throws Exception
	 */
	@Override
	public synchronized boolean gridFSUpload(Object obj, HashMap<String, Object> paramsMap)
			throws IOException {
		boolean flag = false;

		GridFS gridFS = new GridFS(db, bucketName);
		
		GridFSFile gridFSFile = null;
		if (obj instanceof InputStream) {
			gridFSFile = gridFS.createFile((InputStream) obj);
		} else if (obj instanceof byte[]) {
			gridFSFile = gridFS.createFile((byte[]) obj);
		} else if (obj instanceof File) {
			gridFSFile = gridFS.createFile((File) obj);
		}
		if (gridFSFile != null && paramsMap != null) {
			Iterator iter = paramsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Object> entry = (Entry<String, Object>) iter.next();
				gridFSFile.put(entry.getKey(), entry.getValue());
			}
			gridFSFile.save();
			flag = true;
		}
		return flag;
	}

	/**
	 * ͨ��gridFSɾ��
	 * @param paramsMap ����map
	 * @return
	 */
	@Override
	public synchronized boolean gridFSDelete(HashMap<String, Object> paramsMap) {
		boolean flag = false;
		GridFS gridFS = new GridFS(db, bucketName);
		DBObject query = new BasicDBObject();
		if (paramsMap != null) {
			Iterator iter = paramsMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Object> entry = (Entry<String, Object>) iter.next();
				query.put(entry.getKey(), entry.getValue());
			}
		}
		DBObject obj = gridFS.findOne(query);
		if(obj != null){
			gridFS.remove(obj);
			flag = true;
		}
		return flag;
	}

	/**
	 * ����DBObject����
	 * @param idName �ö����Id����
	 * @param dbObject �ö���
	 * @return
	 */
	@Override
	public synchronized DBObject insert(String idName, DBObject dbObject) {
		Integer id = getAutoIncreaseID(idName);
		dbObject.put(idName, id);
		getCollection().insert(dbObject);
		return dbObject;
	}

	/**
	 * ��ȡ����
	 * @return 
	 */
	public DBCollection getCollection() {
		return db.getCollection(this.bucketName);
	}

	/**
	 * ���ݱ�����ȡ����
	 * @param name 
	 * @return
	 */
	public DBCollection getCollection(String name) {
		return db.getCollection(name);
	}

	/**
	 * ����Id
	 * @param idName ����Id����
	 * @return Id
	 */
	public Integer getAutoIncreaseID(String idName) {
		BasicDBObject query = new BasicDBObject();
		query.put("name", idName);

		BasicDBObject update = new BasicDBObject();
		update.put("$inc", new BasicDBObject("id", 1));

		DBObject dbObject2 = getCollection("inc_ids").findAndModify(query,
				null, null, false, update, true, true);
		
		Integer id = (Integer) dbObject2.get("id");
		return id;
	}
	
	/**
	 * ��ȡ����
	 * @param dbObject 
	 * @return
	 */
	@Override
	public synchronized DBObject getByObj(DBObject dbObject) {
		return getCollection().findOne(dbObject);
	}
	
	/**
	 * ��������
	 * @param query �����ݵĶ���
	 * @param obj ���µ�����
	 * @return 
	 */
	@Override
	public synchronized Boolean update(DBObject query,DBObject obj) {
		WriteResult rs = getCollection().update(query, obj);
		return (Boolean) rs.getField("updatedExisting");
	}

	

	@Override
	public void remove(DBObject obj) {
		WriteResult result = getCollection().remove(obj);
	}


}
