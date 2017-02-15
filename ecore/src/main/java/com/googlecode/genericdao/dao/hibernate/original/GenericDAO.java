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

import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
import org.hibernate.NonUniqueResultException;

import java.io.Serializable;
import java.util.List;

/**
 * Interface for a Data Access Object that can be used for a single specified
 * type domain object. A single instance implementing this interface can be used
 * only for the type of domain object specified in the type parameters.
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
public interface GenericDAO<T, ID extends Serializable> {

	/**
	 * Add the specified object as a new entry in the datastore.
	 */
	public void create(T object);

	/**
	 * If the id of the object is null or zero, create, otherwise update.
	 * 
	 * @return <code>true</code> if create; <code>false</code> if update.
	 */
	public boolean createOrUpdate(T object);

	/**
	 * Remove the object of this type with the specified id from the datastore.
	 * 
	 * @return <code>true</code> if the object is found in the datastore and
	 *         removed, <code>false</code> if the item is not found.
	 */
	public boolean deleteById(ID id);

	/**
	 * Remove the specified object from the datastore.
	 * 
	 * @return <code>true</code> if the object is found in the datastore and
	 *         removed, <code>false</code> if the item is not found.
	 */
	public boolean deleteEntity(T object);

	/**
	 * Get the object of this type with the specified id from the datastore.
	 */
	public T fetch(ID id);

	/**
	 * Get a list of all the objects of this type.
	 */
	public List<T> fetchAll();

	/**
	 * Update the corresponding object in the datastore with the properties of
	 * the specified object. The corresponding object is determined by id.
	 */
	public void update(T object);

	/**
	 * Search for objects of this type given the search parameters in the
	 * specified <code>ISearch</code> object.
	 */
	public List<T> search(ISearch search);

	/**
	 * Returns the total number of results that would be returned using the
	 * given <code>ISearch</code> if there were no paging or maxResult limits.
	 */
	public int count(ISearch search);

	/**
	 * Returns a <code>SearchResult</code> object that includes the list of
	 * results like <code>search()</code> and the total length like
	 * <code>searchLength</code>.
	 */
	public SearchResult<T> searchAndCount(ISearch search);

	/**
	 * Search for objects given the search parameters in the specified
	 * <code>ISearch</code> object. Return an untyped result list. The result
	 * type can be determined by fetch mode and fetches on the search.
	 */
	@SuppressWarnings("unchecked")
	public List searchGeneric(ISearch search);

	/**
	 * Search for a single result using the given parameters.
	 */
	public Object searchUnique(ISearch search) throws NonUniqueResultException;

	/**
	 * Returns true if the object is connected to the current Hibernate session.
	 */
	public boolean isConnected(Object object);

	/**
	 * Flushes changes in the Hibernate cache to the datastore.
	 */
	public void flush();

	/**
	 * Refresh the content of the given entity from the current datastore state.
	 */
	public void refresh(Object object);
	
	/**
	 * Generates a search filter from the given example using default options. 
	 */
	public Filter getFilterFromExample(T example);
	
	/**
	 * Generates a search filter from the given example using the specified options. 
	 */
	public Filter getFilterFromExample(T example, ExampleOptions options);

}