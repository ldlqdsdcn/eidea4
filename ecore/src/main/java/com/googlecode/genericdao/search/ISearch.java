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

import java.util.List;

/**
 * The base search DTO (data transfer object). A Search object is passed into a
 * DAO or Search Processor to define the parameters for a search. There are six
 * types of parameters that can be set.
 * <ul>
 * <li>SearchClass - The Class of the object(s) to search for.
 * <li>Filters - Any number of filters may be specified for the search. Filters
 * specify a property and a condition that it must match for the object to be
 * included in the result. Filters are "ANDed" together by default, but
 * disjunction (OR) can be used instead by setting
 * <code>disjunction == true</code>. See also the {@link Filter} class.
 * <li>Sorts - Any number of sorts may be specified. Each sort consists of a
 * property, a flag for ascending or descending and a flag for whether or not to
 * ignore case. The first sort added is the primary sort, the second, secondary
 * and so on. See also the {@link Sort} class.
 * <li>Fields - By default the entity specified in search class is returned as
 * the result for each row. However, by specifying fields, any combination of
 * individual properties can be returned for each row in the result set. These
 * properties can be returned as maps, lists, arrays or a single object
 * depending on <code>resultMode</code>. See also the {@link Field}
 * class.<br/><br/>
 * 
 * Additionally, fields can be specified using column operators:
 * <code>COUNT, COUNT DISTINCT, SUM, AVG, MAX, MIN</code>. Note that fields with
 * column operators can not be mixed with fields that do not use column
 * operators.<br/><br/>
 * 
 * Set <code>distinct</code> to <code>true</code> in order to filter out
 * duplicate results.<br/><br/>
 * 
 * <li>Fetch - This determines which attached objects to pull along with the
 * base search object. With Hibernate this eagerly loads the specified
 * properties. Otherwise they might be loaded lazily. This is useful for
 * performance and results that will be disconnected from Hibernate and copied
 * to a remote client.
 * <li>Paging - The maximum number of results may be specified with
 * <code>maxResults</code>. (This can also be thought of as results per page.)
 * The first result can be specified using either <code>firstResult</code> or
 * <code>page</code>.
 * </ul>
 * 
 * <code>ISearch</code> is intended to be an immutable interface and only
 * provides getters for each of the properties. The {@link IMutableSearch}
 * interface extends <code>ISearch</code> by adding setters for all the
 * properties.
 * 
 * @see Filter
 * @see Field
 * @see Sort
 * @see IMutableSearch
 * 
 * @author dwolverton
 * 
 */
public interface ISearch {
	/**
	 * Value for result mode. This is the default value. With
	 * <code>RESULT_AUTO</code> the result mode is automatically determined
	 * according to the following rules:
	 * <ul>
	 * <li>If any field is specified with a key, use <code>RESULT_MAP</code>.
	 * <li>Otherwise, if zero or one fields are specified, use <code>
	 * RESULT_SINGLE</code>.
	 * <li>Otherwise, use <code>RESULT_ARRAY</code>.
	 * </ul>
	 * 
	 * @see #getResultMode()
	 */
	public static final int RESULT_AUTO = 0;

	/**
	 * Value for result mode. <code>RESULT_ARRAY</code> returns each result as
	 * an Object array (<code>Object[]</code>) with the entries corresponding to
	 * the fields added to the search. Here's an example:
	 * 
	 * <pre>
	 * Search s = new Search(Person.class);
	 * s.setResultMode(Search.RESULT_ARRAY);
	 * s.addField(&quot;firstName&quot;);
	 * s.addField(&quot;lastName&quot;);
	 * for (Object[] array : dao.search(s)) {
	 * 	System.out.println(array[0] + &quot; &quot; + array[1]);
	 * }
	 * </pre>
	 * 
	 * @see #getResultMode()
	 */
	public static final int RESULT_ARRAY = 1;

	/**
	 * Value for result mode. <code>RESULT_LIST</code> returns each result as a
	 * list of Objects (<code>List&lt;Object&gt;</Code> ). Here's an example:
	 * 
	 * <pre>
	 * Search s = new Search(Person.class);
	 * s.setResultMode(Search.RESULT_LIST);
	 * s.addField(&quot;firstName&quot;);
	 * s.addField(&quot;lastName&quot;);
	 * for (List&lt;Object&gt; list : dao.search(s)) {
	 * 	System.out.println(list.get(0) + &quot; &quot; + list.get(1));
	 * }
	 * </pre>
	 * 
	 * @see #getResultMode()
	 */
	public static final int RESULT_LIST = 2;

	/**
	 * Value for result mode. <code>RESULT_MAP</code> returns each row as a map
	 * with properties' names or keys for keys to the corresponding values.
	 * Here's an example:
	 * 
	 * <pre>
	 * Search s = new Search(Person.class);
	 * s.setResultMode(Search.RESULT_MAP;
	 * s.addField(&quot;firstName&quot;);
	 * s.addField(&quot;lastName&quot;, &quot;ln&quot;);
	 * for (Map&lt;String, Object&gt; map : dao.search(s)) {
	 * 	System.out.println(map.get(&quot;firstName&quot;) + &quot; &quot; + map.get(&quot;ln&quot;));
	 * }
	 * </pre>
	 * 
	 * @see #getResultMode()
	 */
	public static final int RESULT_MAP = 3;

	/**
	 * Value for result mode. <code>RESULT_SINGLE</code> - Exactly one field or
	 * no fields must be specified to use this result mode. The result list
	 * contains just the value of that property for each element. Here's an
	 * example:
	 * 
	 * <pre>
	 * Search s = new Search(Person.class);
	 * s.setResultMode(Search.RESULT_SINGLE);
	 * s.addField(&quot;firstName&quot;);
	 * for (Object name : dao.search(s)) {
	 * 	System.out.println(name);
	 * }
	 * </pre>
	 * 
	 * @see #getResultMode()
	 */
	public static final int RESULT_SINGLE = 4;

	/**
	 * Zero based index of first result record to return.
	 * 
	 * <p>
	 * <code>&lt;= 0</code> for unspecified value.
	 */
	public int getFirstResult();

	/**
	 * The maximum number of records to return. Also used as page size when
	 * calculating the first record to return based on <code>page</code>.
	 * 
	 * <p>
	 * <code>&lt;= 0</code> for unspecified value.
	 */
	public int getMaxResults();

	/**
	 * Zero based index of the page of records to return. The size of a page is
	 * determined by <code>maxResults</code>. If both <code>page</code> and
	 * <code>maxResults</code> are specified (i.e. > 0), the first result
	 * returned is calculated by <code>page * maxResults</code>.
	 * 
	 * <p>
	 * <code>firstResult</code> has precedence over <code>page</code>. So if
	 * <code>firstResult</code> is specified (i.e. > 0), <code>page</code> is
	 * ignored.
	 * 
	 * <p>
	 * <code>&lt;= 0</code> for unspecified value.
	 */
	public int getPage();

	public Class<?> getSearchClass();

	public List<Filter> getFilters();

	public boolean isDisjunction();

	public List<Sort> getSorts();

	public List<Field> getFields();
	
	public boolean isDistinct();

	public List<String> getFetches();

	/**
	 * Result mode tells the search what form to use for the results. Options
	 * include <code>RESULT_AUTO</code>, <code>RESULT_ARRAY</code>, <code>
	 * RESULT_LIST</code>
	 * , <code>RESULT_MAP</code> and <code>RESULT_SINGLE
	 * </code>.
	 * 
	 * @see #RESULT_AUTO
	 * @see #RESULT_ARRAY
	 * @see #RESULT_LIST
	 * @see #RESULT_MAP
	 * @see #RESULT_SINGLE
	 */
	public int getResultMode();

}
