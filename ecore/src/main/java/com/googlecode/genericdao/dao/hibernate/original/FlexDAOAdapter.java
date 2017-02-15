/* Copyright 2013 David Wolverton
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.genericdao.dao.hibernate.original;

import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.flex.FlexSearch;
import com.googlecode.genericdao.search.flex.FlexSearchWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * General Remote DAO for Adobe Flex.
 * 
 * @author dwolverton
 */
@SuppressWarnings("unchecked")
public class FlexDAOAdapter {
	private static long mockDelay = 0;
	
	private GeneralDAO dao;

	public void setDAO(GeneralDAO dao) {
		this.dao = dao;
	}
	
	/**
	 * <p>
	 * Here is an example of one DAO method that could be exposed remotely. The
	 * ID of the object and the class of the object to get are passed in and the
	 * object from the datastore is returned.
	 * <p>
	 * Notice that the implementation first checks for a specific DAO for the
	 * class, and if none is found defaults to the general DAO.
	 */
	public Object fetch(Serializable id, String className) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		Class<?> klass;
		try {
			klass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		
		return dao.fetch(klass, id);
	}

	public List fetchAll(String className) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		Class<?> klass;
		try {
			klass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}

		return dao.fetchAll(klass);
	}

	public Object create(Object object) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		dao.create(object);
		return object;
	}

	public Object update(Object object) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		dao.update(object);
		return object;
	}
	
	public Object createOrUpdate(Object object) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		dao.createOrUpdate(object);
		return object;
	}	

	public void deleteById(Serializable id, String className) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		Class<?> klass;
		try {
			klass = Class.forName(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}

		dao.deleteById(klass, id);
	}

	public void deleteEntity(Object object) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		dao.deleteEntity(object);
	}
	
	public void deleteList(Object[] list) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		
		for(int i = 0; i < list.length; i++) {
			deleteEntity(list[i]);
		}
	}
	
	public void deleteListById(Serializable[] ids, String className) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		
		for(int i = 0; i < ids.length; i++) {
			deleteById(ids[i], className);
		}		
	}

	public List search(FlexSearch flexSearch) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);
		
		return dao.search(new FlexSearchWrapper(flexSearch));
	}

	public int searchLength(FlexSearch flexSearch) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);

		return dao.count(new FlexSearchWrapper(flexSearch));
	}

	public SearchResult searchAndLength(FlexSearch flexSearch) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);

		return dao.searchAndCount(new FlexSearchWrapper(flexSearch));
	}
	
	public Object searchUnique(FlexSearch flexSearch) throws Exception {
		if (mockDelay != 0) Thread.sleep(mockDelay);

		return dao.searchUnique(new FlexSearchWrapper(flexSearch));
	}
}
