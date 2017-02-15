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

import com.googlecode.genericdao.dao.DAOUtil;
import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;
import com.googlecode.genericdao.search.*;
import org.hibernate.NonUniqueResultException;

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
        HibernateBaseDAO implements GenericDAO<T, ID> {

	protected Class<T> persistentClass = (Class<T>) DAOUtil.getTypeArguments(GenericDAOImpl.class, this.getClass()).get(0);

	public void create(T object) {
		if (!persistentClass.isInstance(object))
			throw new IllegalArgumentException(
					"Object class does not match dao type.");
		_save(object);
	}

	public boolean createOrUpdate(T object) {
		Serializable id = getMetadataUtil().getId(object);
		if (id == null || (new Long(0)).equals(id)) {
			create(object);
			return true;
		} else {
			update(object);
			return false;
		}
	}

	public boolean deleteById(ID id) {
		return _deleteById(persistentClass, id);
	}

	public boolean deleteEntity(T object) {
		if (!persistentClass.isInstance(object))
			throw new IllegalArgumentException(
					"Object class does not match dao type.");
		return _deleteEntity(object);
	}

	public T fetch(ID id) {
		return (T) _get(persistentClass, id);
	}

	public List<T> fetchAll() {
		return _all(persistentClass);
	}

	public void update(T object) {
		if (!persistentClass.isInstance(object))
			throw new IllegalArgumentException(
					"Object class does not match dao type.");
		_update(object);
	}

	public List<T> search(ISearch search) {
		if (search == null)
			return fetchAll();
		
		return _search(persistentClass, search);
	}

	public int count(ISearch search) {
		if (search == null)
			search = new Search();
		return _count(persistentClass, search);
	}

	public SearchResult<T> searchAndCount(ISearch search) {
		if (search == null) {
			SearchResult<T> result = new SearchResult<T>();
			result.setResult(fetchAll());
			result.setTotalCount(result.getResult().size());
			return result;
		}
		
		return _searchAndCount(persistentClass, search);
	}

	public boolean isConnected(Object object) {
		return _sessionContains(object);
	}

	public void flush() {
		_flush();
	}

	public void refresh(Object object) {
		_refresh(object);
	}

	public List searchGeneric(ISearch search) {
		if (search == null)
			return fetchAll();
		
		return _search(persistentClass, search);
	}

	public Object searchUnique(ISearch search) throws NonUniqueResultException {
		return _searchUnique(persistentClass, search);
	}

	public Filter getFilterFromExample(T example) {
		return _getFilterFromExample(example);
	}

	public Filter getFilterFromExample(T example, ExampleOptions options) {
		return _getFilterFromExample(example, options);
	}}