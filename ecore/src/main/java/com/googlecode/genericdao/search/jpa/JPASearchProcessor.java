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
package com.googlecode.genericdao.search.jpa;

import com.googlecode.genericdao.search.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Query;
import java.util.*;

/**
 * <p>Implementation of BaseSearchProcessor that works with JPA.
 * 
 * <p>This class is designed to be used as a singleton. The constructor requires a
 * MetadataUtil instance. Each MetadataUtil instance is typically associated
 * with a single persistence unit (i.e. EntityManagerFactory). A
 * JPASearchProcessor can only be used with EntityManagers that are associated
 * with the same persistence unit as the MetadataUtil. If an application has
 * multiple persistence units, it will need to have multiple corresponding
 * Search Processors.
 * 
 * @author dwolverton
 */
public class JPASearchProcessor extends BaseSearchProcessor {
	private static Logger logger = LoggerFactory.getLogger(JPASearchProcessor.class);

	public JPASearchProcessor(MetadataUtil mdu) {
		super(QLTYPE_EQL, mdu);
	}

	// --- Public Methods ---

	/**
	 * Search for objects based on the search parameters in the specified
	 * <code>ISearch</code> object.
	 * 
	 * @see ISearch
	 */
	@SuppressWarnings("unchecked")
	public List search(EntityManager entityManager, ISearch search) {
		if (search == null)
			return null;

		return search(entityManager, search.getSearchClass(), search);
	}

	/**
	 * Search for objects based on the search parameters in the specified
	 * <code>ISearch</code> object. Uses the specified searchClass, ignoring the
	 * searchClass specified on the search itself.
	 * 
	 * @see ISearch
	 */
	@SuppressWarnings("unchecked")
	public List search(EntityManager entityManager, Class<?> searchClass, ISearch search) {
		if (searchClass == null || search == null)
			return null;

		List<Object> paramList = new ArrayList<Object>();
		String ql = generateQL(searchClass, search, paramList);
		Query query = entityManager.createQuery(ql);
		addParams(query, paramList);
		addPaging(query, search);

		return transformResults(query.getResultList(), search);
	}

	/**
	 * Returns the total number of results that would be returned using the
	 * given <code>ISearch</code> if there were no paging or maxResult limits.
	 * 
	 * @see ISearch
	 */
	public int count(EntityManager entityManager, ISearch search) {
		if (search == null)
			return 0;
		return count(entityManager, search.getSearchClass(), search);
	}

	/**
	 * Returns the total number of results that would be returned using the
	 * given <code>ISearch</code> if there were no paging or maxResult limits.
	 * Uses the specified searchClass, ignoring the searchClass specified on the
	 * search itself.
	 * 
	 * @see ISearch
	 */
	public int count(EntityManager entityManager, Class<?> searchClass, ISearch search) {
		if (searchClass == null || search == null)
			return 0;

		List<Object> paramList = new ArrayList<Object>();
		String ql = generateRowCountQL(searchClass, search, paramList);
		if (ql == null) { // special case where the query uses column operators
			return 1;
		}
		Query query = entityManager.createQuery(ql);
		addParams(query, paramList);

		return ((Number) query.getSingleResult()).intValue();
	}

	/**
	 * Returns a <code>SearchResult</code> object that includes the list of
	 * results like <code>search()</code> and the total length like
	 * <code>searchLength</code>.
	 * 
	 * @see ISearch
	 */
	@SuppressWarnings("unchecked")
	public SearchResult searchAndCount(EntityManager entityManager, ISearch search) {
		if (search == null)
			return null;
		return searchAndCount(entityManager, search.getSearchClass(), search);
	}

	/**
	 * Returns a <code>SearchResult</code> object that includes the list of
	 * results like <code>search()</code> and the total length like
	 * <code>searchLength</code>. Uses the specified searchClass, ignoring the
	 * searchClass specified on the search itself.
	 * 
	 * @see ISearch
	 */
	@SuppressWarnings("unchecked")
	public SearchResult searchAndCount(EntityManager entityManager, Class<?> searchClass, ISearch search) {
		if (searchClass == null || search == null)
			return null;

		SearchResult result = new SearchResult();
		result.setResult(search(entityManager, searchClass, search));

		if (search.getMaxResults() > 0) {
			result.setTotalCount(count(entityManager, searchClass, search));
		} else {
			result.setTotalCount(result.getResult().size() + SearchUtil.calcFirstResult(search));
		}

		return result;
	}

	/**
	 * Search for a single result using the given parameters.
	 */
	public Object searchUnique(EntityManager entityManager, ISearch search) throws NonUniqueResultException {
		if (search == null)
			return null;
		return searchUnique(entityManager, search.getSearchClass(), search);
	}

	/**
	 * Search for a single result using the given parameters. Uses the specified
	 * searchClass, ignoring the searchClass specified on the search itself.
	 */
	public Object searchUnique(EntityManager entityManager, Class<?> entityClass, ISearch search)
			throws NonUniqueResultException {
		if (search == null)
			return null;

		List<Object> paramList = new ArrayList<Object>();
		String ql = generateQL(entityClass, search, paramList);
		Query query = entityManager.createQuery(ql);
		addParams(query, paramList);
		addPaging(query, search);
		try {
			return transformResult(query.getSingleResult(), search);
		} catch (NoResultException ex) {
			return transformResult(null, search);
		}
	}

	// ---- SEARCH HELPERS ---- //

	private void addParams(Query query, List<Object> params) {
		StringBuilder debug = null;

		int i = 1;
		for (Object o : params) {
			if (logger.isDebugEnabled()) {
				if (debug == null)
					debug = new StringBuilder();
				else
					debug.append("\n\t");
				debug.append("p");
				debug.append(i);
				debug.append(": ");
				debug.append(InternalUtil.paramDisplayString(o));
			}
			query.setParameter("p" + Integer.toString(i++), o);
		}
		if (debug != null && debug.length() != 0) {
			logger.debug(debug.toString());
		}
	}

	private void addPaging(Query query, ISearch search) {
		int firstResult = SearchUtil.calcFirstResult(search);
		if (firstResult > 0) {
			query.setFirstResult(firstResult);
		}
		if (search.getMaxResults() > 0) {
			query.setMaxResults(search.getMaxResults());
		}
	}

	@SuppressWarnings("unchecked")
	private Object transformResult(Object result, ISearch search) {
		List results = new ArrayList(1);
		results.add(result);
		return transformResults(results, search).get(0);
	}

	@SuppressWarnings("unchecked")
	private List transformResults(List results, ISearch search) {
		if (results.size() == 0)
			return results;

		int resultMode = search.getResultMode();
		if (resultMode == ISearch.RESULT_AUTO) {
			int count = 0;
			Iterator<Field> fieldItr = search.getFields().iterator();
			while (fieldItr.hasNext()) {
				Field field = fieldItr.next();
				if (field.getKey() != null && !field.getKey().equals("")) {
					resultMode = ISearch.RESULT_MAP;
					break;
				}
				count++;
			}
			if (resultMode == ISearch.RESULT_AUTO) {
				if (count > 1)
					resultMode = ISearch.RESULT_ARRAY;
				else
					resultMode = ISearch.RESULT_SINGLE;
			}
		}

		switch (resultMode) {
		case ISearch.RESULT_ARRAY:
			if (!(results.get(0) instanceof Object[])) {
				List<Object[]> rArray = new ArrayList<Object[]>(results.size());
				for (Object result : results) {
					rArray.add(new Object[] { result });
				}
				return rArray;
			} else {
				return results;
			}
		case ISearch.RESULT_LIST:
			List<List> rList = new ArrayList<List>(results.size());
			if (results.get(0) instanceof Object[]) {
				for (Object[] result : (List<Object[]>) results) {
					List list = new ArrayList(result.length);
					for (Object o : result) {
						list.add(o);
					}
					rList.add(list);
				}
			} else {
				for (Object result : results) {
					List list = new ArrayList(1);
					list.add(result);
					rList.add(list);
				}
			}
			return rList;
		case ISearch.RESULT_MAP:
			List<String> keyList = new ArrayList<String>();
			Iterator<Field> fieldItr = search.getFields().iterator();
			while (fieldItr.hasNext()) {
				Field field = fieldItr.next();
				if (field.getKey() != null && !field.getKey().equals("")) {
					keyList.add(field.getKey());
				} else {
					keyList.add(field.getProperty());
				}
			}

			List<Map<String, Object>> rMap = new ArrayList<Map<String, Object>>(results.size());
			if (results.get(0) instanceof Object[]) {
				for (Object[] result : (List<Object[]>) results) {
					Map<String, Object> map = new HashMap<String, Object>();
					for (int i = 0; i < keyList.size(); i++) {
						String key = keyList.get(i);
						if (key != null) {
							map.put(key, result[i]);
						}
					}
					rMap.add(map);
				}
			} else if (keyList.size() == 1) {
				for (Object result : results) {
					Map<String, Object> map = new HashMap<String, Object>();
					;
					if (keyList.get(0) != null)
						map.put(keyList.get(0), result);
					rMap.add(map);
				}
			} else {
				throw new RuntimeException(
						"Unexpected condition: a single object was returned from the query for each record, but the Search expects multiple.");
			}

			return rMap;
		default: // ISearch.RESULT_SINGLE
			return results;
		}
	}
}
