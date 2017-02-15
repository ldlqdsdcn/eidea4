package com.googlecode.genericdao.search.jpa;

import com.googlecode.genericdao.search.Metadata;

import java.io.Serializable;

public class JPAAnnotationCollectionMetadata implements Metadata {

	Metadata classMetadata;
	Class<?> collectionClass;

	public JPAAnnotationCollectionMetadata(Class<?> klass, Class<?> collectionClass) {
		classMetadata = JPAAnnotationMetadata.getMetadata(klass);
		this.collectionClass = collectionClass;
	}

	public Class<?> getCollectionClass() {
		return collectionClass;
	}

	public String getIdProperty() {
		return classMetadata.getIdProperty();
	}

	public Metadata getIdType() {
		return classMetadata.getIdType();
	}

	public Serializable getIdValue(Object object) {
		return classMetadata.getIdValue(object);
	}

	public Class<?> getJavaClass() {
		return classMetadata.getJavaClass();
	}
	
	public String getEntityName() {
		return classMetadata.getEntityName();
	}

	public String[] getProperties() {
		return classMetadata.getProperties();
	}

	public Metadata getPropertyType(String property) {
		return classMetadata.getPropertyType(property);
	}

	public Object getPropertyValue(Object object, String property) {
		return classMetadata.getPropertyValue(object, property);
	}

	public boolean isCollection() {
		return true;
	}

	public boolean isEmbeddable() {
		return classMetadata.isEmbeddable();
	}

	public boolean isEntity() {
		return classMetadata.isEntity();
	}

	public boolean isNumeric() {
		return classMetadata.isNumeric();
	}

	public boolean isString() {
		return classMetadata.isString();
	}
}
