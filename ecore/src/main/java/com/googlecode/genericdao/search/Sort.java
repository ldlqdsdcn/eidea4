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
 * Used to specify field ordering in <code>Search</code>.
 * 
 * @see Search
 */
public class Sort implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String property;
	protected boolean desc = false;
	protected boolean ignoreCase = false;
	protected boolean customExpression = false;

	public Sort() {

	}

	public Sort(String property, boolean desc, boolean ignoreCase) {
		this.property = property;
		this.desc = desc;
		this.ignoreCase = ignoreCase;
	}

	public Sort(String property, boolean desc) {
		this.property = property;
		this.desc = desc;
	}

	public Sort(String property) {
		this.property = property;
	}

	/**
	 * If isCustomExpression is true, the "property" of this Sort is reckoned
	 * as a free-form JPQL/HQL order-by expression. Reference properties by
	 * wrapping them with curly braces ({}).
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * new Sort(true, "cast({employeeno} as integer)");
	 * new Sort(true, "abs({prop1} - {prop2})");
	 * </pre> 
	 */
	public Sort(boolean isCustomExpression, String property, boolean desc) {
		this.customExpression = isCustomExpression;
		this.property = property;
		this.desc = desc;
	}

	/**
	 * If isCustomExpression is true, the "property" of this Sort is reckoned
	 * as a free-form JPQL/HQL order-by expression. Reference properties by
	 * wrapping them with curly braces ({}).
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * new Sort(true, "cast({employeeno} as integer)", true);
	 * new Sort(true, "abs({prop1} - {prop2})", true);
	 * </pre> 
	 */
	public Sort(boolean isCustomExpression, String property) {
		this.customExpression = isCustomExpression;
		this.property = property;
	}

	public static Sort asc(String property) {
		return new Sort(property);
	}

	public static Sort asc(String property, boolean ignoreCase) {
		return new Sort(property, ignoreCase);
	}

	public static Sort desc(String property) {
		return new Sort(property, true);
	}

	public static Sort desc(String property, boolean ignoreCase) {
		return new Sort(property, true, ignoreCase);
	}
	
	/**
	 * Instead of a property for this Sort, use a free-form JPQL/HQL order-by
	 * expression. Reference properties by wrapping them with curly braces ({}).
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * Sort.customExpressionAsc("cast({employeeno} as integer)");
	 * Sort.customExpressionAsc("abs({prop1} - {prop2})");
	 * </pre> 
	 */
	public static Sort customExpressionAsc(String expression) {
		return new Sort(true, expression);
	}

	/**
	 * Instead of a property for this Sort, use a free-form JPQL/HQL order-by
	 * expression. Reference properties by wrapping them with curly braces ({}).
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * Sort.customExpressionDesc("cast({employeeno} as integer)");
	 * Sort.customExpressionDesc("abs({prop1} - {prop2})");
	 * </pre> 
	 */
	public static Sort customExpressionDesc(String expression) {
		return new Sort(true, expression, true);
	}

	/**
	 * Property on which to sort
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * Property on which to sort
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * If true, sort descending by the given property; otherwise, sort
	 * ascending.
	 */
	public boolean isDesc() {
		return desc;
	}

	/**
	 * If true, sort descending by the given property; otherwise, sort
	 * ascending.
	 */
	public void setDesc(boolean desc) {
		this.desc = desc;
	}

	/**
	 * If true the ordering will be case insensitive for this property. Ignore
	 * case has no effect when customExpression is specified. 
	 */
	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * If true the ordering will be case insensitive for this property. Ignore
	 * case has no effect when customExpression is specified. 
	 */
	public void setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
	}
	
	/**
	 * <p>If true, the "property" of this Sort is reckoned as a free-form JPQL/HQL
	 * order-by expression. Reference properties by wrapping them with curly
	 * braces ({}).
	 * 
	 * <p>When set to <code>true</code>, the <code>ignoreCase</code> property
	 * is ignored.
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * new Sort(true, "cast({employeeno} as integer)");
	 * new Sort(true, "abs({prop1} - {prop2})", true);
	 * Sort.ascCustom("cast({employeeno} as integer)");
	 * Sort.descCustom("abs({prop1} - {prop2})");
	 * </pre> 
	 */
	public boolean isCustomExpression() {
		return customExpression;
	}

	/**
	 * <p>If true, the "property" of this Sort is reckoned as a free-form JPQL/HQL
	 * order-by expression. Reference properties by wrapping them with curly
	 * braces ({}).
	 * 
	 * <p>When set to <code>true</code>, the <code>ignoreCase</code> property
	 * is ignored.
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * new Sort(true, "cast({employeeno} as integer)");
	 * new Sort(true, "abs({prop1} - {prop2})", true);
	 * Sort.ascCustom("cast({employeeno} as integer)");
	 * Sort.descCustom("abs({prop1} - {prop2})");
	 * </pre> 
	 */
	public void setCustomExpression(boolean customExpression) {
		this.customExpression = customExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (customExpression ? 1231 : 1237);
		result = prime * result + (desc ? 1231 : 1237);
		result = prime * result + (ignoreCase ? 1231 : 1237);
		result = prime * result
				+ ((property == null) ? 0 : property.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sort other = (Sort) obj;
		if (customExpression != other.customExpression)
			return false;
		if (desc != other.desc)
			return false;
		if (ignoreCase != other.ignoreCase)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if (customExpression) {
			sb.append("CUSTOM: ");
		}
		
		if (property == null) {
			sb.append("null");
		} else {
			sb.append("`");
			sb.append(property);
			sb.append("`");
		}
		sb.append(desc ? " desc" : " asc");
		if (ignoreCase && !customExpression) {
			sb.append(" (ignore case)");
		}
		return sb.toString();
	}
}
