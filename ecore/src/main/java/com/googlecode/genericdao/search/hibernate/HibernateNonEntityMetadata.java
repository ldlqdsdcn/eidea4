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

import com.googlecode.genericdao.search.Metadata;
import org.hibernate.EntityMode;
import org.hibernate.SessionFactory;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.CollectionType;
import org.hibernate.type.ComponentType;
import org.hibernate.type.EntityType;
import org.hibernate.type.Type;

import java.io.Serializable;

/**
 * Implementation of Metadata for a non-entity type in Hibernate.
 * 
 * @author dwolverton
 */
public class HibernateNonEntityMetadata implements Metadata {

	private SessionFactory sessionFactory;
	private Type type;
	private Class<?> collectionType;
	
	public HibernateNonEntityMetadata(SessionFactory sessionFactory, Type type, Class<?> collectionType) {
		this.sessionFactory = sessionFactory;
		this.type = type;
		this.collectionType = collectionType;
	}
	
	public String getIdProperty() {
		return null;
	}

	public Metadata getIdType() {
		return null;
	}

	public Serializable getIdValue(Object object) {
		return null;
	}

	public Class<?> getJavaClass() {
		return type.getReturnedClass();
	}
	
	public String getEntityName() {
		throw new UnsupportedOperationException("Cannot get Entity Name of non-entity type.");
	}

	public String[] getProperties() {
		if (type.isComponentType())
			return ((ComponentType)type).getPropertyNames();
		else
			return null;
	}

	public Metadata getPropertyType(String property) {
		if (!type.isComponentType())
			return null;
		
		int i = getPropertyIndex(property);
		if (i == -1) {
			return null;
		} else {
			Type pType = ((ComponentType)type).getSubtypes()[i];
			Class<?> pCollectionType = null;
			if (pType.isCollectionType()) {
				pType = ((CollectionType)pType).getElementType((SessionFactoryImplementor) sessionFactory);
				pCollectionType = pType.getReturnedClass();
			}
			if (pType.isEntityType()) {
				return new HibernateEntityMetadata(sessionFactory, sessionFactory.getClassMetadata(((EntityType)pType).getName()), pCollectionType);
			} else {
				return new HibernateNonEntityMetadata(sessionFactory, pType, pCollectionType);
			}
		}
	}

	public Object getPropertyValue(Object object, String property) {
		if (!type.isComponentType())
			return null;
		int i = getPropertyIndex(property);
		if (i == -1) {
			return null;
		} else {
			return ((ComponentType)type).getPropertyValue(object, i, EntityMode.POJO);
		}		
	}

	public boolean isCollection() {
		return collectionType != null;
	}
	
	public Class<?> getCollectionClass() {
		return collectionType;
	}

	public boolean isEmbeddable() {
		return type.isComponentType();
	}

	public boolean isEntity() {
		return false;
	}

	public boolean isNumeric() {
		return Number.class.isAssignableFrom(getJavaClass());
	}

	public boolean isString() {
		int[] types = type.sqlTypes((Mapping) sessionFactory);
		return types.length == 1 && (types[0] == java.sql.Types.VARCHAR || types[0] == java.sql.Types.CHAR);
	}
	
	
	private int getPropertyIndex(String property) {
		String[] properties = getProperties();
		for (int i = 0; i < properties.length; i++) {
			if (properties[i].equals(property)) {
				return i;
			}
		}
		return -1;
	}
}
