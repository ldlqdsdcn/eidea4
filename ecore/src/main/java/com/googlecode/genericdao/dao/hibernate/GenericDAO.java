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

import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

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
	 * <p>
	 * Get the entity with the specified type and id from the datastore.
	 * 
	 * <p>
	 * If none is found, return null.
	 */
	public T find(ID id);

	/**
	 * Get all entities of the specified type from the datastore that have one
	 * of these ids.
	 */
	public T[] find(ID... ids);

	/**
	 * <p>
	 * Get a reference to the entity with the specified type and id from the
	 * datastore.
	 * 
	 * <p>
	 * This does not require a call to the datastore and does not populate any
	 * of the entity's values. Values may be fetched lazily at a later time.
	 * This increases performance if a another entity is being saved that should
	 * reference this entity but the values of this entity are not needed.
	 * 
	 * @throws a
	 *             HibernateException if no matching entity is found
	 */
	public T getReference(ID id);

	/**
	 * <p>
	 * Get a reference to the entities of the specified type with the given ids
	 * from the datastore.
	 * 
	 * <p>
	 * This does not require a call to the datastore and does not populate any
	 * of the entities' values. Values may be fetched lazily at a later time.
	 * This increases performance if a another entity is being saved that should
	 * reference these entities but the values of these entities are not needed.
	 * 
	 * @throws a
	 *             HibernateException if any of the matching entities are not
	 *             found.
	 */
	public T[] getReferences(ID... ids);

	/**
	 * <p>
	 * If the id of the entity is null or zero, add it to the datastore and
	 * assign it an id; otherwise, update the corresponding entity in the
	 * datastore with the properties of this entity. In either case the entity
	 * passed to this method will be attached to the session.
	 * 
	 * <p>
	 * If an entity to update is already attached to the session, this method
	 * will have no effect. If an entity to update has the same id as another
	 * instance already attached to the session, an error will be thrown.
	 * 
	 * @return <code>true</code> if create; <code>false</code> if update.
	 */
	public boolean save(T entity);

	/**
	 * <p>
	 * For each entity, if the id of the entity is null or zero, add it to the
	 * datastore and assign it an id; otherwise, update the corresponding entity
	 * in the datastore with the properties of this entity. In either case the
	 * entity passed to this method will be attached to the session.
	 * 
	 * <p>
	 * If an entity to update is already attached to the session, this method
	 * will have no effect. If an entity to update has the same id as another
	 * instance already attached to the session, an error will be thrown.
	 */
	public boolean[] save(T... entities);

	/**
	 * Remove the specified entity from the datastore.
	 * 
	 * @return <code>true</code> if the entity is found in the datastore and
	 *         removed, <code>false</code> if it is not found.
	 */
	public boolean remove(T entity);

	/**
	 * Remove all of the specified entities from the datastore.
	 */
	public void remove(T... entities);

	/**
	 * Remove the entity with the specified type and id from the datastore.
	 * 
	 * @return <code>true</code> if the entity is found in the datastore and
	 *         removed, <code>false</code> if it is not found.
	 */
	public boolean removeById(ID id);

	/**
	 * Remove all the entities of the given type from the datastore that have
	 * one of these ids.
	 */
	public void removeByIds(ID... ids);

	/**
	 * Get a list of all the objects of the specified type.
	 */
	public List<T> findAll();

	/**
	 * Search for entities given the search parameters in the specified
	 * <code>ISearch</code> object.
	 * 
	 * @param RT The result type is automatically determined by the context in which the method is called.
	 */
	public <RT> List<RT> search(ISearch search);

	/**
	 * Search for a single entity using the given parameters.
	 * 
	 * @param RT The result type is automatically determined by the context in which the method is called.
	 */
	public <RT> RT searchUnique(ISearch search);

	/**
	 * Returns the total number of results that would be returned using the
	 * given <code>ISearch</code> if there were no paging or maxResults limits.
	 */
	public int count(ISearch search);

	/**
	 * Returns a <code>SearchResult</code> object that includes both the list of
	 * results like <code>search()</code> and the total length like
	 * <code>count()</code>.
	 * 
	 * @param RT The result type is automatically determined by the context in which the method is called.
	 */
	public <RT> SearchResult<RT> searchAndCount(ISearch search);

	/**
	 * Returns <code>true</code> if the object is connected to the current
	 * Hibernate session.
	 */
	public boolean isAttached(T entity);

	/**
	 * Refresh the content of the given entity from the current datastore state.
	 */
	public void refresh(T... entities);

	/**
	 * Flushes changes in the Hibernate session to the datastore.
	 */
	public void flush();
	
	/**
	 * Generates a search filter from the given example using default options. 
	 */
	public Filter getFilterFromExample(T example);
	
	/**
	 * Generates a search filter from the given example using the specified options. 
	 */
	public Filter getFilterFromExample(T example, ExampleOptions options);

}
