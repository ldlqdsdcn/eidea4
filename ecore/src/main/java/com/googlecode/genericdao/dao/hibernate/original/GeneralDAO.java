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
 * Interface for general Data Access Object that can be used for any type domain
 * object. A single instance implementing this interface can be used for
 * multiple types of domain objects.
 * 
 * @author dwolverton
 */
@SuppressWarnings("unchecked")
public interface GeneralDAO {

	/**
	 * Add the specified object as a new entry in the datastore.
	 */
	public void create(Object object);

	/**
	 * If the id of the object is null or zero, create, otherwise update.
	 * 
	 * @return <code>true</code> if create; <code>false</code> if update.
	 */
	public boolean createOrUpdate(Object object);

	/**
	 * Remove the object with the specified id and class from the datastore.
	 * 
	 * @return <code>true</code> if the object is found in the datastore and
	 *         removed, <code>false</code> if the item is not found.
	 */
	public boolean deleteById(Class<?> klass, Serializable id);

	/**
	 * Remove the specified object from the datastore.
	 * 
	 * @return <code>true</code> if the object is found in the datastore and
	 *         removed, <code>false</code> if the item is not found.
	 */
	public boolean deleteEntity(Object object);

	/**
	 * Get the object with the specified id and class from the datastore.
	 */
	public <T> T fetch(Class<T> klass, Serializable id);

	/**
	 * Get a list of all the objects of the specified type.
	 */
	public <T> List<T> fetchAll(Class<T> klass);

	/**
	 * Update the corresponding object in the datastore with the properties of
	 * the specified object. The corresponding object is determined by id.
	 */
	public void update(Object object);

	/**
	 * Search for objects given the search parameters in the specified
	 * <code>ISearch</code> object.
	 */
	public List search(ISearch search);

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
	public SearchResult searchAndCount(ISearch search);

	/**
	 * ISearch for a single result using the given parameters.
	 */
	public Object searchUnique(ISearch search) throws NonUniqueResultException;

	/**
	 * Flushes changes in the Hibernate session to the datastore.
	 */
	public void flush();

	/**
	 * Returns true if the object is connected to the current Hibernate session.
	 */
	public boolean isConnected(Object object);

	/**
	 * Refresh the content of the given entity from the current datastore state.
	 */
	public void refresh(Object object);
	
	/**
	 * Generates a search filter from the given example using default options. 
	 */
	public Filter getFilterFromExample(Object example);
	
	/**
	 * Generates a search filter from the given example using the specified options. 
	 */
	public Filter getFilterFromExample(Object example, ExampleOptions options);

}