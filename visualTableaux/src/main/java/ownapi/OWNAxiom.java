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

import ver1.util.StringAxiomConstants;

/**
 * Parent class for specific OWNAxiom types
 */
public class OWNAxiom {
	public enum AXIOM_TYPE {TOP, BOTTOM, ATOM, DISJUNCTION, CONJUNCTION, EXISTENTIAL, UNIVERSAL, COMPLEMENT}
	
	public static final OWNAxiom TOP = new OWNAxiom(true);
	public static final OWNAxiom BOTTOM = new OWNAxiom(false);
	
	protected AXIOM_TYPE type;
	
	/**
	 * Constructor only created to be able to create related objects
	 */
	public OWNAxiom() {}
	
	/**
	 * Constructor only created to build the constant axioms TOP and BOTTOM
	 * @param isTop true for constant TOP, false for constant BOTTOM
	 */
	private OWNAxiom(boolean isTop) {
		this.type = isTop ? AXIOM_TYPE.TOP : AXIOM_TYPE.BOTTOM;
	}
	
	/**
	 * Assumed that will only be called from related created objects (i.e.: set of OWNAxiom
	 * filled with OWNDisjunction, OWNAtom, etc.) 
	 * @return Enum type AXIOM_TYPE given upon object creation
	 */
	public AXIOM_TYPE getType() {
		return type;
	}
	
	/**
	 * @return true if is a literal axiom (OWNAtom or OWNComplement)
	 */
	public boolean isLiteral() {
		return this.type == AXIOM_TYPE.ATOM || this.type == AXIOM_TYPE.COMPLEMENT;
	}
	
	/**
	 * @return true if is the Top axiom
	 */
	public boolean isTop() {
		return this.type == AXIOM_TYPE.TOP;
	}
	
	/**
	 * @return true if is the Bottom axiom
	 */
	public boolean isBottom() {
		return this.type == AXIOM_TYPE.BOTTOM;
	}
	
	/**
	 * Checks if OWNAxiom is of specified type
	 * @param type
	 * @return 
	 */
	public boolean isOfType(AXIOM_TYPE type) {
		return (this.type == type);
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
	
	@Override
	public String toString() {
		if (this.isTop())
			return StringAxiomConstants.OP_TOP;
		else if (this.isBottom())
			return StringAxiomConstants.OP_BOTTOM;
		return "";
	}
}
