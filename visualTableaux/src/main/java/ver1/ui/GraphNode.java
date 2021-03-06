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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.io.File;
import java.io.IOException;

import ownapi.OWNAxiom;
import ownapi.OWNComplement;
import ver1.Node;

public class GraphNode {
	
	private static final String SEP = ", ";
	private static final Color COL_BG = Color.GRAY;
	private static final Color COL_FONT = Color.BLACK;
	private static final Color COL_LINE = Color.BLACK;
	private static final Color COL_HL = Color.YELLOW;
	private static final Font FONT_NAME = new Font(Font.MONOSPACED, Font.PLAIN, 16);
	private static final Font FONT_TEXT = new Font(Font.MONOSPACED, Font.PLAIN, 14);
	private static final String PATH_DEF_FONT = "fonts"+File.separator+"unifont.ttf";
	private static final int STD_MARGIN = 5;
	
	private static final int MIN_WIDTH = 200;
	
	// static because all the GraphNode share the same width
	private static int width = MIN_WIDTH;
	
	private Node node;
	private GraphAxiom[] axioms;
	private int height;
	// Top-left corner
	private Point position;
	// Background color
	private Color colBG;
	// Font color
	private Color colFont;
	// Line color
	private Color colLine;
	// Highlight color
	private Color colHL;
	private Font nameFont, textFont;
	private boolean blocked;
	private boolean bolded;
		
	public GraphNode(Node node, String[] axioms, int x, int y) {
		this.node = node;
		this.axioms = new GraphAxiom[axioms.length];
		int i = 0;
		for (String s : axioms) 
			this.axioms[i++] = new GraphAxiom(s);
		this.height = 0;
		position = new Point(x, y);
		
		colBG = COL_BG;
		colFont = COL_FONT;
		colLine = COL_LINE;
		colHL = COL_HL;
		
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
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			nameFont = FONT_NAME;
			textFont = FONT_TEXT;
		} else {
			nameFont = f.deriveFont(16F);
			textFont = f.deriveFont(14F);
		}
		
		blocked = false;
		bolded = false;
	}
	
	
	public Node getNode() {
		return node;
	}
	
	public static int getWidth() {
		return width;
	}
	
	/**
	 * Sets widths to the given width if it's larger than the minimum
	 * @param width
	 */
	public static void setWidth(int width) {
		GraphNode.width = width < MIN_WIDTH ? MIN_WIDTH : width;
	}
	
	/**
	 * Returns the height corresponding to a new creathed GraphNode: the borders, the node name, and 1 GraphAxiom
	 * @param g Necessary to obtain FontMetrics
	 * @return
	 */
	public static int getStdHeight(Graphics g) {
		int stdHeight = 0;
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(FONT_NAME);
		stdHeight += g2d.getFontMetrics().getHeight();
		stdHeight += STD_MARGIN;
		g2d.setFont(FONT_TEXT);
		stdHeight += g2d.getFontMetrics().getHeight();
		stdHeight += STD_MARGIN;
		return stdHeight;
	}
	
	/**
	 * Current height of the GraphNode
	 * @return
	 */
	public int getHeight() {
		return height;
	}
	
	public void toggleBlocked() {
		blocked = !blocked;
	}
	
	public void setBolded(boolean state) {
		bolded = state;
	}
	
	public boolean isBolded() {
		return bolded;
	}
	
	public GraphAxiom[] getGraphAxioms() {
		return axioms;
	}
	
	/**
	 * Merges the previous GraphAxiom to the current ones
	 * Check if a previous GraphAxiom is equal to a current one and copies the state of the previous
	 * Necessary because while painting Graph, all GreaphNode are renewed, and the previous GraphAxiom
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
	 * Sets the highlight of the GraphAxiom with corresponding to ax to state
	 * @param ax
	 * @param state
	 */
	public void setHighlight(OWNAxiom ax, boolean state) {
		for (GraphAxiom ga : axioms) {
			// If is Complement, then also highlight its literal, since we're highlighting a BOTTOM operation
			if (ax.isOfType(OWNAxiom.AXIOM_TYPE.COMPLEMENT)) {
				if (ga.getText().equals(ax.toString()) || ga.getText().equals(((OWNComplement)ax).getOperand().toString())) {
					ga.setHighlight(state);
				}
			} else {
				if (ga.getText().equals(ax.toString())) {
					ga.setHighlight(state);
				}
			}
		}
	}
	
	
	/**
	 * Checks if coordinates x and y correspond to the GraphNode
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean isGraphNode(int x, int y) {
		boolean xIn = (x >= position.x) && (x <= position.x + width);
		boolean yIn = (y >= position.y) && (y <= position.y + height);
		return xIn && yIn;
	}
	
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setStroke(new BasicStroke(bolded ? 5.0f : 3.0f));
		
		g2d.setFont(nameFont);
		FontMetrics fm = g2d.getFontMetrics();
		int fontHeight = fm.getHeight();
		// If blocked, color background of node name
		if (blocked) {
			g2d.setPaint(colBG);
			g2d.fillRect(position.x, position.y, width, fontHeight+STD_MARGIN);
		}
		// Upper line
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y, position.x+width, position.y);
		// Node name
		g2d.setPaint(colFont);
		int nodeWidth = fm.stringWidth(node.getId());
		g2d.drawString(node.getId(), position.x+(width/2-nodeWidth/2), position.y+fontHeight); // WIDTH/2-nodeWidth/2 -> Centers the text
		// Middle line
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y+fontHeight+STD_MARGIN, position.x+width, position.y+fontHeight+STD_MARGIN);
		// Axioms
		g2d.setFont(textFont);
		fm = g2d.getFontMetrics();
		fontHeight = fm.getHeight();
		int height = fontHeight + STD_MARGIN; // height of the box; each text line height is going to be added
		String text = "";
		int xCursor = position.x+STD_MARGIN, yCursor = position.y+height+STD_MARGIN; // cursor of the text, to save each axiom position
		for (int i = 0; i < axioms.length; i++) {
			// If last line longer than the box, add a new line
			String lastLine = text.split("\n")[text.split("\n").length-1];
			if (fm.stringWidth(lastLine+axioms[i].getText()+SEP) > width-STD_MARGIN*2) {
				text += "\n";
				yCursor += fontHeight; // update yCursor
				xCursor = position.x+STD_MARGIN; // reset xCursor
			}
			
			if (axioms[i].isHighligthed()) {
				g2d.setPaint(colHL);
				g2d.fillRect(xCursor, yCursor, fm.stringWidth(axioms[i].getText()), fontHeight);
			}
			
			text += axioms[i].getText() + (i+1 < axioms.length ? SEP : "");
			
			xCursor += fm.stringWidth(axioms[i].getText() + SEP); // Even though the last axiom doesn't add ', ', xCursor is not used after it, so it doesn't matter
		}
		// Write each line
		g2d.setPaint(colFont);
		for (String line : text.split("\n")) {
			g2d.drawString(line, position.x+STD_MARGIN, position.y+height+fontHeight);
			height += fontHeight;
		}
		// Save GraphNode total height
		this.height = height + STD_MARGIN;
		// Bottom line
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y+this.height, position.x+width, position.y+this.height);
		// Side lines
		g2d.setPaint(colLine);
		g2d.drawLine(position.x, position.y, position.x, position.y+this.height);
		g2d.drawLine(position.x+width, position.y, position.x+width, position.y+this.height);
	}
}
