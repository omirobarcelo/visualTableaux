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

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import ver1.util.Pair;

public class GraphOntology {
	private static final Color COL_FONT = Color.BLACK;
	private static final Color COL_LINE = Color.BLACK;
	private static final Color COL_HL = Color.YELLOW;
	private static final String SEP = ", ";
	private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 18);
	private static final String PATH_DEF_FONT = "fonts"+File.separator+"unifont.ttf";
	private static final int X_MARGIN = 10;
	private static final int Y_MARGIN = 5;
	
	private GraphAxiom[] axioms;
	private Color colFont, colLine, colHL;
	private Font font;
	
	public GraphOntology(String[] axioms) {
		this.axioms = new GraphAxiom[axioms.length];
		int i = 0;
		for (String s : axioms) 
			this.axioms[i++] = new GraphAxiom(s);
		
		this.colFont = COL_FONT;
		this.colLine = COL_LINE;
		this.colHL = COL_HL;
		
		Font f = null;
		try {
			f = Font.createFont(Font.TRUETYPE_FONT, new File(PATH_DEF_FONT));
		} catch (FontFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (System.getProperty("os.name").toLowerCase().contains("mac"))
			this.font = FONT;
		else
			this.font = f.deriveFont(18F);
	}
	
	
	public GraphAxiom[] getGraphAxioms() {
		return axioms;
	}
	
	/**
	 * Merges the previous GraphAxiom to the current ones
	 * Check if a previous GraphAxiom is equal to a current one and copies the state of the previous
	 * Necessary because while painting Graph, the GraphOntology is renewed, and the previous GraphAxiom
	 * with the correct highlighting, erased
	 * @param prevAxioms
	 */
	public void mergeGraphAxioms(GraphAxiom[] prevAxioms) {
		for (int i = 0; i < prevAxioms.length; i++) {
			for (int j = 0; j < axioms.length; j++) {
				if (prevAxioms[i].getText().equals(axioms[j].getText()))
					axioms[j].setHighlight(prevAxioms[i].isHighligthed());
			}
		}
	}
	
	/**
	 * Sets the highlight of all of its GraphAxiom to false
	 */
	public void clearHighlight() {
		for (GraphAxiom ga : axioms) {
			ga.setHighlight(false);
		}
	}
	
	/**
	 * Sets the highlight of the GraphAxiom with text axiom to state
	 * @param axiom
	 * @param state
	 */
	public void setHighlight(String axiom, boolean state) {
		for (GraphAxiom ga : axioms) {
			if (ga.getText().equals(axiom)) {
				//highlighted = state ? axiom : "";
				ga.setHighlight(state);
			}
		}
	}
	
	
	/**
	 * Paints ontology and returns a pair with (ontologyLength, maxWidthOfAxiom)
	 * @param g
	 * @return
	 */
	public Pair<Integer, Integer> paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		
		// length -> How long is the drawn ontology, maxWidth -> Longest axiom
		int length = 0, maxWidth = 0;
		
		// Get axioms text and highlighting, if any
		String text = "";
		for (int i = 0; i < axioms.length; i++) {
			if (axioms[i].isHighligthed()) {
				g2d.setPaint(colHL);
				g2d.fillRect(X_MARGIN+fm.stringWidth(text), Y_MARGIN, fm.stringWidth(axioms[i].getText()), fm.getHeight());
			}

			maxWidth = Math.max(maxWidth, fm.stringWidth(axioms[i].getText()+SEP));
			
			text += axioms[i].getText() + (i+1 < axioms.length ? SEP : "");;
		}
		
		// Write K
		g2d.setPaint(colFont);
		g2d.drawString(text, X_MARGIN, Y_MARGIN+fm.getHeight());
		
		// Bottom line
		g2d.setPaint(colLine);
		length = X_MARGIN+fm.stringWidth(text)+X_MARGIN;
		g2d.drawLine(0, Y_MARGIN+fm.getHeight()+Y_MARGIN, length, Y_MARGIN+fm.getHeight()+Y_MARGIN);
		
		// Side line
		g2d.drawLine(length, 0, length, Y_MARGIN+fm.getHeight()+Y_MARGIN);
		
		return new Pair<Integer, Integer>(length, maxWidth);
	}
}
