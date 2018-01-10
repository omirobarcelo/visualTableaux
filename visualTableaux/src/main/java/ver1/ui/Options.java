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

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import ver1.Node;
import ver1.Operation;
import ver1.util.Pair;
import ver1.util.StringAxiomConstants;

public class Options extends JPanel implements ActionListener, MouseListener {
	private static final int BORDER = 10;
	
	private static final int BUTTON_WIDTH = 270;
	private static final int BUTTON_HEIGHT = 60;
	private static final int MAX_STRING_SIZE = 25;
	
	private static final String PATH_DEF_FONT = "fonts"+File.separator+"unifont.ttf";
	
	private List<JButton> jbList;
	
	public Options() {		
		jbList = new ArrayList<JButton>();
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		this.setBorder(BorderFactory.createEmptyBorder(BORDER, BORDER, BORDER, BORDER));
	}
	
	/**
	 * NOT UPDATED. LEFT IN CASE IS NECESSARY IN THE FUTURE
	 * Sets all the possible operations, and returns a list of pairs <node, operation>, 
	 * so when GUI.execOption is called, we can pass the index from the returned list
	 * @param options Map of node and its applicable operations
	 * @param nextCreatedNode The id of the node that would be created next, necessary for Existential operations
	 * @return
	 */
	public List<Pair<Node, Operation>> setOptions(Map<Node, HashSet<Operation>> options, 
												  String nextCreatedNode) {
		jbList.clear();
		this.removeAll();
		
		List<Pair<Node, Operation>> ops = new ArrayList<Pair<Node, Operation>>();
		for (Node n : options.keySet()) {
			JLabel jlNode = new JLabel("Node " + n.getId());
			this.add(jlNode);
			for (Operation op : options.get(n)) {
				ops.add(new Pair<Node, Operation>(n, op));
				JButton jbOp = new JButton(transformText(op.fullString(n, nextCreatedNode)));
				// Needed both minimum and maximum size to make the button use all the 
				// panel space, plus preferredSize because Java layout is a bitch
				jbOp.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
				// With preferredSize button doesn't adapt to the text,
				// but the horizontal size now changes a bit,
				// because Java pretty much ignores min/max without preferred
				jbOp.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT+30));
				jbOp.setHorizontalAlignment(SwingConstants.CENTER);
				jbOp.addActionListener(this);
				jbList.add(jbOp);
				this.add(jbOp);
			}

		}
		// To make everything stick to the top
		this.add(Box.createVerticalGlue());
		
		this.revalidate();
		this.repaint();
		
		return ops;
	}
	
	/**
	 * Sets possible operations of node n, and returns a list of pairs <node, operation>, 
	 * so when GUI.execOption is called, we can pass the index from the returned list
	 * @param options Map of node and its applicable operations
	 * @param nextCreatedNode The id of the node that would be created next, necessary for Existential operations
	 * @param n
	 * @return
	 */
	public List<Pair<Node, Operation>> setOptions(Map<Node, HashSet<Operation>> options, 
			String nextCreatedNode, Node n) {
		jbList.clear();
		this.removeAll();

		List<Pair<Node, Operation>> ops = new ArrayList<Pair<Node, Operation>>();
		// If a node has been selected
		if (n != null) {
			JLabel jlNode = new JLabel("Node " + n.getId());
			this.add(jlNode);
			if (options.containsKey(n)) {
				for (Operation op : options.get(n)) {
					ops.add(new Pair<Node, Operation>(n, op));
					OptionButton jbOp = new OptionButton(transformText(op.fullString(n, nextCreatedNode)), op);
					// Set font if not Mac OS system
					if (!System.getProperty("os.name").toLowerCase().contains("mac")) {
						try {
							Font f  = Font.createFont(Font.TRUETYPE_FONT, new File(PATH_DEF_FONT));
							jbOp.setFont(f.deriveFont(13F));
							jbOp.revalidate();
						} catch (FontFormatException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}	
					}
					// Needed both minimum and maximum size to make the button use all the 
					// panel space, plus preferredSize because Java layout is a bitch
					jbOp.setMinimumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
					// With preferredSize button doesn't adapt to the text,
					// but the horizontal size now changes a bit,
					// because Java pretty much ignores min/max without preferred
					jbOp.setMaximumSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT+30));
					jbOp.setHorizontalAlignment(SwingConstants.CENTER);
					jbOp.addActionListener(this);
					jbOp.addMouseListener(this);
					//jbOp.revalidate();
					jbList.add(jbOp);
					this.add(jbOp);
					//System.out.println(jbOp.getFont().getFontName() + ", " + jbOp.getFont().getSize());
				}
			}
		}

		// To make everything stick to the top
		this.add(Box.createVerticalGlue());

		this.revalidate();
		this.repaint();

		return ops;
	}
	
	public void clearOptions() {
		jbList.clear();
		this.removeAll();
		this.revalidate();
		this.repaint();
	}
	
	/**
	 * Transform the button text to fit
	 * @param s
	 * @return
	 */
	private String transformText(String s) {
		// TODO take out HTML wrapper if it doesn't make it work in other systems
		String t = "<html><center>" + s + "</center></html>";
		String[] splitted = s.split(StringAxiomConstants.ARROW_RIGHT);
		// Line breaks have to be done in HTML, and also centered in HTML
		if (s.length() > MAX_STRING_SIZE) {
			t = "<html><center>" + 
					splitted[0].trim() + 
					"<br />" + StringAxiomConstants.ARROW_DOWN + "<br />" + 
					splitted[1].trim() + 
				"</center></html>";
		}
		return t;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton button = (JButton)e.getSource();
		int index = jbList.indexOf(button);
		// Get GUI where this component lives and execute option
		((GUI)SwingUtilities.getWindowAncestor(this)).execOption(index);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		((GUI)SwingUtilities.getWindowAncestor(this)).setHighlightAxiom(((OptionButton)e.getSource()).getOperation(), true);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		((GUI)SwingUtilities.getWindowAncestor(this)).setHighlightAxiom(((OptionButton)e.getSource()).getOperation(), false);
	}
}
