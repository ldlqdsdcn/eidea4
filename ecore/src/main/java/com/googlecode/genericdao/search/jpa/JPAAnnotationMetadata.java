package com.googlecode.genericdao.search.jpa;

import com.googlecode.genericdao.search.Metadata;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;

public class JPAAnnotationMetadata implements Metadata {

	Class<?> klass;

	private JPAAnnotationMetadata(Class<?> klass) {
		this.klass = klass;
	}

	static Map<Class<?>, JPAAnnotationMetadata> metadataCache = new HashMap<Class<?>, JPAAnnotationMetadata>();

	public static <T> Metadata getMetadata(Type type) {
		Class<?> klass = null;
		Type[] typeArguments = null;
		if (type instanceof ParameterizedType) {
			typeArguments = ((ParameterizedType) type).getActualTypeArguments();
			type = ((ParameterizedType) type).getRawType();
		}
		if (type instanceof Class) {
			klass = (Class<?>) type;
		} else {
			throw new IllegalArgumentException("weirdness");
		}
		if (Collection.class.isAssignableFrom(klass)) {
			if (typeArguments == null || typeArguments.length != 1) {
				throw new IllegalArgumentException("weirdness again.");
			}
			if (!(typeArguments[0] instanceof Class)) {
				throw new IllegalArgumentException("weirdness a third time.");
			}
			return new JPAAnnotationCollectionMetadata((Class<?>) typeArguments[0], klass);
		} else if (klass.isArray()) {
			return new JPAAnnotationCollectionMetadata(klass.getComponentType(), klass);
		}

		JPAAnnotationMetadata md = metadataCache.get(klass);
		if (md == null) {
			md = new JPAAnnotationMetadata(klass);
			metadataCache.put(klass, md);
		}
		return md;
	}

	public Class<?> getCollectionClass() {
		return null;
	}
	
	public String getEntityName() {
		Entity annotation = klass.getAnnotation(Entity.class);
		if (annotation == null) {
			throw new UnsupportedOperationException("Cannot get Entity Name of non-entity type.");
		} else {
			if (annotation.name() != null && !annotation.name().isEmpty()) {
				return annotation.name();
			} else {
				return klass.getSimpleName();
			}
		}
	}

	public String getIdProperty() {
		for (Property prop : getProps().values()) {
			if (prop.hasAnnotation(Id.class) || prop.hasAnnotation(EmbeddedId.class)) {
				return prop.name;
			}
		}
		return null;
	}

	public Metadata getIdType() {
		String idProp = getIdProperty();
		if (idProp != null) {
			return getPropertyType(idProp);
		}
		return null;
	}

	public Serializable getIdValue(Object object) {
		String idProp = getIdProperty();
		if (idProp != null) {
			return (Serializable) getPropertyValue(object, idProp);
		}
		return null;
	}

	public Class<?> getJavaClass() {
		return klass;
	}

	public String[] getProperties() {
		String[] array = new String[getProps().size()];
		int i = 0;
		for (String prop : getProps().keySet()) {
			array[i++] = prop;
		}
		return array;
	}

	public Metadata getPropertyType(String property) {
		Property prop = getProps().get(property);
		if (prop == null)
			return null;
		return getMetadata(prop.getType());
	}

	public Object getPropertyValue(Object object, String property) {
		Property prop = getProps().get(property);
		if (prop == null)
			return null;
		return prop.getValue(object);
	}

	public boolean isCollection() {
		return false;
	}

	public boolean isEmbeddable() {
		return null != klass.getAnnotation(Embeddable.class);
	}

	public boolean isEntity() {
		return null != klass.getAnnotation(Entity.class);
	}

	public boolean isNumeric() {
		return Number.class.isAssignableFrom(klass);
	}

	public boolean isString() {
		return String.class.equals(klass);
	}

	Map<String, Property> props;

	public synchronized Map<String, Property> getProps() {
		if (props != null)
			return props;

		props = new TreeMap<String, Property>();
		
		if (!isEntity() && !isEmbeddable())
			return props; //Will have no persistable properties.

		for (Field field : klass.getFields()) {
			if (null != field.getDeclaringClass().getAnnotation(Entity.class)
					|| null != field.getDeclaringClass().getAnnotation(Embeddable.class)) {
				props.put(field.getName(), new Property(field));
			}
		}

		for (Method method : klass.getMethods()) {
			String[] name = getterName(method);
			if (name != null) {
				if (null != method.getDeclaringClass().getAnnotation(Entity.class)
						|| null != method.getDeclaringClass().getAnnotation(Embeddable.class)) {
					Property property = props.get(name[0]);
					if (property == null) {
						property = new Property(name[0]);
						props.put(name[0], property);
					}
					property.getter = method;

					// if there is a private field with the same name and type,
					// add that too.
					// it might have annotations
					if (property.field == null) {
						try {
							property.field = klass.getDeclaredField(name[0]);
							if (!property.field.getGenericType().equals(property.getter.getGenericReturnType()))
								property.field = null;
						} catch (SecurityException e) {
						} catch (NoSuchFieldException e) {
							// if not found just don't add
						}
					}

					// if there is a setter, add that too
					try {
						property.setter = klass.getMethod("set" + name[1], method.getReturnType());
					} catch (SecurityException e) {
					} catch (NoSuchMethodException e) {
						// if not found just don't add
					}
				}
			}
		}

		// filter out
		for (Iterator<Map.Entry<String, Property>> iterator = props.entrySet().iterator(); iterator.hasNext();) {
			Property property = iterator.next().getValue();
			if (property.hasAnnotation(Transient.class))
				iterator.remove();
		}
		
		return props;
	}

	public static String[] getterName(Method method) {
		if (method.getParameterTypes().length != 0 || method.getReturnType() == null)
			return null;

		String name = method.getName();

		if (name.length() > 3 && name.startsWith("get")) {
			name = name.substring(3);
		} else if (name.length() > 2
				&& (method.getReturnType().equals(Boolean.class) || method.getReturnType().equals(boolean.class))
				&& name.startsWith("is")) {
			name = name.substring(2);
		} else {
			return null;
		}

		if (Character.isUpperCase(name.charAt(0))) {
			return new String[] { name.substring(0, 1).toLowerCase() + name.substring(1), name };
		} else {
			return null;
		}
	}

	private static class Property {
		public Property(String name) {
			this.name = name;
		}

		public Property(Field field) {
			name = field.getName();
			this.field = field;
		};

		public Type getType() {
			if (getter != null) {
				return getter.getGenericReturnType();
			} else if (field != null) {
				return field.getGenericType();
			} else if (setter != null) {
				return setter.getGenericParameterTypes()[0];
			} else {
				return null;
			}
		}

		public Object getValue(Object o) {
			try {
				if (getter != null) {
					return getter.invoke(o);
				} else if (field != null) {
					return field.get(o);
				}
			} catch (IllegalArgumentException e) {
				throw new RuntimeException("Unexpected error getting value of property");
			} catch (IllegalAccessException e) {
				throw new RuntimeException("Unexpected error getting value of property");
			} catch (InvocationTargetException e) {
				throw new RuntimeException("Unexpected error getting value of property");
			}
			return null;
		}
		
		public <T extends Annotation> boolean hasAnnotation(Class<T> annotationClass) {
			return getAnnotation(annotationClass) != null;
		}
		
		public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
			T val = null;
			if (getter != null)
				val = getter.getAnnotation(annotationClass);
			if (val == null && field != null)
				val = field.getAnnotation(annotationClass);
			if (val == null && setter != null)
				val = setter.getAnnotation(annotationClass);
			
			return val;
		}

		String name;
		Field field;
		Method getter;
		Method setter;
	}
}
