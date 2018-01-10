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

package ver1.ui;

/**
 * Only holds the information of the GraphAxiom, does not paint it
 * @author Oriol Miro-Barcelo
 *
 */
public class GraphAxiom {
	
	private String axiom;
	private boolean highlighted;
	
	public GraphAxiom(String text) {
		axiom = text;
		highlighted = false;
	}
	
	public String getText() {
		return axiom;
	}
	
	public boolean isHighligthed() {
		return highlighted;
	}
	
	public void setHighlight(boolean state) {
		highlighted = state;
	}
}
