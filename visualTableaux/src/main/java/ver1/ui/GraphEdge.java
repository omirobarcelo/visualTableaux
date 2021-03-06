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
import java.awt.Point;
import java.io.File;
import java.io.IOException;

public class GraphEdge {
	private static final Color COL_LINE = Color.BLUE;
	private static final Color COL_FONT = Color.BLACK;
	private static final Color COL_HL = Color.YELLOW;
	private static final String SEP = ", ";
	private static final Font FONT = new Font(Font.MONOSPACED, Font.PLAIN, 16);
	private static final String PATH_DEF_FONT = "fonts"+File.separator+"unifont.ttf";
	private static final int STD_MARGIN = 5;
	private static final int PROPORTION = 25;
	
	private GraphAxiom[] relations;
	private Point pos1, pos2;
	private Color colLine, colFont, colHL;
	private Font font;
	
	public GraphEdge(String[] relations, Point pos1, Point pos2) {
		this.relations = new GraphAxiom[relations.length];
		int i = 0;
		for (String s : relations) 
			this.relations[i++] = new GraphAxiom(s);
		
		this.pos1 = pos1;
		this.pos2 = pos2;
		
		this.colLine = COL_LINE;
		this.colFont = COL_FONT;
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
			this.font = f.deriveFont(16F);
	}
	
	public void paint(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setFont(font);
		FontMetrics fm = g2d.getFontMetrics();
		
		// Draw line
		g2d.setPaint(colLine);
		g2d.drawLine(pos1.x, pos1.y, pos2.x, pos2.y);
		
		// Get relations text and highlighting, if any
		String text = "";
		int offsetHL = -1, width = -1; // offset respect the String beginning and the width of highlighted axiom, if any
		for (int i = 0; i < relations.length; i++) {
			if (relations[i].isHighligthed()) {
				width = fm.stringWidth(relations[i].getText());
				offsetHL = fm.stringWidth(text);
			}
			
			text += relations[i].getText() + (i+1 < relations.length ? SEP : "");;
		}
		
		// Locate text
		int x = 0;
		if (pos1.x < pos2.x)
			x = pos1.x + (pos2.x-pos1.x)/2 + STD_MARGIN*(pos2.x-pos1.x)/PROPORTION; // Separate from edge proportional to the width of the edge
		else if (pos1.x > pos2.x)
			x = pos1.x - (pos1.x-pos2.x)/2 - fm.stringWidth(text) - STD_MARGIN*(pos1.x-pos2.x)/PROPORTION; // Separate from edge proportional to the width of the edge
		else
			x = pos1.x - (pos1.x-pos2.x)/2 - fm.stringWidth(text) - STD_MARGIN;
		int y = pos1.y + (pos2.y-pos1.y)/2 + fm.getHeight()/2;
		
		// If some relation is highlighted
		if (offsetHL != -1 && width != -1) {
			g2d.setPaint(colHL);
			g2d.fillRect(x+offsetHL, y-fm.getHeight(), width, fm.getHeight());
		}
		
		// Write text
		g2d.setPaint(colFont);
		g2d.drawString(text, x, y);
	}
}
