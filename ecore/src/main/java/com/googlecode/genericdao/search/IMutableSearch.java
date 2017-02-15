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
 * <code>IMutableSearch</code> is an extension of <code>ISearch</code> that
 * provides setters for all of the properties.
 * 
 * @author dwolverton
 * 
 */
public interface IMutableSearch extends ISearch {
	public IMutableSearch setFirstResult(int firstResult);

	public IMutableSearch setMaxResults(int maxResults);

	public IMutableSearch setPage(int page);

	public IMutableSearch setSearchClass(Class<?> searchClass);

	public IMutableSearch setFilters(List<Filter> filters);

	public IMutableSearch setDisjunction(boolean disjunction);

	public IMutableSearch setSorts(List<Sort> sorts);

	public IMutableSearch setFields(List<Field> fields);
	
	public IMutableSearch setDistinct(boolean distinct);

	public IMutableSearch setFetches(List<String> fetches);

	public IMutableSearch setResultMode(int resultMode);
}
