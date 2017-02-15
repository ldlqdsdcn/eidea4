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
package com.googlecode.genericdao.search.hibernate;

import com.googlecode.genericdao.search.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * <p>
 * Hibernate implementation of SearchFacade.
 * 
 * <p>
 * The SessionFactory must be set before an instance of this class can be used.
 * The <code>getCurrentSession()</code> method of the SessionFactory is used
 * when a session is needed.
 * 
 * <p>To change this default behavior, you can override the protected {@link #getSession()} method.
 * 
 * @author dwolverton
 */
public class HibernateSearchFacade implements SearchFacade {
	private SessionFactory sessionFactory;
	private HibernateSearchProcessor processor;

	public HibernateSearchFacade() {
	}

	public HibernateSearchFacade(SessionFactory sessionFactory) {
		setSessionFactory(sessionFactory);
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.processor = HibernateSearchProcessor.getInstanceForSessionFactory(sessionFactory);
		this.sessionFactory = sessionFactory;
	}

	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	protected HibernateSearchProcessor getProcessor() {
		return processor;
	}

	@SuppressWarnings("unchecked")
	public List search(ISearch search) {
		return processor.search(getSession(), search);
	}

	@SuppressWarnings("unchecked")
	public List search(Class<?> searchClass, ISearch search) {
		return processor.search(getSession(), searchClass, search);
	}

	public int count(ISearch search) {
		return processor.count(getSession(), search);
	}

	public int count(Class<?> searchClass, ISearch search) {
		return processor.count(getSession(), searchClass, search);
	}

	@SuppressWarnings("unchecked")
	public SearchResult searchAndCount(ISearch search) {
		return processor.searchAndCount(getSession(), search);
	}

	@SuppressWarnings("unchecked")
	public SearchResult searchAndCount(Class<?> searchClass, ISearch search) {
		return processor.searchAndCount(getSession(), searchClass, search);
	}

	public Object searchUnique(ISearch search) {
		return processor.searchUnique(getSession(), search);
	}

	public Object searchUnique(Class<?> searchClass, ISearch search) {
		return processor.searchUnique(getSession(), searchClass, search);
	}

	public Filter getFilterFromExample(Object example) {
		return processor.getFilterFromExample(example);
	}

	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		return processor.getFilterFromExample(example, options);
	}
}
