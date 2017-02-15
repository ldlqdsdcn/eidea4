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
package com.googlecode.genericdao.dao.jpa;

import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of <code>GeneralDAO</code> using Hibernate.
 * The SessionFactory property is annotated for automatic resource injection.
 * 
 * @author dwolverton
 */
@SuppressWarnings("unchecked")
public class GeneralDAOImpl extends JPABaseDAO implements GeneralDAO {

	public int count(ISearch search) {
		return _count(search);
	}

	public <T> T find(Class<T> type, Serializable id) {
		return (T) _find(type, id);
	}

	public <T> T[] find(Class<T> type, Serializable... ids) {
		return _find(type, ids);
	}

	public <T> List<T> findAll(Class<T> type) {
		return _all(type);
	}

	public void flush() {
		_flush();
	}

	public <T> T getReference(Class<T> type, Serializable id) {
		return _getReference(type, id);
	}

	public <T> T[] getReferences(Class<T> type, Serializable... ids) {
		return _getReferences(type, ids);
	}

	public boolean isAttached(Object entity) {
		return _contains(entity);
	}

	public void refresh(Object... entities) {
		_refresh(entities);
	}

	public boolean remove(Object entity) {
		return _removeEntity(entity);
	}

	public void remove(Object... entities) {
		_removeEntities(entities);
	}

	public boolean removeById(Class<?> type, Serializable id) {
		return _removeById(type, id);
	}

	public void removeByIds(Class<?> type, Serializable... ids) {
		_removeByIds(type, ids);
	}

	public <T> T merge(T entity) {
		return _merge(entity);
	}

	public Object[] merge(Object... entities) {
		return _merge(Object.class, entities);
	}

	public void persist(Object... entities) {
		_persist(entities);
	}

	public <T> T save(T entity) {
		return _persistOrMerge(entity);
	}

	public Object[] save(Object... entities) {
		return _persistOrMerge(Object.class, entities);
	}

	public List search(ISearch search) {
		return _search(search);
	}

	public SearchResult searchAndCount(ISearch search) {
		return _searchAndCount(search);
	}

	public Object searchUnique(ISearch search) {
		return _searchUnique(search);
	}

	public Filter getFilterFromExample(Object example) {
		return _getFilterFromExample(example);
	}

	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		return _getFilterFromExample(example, options);
	}
}
