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
package com.googlecode.genericdao.search.flex;

import com.googlecode.genericdao.search.Field;
import com.googlecode.genericdao.search.Filter;
import com.googlecode.genericdao.search.ISearch;
import com.googlecode.genericdao.search.Sort;

import java.util.List;

/**
 * This provides a wrapper around a FlexSearch so that is can be passed into DAO
 * methods requiring the ISearch interface.
 * 
 * @author dwolverton
 * 
 */
public class FlexSearchWrapper implements ISearch {

	FlexSearch search;

	public FlexSearchWrapper(FlexSearch flexSearch) {
		search = flexSearch;
	}

	public List<String> getFetches() {
		return search.fetches;
	}

	public List<Field> getFields() {
		return search.fields;
	}

	public List<Filter> getFilters() {
		return search.filters;
	}

	public int getFirstResult() {
		return search.firstResult;
	}

	public int getMaxResults() {
		return search.maxResults;
	}

	public int getPage() {
		return search.page;
	}

	public int getResultMode() {
		return search.resultMode;
	}

	public Class<?> getSearchClass() {
		if (search.searchClassName == null || "".equals(search.searchClassName)) {
			return null;
		} else {
			try {
				return Class.forName(search.searchClassName);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

	public List<Sort> getSorts() {
		return search.sorts;
	}

	public boolean isDisjunction() {
		return search.disjunction;
	}

	public boolean isDistinct() {
		return search.distinct;
	}
}
