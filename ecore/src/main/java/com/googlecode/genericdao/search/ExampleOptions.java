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

import java.util.ArrayList;
import java.util.Collection;

/**
 * Use this class to pass options to find by example functionality.
 * 
 * @author dwolverton
 */
public class ExampleOptions {
	public final static int EXACT = 0;
	public final static int START = 1;
	public final static int END = 2;
	public final static int ANYWHERE = 3;

	private Collection<String> excludeProps;
	private int likeMode = EXACT;
	private boolean excludeNulls = true;
	private boolean excludeZeros = false;
	private boolean ignoreCase = false;

	/**
	 * Add a property to the excludeProps collection
	 */
	public ExampleOptions excludeProp(String property) {
		if (excludeProps == null)
			excludeProps = new ArrayList<String>();
		excludeProps.add(property);
		return this;
	}

	public Collection<String> getExcludeProps() {
		return excludeProps;
	}

	/**
	 * <p>This is a list of properties to exclude. For example if a person object is
	 * as an example and it is not desirable to filter on the person's parents, the
	 * mother and father properties can be excluded by setting this list to contain
	 * the strings "mother" and "father".
	 * 
	 * <p>Default: &lt;none&gt;
	 */
	public ExampleOptions setExcludeProps(Collection<String> excludeProps) {
		this.excludeProps = excludeProps;
		return this;
	}

	public boolean isExcludeNulls() {
		return excludeNulls;
	}

	/**
	 * <p>If this is true, all properties with <code>null</code> values will be ignored.
	 * 
	 * <p>Default: true
	 */
	public ExampleOptions setExcludeNulls(boolean excludeNulls) {
		this.excludeNulls = excludeNulls;
		return this;
	}

	public boolean isExcludeZeros() {
		return excludeZeros;
	}

	/**
	 * <p>If this is true, all properties with the value <code>0</code> will be ignored.
	 * 
	 * <p>Default: false
	 */
	public ExampleOptions setExcludeZeros(boolean excludeZeros) {
		this.excludeZeros = excludeZeros;
		return this;
	}

	public boolean isIgnoreCase() {
		return ignoreCase;
	}

	/**
	 * <p>If this is true, case is ignored when comparing string values.
	 * 
	 * <p>Default: true
	 */
	public ExampleOptions setIgnoreCase(boolean ignoreCase) {
		this.ignoreCase = ignoreCase;
		return this;
	}

	public int getLikeMode() {
		return likeMode;
	}

	/**
	 * <p>This options describes how all string values are compared. The options are:
	 * <ul>
	 * 	<li>EXACT: The entire strings must match entirely.
	 *  <li>START: The value must start with the example string.
	 *  <li>END: The value must end with the example string.
	 *  <li>ANYWHERE: The value may contain the example string anywhere.
	 * </ul>
	 * 
	 * <p>Default: EXACT
	 */
	public ExampleOptions setLikeMode(int likeMode) {
		this.likeMode = likeMode;
		return this;
	}
}
