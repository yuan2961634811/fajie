package com.glasgow.cs;

import java.util.HashMap;

import com.mongodb.DBObject;

/**
 * 数据库接口
 * @author thomescai@163.com
 * @version 2011-9-27
 */

public interface  MongoService {
	/**
	 * 通过gridFS上传对象
	 * @param obj 目标对象
	 * @param paramsMap 参数map
	 * @return
	 * @throws Exception
	 */
	public  boolean gridFSUpload(Object obj, HashMap<String, Object> paramsMap)
			throws Exception;

	/**
	 * 通过gridFS删除
	 * @param paramsMap 参数map
	 * @return
	 */
	public boolean gridFSDelete(HashMap<String, Object> paramsMap);

	/**
	 * 插入DBObject对象
	 * @param idName 该对象的Id名称
	 * @param dbObject 该对象
	 * @return
	 */
	public DBObject insert(String idName, DBObject dbObject);

	/**
	 * 获取对象
	 * @param dbObject 
	 * @return
	 */
	public DBObject getByObj(DBObject dbObject);
	
	/**
	 * 更新数据
	 * @param query 该数据的对象
	 * @param obj 更新的数据
	 * @return 
	 */
	public Boolean update(DBObject query,DBObject obj);
	
	
	/**
	 * 删除对象
	 * @param obj
	 */
	public void remove(DBObject obj);


}
