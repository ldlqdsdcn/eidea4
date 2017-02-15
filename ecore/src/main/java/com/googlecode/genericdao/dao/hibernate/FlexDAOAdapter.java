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
package com.googlecode.genericdao.dao.hibernate;

import com.googlecode.genericdao.search.SearchResult;
import com.googlecode.genericdao.search.flex.FlexSearch;
import com.googlecode.genericdao.search.flex.FlexSearchWrapper;

import java.io.Serializable;
import java.util.List;

public class FlexDAOAdapter {
	
	private GeneralDAO dao;
	
	public void setDao(GeneralDAO dao) {
		this.dao = dao;
	}
	
	public Object find(String className, Serializable id) throws ClassNotFoundException {
		Class<?> type = Class.forName(className);
		return dao.find(type, id);
	}
	
	public Object[] findMulti(String className, Serializable[] ids) throws ClassNotFoundException {
		Class<?> type = Class.forName(className);
		return dao.find(type, ids);
	}
	
	public List<?> findAll(String className) throws ClassNotFoundException {
		Class<?> type = Class.forName(className);
		return dao.findAll(type);
	}
	
	 //create or update based on whether id already exist in datastore
	public Object save(Object entity) {
		dao.save(entity);
		return entity;
	}
	
	public Object[] saveMulti(Object[] entities) {
		dao.save(entities);
		return entities;
	}
	
	public void remove(Object entity) {
		dao.remove(entity);
	}
	
	public void removeById(String className, Serializable id) throws ClassNotFoundException {
		Class<?> type = Class.forName(className);
		dao.removeById(type, id);
	}
	
	public void removeMulti(Object[] entities) {
		dao.remove(entities);
	}
	
	public void removeMultiById(String className, Serializable[] ids) throws ClassNotFoundException {
		Class<?> type = Class.forName(className);
		dao.removeByIds(type, ids);
	}
	
	public List<?> search(FlexSearch flexSearch) throws ClassNotFoundException {
		return dao.search(new FlexSearchWrapper(flexSearch));
	}
	
	public int count(FlexSearch flexSearch) throws ClassNotFoundException {
		return dao.count(new FlexSearchWrapper(flexSearch));
	}
	
	public SearchResult<?> searchAndCount(FlexSearch flexSearch) throws ClassNotFoundException {
		return dao.searchAndCount(new FlexSearchWrapper(flexSearch));
	}
	
	public Object searchUnique(FlexSearch flexSearch) throws ClassNotFoundException {
		return dao.searchUnique(new FlexSearchWrapper(flexSearch));
	}
}
