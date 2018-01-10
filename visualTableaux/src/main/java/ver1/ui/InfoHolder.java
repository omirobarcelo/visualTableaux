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

import java.awt.Point;

import ver1.util.Pair;

/**
 * Stores information of GraphNode
 * @author Oriol Miro-Barcelo
 *
 */
public class InfoHolder {
	private Point topRight; // x, y coordinates of the top right corner
	private int leaves; // number of leaves
	private Pair<Integer, Integer> workspace; // horizontal workspace; workspace defines the space where the node and its leaves can be painted
	
	public InfoHolder() {}

	public Point getTopRight() {
		return topRight;
	}

	public void setTopRight(Point topRight) {
		this.topRight = topRight;
	}

	public int getLeaves() {
		return leaves;
	}

	public void setLeaves(int leaves) {
		this.leaves = leaves;
	}

	public Pair<Integer, Integer> getWorkspace() {
		return workspace;
	}

	/**
	 * 
	 * @param wsStart Pixel where workspace starts
	 * @param wsEnd Pixel where workspace ends
	 */
	public void setWorkspace(int wsStart, int wsEnd) {
		this.workspace = new Pair<Integer, Integer>(wsStart, wsEnd);
	}
	
	
}
