package com.glasgow.cs;

import java.util.HashMap;

import com.mongodb.DBObject;

/**
 * ���ݿ�ӿ�
 * @author thomescai@163.com
 * @version 2011-9-27
 */

public interface  MongoService {
	/**
	 * ͨ��gridFS�ϴ�����
	 * @param obj Ŀ�����
	 * @param paramsMap ����map
	 * @return
	 * @throws Exception
	 */
	public  boolean gridFSUpload(Object obj, HashMap<String, Object> paramsMap)
			throws Exception;

	/**
	 * ͨ��gridFSɾ��
	 * @param paramsMap ����map
	 * @return
	 */
	public boolean gridFSDelete(HashMap<String, Object> paramsMap);

	/**
	 * ����DBObject����
	 * @param idName �ö����Id����
	 * @param dbObject �ö���
	 * @return
	 */
	public DBObject insert(String idName, DBObject dbObject);

	/**
	 * ��ȡ����
	 * @param dbObject 
	 * @return
	 */
	public DBObject getByObj(DBObject dbObject);
	
	/**
	 * ��������
	 * @param query �����ݵĶ���
	 * @param obj ���µ�����
	 * @return 
	 */
	public Boolean update(DBObject query,DBObject obj);
	
	
	/**
	 * ɾ������
	 * @param obj
	 */
	public void remove(DBObject obj);


}
