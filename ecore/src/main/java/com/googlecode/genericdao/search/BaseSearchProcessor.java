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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class provides two methods for generating query language to fulfill an
 * <code>ISearch</code>.
 * <ol>
 * <li><code>generateQL()</code> - is used for getting the actual search
 * results.</li>
 * <li><code>generateRowCountQL()</code> - is used for getting just the number
 * of results.</li>
 * </ol>
 * Both methods return a query language sting and a list of values for filling
 * named parameters. For example the following query and parameter list might be
 * returned:
 * 
 * <pre>
 * select _it from com.example.Cat _it
 *   where _it.age &gt; :p1 and _it.name != :p2
 *   
 * parameter list: [3, 'Mittens']
 * </pre>
 * 
 * This is an abstract class. A subclass must be used to implement individual
 * query languages. Currently only HQL query language is supported (
 * <code>com.googlecode.genericdao.dao.hibernate.HibernateSearchToQLProcessor</code>). The that
 * implementation could be used for EQL query language as well with no or minor
 * modifications.
 */
public abstract class BaseSearchProcessor {

	private static Logger logger = LoggerFactory.getLogger(BaseSearchProcessor.class);

	protected static int QLTYPE_HQL = 0;
	protected static int QLTYPE_EQL = 1;

	protected int qlType;

	protected MetadataUtil metadataUtil;

	protected String rootAlias = "_it";

	protected static final String ROOT_PATH = "";

	protected BaseSearchProcessor(int qlType, MetadataUtil metadataUtil) {
		if (metadataUtil == null) {
			throw new IllegalArgumentException("A SearchProcessor cannot be initialized with a null MetadataUtil.");
		}
		this.qlType = qlType;
		this.metadataUtil = metadataUtil;
	}

	/**
	 * The MetadataUtil used by this search processor. This can only be set in
	 * the constructor.
	 */
	public MetadataUtil getMetadataUtil() {
		return metadataUtil;
	}

	/**
	 * This is the string used to represent the root entity of the search within
	 * the query. The default value is <code>"_it"</code>. It may be necessary
	 * to use a different alias if there are entities in the data model with the
	 * name or property "_it".
	 */
	public void setRootAlias(String alias) {
		this.rootAlias = alias;
	}

	/**
	 * Generate the QL string for a given search. Fill paramList with the values
	 * to be used for the query. All parameters within the query string are
	 * specified as named parameters ":pX", where X is the index of the
	 * parameter value in paramList.
	 */
	public String generateQL(Class<?> entityClass, ISearch search, List<Object> paramList) {
		if (entityClass == null)
			throw new NullPointerException("The entity class for a search cannot be null");

		SearchContext ctx = new SearchContext(entityClass, rootAlias, paramList);

		List<Field> fields = checkAndCleanFields(search.getFields());
		
		applyFetches(ctx, checkAndCleanFetches(search.getFetches()), fields);
		
		String select = generateSelectClause(ctx, fields, search.isDistinct());
		String where = generateWhereClause(ctx, checkAndCleanFilters(search.getFilters()), search.isDisjunction());
		String orderBy = generateOrderByClause(ctx, checkAndCleanSorts(search.getSorts()));
		String from = generateFromClause(ctx, true);

		StringBuilder sb = new StringBuilder();
		sb.append(select);
		sb.append(from);
		sb.append(where);
		sb.append(orderBy);

		String query = sb.toString();
		if (logger.isDebugEnabled())
			logger.debug("generateQL:\n  " + query);
		return query;
	}

	/**
	 * Generate the QL string that will query the total number of results from a
	 * given search (paging is ignored). Fill paramList with the values to be
	 * used for the query. All parameters within the query string are specified
	 * as named parameters ":pX", where X is the index of the parameter value in
	 * paramList.
	 * 
	 * <b>NOTE:</b> Returns null if column operators are used in the search.
	 * Such a search will always return 1 row.
	 */
	public String generateRowCountQL(Class<?> entityClass, ISearch search, List<Object> paramList) {
		if (entityClass == null)
			throw new NullPointerException("The entity class for a search cannot be null");

		SearchContext ctx = new SearchContext(entityClass, rootAlias, paramList);

		String where = generateWhereClause(ctx, checkAndCleanFilters(search.getFilters()), search.isDisjunction());
		String from = generateFromClause(ctx, false);

		boolean useOperator = false, notUseOperator = false;
		List<Field> fields = search.getFields();
		if (fields != null) {
			for (Field field : fields) {
				switch (field.getOperator()) {
				case Field.OP_AVG:
				case Field.OP_COUNT:
				case Field.OP_COUNT_DISTINCT:
				case Field.OP_MAX:
				case Field.OP_MIN:
				case Field.OP_SUM:
					useOperator = true;
					break;
				default:
					notUseOperator = true;
					break;
				}
			}
		}
		if (useOperator && notUseOperator) {
			throw new Error("A search can not have a mix of fields with operators and fields without operators.");
		} else if (useOperator) {
			return null; // if we're using column operators, the query will
							// always return 1 result.
		}

		StringBuilder sb = new StringBuilder();
		if (!search.isDistinct()) {
			sb.append("select count(").append(rootAlias).append(")");
		} else if (fields.size() == 0) {
			sb.append("select count(distinct ");
			sb.append(rootAlias).append(")");
		} else if (fields.size() == 1) {
			sb.append("select count(distinct ");
			String prop = fields.get(0).getProperty();
			if (prop == null || "".equals(prop)) {
				sb.append(ctx.getRootAlias());
			} else {
				sb.append(getPathRef(ctx, prop));
			}
			sb.append(")");
		} else {
			throw new IllegalArgumentException("Unfortunately, Hibernate Generic DAO does not currently support "
					+ "the count operation on a search that has distinct set with multiple fields.");
		}
		sb.append(from);
		sb.append(where);

		String query = sb.toString();
		if (logger.isDebugEnabled())
			logger.debug("generateRowCountQL:\n  " + query);
		return query;
	}

	/**
	 * Internal method for generating the select clause based on the fields of
	 * the given search.
	 */
	protected String generateSelectClause(SearchContext ctx, List<Field> fields, boolean distinct) {

		StringBuilder sb = null;
		boolean useOperator = false, notUseOperator = false;
		boolean first = true;

		if (fields != null) {
			for (Field field : fields) {
				if (first) {
					sb = new StringBuilder("select ");
					if (distinct)
						sb.append("distinct ");
					first = false;
				} else {
					sb.append(", ");
				}

				if (field.getOperator() == Field.OP_CUSTOM) {
					// Special case for custom operator.
					if (field.getProperty() == null) {
						sb.append("null");
					} else {
						appendCustomExpression(sb, ctx, field.getProperty());
					}
				} else {
				
					String prop;
					if (field.getProperty() == null || "".equals(field.getProperty())) {
						prop = ctx.getRootAlias();
					} else {
						AliasNode aliasNodeForProperty = getAliasForPathIfItExists(ctx, field.getProperty());
						if (aliasNodeForProperty != null) {
							prop = aliasNodeForProperty.alias;
						} else {
							prop = getPathRef(ctx, field.getProperty());
						}
					}
	
					switch (field.getOperator()) {
					case Field.OP_AVG:
						sb.append("avg(");
						useOperator = true;
						break;
					case Field.OP_COUNT:
						sb.append("count(");
						useOperator = true;
						break;
					case Field.OP_COUNT_DISTINCT:
						sb.append("count(distinct ");
						useOperator = true;
						break;
					case Field.OP_MAX:
						sb.append("max(");
						useOperator = true;
						break;
					case Field.OP_MIN:
						sb.append("min(");
						useOperator = true;
						break;
					case Field.OP_SUM:
						sb.append("sum(");
						useOperator = true;
						break;
					default:
						notUseOperator = true;
						break;
					}
					sb.append(prop);
					if (useOperator) {
						sb.append(")");
					}
				}
			}
		}
		if (first) {
			// there are no fields
			if (distinct)
				return "select distinct " + ctx.getRootAlias();
			else
				return "select " + ctx.getRootAlias();
		}
		if (useOperator && notUseOperator) {
			throw new Error("A search can not have a mix of fields with operators and fields without operators.");
		}
		return sb.toString();
	}

	/**
	 * Apply the fetch list to the alias tree in the search context.
	 */
	protected void applyFetches(SearchContext ctx, List<String> fetches, List<Field> fields) {
		if (fetches != null) {
			// apply fetches
			boolean hasFetches = false, hasFields = false;
			for (String fetch : fetches) {
				getOrCreateAlias(ctx, fetch, true);
				hasFetches = true;
			}
			if (hasFetches && fields != null) {
				// don't fetch nodes whose ancestors aren't found in the select
				// clause
				List<String> fieldProps = new ArrayList<String>();
				for (Field field : fields) {
					if (field.getOperator() == Field.OP_PROPERTY) {
						fieldProps.add(field.getProperty() + ".");
					}
					hasFields = true;
				}
				if (hasFields) {
					for (AliasNode node : ctx.aliases.values()) {
						if (node.fetch) {
							// make sure it has an ancestor in the select clause
							boolean hasAncestor = false;
							for (String field : fieldProps) {
								if (node.getFullPath().startsWith(field)) {
									hasAncestor = true;
									break;
								}
							}
							if (!hasAncestor)
								node.fetch = false;
						}
					}
				}
			}
		}
	}

	/**
	 * Internal method for generating from clause. This method should be called
	 * after generating other clauses because it relies on the aliases they
	 * create. This method takes every path that is called for in the other
	 * clauses and makes it available as an alias using left joins. It also adds
	 * join fetching for properties specified by <code>fetches</code> if
	 * <code>doEagerFetching</code> is <code>true</code>. <b>NOTE:</b> When
	 * using eager fetching, <code>applyFetches()</code> must be executed first.
	 */
	protected String generateFromClause(SearchContext ctx, boolean doEagerFetching) {
		StringBuilder sb = new StringBuilder(" from ");
		
		sb.append(getMetadataUtil().get(ctx.rootClass).getEntityName());
		sb.append(" ");
		sb.append(ctx.getRootAlias());
		sb.append(generateJoins(ctx, doEagerFetching));
		return sb.toString();
	}

	/**
	 * Internal method for generating the join portion of the from clause. This
	 * method should be called after generating other clauses because it relies
	 * on the aliases they create. This method takes every path that is called
	 * for in the other clauses and makes it available as an alias using left
	 * joins. It also adds join fetching for properties specified by
	 * <code>fetches</code> if <code>doEagerFetching</code> is <code>true</code>
	 * . <b>NOTE:</b> When using eager fetching, <code>applyFetches()</code>
	 * must be executed first.
	 */
	protected String generateJoins(SearchContext ctx, boolean doEagerFetching) {
		StringBuilder sb = new StringBuilder();

		// traverse alias graph breadth-first
		Queue<AliasNode> queue = new LinkedList<AliasNode>();
		queue.offer(ctx.aliases.get(ROOT_PATH));
		while (!queue.isEmpty()) {
			AliasNode node = queue.poll();
			if (node.parent != null) {
				sb.append(" left join ");
				if (doEagerFetching && node.fetch)
					sb.append("fetch ");
				sb.append(node.parent.alias);
				sb.append(".");
				sb.append(node.property);
				sb.append(" as ");
				sb.append(node.alias);
			}
			for (AliasNode child : node.children) {
				queue.offer(child);
			}
		}

		return sb.toString();
	}

	/**
	 * Internal method for generating order by clause. Uses sort options from
	 * search.
	 */
	protected String generateOrderByClause(SearchContext ctx, List<Sort> sorts) {
		if (sorts == null)
			return "";

		StringBuilder sb = null;
		boolean first = true;
		for (Sort sort : sorts) {
			if (first) {
				sb = new StringBuilder(" order by ");
				first = false;
			} else {
				sb.append(", ");
			}
			if (sort.isCustomExpression()) {
				appendCustomExpression(sb, ctx, sort.getProperty());
			} else if (sort.isIgnoreCase() && metadataUtil.get(ctx.rootClass, sort.getProperty()).isString()) {
				sb.append("lower(");
				sb.append(getPathRef(ctx, sort.getProperty()));
				sb.append(")");
			} else {
				sb.append(getPathRef(ctx, sort.getProperty()));
			}
			sb.append(sort.isDesc() ? " desc" : " asc");
		}
		if (first) {
			return "";
		}
		return sb.toString();
	}

	/**
	 * Internal method for generating where clause for given search. Uses filter
	 * options from search.
	 */
	protected String generateWhereClause(SearchContext ctx, List<Filter> filters, boolean isDisjunction) {
		String content = null;
		if (filters == null || filters.size() == 0) {
			return "";
		} else if (filters.size() == 1) {
			content = filterToQL(ctx, filters.get(0));
		} else {
			Filter junction = new Filter(null, filters, isDisjunction ? Filter.OP_OR : Filter.OP_AND);
			content = filterToQL(ctx, junction);
		}

		return (content == null) ? "" : " where " + content;
	}

	/**
	 * Recursively generate the QL fragment for a given search filter option.
	 */
	@SuppressWarnings("unchecked")
	protected String filterToQL(SearchContext ctx, Filter filter) {
		String property = filter.getProperty();
		Object value = filter.getValue();
		int operator = filter.getOperator();

		// for IN and NOT IN, if value is empty list, return false, and true
		// respectively
		if (operator == Filter.OP_IN || operator == Filter.OP_NOT_IN) {
			if (value instanceof Collection && ((Collection) value).size() == 0) {
				return operator == Filter.OP_IN ? "1 = 2" : "1 = 1";
			}
			if (value instanceof Object[] && ((Object[]) value).length == 0) {
				return operator == Filter.OP_IN ? "1 = 2" : "1 = 1";
			}
		}

		// convert numbers to the expected type if needed (ex: Integer to Long)
		if (filter.isTakesListOfValues()) {
			value = prepareValue(ctx.rootClass, property, value, true);
		} else if (filter.isTakesSingleValue()) {
			value = prepareValue(ctx.rootClass, property, value, false);
		}

		Metadata metadata;

		switch (operator) {
		case Filter.OP_NULL:
			return getPathRef(ctx, property) + " is null";
		case Filter.OP_NOT_NULL:
			return getPathRef(ctx, property) + " is not null";
		case Filter.OP_IN:
			return getPathRef(ctx, property) + " in (" + param(ctx, value) + ")";
		case Filter.OP_NOT_IN:
			return getPathRef(ctx, property) + " not in (" + param(ctx, value) + ")";
		case Filter.OP_EQUAL:
			return getPathRef(ctx, property) + " = " + param(ctx, value);
		case Filter.OP_NOT_EQUAL:
			return getPathRef(ctx, property) + " != " + param(ctx, value);
		case Filter.OP_GREATER_THAN:
			return getPathRef(ctx, property) + " > " + param(ctx, value);
		case Filter.OP_LESS_THAN:
			return getPathRef(ctx, property) + " < " + param(ctx, value);
		case Filter.OP_GREATER_OR_EQUAL:
			return getPathRef(ctx, property) + " >= " + param(ctx, value);
		case Filter.OP_LESS_OR_EQUAL:
			return getPathRef(ctx, property) + " <= " + param(ctx, value);
		case Filter.OP_LIKE:
			return getPathRef(ctx, property) + " like " + param(ctx, value.toString());
		case Filter.OP_ILIKE:
			return "lower(" + getPathRef(ctx, property) + ") like lower(" + param(ctx, value.toString()) + ")";
		case Filter.OP_AND:
		case Filter.OP_OR:
			if (!(value instanceof List)) {
				return null;
			}

			String op = filter.getOperator() == Filter.OP_AND ? " and " : " or ";

			StringBuilder sb = new StringBuilder("(");
			boolean first = true;
			for (Object o : ((List) value)) {
				if (o instanceof Filter) {
					String filterStr = filterToQL(ctx, (Filter) o);
					if (filterStr != null) {
						if (first) {
							first = false;
						} else {
							sb.append(op);
						}
						sb.append(filterStr);
					}
				}
			}
			if (first)
				return null;

			sb.append(")");
			return sb.toString();
		case Filter.OP_NOT:
			if (!(value instanceof Filter)) {
				return null;
			}
			String filterStr = filterToQL(ctx, (Filter) value);
			if (filterStr == null)
				return null;

			return "not " + filterStr;
		case Filter.OP_EMPTY:
			metadata = metadataUtil.get(ctx.rootClass, property);
			if (metadata.isCollection()) {
				return "not exists elements(" + getPathRef(ctx, property) + ")";
			} else if (metadata.isString()) {
				String pathRef = getPathRef(ctx, property);
				return "(" + pathRef + " is null or " + pathRef + " = '')";
			} else {
				return getPathRef(ctx, property) + " is null";
			}
		case Filter.OP_NOT_EMPTY:
			metadata = metadataUtil.get(ctx.rootClass, property);
			if (metadata.isCollection()) {
				return "exists elements(" + getPathRef(ctx, property) + ")";
			} else if (metadata.isString()) {
				String pathRef = getPathRef(ctx, property);
				return "(" + pathRef + " is not null and " + pathRef + " != '')";
			} else {
				return getPathRef(ctx, property) + " is not null";
			}
		case Filter.OP_SOME:
			if (!(value instanceof Filter)) {
				return null;
			} else if (value instanceof Filter) {
				String simple = generateSimpleAllOrSome(ctx, property, (Filter) value, "some");
				if (simple != null) {
					return simple;
				} else {
					return "exists " + generateSubquery(ctx, property, (Filter) value);
				}
			}
		case Filter.OP_ALL:
			if (!(value instanceof Filter)) {
				return null;
			} else if (value instanceof Filter) {
				String simple = generateSimpleAllOrSome(ctx, property, (Filter) value, "all");
				if (simple != null) {
					return simple;
				} else {
					return "not exists " + generateSubquery(ctx, property, negate((Filter) value));
				}
			}
		case Filter.OP_NONE:
			if (!(value instanceof Filter)) {
				return null;
			} else if (value instanceof Filter) {
				// NOTE: Using "all" for the simple all or some is logically
				// incorrect. It should be "some". However,
				// because of a bug in how Hibernate 3.1.1 tries to simplify
				// "not ... some/all ...) it actually ends
				// up working as desired. TODO: If and when the Hibernate bug is
				// fixed, this should be switched to "some".
				String simple = generateSimpleAllOrSome(ctx, property, (Filter) value, "all");
				if (simple != null) {
					return "not ( " + simple + " )";
				} else {
					return "not exists " + generateSubquery(ctx, property, (Filter) value);
				}
			}
		case Filter.OP_CUSTOM:
			List<?> values = filter.getValuesAsList();
			if (values == null) {
				values = Collections.singletonList(null);
			}
			StringBuilder sbCustom = new StringBuilder();
			appendCustomExpression(sbCustom, ctx, filter.getProperty(), values);
			return sbCustom.toString();
		default:
			throw new IllegalArgumentException("Filter comparison ( " + operator + " ) is invalid.");
		}
	}

	/**
	 * Generate a QL string for a subquery on the given property that uses the
	 * given filter. This is used by SOME, ALL and NONE filters.
	 * 
	 * @param ctx
	 *            - a new context just for this sub-query
	 * @param property
	 *            - the property of the main query that points to the collection
	 *            on which to query
	 * @param filter
	 *            - the filter to use for the where clause of the sub-query
	 */
	protected String generateSubquery(SearchContext ctx, String property, Filter filter) {
		SearchContext ctx2 = new SearchContext();
		ctx2.rootClass = metadataUtil.get(ctx.rootClass, property).getJavaClass();
		ctx2.setRootAlias(rootAlias + (ctx.nextSubqueryNum++));
		ctx2.paramList = ctx.paramList;
		ctx2.nextAliasNum = ctx.nextAliasNum;
		ctx2.nextSubqueryNum = ctx.nextSubqueryNum;

		List<Filter> filters = new ArrayList<Filter>(1);
		filters.add(filter);
		String where = generateWhereClause(ctx2, filters, false);
		String joins = generateJoins(ctx2, false);
		ctx.nextAliasNum = ctx2.nextAliasNum;
		ctx.nextSubqueryNum = ctx2.nextSubqueryNum;

		StringBuilder sb = new StringBuilder();
		sb.append("(from ");
		sb.append(getPathRef(ctx, property));
		sb.append(" ");
		sb.append(ctx2.getRootAlias());
		sb.append(joins);
		sb.append(where);
		sb.append(")");

		return sb.toString();
	}

	/**
	 * <p>
	 * In the case of simple ALL/SOME/NONE filters, a simpler hql syntax is used
	 * (which is also compatible with collections of values). Simple filters
	 * include ALL/SOME/NONE filters with exactly one sub-filter where that
	 * filter applies to the elements of the collection directly (as opposed to
	 * their properties) and the operator is =, !=, <, <=, >, or >=.
	 * 
	 * <p>
	 * For example:
	 * 
	 * <pre>
	 * Filter.some(&quot;some_collection_of_strings&quot;, Filter.equal(&quot;&quot;, &quot;Bob&quot;)
	 * Filter.all(&quot;some_collection_of_numbers&quot;, Filter.greaterThan(null, 23)
	 * </pre>
	 * 
	 * If the filter meets these criteria as a simple ALL/SOME/NONE filter, the
	 * QL string for the filter will be returned. If not, <code>null</code> is
	 * returned.
	 * 
	 * @param ctx
	 *            - the context of the SOME/ALL/NONE filter
	 * @param property
	 *            - the property of the SOME/ALL/NONE filter
	 * @param filter
	 *            - the sub-filter that is the value of the SOME/ALL/NONE filter
	 * @param operation
	 *            - a string used to fill in the collection operation. The value
	 *            should be either "some" or "all".
	 */
	protected String generateSimpleAllOrSome(SearchContext ctx, String property, Filter filter, String operation) {
		if (filter.getProperty() != null && !filter.getProperty().equals(""))
			return null;

		String op;

		switch (filter.getOperator()) {
		case Filter.OP_EQUAL:
			op = " = ";
			break;
		case Filter.OP_NOT_EQUAL:
			op = " != ";
			break;
		case Filter.OP_LESS_THAN:
			op = " > ";
			break;
		case Filter.OP_LESS_OR_EQUAL:
			op = " >= ";
			break;
		case Filter.OP_GREATER_THAN:
			op = " < ";
			break;
		case Filter.OP_GREATER_OR_EQUAL:
			op = " <= ";
			break;
		default:
			return null;
		}

		Object value = InternalUtil.convertIfNeeded(filter.getValue(), metadataUtil.get(ctx.rootClass, property)
				.getJavaClass());
		return param(ctx, value) + op + operation + " elements(" + getPathRef(ctx, property) + ")";
	}

	/**
	 * Convert a property value to the expected type for that property. Ex. a
	 * Long to and Integer.
	 * 
	 * @param isCollection
	 *            <code>true</code> if the value is a collection of values, for
	 *            example with IN and NOT_IN operators.
	 * @return the converted value.
	 */
	@SuppressWarnings("unchecked")
	protected Object prepareValue(Class<?> rootClass, String property, Object value, boolean isCollection) {
		if (value == null)
			return null;

		Class<?> expectedClass;
		if (property != null && ("class".equals(property) || property.endsWith(".class"))) {
			expectedClass = Class.class;
		} else if (property != null && ("size".equals(property) || property.endsWith(".size"))) {
			expectedClass = Integer.class;
		} else {
			expectedClass = metadataUtil.get(rootClass, property).getJavaClass();
		}

		// convert numbers to the expected type if needed (ex: Integer to Long)
		if (isCollection) {
			// Check each element in the collection.
			Object[] val2;

			if (value instanceof Collection) {
				val2 = new Object[((Collection) value).size()];
				int i = 0;
				for (Object item : (Collection) value) {
					val2[i++] = InternalUtil.convertIfNeeded(item, expectedClass);
				}
			} else {
				val2 = new Object[((Object[]) value).length];
				int i = 0;
				for (Object item : (Object[]) value) {
					val2[i++] = InternalUtil.convertIfNeeded(item, expectedClass);
				}
			}
			return val2;
		} else {
			return InternalUtil.convertIfNeeded(value, expectedClass);
		}
	}

	/**
	 * Return a filter that negates the given filter.
	 */
	protected Filter negate(Filter filter) {
		return Filter.not(addExplicitNullChecks(filter));
	}

	/**
	 * Used by {@link #negate(Filter)}. There's a complication with null values
	 * in the database so that !(x == 1) is not the opposite of (x == 1). Rather
	 * !(x == 1 and x != null) is the same as (x == 1). This method applies the
	 * null check explicitly to all filters included in the given filter tree.
	 */
	protected Filter addExplicitNullChecks(Filter filter) {
		return SearchUtil.walkFilter(filter, new SearchUtil.FilterVisitor() {
			@Override
			public Filter visitAfter(Filter filter) {
				if (filter.isTakesSingleValue() || filter.isTakesListOfValues()) {
					return Filter.and(filter, Filter.isNotNull(filter.getProperty()));
				} else {
					return filter;
				}
			}
		}, false);

	}
	
	/**
	 * append a custom expression to the string builder, replacing any
	 * property tokens (i.e "{prop}") with a reference to the property. 
	 */
	protected void appendCustomExpression(StringBuilder sb, SearchContext ctx, String expression) {
		Matcher matcher = Pattern.compile("\\{[\\w\\.]*\\}").matcher(expression);
		int lastEnd = 0;
		while (matcher.find()) {
			sb.append(expression.substring(lastEnd, matcher.start()));
			sb.append(getPathRef(ctx, expression.substring(matcher.start() + 1, matcher.end() - 1)));
			lastEnd = matcher.end();
		}
		sb.append(expression.substring(lastEnd));
	}
	
	/**
	 * append a custom expression to the string builder, replacing any
	 * property tokens (i.e "{prop}") with a reference to the property and
	 * value tokens (i.e. "?n") with params. 
	 */
	protected void appendCustomExpression(StringBuilder sb, SearchContext ctx, String expression, List<?> values) {
		Matcher matcher = Pattern.compile("(\\{[\\w\\.]*\\})|(\\?\\d+\\b)").matcher(expression);
		int lastEnd = 0;
		while (matcher.find()) {
			sb.append(expression.substring(lastEnd, matcher.start()));
			if (expression.charAt(matcher.start()) == '{') {
				sb.append(getPathRef(ctx, expression.substring(matcher.start() + 1, matcher.end() - 1)));
			} else {
				int valueIndex = Integer.valueOf(expression.substring(matcher.start() + 1, matcher.end()));
				if (valueIndex == 0) {
					throw new IllegalArgumentException("This custom filter expression (" + expression + ") contains a value placeholder for zero (?0), but placeholders should be numbered starting at ?1.");
				}
				if (values == null || values.isEmpty()) {
					throw new IllegalArgumentException("This custom filter expression (" + expression + ") calls for a value placeholder number " + valueIndex + ", but no values were specified.");
				}
				if (valueIndex > values.size()) {
					throw new IllegalArgumentException("This custom filter expression (" + expression + ") calls for a value placeholder number " + valueIndex + ", but only " + values.size() + " values were specified.");
				}
				sb.append(param(ctx, values.get(valueIndex - 1)));
			}
			lastEnd = matcher.end();
		}
		sb.append(expression.substring(lastEnd));
	}

	/**
	 * Add value to paramList and return the named parameter string ":pX".
	 */
	protected String param(SearchContext ctx, Object value) {
		if (value instanceof Class) {
			return ((Class<?>) value).getName();
		}

		if (value instanceof Collection) {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (Object o : (Collection) value) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				ctx.paramList.add(o);
				sb.append(":p");
				sb.append(Integer.toString(ctx.paramList.size()));
			}
			return sb.toString();
		} else if (value instanceof Object[]) {
			StringBuilder sb = new StringBuilder();
			boolean first = true;
			for (Object o : (Object[]) value) {
				if (first) {
					first = false;
				} else {
					sb.append(",");
				}
				ctx.paramList.add(o);
				sb.append(":p");
				sb.append(Integer.toString(ctx.paramList.size()));
			}
			return sb.toString();
		} else {
			ctx.paramList.add(value);
			return ":p" + Integer.toString(ctx.paramList.size());
		}
	}

	/**
	 * Given a full path to a property (ex. department.manager.salary), return
	 * the reference to that property that uses the appropriate alias (ex.
	 * a4_manager.salary).
	 */
	protected String getPathRef(SearchContext ctx, String path) {
		if (path == null || "".equals(path)) {
			return ctx.getRootAlias();
		}

		String[] parts = splitPath(ctx, path);

		return getOrCreateAlias(ctx, parts[0], false).alias + "." + parts[1];
	}

	/**
	 * Split a path into two parts. The first part will need to be aliased. The
	 * second part will be a property of that alias. For example:
	 * (department.manager.salary) would return [department.manager, salary].
	 */
	protected String[] splitPath(SearchContext ctx, String path) {
		if (path == null || "".equals(path))
			return null;

		int pos = path.lastIndexOf('.');

		if (pos == -1) {
			return new String[] { "", path };
		} else {
			String lastSegment = path.substring(pos + 1);
			String currentPath = path;
			boolean first = true;

			// Basically gobble up as many segments as possible until we come to
			// an entity. Entities must become aliases so we can use our left
			// join feature.
			// The exception is that if a segment is an id, we want to skip the
			// entity preceding it because (entity.id) is actually stored in the
			// same table as the foreign key.
			while (true) {
				if (metadataUtil.isId(ctx.rootClass, currentPath)) {
					// if it's an id property
					// skip one segment
					if (pos == -1) {
						return new String[] { "", path };
					}
					pos = currentPath.lastIndexOf('.', pos - 1);
				} else if (!first && metadataUtil.get(ctx.rootClass, currentPath).isEntity()) {
					// when we reach an entity (excluding the very first
					// segment), we're done
					return new String[] { currentPath, path.substring(currentPath.length() + 1) };
				}
				first = false;

				// For size, we need to go back to the 'first' behavior
				// for the next segment.
				if (pos != -1 && lastSegment.equals("size")
						&& metadataUtil.get(ctx.rootClass, currentPath.substring(0, pos)).isCollection()) {
					first = true;
				}

				// if that was the last segment, we're done
				if (pos == -1) {
					return new String[] { "", path };
				}
				// proceed to the next segment
				currentPath = currentPath.substring(0, pos);
				pos = currentPath.lastIndexOf('.');
				if (pos == -1) {
					lastSegment = currentPath;
				} else {
					lastSegment = currentPath.substring(pos + 1);
				}
			}

		}

		// 1st
		// if "id", go 2; try again
		// if component, go 1; try again
		// if entity, go 1; try again
		// if size, go 1; try 1st again

		// successive
		// if "id", go 2; try again
		// if component, go 1; try again
		// if entity, stop
	}

	/**
	 * Given a full path to an entity (ex. department.manager), return the alias
	 * to reference that entity (ex. a4_manager). If there is no alias for the
	 * given path, one will be created.
	 */
	protected AliasNode getOrCreateAlias(SearchContext ctx, String path, boolean setFetch) {
		AliasNode foundNode = getAliasForPathIfItExists(ctx, path);
		
		if (foundNode != null) {
			if (setFetch)
				setFetchOnAliasNodeAndAllAncestors(foundNode);

			return foundNode;
		} else {
			String[] parts = splitPath(ctx, path);

			int pos = parts[1].lastIndexOf('.');

			String alias = "a" + (ctx.nextAliasNum++) + "_" + (pos == -1 ? parts[1] : parts[1].substring(pos + 1));

			AliasNode node = new AliasNode(parts[1], alias);

			// set up path recursively
			getOrCreateAlias(ctx, parts[0], setFetch).addChild(node);

			if (setFetch)
				setFetchOnAliasNodeAndAllAncestors(node);
			
			ctx.aliases.put(path, node);

			return node;
		}
	}

	/**
	 * Given a full path to an entity (ex. department.manager), return the alias
	 * to reference that entity (ex. a4_manager). If there is no alias for the
	 * given path, one will be created.
	 * 
	 * @return the associated AliasNode or <code>null</code> if none.
	 */
	protected AliasNode getAliasForPathIfItExists(SearchContext ctx, String path) {
		if (path == null || path.equals("")) {
			return ctx.aliases.get(ROOT_PATH);
		} else {
			return ctx.aliases.get(path);
		}
	}
	
	protected void setFetchOnAliasNodeAndAllAncestors(AliasNode node) {
		while (node.parent != null) {
			node.fetch = true;
			node = node.parent;
		}
	}

	protected static final class AliasNode {
		String property;
		String alias;
		boolean fetch;
		AliasNode parent;
		List<AliasNode> children = new ArrayList<AliasNode>();

		AliasNode(String property, String alias) {
			this.property = property;
			this.alias = alias;
		}

		void addChild(AliasNode node) {
			children.add(node);
			node.parent = this;
		}

		public String getFullPath() {
			if (parent == null)
				return "";
			else if (parent.parent == null)
				return property;
			else
				return parent.getFullPath() + "." + property;
		}
	}

	protected static final class SearchContext {
		Class<?> rootClass;
		Map<String, AliasNode> aliases = new HashMap<String, AliasNode>();
		List<Object> paramList;

		int nextAliasNum = 1;
		int nextSubqueryNum = 1;

		public SearchContext() {
		}

		public SearchContext(Class<?> rootClass, String rootAlias, List<Object> paramList) {
			this.rootClass = rootClass;
			setRootAlias(rootAlias);
			this.paramList = paramList;
		}

		public void setRootAlias(String rootAlias) {
			this.aliases.put(ROOT_PATH, new AliasNode(ROOT_PATH, rootAlias));
		}

		public String getRootAlias() {
			return this.aliases.get(ROOT_PATH).alias;
		}
	}

	// ---- SECURITY CHECK ---- //

	/**
	 * <ol>
	 * <li>Check for injection attack in property strings. <li>The field list
	 * may not contain nulls.
	 * </ol>
	 */
	protected List<Field> checkAndCleanFields(List<Field> fields) {
		if (fields == null)
			return null;

		for (Field field : fields) {
			if (field == null) {
				throw new IllegalArgumentException("The search contains a null field.");
			}
			if (field.getProperty() != null && field.getOperator() != Field.OP_CUSTOM)
				securityCheckProperty(field.getProperty());
		}

		return fields;
	}

	/**
	 * <ol>
	 * <li>Check for injection attack in property strings. <li>Remove null
	 * fetches from the list.
	 * </ol>
	 */
	protected List<String> checkAndCleanFetches(List<String> fetches) {
		return SearchUtil.walkList(fetches, new SearchUtil.ItemVisitor<String>() {
			@Override
			public String visit(String fetch) {
				securityCheckProperty(fetch);
				return fetch;
			}
		}, true);
	}

	/**
	 * <ol>
	 * <li>Check for injection attack in property strings. <li>Remove null sorts
	 * from the list.
	 * </ol>
	 */
	protected List<Sort> checkAndCleanSorts(List<Sort> sorts) {
		return SearchUtil.walkList(sorts, new SearchUtil.ItemVisitor<Sort>() {
			@Override
			public Sort visit(Sort sort) {
				if (!sort.isCustomExpression()) {
					securityCheckProperty(sort.getProperty());
				}
				return sort;
			}
		}, true);
	}

	/**
	 * <ol>
	 * <li>Check for injection attack in property strings. <li>Check for values
	 * that are incongruent with the operator. <li>Remove null filters from the
	 * list. <li>Simplify out junctions (and/or) that have only one sub-filter.
	 * <li>Remove filters that require sub-filters but have none
	 * (and/or/not/some/all/none)
	 * </ol>
	 */
	protected List<Filter> checkAndCleanFilters(List<Filter> filters) {
		return SearchUtil.walkFilters(filters, new SearchUtil.FilterVisitor() {
			@SuppressWarnings("unchecked")
			@Override
			public Filter visitBefore(Filter filter) {
				if (filter == null) {
					return null;
				}
				
				// If the operator needs a value and no value is specified, ignore this filter.
				// Only NULL, NOT_NULL, EMPTY, NOT_EMPTY and CUSTOM do not need a value.
				if (filter.getValue() == null && !filter.isTakesNoValue()) {
					return null;
				}
				
				if (filter.getValue() != null) {
					if (filter.isTakesListOfSubFilters()) {
						// make sure that filters that take lists of filters
						// actually have lists of filters for their values
						if (filter.getValue() instanceof List) {
							for (Object o : (List) filter.getValue()) {
								if (!(o instanceof Filter)) {
									throw new IllegalArgumentException(
											"The search has a filter ("
													+ filter
													+ ") for which the value should be a List of Filters but there is an element in the list that is of type: "
													+ o.getClass());
								}
							}
						} else {
							throw new IllegalArgumentException(
									"The search has a filter ("
											+ filter
											+ ") for which the value should be a List of Filters but is not a list. The actual type is "
											+ filter.getValue().getClass());
						}
					} else if (filter.isTakesSingleSubFilter()) {
						// make sure filters that take filters actually have
						// filters for their values
						if (!(filter.getValue() instanceof Filter)) {
							throw new IllegalArgumentException("The search has a filter (" + filter
									+ ") for which the value should be of type Filter but is of type: "
									+ filter.getValue().getClass());
						}
					}
				}

				return filter;
			}

			@SuppressWarnings("unchecked")
			@Override
			public Filter visitAfter(Filter filter) {
				if (filter == null)
					return null;

				if (filter.getOperator() == Filter.OP_CUSTOM) {
					if (filter.getProperty() == null || filter.getProperty().isEmpty()) {
						 throw new IllegalArgumentException("This search has a custom filter with no expression specified.");
					}
				} else if (!filter.isTakesNoProperty()) {
					securityCheckProperty(filter.getProperty());
				}

				// Remove operators that take sub filters but have none
				// assigned. Replace conjunctions that only have a single
				// sub-filter with that sub-filter.
				if (filter.isTakesSingleSubFilter()) {
					if (filter.getValue() == null) {
						return null;
					}
				} else if (filter.isTakesListOfSubFilters()) {
					if (filter.getValue() == null) {
						return null;
					} else {
						List<Filter> list = (List<Filter>) filter.getValue();
						if (list.size() == 0) {
							return null;
						} else if (list.size() == 1) {
							return list.get(0);
						}
					}
				}

				return filter;
			}
		}, true);
	}

	/**
	 * Regex pattern for a valid property name/path.
	 */
	protected Pattern INJECTION_CHECK = Pattern.compile("^[\\w\\.]*$");

	/**
	 * Used by <code>securityCheck()</code> to check a property string for
	 * injection attack.
	 */
	protected void securityCheckProperty(String property) {
		if (property == null)
			return;
		if (!INJECTION_CHECK.matcher(property).matches())
			throw new IllegalArgumentException(
					"A property used in a Search may only contain word characters (alphabetic, numeric and underscore \"_\") and dot \".\" seperators. This constraint was violated: "
							+ property);
	}

	private static final ExampleOptions defaultExampleOptions = new ExampleOptions();

	public Filter getFilterFromExample(Object example) {
		return getFilterFromExample(example, null);
	}

	public Filter getFilterFromExample(Object example, ExampleOptions options) {
		if (example == null)
			return null;
		if (options == null)
			options = defaultExampleOptions;

		List<Filter> filters = new ArrayList<Filter>();
		LinkedList<String> path = new LinkedList<String>();
		Metadata metadata = metadataUtil.get(example.getClass());
		getFilterFromExampleRecursive(example, metadata, options, path, filters);

		if (filters.size() == 0) {
			return null;
		} else if (filters.size() == 1) {
			return filters.get(0);
		} else {
			return new Filter("AND", filters, Filter.OP_AND);
		}
	}

	private void getFilterFromExampleRecursive(Object example, Metadata metadata, ExampleOptions options,
			LinkedList<String> path, List<Filter> filters) {
		if (metadata.isEntity() && !metadata.getIdType().isEmbeddable()) {
			Object id = metadata.getIdValue(example);
			if (id != null) {
				filters.add(Filter.equal(listToPath(path, "id"), id));
				return;
			}
		}

		for (String property : metadata.getProperties()) {
			if (options.getExcludeProps() != null && options.getExcludeProps().size() != 0) {
				if (options.getExcludeProps().contains(listToPath(path, property)))
					continue;
			}

			Metadata pMetadata = metadata.getPropertyType(property);
			if (pMetadata.isCollection()) {
				// ignore collections
			} else {
				Object value = metadata.getPropertyValue(example, property);
				if (value == null) {
					if (!options.isExcludeNulls()) {
						filters.add(Filter.isNull(listToPath(path, property)));
					}
				} else if (options.isExcludeZeros() && value instanceof Number && ((Number) value).longValue() == 0) {
					// ignore zeros
				} else {
					if (pMetadata.isEntity() || pMetadata.isEmbeddable()) {
						path.add(property);
						getFilterFromExampleRecursive(value, pMetadata, options, path, filters);
						path.removeLast();
					} else if (pMetadata.isString()
							&& (options.getLikeMode() != ExampleOptions.EXACT || options.isIgnoreCase())) {
						String val = value.toString();
						switch (options.getLikeMode()) {
						case ExampleOptions.START:
							val = val + "%";
							break;
						case ExampleOptions.END:
							val = "%" + val;
							break;
						case ExampleOptions.ANYWHERE:
							val = "%" + val + "%";
							break;
						}
						filters.add(new Filter(listToPath(path, property), val,
								options.isIgnoreCase() ? Filter.OP_ILIKE : Filter.OP_LIKE));
					} else {
						filters.add(Filter.equal(listToPath(path, property), value));
					}
				}
			}
		}
	}

	private String listToPath(List<String> list, String lastProperty) {
		StringBuilder sb = new StringBuilder();
		for (String prop : list) {
			sb.append(prop).append(".");
		}
		sb.append(lastProperty);
		return sb.toString();
	}
}
