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
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * A <code>Filter</code> is used by the <code>Search</code> class to specify a
 * restriction on what results should be returned in the search. For example, if
 * a filter <code>Filter.equal("name","Paul")</code> were added to the search,
 * only objects with the property "name" equal to the string "Paul" would be
 * returned.
 * <p>
 * Nested properties can also be specified, for example
 * <code>Filter.greaterThan("employee.age",65)</code>.
 * 
 * @author dwolverton
 */
public class Filter implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * Property string representing the root entity of the search. This is just the empty string ("").
	 */
	public static final String ROOT_ENTITY = "";
	
	/**
	 * The name of the property to filter on. It may be nested. Examples:
	 * <code>"name", "dateOfBirth", "employee.age", "employee.spouse.job.title"</code>
	 */
	protected String property;

	/**
	 * The value to compare the property with. Should be of a compatible type
	 * with the property. Examples: <code>"Fred", new Date(), 45</code>
	 */
	protected Object value;

	/**
	 * The type of comparison to do between the property and the value. The
	 * options are limited to the integer constants on this class:
	 * 
	 * <code>OP_EQAUL, OP_NOT_EQUAL, OP_LESS_THAN, OP_GREATER_THAN, LESS_OR_EQUAL, OP_GREATER_OR_EQUAL, OP_IN, OP_NOT_IN, OP_LIKE, OP_ILIKE, OP_NULL, OP_NOT_NULL, OP_EMPTY, OP_NOT_EMPTY, OP_SOME, OP_ALL, OP_NONE, OP_AND, OP_OR, OP_NOT</code>
	 * .
	 */
	protected int operator;

	public Filter() {

	}

	public Filter(String property, Object value, int operator) {
		this.property = property;
		this.value = value;
		this.operator = operator;
	}

	public Filter(String property, Object value) {
		this.property = property;
		this.value = value;
		this.operator = OP_EQUAL;
	}

	public static final int OP_EQUAL = 0;
	public static final int OP_NOT_EQUAL = 1;
	public static final int OP_LESS_THAN = 2;
	public static final int OP_GREATER_THAN = 3;
	public static final int OP_LESS_OR_EQUAL = 4;
	public static final int OP_GREATER_OR_EQUAL = 5;
	public static final int OP_LIKE = 6;
	public static final int OP_ILIKE = 7;
	public static final int OP_IN = 8;
	public static final int OP_NOT_IN = 9;
	public static final int OP_NULL = 10;
	public static final int OP_NOT_NULL = 11;
	public static final int OP_EMPTY = 12;
	public static final int OP_NOT_EMPTY = 13;
	public static final int OP_AND = 100;
	public static final int OP_OR = 101;
	public static final int OP_NOT = 102;
	public static final int OP_SOME = 200;
	public static final int OP_ALL = 201;
	public static final int OP_NONE = 202;
	public static final int OP_CUSTOM = 999;

	/**
	 * Create a new Filter using the == operator.
	 */
	public static Filter equal(String property, Object value) {
		return new Filter(property, value, OP_EQUAL);
	}

	/**
	 * Create a new Filter using the < operator.
	 */
	public static Filter lessThan(String property, Object value) {
		return new Filter(property, value, OP_LESS_THAN);
	}

	/**
	 * Create a new Filter using the > operator.
	 */
	public static Filter greaterThan(String property, Object value) {
		return new Filter(property, value, OP_GREATER_THAN);
	}

	/**
	 * Create a new Filter using the <= operator.
	 */
	public static Filter lessOrEqual(String property, Object value) {
		return new Filter(property, value, OP_LESS_OR_EQUAL);
	}

	/**
	 * Create a new Filter using the >= operator.
	 */
	public static Filter greaterOrEqual(String property, Object value) {
		return new Filter(property, value, OP_GREATER_OR_EQUAL);
	}

	/**
	 * Create a new Filter using the IN operator.
	 * 
	 * <p>
	 * This takes a variable number of parameters. Any number of values can be
	 * specified.
	 */
	public static Filter in(String property, Collection<?> value) {
		return new Filter(property, value, OP_IN);
	}

	/**
	 * Create a new Filter using the IN operator.
	 * 
	 * <p>
	 * This takes a variable number of parameters. Any number of values can be
	 * specified.
	 */
	public static Filter in(String property, Object... value) {
		return new Filter(property, value, OP_IN);
	}

	/**
	 * Create a new Filter using the NOT IN operator.
	 * 
	 * <p>
	 * This takes a variable number of parameters. Any number of values can be
	 * specified.
	 */
	public static Filter notIn(String property, Collection<?> value) {
		return new Filter(property, value, OP_NOT_IN);
	}

	/**
	 * Create a new Filter using the NOT IN operator.
	 * 
	 * <p>
	 * This takes a variable number of parameters. Any number of values can be
	 * specified.
	 */
	public static Filter notIn(String property, Object... value) {
		return new Filter(property, value, OP_NOT_IN);
	}

	/**
	 * Create a new Filter using the LIKE operator.
	 */
	public static Filter like(String property, String value) {
		return new Filter(property, value, OP_LIKE);
	}

	/**
	 * Create a new Filter using the ILIKE operator.
	 */
	public static Filter ilike(String property, String value) {
		return new Filter(property, value, OP_ILIKE);
	}

	/**
	 * Create a new Filter using the != operator.
	 */
	public static Filter notEqual(String property, Object value) {
		return new Filter(property, value, OP_NOT_EQUAL);
	}

	/**
	 * Create a new Filter using the IS NULL operator.
	 */
	public static Filter isNull(String property) {
		return new Filter(property, true, OP_NULL);
	}

	/**
	 * Create a new Filter using the IS NOT NULL operator.
	 */
	public static Filter isNotNull(String property) {
		return new Filter(property, true, OP_NOT_NULL);
	}

	/**
	 * Create a new Filter using the IS EMPTY operator.
	 */
	public static Filter isEmpty(String property) {
		return new Filter(property, true, OP_EMPTY);
	}

	/**
	 * Create a new Filter using the IS NOT EMPTY operator.
	 */
	public static Filter isNotEmpty(String property) {
		return new Filter(property, true, OP_NOT_EMPTY);
	}

	/**
	 * Create a new Filter using the AND operator.
	 * 
	 * <p>
	 * This takes a variable number of parameters. Any number of
	 * <code>Filter</code>s can be specified.
	 */
	public static Filter and(Filter... filters) {
		Filter filter = new Filter("AND", null, OP_AND);
		for (Filter f : filters) {
			filter.add(f);
		}
		return filter;
	}

	/**
	 * Create a new Filter using the OR operator.
	 * 
	 * <p>
	 * This takes a variable number of parameters. Any number of
	 * <code>Filter</code>s can be specified.
	 */
	public static Filter or(Filter... filters) {
		Filter filter = and(filters);
		filter.property = "OR";
		filter.operator = OP_OR;
		return filter;
	}

	/**
	 * Create a new Filter using the NOT operator.
	 */
	public static Filter not(Filter filter) {
		return new Filter("NOT", filter, OP_NOT);
	}

	/**
	 * Create a new Filter using the SOME operator.
	 */
	public static Filter some(String property, Filter filter) {
		return new Filter(property, filter, OP_SOME);
	}

	/**
	 * Create a new Filter using the ALL operator.
	 */
	public static Filter all(String property, Filter filter) {
		return new Filter(property, filter, OP_ALL);
	}

	/**
	 * Create a new Filter using the NONE operator. This is equivalent to NOT
	 * SOME.
	 */
	public static Filter none(String property, Filter filter) {
		return new Filter(property, filter, OP_NONE);
	}
	
	/**
	 * <p>Create a new Filter using a custom JPQL/HQL expression. This can be
	 * any valid where-clause type expression. Reference properties by wrapping
	 * them with curly braces ({}).
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * // Referencing a property in a custom expression
	 * Filter.custom("{serialNumber} like '%4780%'");
	 * // comparing two properties
	 * Filter.custom("{parent.spotCount} > {spotCount} + 4");
	 * // A constant
	 * Filter.custom("1 = 1");
	 * // A function
	 * Filter.custom("{dueDate} > current_date()");
	 * // A subquery
	 * Filter.custom("{id} in (select pc.cat_id from popular_cats pc where pc.color = 'blue')");
	 * </pre>
	 * 
	 * @param expression JPQL/HQL where-clause expression
	 */
	public static Filter custom(String expression) {
		return new Filter(expression, null, OP_CUSTOM);
	}
	
	/**
	 * <p>Create a new Filter using a custom JPQL/HQL expression. This can be
	 * any valid where-clause type expression. Reference properties by wrapping
	 * them with curly braces ({}). The expression can also contain place
	 * holders for the Filter values; these are indicated by JPQL-style
	 * positional parameters (i.e. a question mark (?) followed by a number
	 * indicating the parameter order, starting with one).
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * // Referencing a property in a custom expression
	 * Filter.custom("{serialNumber} like ?1", "%4780%");
	 * // comparing two properties
	 * Filter.custom("{parent.spotCount} + ?1 > {spotCount} + ?2", 0, 4);
	 * // A constant
	 * Filter.custom("?1 = ?2", 1, 1);
	 * // A function
	 * Filter.custom("?1 > current_date()", someDate);
	 * // A subquery
	 * Filter.custom("{id} in (select pc.cat_id from popular_cats pc where pc.color = ?1)", "blue");
	 * </pre>
	 * 
	 * @param expression JPQL/HQL where-clause expression
	 * @param values one or more values to fill in the numbered placeholders in
	 * 		  the expression
	 */
	public static Filter custom(String expression, Object... values) {
		return new Filter(expression, values, OP_CUSTOM);
	}

	/**
	 * <p>Create a new Filter using a custom JPQL/HQL expression. This can be
	 * any valid where-clause type expression. Reference properties by wrapping
	 * them with curly braces ({}). The expression can also contain place
	 * holders for the Filter values; these are indicated by JPQL-style
	 * positional parameters (i.e. a question mark (?) followed by a number
	 * indicating the parameter order, starting with one).
	 * 
	 * <p>Here are some examples:
	 * <pre>
	 * // Referencing a property in a custom expression
	 * Filter.custom("{serialNumber} like ?1", Collections.singleton("%4780%"));
	 * // comparing two properties
	 * Filter.custom("{parent.spotCount} + ?1 > {spotCount} + ?2", Arrays.asList(0, 4));
	 * // A constant
	 * Filter.custom("?1 = ?2", Arrays.asList(1, 1));
	 * // A function
	 * Filter.custom("?1 > current_date()", Collections.singleton(someDate));
	 * // A subquery
	 * Filter.custom("{id} in (select pc.cat_id from popular_cats pc where pc.color = ?1)", Collections.singleton("blue"));
	 * </pre>
	 * 
	 * @param expression JPQL/HQL where-clause expression
	 * @param values one or more values to fill in the numbered placeholders in
	 * 		  the expression
	 */
	public static Filter custom(String expression, Collection<?> values) {
		return new Filter(expression, values, OP_CUSTOM);
	}

	/**
	 * Used with OP_OR and OP_AND filters. These filters take a collection of
	 * filters as their value. This method adds a filter to that list.
	 */
	@SuppressWarnings("unchecked")
	public void add(Filter filter) {
		if (value == null || !(value instanceof List)) {
			value = new ArrayList();
		}
		((List) value).add(filter);
	}

	/**
	 * Used with OP_OR and OP_AND filters. These filters take a collection of
	 * filters as their value. This method removes a filter from that list.
	 */
	@SuppressWarnings("unchecked")
	public void remove(Filter filter) {
		if (value == null || !(value instanceof List)) {
			return;
		}
		((List) value).remove(filter);
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public int getOperator() {
		return operator;
	}

	public void setOperator(int operator) {
		this.operator = operator;
	}
	
	/**
	 * Returns the value as a List, converting if necessary. If the value is a
	 * List, it will be returned directly. If it is any other Collection type or
	 * if it is an Array, an ArrayList will be returned with all the same
	 * elements. If the value is any other non-null value, a List containing
	 * just that one value will be returned. If it is <code>null</code>,
	 * <code>null</code> will be returned.
	 */
	public List<?> getValuesAsList() {
		if (value == null) {
			return null;
		} else if (value instanceof List<?>) {
			return (List<?>) value;
		} else if (value instanceof Collection<?>) {
			return new ArrayList<Object>((Collection<?>) value);
		} else if (value.getClass().isArray()) {
			ArrayList<Object> list = new ArrayList<Object>(Array.getLength(value));
			for (int i = 0; i < Array.getLength(value); i++) {
				list.add(Array.get(value, i));
			}
			return list;
		} else {
			return Collections.singletonList(value);
		}
	}
	
	/**
	 * Returns the value as a Collection, converting if necessary. If the value
	 * is a Collection, it will be returned directly. If it is an Array, an
	 * ArrayList will be returned with all the same elements. If the value is
	 * any other non-null value, a Set containing just that one value will be
	 * returned. If it is <code>null</code>, <code>null</code> will be returned.
	 * 
	 * @return
	 */
	public Collection<?> getValuesAsCollection() {
		if (value == null) {
			return null;
		} else if (value instanceof Collection<?>) {
			return (Collection<?>) value;
		} else if (value.getClass().isArray()) {
			ArrayList<Object> list = new ArrayList<Object>(Array.getLength(value));
			for (int i = 0; i < Array.getLength(value); i++) {
				list.add(Array.get(value, i));
			}
			return list;
		} else {
			return Collections.singleton(value);
		}
	}

	/**
	 * @return true if the operator should have a single value specified.
	 * 
	 *         <p>
	 *         <code>EQUAL, NOT_EQUAL, LESS_THAN, LESS_OR_EQUAL, GREATER_THAN, GREATER_OR_EQUAL, LIKE, ILIKE</code>
	 */
	public boolean isTakesSingleValue() {
		return operator <= 7;
	}

	/**
	 * @return true if the operator should have a list of values specified.
	 * 
	 *         <p>
	 *         <code>IN, NOT_IN</code>
	 */
	public boolean isTakesListOfValues() {
		return operator == OP_IN || operator == OP_NOT_IN;
	}

	/**
	 * @return true if the operator does not require a value to be specified.
	 * 
	 *         <p>
	 *         <code>NULL, NOT_NULL, EMPTY, NOT_EMPTY</code>
	 */
	public boolean isTakesNoValue() {
		return (operator >= 10 && operator <= 13) || operator == OP_CUSTOM;
	}

	/**
	 * @return true if the operator should have a single Filter specified for
	 *         the value.
	 * 
	 *         <p>
	 *         <code>NOT, ALL, SOME, NONE</code>
	 */
	public boolean isTakesSingleSubFilter() {
		return operator == OP_NOT || (operator >= 200 && operator < 300);
	}

	/**
	 * @return true if the operator should have a list of Filters specified for
	 *         the value.
	 * 
	 *         <p>
	 *         <code>AND, OR</code>
	 */
	public boolean isTakesListOfSubFilters() {
		return operator == OP_AND || operator == OP_OR;
	}

	/**
	 * @return true if the operator does not require a property to be specified.
	 * 
	 *         <p>
	 *         <code>AND, OR, NOT</code>
	 */
	public boolean isTakesNoProperty() {
		return operator >= 100 && operator <= 102;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + operator;
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		Filter other = (Filter) obj;
		if (operator != other.operator)
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String toString() {
		switch (operator) {
		case Filter.OP_IN:
			return "`" + property + "` in (" + InternalUtil.paramDisplayString(value) + ")";
		case Filter.OP_NOT_IN:
			return "`" + property + "` not in (" + InternalUtil.paramDisplayString(value) + ")";
		case Filter.OP_EQUAL:
			return "`" + property + "` = " + InternalUtil.paramDisplayString(value);
		case Filter.OP_NOT_EQUAL:
			return "`" + property + "` != " + InternalUtil.paramDisplayString(value);
		case Filter.OP_GREATER_THAN:
			return "`" + property + "` > " + InternalUtil.paramDisplayString(value);
		case Filter.OP_LESS_THAN:
			return "`" + property + "` < " + InternalUtil.paramDisplayString(value);
		case Filter.OP_GREATER_OR_EQUAL:
			return "`" + property + "` >= " + InternalUtil.paramDisplayString(value);
		case Filter.OP_LESS_OR_EQUAL:
			return "`" + property + "` <= " + InternalUtil.paramDisplayString(value);
		case Filter.OP_LIKE:
			return "`" + property + "` LIKE " + InternalUtil.paramDisplayString(value);
		case Filter.OP_ILIKE:
			return "`" + property + "` ILIKE " + InternalUtil.paramDisplayString(value);
		case Filter.OP_NULL:
			return "`" + property + "` IS NULL";
		case Filter.OP_NOT_NULL:
			return "`" + property + "` IS NOT NULL";
		case Filter.OP_EMPTY:
			return "`" + property + "` IS EMPTY";
		case Filter.OP_NOT_EMPTY:
			return "`" + property + "` IS NOT EMPTY";
		case Filter.OP_AND:
		case Filter.OP_OR:
			if (!(value instanceof List)) {
				return (operator == Filter.OP_AND ? "AND: " : "OR: ") + "**INVALID VALUE - NOT A LIST: (" + value
						+ ") **";
			}

			String op = operator == Filter.OP_AND ? " and " : " or ";

			StringBuilder sb = new StringBuilder("(");
			boolean first = true;
			for (Object o : ((List) value)) {
				if (first) {
					first = false;
				} else {
					sb.append(op);
				}
				if (o instanceof Filter) {
					sb.append(o.toString());
				} else {
					sb.append("**INVALID VALUE - NOT A FILTER: (" + o + ") **");
				}
			}
			if (first)
				return (operator == Filter.OP_AND ? "AND: " : "OR: ") + "**EMPTY LIST**";

			sb.append(")");
			return sb.toString();
		case Filter.OP_NOT:
			if (!(value instanceof Filter)) {
				return "NOT: **INVALID VALUE - NOT A FILTER: (" + value + ") **";
			}
			return "not " + value.toString();
		case Filter.OP_SOME:
			if (!(value instanceof Filter)) {
				return "SOME: **INVALID VALUE - NOT A FILTER: (" + value + ") **";
			}
			return "some `" + property + "` {" + value.toString() + "}";
		case Filter.OP_ALL:
			if (!(value instanceof Filter)) {
				return "ALL: **INVALID VALUE - NOT A FILTER: (" + value + ") **";
			}
			return "all `" + property + "` {" + value.toString() + "}";
		case Filter.OP_NONE:
			if (!(value instanceof Filter)) {
				return "NONE: **INVALID VALUE - NOT A FILTER: (" + value + ") **";
			}
			return "none `" + property + "` {" + value.toString() + "}";
		case Filter.OP_CUSTOM:
			if (value == null || (value instanceof Collection && ((Collection) value).isEmpty()) || (value.getClass().isArray() && Array.getLength(value) == 0)) {
				return "CUSTOM[" + property + "]";
			} else {
				StringBuilder sb2 = new StringBuilder();
				sb2.append("CUSTOM[").append(property).append("]values(");
				boolean first2 = true;
				if (value instanceof Collection) {
					if (first2) {
						first2 = false;
					} else {
						sb2.append(',');
					}
					for (Object o : (Collection) value) {
						sb2.append(o);
					}
				} else if (value.getClass().isArray()) {
					if (first2) {
						first2 = false;
					} else {
						sb2.append(',');
					}
					for (int i = 0; i < Array.getLength(value); i++) {
						sb2.append(Array.get(value, i));
					}
				} else {
					sb2.append(value);
				}
				sb2.append(")");
			}
		default:
			return "**INVALID OPERATOR: (" + operator + ") - VALUE: " + InternalUtil.paramDisplayString(value) + " **";
		}
	}

}
