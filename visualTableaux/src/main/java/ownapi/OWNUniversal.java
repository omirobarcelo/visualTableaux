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

public class OWNUniversal extends OWNAxiom{
	/**
	 * Assumed that relations will always be literals
	 */
	private OWNAtom relation;
	private OWNAxiom operand;
	
	/**
	 * Not null arguments assumed
	 * @param relation
	 * @param op
	 */
	public OWNUniversal(OWNAtom relation, OWNAxiom op) {
		super();
		this.relation = relation;
		this.operand = op;
		super.type = AXIOM_TYPE.UNIVERSAL;
	}
	
	public OWNAtom getRelation() {
		return relation;
	}
	
	public OWNAxiom getOperand() {
		return operand;
	}
	
	@Override
	public String toString() {
		return StringAxiomConstants.OP_UNIVERSAL + relation + "." + (operand.isLiteral() ? operand : "("+operand+")");
		//return "OWNUniversal [relation=(" + relation + "), operand=(" + operand1 + ")]";
	}
	
	@Override
	public int hashCode() {
		return type.hashCode() ^ relation.hashCode() ^ operand.hashCode();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof OWNUniversal) {
			OWNUniversal universal = (OWNUniversal)other;
			return type == universal.type && relation.equals(universal.relation) && operand.equals(universal.operand);
		}
		return false;
	}
	
	public void accept(OWNAxiomVisitor visitor) {
		visitor.visit(this);
	}
}