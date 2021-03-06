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

public class OWNComplement extends OWNAxiom{
	/**
	 * Assumed that OWNAxioms will always be created from NNF, so a complement only
	 * complements an atom, not a whole axiom
	 */
	private OWNAtom operand;
	
	/**
	 * Not null arguments assumed
	 * @param op
	 */
	public OWNComplement(OWNAtom op) {
		super();
		this.operand = op;
		this.type = AXIOM_TYPE.COMPLEMENT;
	}
	
	public OWNAtom getOperand() {
		return this.operand;
	}

	@Override
	public String toString() {
		return StringAxiomConstants.OP_COMPLEMENT + operand;
		//return "OWNComplement [operand=(" + operand + ")]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ operand.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNComplement) {
			OWNComplement complement = (OWNComplement)other;
			return type == complement.type && operand.equals(complement.operand);
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}

}
