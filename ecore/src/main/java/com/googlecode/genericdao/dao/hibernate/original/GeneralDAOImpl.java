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

import com.googlecode.genericdao.dao.hibernate.HibernateBaseDAO;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
import org.hibernate.NonUniqueResultException;

import java.io.Serializable;
import java.util.List;

/**
 * Implementation of <code>GeneralDAO</code> using Hibernate.
 * The SessionFactory property is annotated for automatic resource injection.
 * 
 * @author dwolverton
 */
@SuppressWarnings("unchecked")
public class GeneralDAOImpl extends HibernateBaseDAO implements GeneralDAO {

	public void create(Object object) {
		_save(object);
	}

	public boolean createOrUpdate(Object object) {
		Serializable id = getMetadataUtil().getId(object);
		if (id == null || (new Long(0)).equals(id)) {
			create(object);
			return true;
		} else {
			update(object);
			return false;
		}
	}

	public boolean deleteById(Class<?> klass, Serializable id) {
		return _deleteById(klass, id);
	}

	public boolean deleteEntity(Object object) {
		return _deleteEntity(object);
	}

	public <T> T fetch(Class<T> klass, Serializable id) {
		return _get(klass, id);
	}

	public <T> List<T> fetchAll(Class<T> klass) {
		return _all(klass);
	}

	public void update(Object object) {
		_update(object);
	}

	public List search(ISearch search) {
		return _search(search);
	}

	public int count(ISearch search) {
		return _count(search);
	}

	public SearchResult searchAndCount(ISearch search) {
		return _searchAndCount(search);
	}

	public void flush() {
		_flush();
	}

	public boolean isConnected(Object object) {
		return _sessionContains(object);
	}

	public Object searchUnique(ISearch search) throws NonUniqueResultException {
		return _searchUnique(search);
	}

	public void refresh(Object object) {
		_refresh(object);
	}
	
	public Filter getFilterFromExample(Object example) {
		return _getFilterFromExample(example);
	}

	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		return _getFilterFromExample(example, options);
	}
}
