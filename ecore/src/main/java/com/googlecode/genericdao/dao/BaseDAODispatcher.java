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
package com.googlecode.genericdao.dao;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * This class has some helpful properties and methods for use in making
 * DAODispatchers for various DAO implementations.
 * 
 * @author dwolverton
 */
public class BaseDAODispatcher {

	protected Map<String, Object> specificDAOs;

	/**
	 * In practice some DAOs could be put into this map using Spring. If a DAO
	 * is in this map, it will be used instead of the general DAO. This provides
	 * a way to override the default implementation for objects with special
	 * considerations.
	 */
	public void setSpecificDAOs(Map<String, Object> specificDAOs) {
		this.specificDAOs = specificDAOs;
	}

	protected Object getSpecificDAO(String className) {
		return specificDAOs == null ? null : specificDAOs.get(className);
	}

	protected Object callMethod(Object specificDAO, String methodName, Object... args) {
		try {
			return DAOUtil.callMethod(specificDAO, methodName, args);
		} catch (IllegalArgumentException e) {
			throw new DAODispatcherException(e);
		} catch (NoSuchMethodException e) {
			throw new DAODispatcherException(e);
		} catch (IllegalAccessException e) {
			throw new DAODispatcherException(e);
		} catch (InvocationTargetException e) {
			throw new DAODispatcherException(e);
		}
	}

	protected Object callMethod(Object specificDAO, String methodName, Class<?>[] paramTypes, Object... args) {
		try {
			return DAOUtil.callMethod(specificDAO, methodName, paramTypes, args);
		} catch (IllegalArgumentException e) {
			throw new DAODispatcherException(e);
		} catch (NoSuchMethodException e) {
			throw new DAODispatcherException(e);
		} catch (IllegalAccessException e) {
			throw new DAODispatcherException(e);
		} catch (InvocationTargetException e) {
			throw new DAODispatcherException(e);
		}
	}

	/**
	 * Get the type of an array's elements. If the type of the array is more
	 * specific than "Object", the array type will be returned. Otherwise the
	 * most general of the types of the elements will be returned.
	 */
	protected static Class<?> getTypeFromArray(Object[] array) {
		if (array == null)
			return null;

		if (!array.getClass().getComponentType().equals(Object.class)) {
			// if the type of the array is more specific than Object, use that
			return array.getClass().getComponentType();
		} else {
			// otherwise, select the most general element class
			Class<?> klass = null;
			for (Object o : array) {
				if (o != null) {
					if (klass == null || o.getClass().isAssignableFrom(klass)) {
						klass = o.getClass();
					}
				}
			}
			return klass;
		}
	}

	/**
	 * <ul>
	 * <li>If array is null, empty or has no non-null elements, return null
	 * <li>If array contains all elements of the same type, return that type
	 * <li>If array contains several different element types, return
	 * Object.class
	 * </ul>
	 */
	protected static Class<?> getUniformArrayType(Object[] array) {
		if (array == null)
			return null;

		Class<?> klass = null;
		for (Object o : array) {
			if (o != null) {
				if (klass == null) {
					klass = o.getClass();
				} else {
					if (!klass.equals(o.getClass()))
						return Object.class;
				}
			}
		}

		return klass;
	}
}
