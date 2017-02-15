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

import com.googlecode.genericdao.dao.DAOUtil;
import com.googlecode.genericdao.search.*;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of <code>GenericDAO</code> using Hibernate.
 * The SessionFactory property is annotated for automatic resource injection.
 * 
 * @author dwolverton
 * 
 * @param <T>
 *            The type of the domain object for which this instance is to be
 *            used.
 * @param <ID>
 *            The type of the id of the domain object for which this instance is
 *            to be used.
 */
@SuppressWarnings("unchecked")
public class GenericDAOImpl<T, ID extends Serializable> extends
        JPABaseDAO implements GenericDAO<T, ID> {

	protected Class<T> persistentClass = (Class<T>) DAOUtil.getTypeArguments(GenericDAOImpl.class, this.getClass()).get(0);

	public int count(ISearch search) {
		if (search == null)
			search = new Search();
		return _count(persistentClass, search);
	}

	public T find(ID id) {
		return _find(persistentClass, id);
	}

	public T[] find(ID... ids) {
		return _find(persistentClass, ids);
	}

	public List<T> findAll() {
		return _all(persistentClass);
	}

	public void flush() {
		_flush();
	}

	public T getReference(ID id) {
		return _getReference(persistentClass, id);
	}

	public T[] getReferences(ID... ids) {
		return _getReferences(persistentClass, ids);
	}

	public boolean isAttached(T entity) {
		return _contains(entity);
	}

	public void refresh(T... entities) {
		_refresh(entities);
	}

	public boolean remove(T entity) {
		return _removeEntity(entity);
	}

	public void remove(T... entities) {
		_removeEntities((Object[]) entities);
	}

	public boolean removeById(ID id) {
		return _removeById(persistentClass, id);
	}

	public void removeByIds(ID... ids) {
		_removeByIds(persistentClass, ids);
	}

	public T merge(T entity) {
		return _merge(entity);
	}

	public T[] merge(T... entities) {
		return _merge(persistentClass, entities);
	}

	public void persist(T... entities) {
		_persist(entities);
	}
	
	public T save(T entity) {
		return _persistOrMerge(entity);
	}

	public T[] save(T... entities) {
		return _persistOrMerge(persistentClass, entities);
	}

	public <RT> List<RT> search(ISearch search) {
		if (search == null)
			return (List<RT>) findAll();
		return _search(persistentClass, search);
	}

	public <RT> SearchResult<RT> searchAndCount(ISearch search) {
		if (search == null) {
			SearchResult<RT> result = new SearchResult<RT>();
			result.setResult((List<RT>) findAll());
			result.setTotalCount(result.getResult().size());
			return result;
		}
		return _searchAndCount(persistentClass, search);
	}

	public <RT> RT searchUnique(ISearch search) {
		return (RT) _searchUnique(persistentClass, search);
	}

	public Filter getFilterFromExample(T example) {
		return _getFilterFromExample(example);
	}

	public Filter getFilterFromExample(T example, ExampleOptions options) {
		return _getFilterFromExample(example, options);
	}
}