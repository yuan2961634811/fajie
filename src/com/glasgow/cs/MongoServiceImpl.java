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
	 * 通过gridFS上传对象
	 * @param obj 目标对象
	 * @param paramsMap 参数map
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
	 * 通过gridFS删除
	 * @param paramsMap 参数map
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
	 * 插入DBObject对象
	 * @param idName 该对象的Id名称
	 * @param dbObject 该对象
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
	 * 获取连接
	 * @return 
	 */
	public DBCollection getCollection() {
		return db.getCollection(this.bucketName);
	}

	/**
	 * 根据表名获取连接
	 * @param name 
	 * @return
	 */
	public DBCollection getCollection(String name) {
		return db.getCollection(name);
	}

	/**
	 * 自增Id
	 * @param idName 自增Id名称
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
	 * 获取对象
	 * @param dbObject 
	 * @return
	 */
	@Override
	public synchronized DBObject getByObj(DBObject dbObject) {
		return getCollection().findOne(dbObject);
	}
	
	/**
	 * 更新数据
	 * @param query 该数据的对象
	 * @param obj 更新的数据
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
