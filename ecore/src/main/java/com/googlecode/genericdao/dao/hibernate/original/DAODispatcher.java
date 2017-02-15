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

import com.googlecode.genericdao.dao.BaseDAODispatcher;
import com.googlecode.genericdao.dao.DAODispatcherException;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;
import org.hibernate.NonUniqueResultException;

import java.io.Serializable;
import java.util.List;

/**
 * <p>This is an implementation of GeneralDAO that delegates to other DAOs
 * depending on what entity class is being processed.
 * 
 * <p>Set the specificDAOs Map in order to configure which DAO will be used
 * for which entity class. If the map contains no entry for a given class,
 * the generalDAO is used.
 * 
 * <p>For example to dispatch operation on com.myproject.model.Customer to a DAO called customerDAO,
 * set the map like this. (Of course tools like Spring can be used to do this
 * configuration more elequently.)
 * <pre>
 * Map<String,Object> specificDAOs = new HashMap<String,Object>();
 * specificDAOs.put("com.myproject.model.Customer", customerDAO);
 * 
 * DAODispatcher dispatcher = new DAODispatcher();
 * dispatcher.setSpecificDAOs(specificDAOs);
 * </pre>
 * 
 * @author dwolverton
 *
 */
@SuppressWarnings("unchecked")
public class DAODispatcher extends BaseDAODispatcher implements GeneralDAO {

	protected GeneralDAO generalDAO;
	
	/**
	 * GeneralDAO has default implementations for the standard DAO methods.
	 * Which model class it uses is specified when calling the particular
	 * method.
	 */
	public void setGeneralDAO(GeneralDAO generalDAO) {
		this.generalDAO = generalDAO;
	}
	
	public void create(Object object) {
		Object specificDAO = getSpecificDAO(object.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				((GenericDAO) specificDAO).create(object);
			} else {
				callMethod(specificDAO, "create", object);
			}
		} else {
			generalDAO.create(object);
		}
	}

	public boolean createOrUpdate(Object object) {
		Object specificDAO = getSpecificDAO(object.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).createOrUpdate(object);
			} else {
				return (Boolean) callMethod(specificDAO, "createOrUpdate", object);
			}
		} else {
			return generalDAO.createOrUpdate(object);
		}
	}

	public boolean deleteById(Class<?> klass, Serializable id) {
		Object specificDAO = getSpecificDAO(klass.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).deleteById(id);
			} else {
				return (Boolean) callMethod(specificDAO, "deleteById", id);
			}
		} else {
			return generalDAO.deleteById(klass, id);
		}
	}

	public boolean deleteEntity(Object object) {
		Object specificDAO = getSpecificDAO(object.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).deleteEntity(object);
			} else {
				return (Boolean) callMethod(specificDAO, "deleteEntity", object);
			}
		} else {
			return generalDAO.deleteEntity(object);
		}
	}

	public <T> T fetch(Class<T> klass, Serializable id) {
		Object specificDAO = getSpecificDAO(klass.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return (T) ((GenericDAO) specificDAO).fetch(id);
			} else {
				return (T) callMethod(specificDAO, "fetch", id);
			}
		} else {
			return generalDAO.fetch(klass, id);
		}
	}

	public <T> List<T> fetchAll(Class<T> klass) {
		Object specificDAO = getSpecificDAO(klass.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).fetchAll();
			} else {
				return (List<T>) callMethod(specificDAO, "fetchAll");
			}
		} else {
			return generalDAO.fetchAll(klass);
		}
	}

	/**
	 * @deprecated use flush(Class<?>)
	 */
	public void flush() {
		throw new DAODispatcherException(
				"The flush() method cannot be used with DAODispatcher because it could does not include a Class type to dispatch to. Use flush(Class<?>).");
	}

	public void flush(Class<?> klass) {
		Object specificDAO = getSpecificDAO(klass.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				((GenericDAO) specificDAO).flush();
			} else {
				callMethod(specificDAO, "flush");
			}
		} else {
			generalDAO.flush();
		}
	}

	public boolean isConnected(Object object) {
		Object specificDAO = getSpecificDAO(object.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).isConnected(object);
			} else {
				return (Boolean) callMethod(specificDAO, "isConnected", object);
			}
		} else {
			return generalDAO.isConnected(object);
		}
	}

	public void refresh(Object object) {
		Object specificDAO = getSpecificDAO(object.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				((GenericDAO) specificDAO).refresh(object);
			} else {
				callMethod(specificDAO, "refresh", object);
			}
		} else {
			generalDAO.refresh(object);
		}
	}

	public List search(ISearch search) {
		Object specificDAO = getSpecificDAO(search.getSearchClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).searchGeneric(search);
			} else {
				try {
					return (List) callMethod(specificDAO, "searchGeneric", search);
				} catch (DAODispatcherException ex) {
					if (ex.getCause() instanceof NoSuchMethodException) {
						return (List) callMethod(specificDAO, "search", search);
					} else {
						throw ex;
					}
				}
			}
		} else {
			return generalDAO.search(search);
		}
	}

	public SearchResult searchAndCount(ISearch search) {
		Object specificDAO = getSpecificDAO(search.getSearchClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).searchAndCount(search);
			} else {
				return (SearchResult) callMethod(specificDAO,
						"searchAndCount", search);
			}
		} else {
			return generalDAO.searchAndCount(search);
		}
	}

	public int count(ISearch search) {
		Object specificDAO = getSpecificDAO(search.getSearchClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).count(search);
			} else {
				return (Integer) callMethod(specificDAO, "count", search);
			}
		} else {
			return generalDAO.count(search);
		}
	}

	public Object searchUnique(ISearch search) throws NonUniqueResultException {
		Object specificDAO = getSpecificDAO(search.getSearchClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).searchUnique(search);
			} else {
				return callMethod(specificDAO, "searchUnique", search);
			}
		} else {
			return generalDAO.searchUnique(search);
		}
	}

	public void update(Object object) {
		Object specificDAO = getSpecificDAO(object.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				((GenericDAO) specificDAO).update(object);
			} else {
				callMethod(specificDAO, "update", object);
			}
		} else {
			generalDAO.update(object);
		}
	}
	
	public Filter getFilterFromExample(Object example) {
		Object specificDAO = getSpecificDAO(example.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).getFilterFromExample(example);
			} else {
				return (Filter) callMethod(specificDAO, "getFilterFromExample", example);
			}
		} else {
			return generalDAO.getFilterFromExample(example);
		}
	}
	
	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		Object specificDAO = getSpecificDAO(example.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).getFilterFromExample(example, options);
			} else {
				return (Filter) callMethod(specificDAO, "getFilterFromExample", example, options);
			}
		} else {
			return generalDAO.getFilterFromExample(example, options);
		}
	}
}
