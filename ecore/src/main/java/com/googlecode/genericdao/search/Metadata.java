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
package com.googlecode.genericdao.search;

import java.io.Serializable;

/**
 * This interface provides meta data for a single persistable type. Use
 * {@link MetadataUtil#get(Class)} or {@link MetadataUtil#get(Class, String)} to
 * get meta data instances.
 * 
 * This interface provides a layer of abstraction between the framework and the
 * underlying JPA provider (ex. Hibernate). By switching out the implementation
 * of this interface, the framework should be able to be used with different JPA
 * providers.
 * 
 * @author dwolverton
 */
public interface Metadata {
	/**
	 * Return true if the type is an entity.
	 */
	public boolean isEntity();

	/**
	 * Return true if the type is an embeddable class (a component class in
	 * Hibernate).
	 */
	public boolean isEmbeddable();

	/**
	 * Return true if the type is a collection.
	 */
	public boolean isCollection();

	/**
	 * Return true if the type is persisted as a string (char or varchar) type
	 * in the database.
	 */
	public boolean isString();

	/**
	 * Return true if the type is a number. For example: int, Float, Number,
	 * double, etc.
	 */
	public boolean isNumeric();

	/**
	 * Return the Java class of this type. If the type is a collection, return
	 * the type of the collection elements.
	 */
	public Class<?> getJavaClass();

	/**
	 * If the type is an entity return the entity name. Otherwise throw an
	 * UnsupportedOperationException.
	 */
	public String getEntityName();

	/**
	 * Return an array of the names of all the properties that this type has, if
	 * any. Return null if this a simple value type with no properties.
	 */
	public String[] getProperties();

	/**
	 * Return the value of the given property of the given object of this type.
	 * Return null if this a simple value type with no properties.
	 */
	public Object getPropertyValue(Object object, String property);

	/**
	 * Return the metadata for the given property of this type. Return null if
	 * this a simple value type with no properties.
	 */
	public Metadata getPropertyType(String property);

	/**
	 * Return the name of the id property of this type. Return null if this is
	 * not an entity type.
	 */
	public String getIdProperty();

	/**
	 * Return the metadata for the id property of this type. Return null if this
	 * is not an entity type.
	 */
	public Metadata getIdType();

	/**
	 * Return the value of the id property of the given object of this type.
	 * Return null if this is not an entity type.
	 */
	public Serializable getIdValue(Object object);

	/**
	 * If the type is a collection, return the Java class of the collection
	 * itself, not the Java class of it's elements as with
	 * {@link #getJavaClass()}. For example: ArrayList&lt;Project&gt;,
	 * Set&lt;Person&gt;, String[].
	 */
	public Class<?> getCollectionClass();
}
