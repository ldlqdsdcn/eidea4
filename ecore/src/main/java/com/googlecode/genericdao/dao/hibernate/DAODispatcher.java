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

import com.googlecode.genericdao.dao.BaseDAODispatcher;
import com.googlecode.genericdao.dao.DAODispatcherException;
import com.googlecode.genericdao.search.ExampleOptions;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.SearchResult;

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

	public <T> T find(Class<T> type, Serializable id) {
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return (T) ((GenericDAO) specificDAO).find(id);
			} else {
				return (T) callMethod(specificDAO, "find", id);
			}
		} else {
			return (T) generalDAO.find(type, id);
		}
	}

	public <T> T[] find(Class<T> type, Serializable... ids) {
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return (T[]) ((GenericDAO) specificDAO).find(ids);
			} else {
				return (T[]) callMethod(specificDAO, "find", (Object[]) ids);
			}
		} else {
			return (T[]) generalDAO.find(type, ids);
		}
	}

	public <T> List<T> findAll(Class<T> type) {
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).findAll();
			} else {
				return (List) callMethod(specificDAO, "findAll");
			}
		} else {
			return generalDAO.findAll(type);
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

	public <T> T getReference(Class<T> type, Serializable id) {
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return (T) ((GenericDAO) specificDAO).getReference(id);
			} else {
				return (T) callMethod(specificDAO, "getReference", id);
			}
		} else {
			return (T) generalDAO.getReference(type, id);
		}
	}

	public <T> T[] getReferences(Class<T> type, Serializable... ids) {
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return (T[]) ((GenericDAO) specificDAO).getReferences(ids);
			} else {
				return (T[]) callMethod(specificDAO, "getReferences", (Object[]) ids);
			}
		} else {
			return generalDAO.getReferences(type, ids);
		}
	}

	public boolean isAttached(Object entity) {
		Object specificDAO = getSpecificDAO(entity.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).isAttached(entity);
			} else {
				return (Boolean) callMethod(specificDAO, "isAttached", entity);
			}
		} else {
			return generalDAO.isAttached(entity);
		}
	}

	public void refresh(Object... entities) {
		Class<?> type = getUniformArrayType(entities);
		if (type == null) return;
		if (type.equals(Object.class)) {
			//There are several different types of entities
			for (Object entity : entities) {
				refresh(entity);
			}
			return;
		}		
		
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				((GenericDAO) specificDAO).refresh(entities);
			} else {
				callMethod(specificDAO, "refresh", entities);
			}
		} else {
			generalDAO.refresh(entities);
		}
	}

	public boolean remove(Object entity) {
		Object specificDAO = getSpecificDAO(entity.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).remove(entity);
			} else {
				return (Boolean) callMethod(specificDAO, "remove", entity);
			}
		} else {
			return generalDAO.remove(entity);
		}
	}

	public void remove(Object... entities) {
		Class<?> type = getUniformArrayType(entities);
		if (type == null) return;
		if (type.equals(Object.class)) {
			//There are several different types of entities
			for (Object entity : entities) {
				remove(entity);
			}
			return;
		}
		
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				((GenericDAO) specificDAO).remove(entities);
			} else {
				callMethod(specificDAO, "remove", entities);
			}
		} else {
			generalDAO.remove(entities);
		}
	}

	public boolean removeById(Class<?> type, Serializable id) {
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).removeById(id);
			} else {
				return (Boolean) callMethod(specificDAO, "removeById", id);
			}
		} else {
			return generalDAO.removeById(type, id);
		}
	}

	public void removeByIds(Class<?> type, Serializable... ids) {
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				((GenericDAO) specificDAO).removeByIds(ids);
			} else {
				callMethod(specificDAO, "removeByIds", (Object[]) ids);
			}
		} else {
			generalDAO.removeByIds(type, ids);
		}
	}

	public boolean save(Object entity) {
		Object specificDAO = getSpecificDAO(entity.getClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).save(entity);
			} else {
				return (Boolean) callMethod(specificDAO, "save", entity);
			}
		} else {
			return generalDAO.save(entity);
		}
	}

	public boolean[] save(Object... entities) {
		if (entities == null)
			return null;
		Class<?> type = getUniformArrayType(entities);
		if (type == null)
			return new boolean[entities.length];
		if (type.equals(Object.class)) {
			//There are several different types of entities
			boolean[] isNew = new boolean[entities.length];
			for (int i = 0; i < entities.length; i++) {
				isNew[i] = save(entities[i]);
			}
			return isNew;
		}
		
		Object specificDAO = getSpecificDAO(type.getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).save(entities);
			} else {
				return (boolean[]) callMethod(specificDAO, "save", entities);
			}
		} else {
			return generalDAO.save(entities);
		}
	}

	public List search(ISearch search) {
		Object specificDAO = getSpecificDAO(search.getSearchClass().getName());
		if (specificDAO != null) {
			if (specificDAO instanceof GenericDAO) {
				return ((GenericDAO) specificDAO).search(search);
			} else {
				return (List) callMethod(specificDAO, "search", search);
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
				return (SearchResult) callMethod(specificDAO, "searchAndCount", search);
			}
		} else {
			return generalDAO.searchAndCount(search);
		}
	}

	public Object searchUnique(ISearch search) {
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
