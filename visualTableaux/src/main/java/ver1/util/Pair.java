/*
 * Licensed to the Apache Software Foundation (ASF) under one
or more contributor license agreements.  See the NOTICE file
distributed with this work for additional information
regarding copyright ownership.  The ASF licenses this file
to you under the Apache License, Version 2.0 (the
"License"); you may not use this file except in compliance
with the License.  You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing,
software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
KIND, either express or implied.  See the License for the
specific language governing permissions and limitations
under the License.
 */

package ver1.util;

public class Pair<F, S> {
    private F first; //first member of pair
    private S second; //second member of pair

    /**
     * Assumed not null arguments
     * @param first
     * @param second
     */
    public Pair(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public void setFirst(F first) {
        this.first = first;
    }

    public void setSecond(S second) {
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
    
    @Override
    public String toString() {
    	return "<" + first + ", " + second + ">";
    }
    
    @Override
    public int hashCode() {
    	return first.hashCode() ^ second.hashCode();
    }
    
    @Override
    public boolean equals(Object other) {
    	if (other instanceof Pair) {
    		Pair<?, ?> p = (Pair<?, ?>)other;
    		return first.equals(p.first) && second.equals(p.second);
    	}
    	return false;
    }
}
