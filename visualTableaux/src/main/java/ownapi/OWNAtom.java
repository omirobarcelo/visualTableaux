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

package ownapi;

public class OWNAtom extends OWNAxiom {
	private String fullName;
	
	/**
	 * Not blank string or without ontology#name structure assumed
	 * @param fullName
	 */
	public OWNAtom(String fullName) {
		super();
		this.fullName = fullName;
		super.type = AXIOM_TYPE.ATOM;
	}

	/**
	 * Assumed that the full name of an atom is ontology#name
	 * @return string with the name
	 */
	public String getId() {
		return fullName.split("#")[1];
	}
	
	public String getFullName() {
		return fullName;
	}

	@Override
	public String toString() {
		return getId();
		//return "OWNAtom [Id=" + getId() + "]";
	}
	
	/**
	 * Use of getId() instead of fullName so axioms from different ontologies that
	 * are actually equal, remain equal when compared or inserted into a set or list
	 */
	@Override
	public int hashCode() {
		//return type.hashCode() ^ fullName.hashCode();
		return type.hashCode() ^ getId().hashCode();
	}
	
	/**
	 * Use of getId() instead of fullName so axioms from different ontologies that
	 * are actually equal, remain equal when compared or inserted into a set or list
	 */
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNAtom) {
			OWNAtom atom = (OWNAtom)other;
			//return type == literal.type && fullName.equals(literal.fullName);
			return type == atom.type && getId().equals(atom.getId());
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}
