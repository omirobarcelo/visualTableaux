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

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import ownapi.*;
import ver1.*;
import ver1.util.*;

public class TUI {
	private static final int END_PROGRAM = 0;

	public static void main(String[] args) {
		// Load files
		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
		IRI conceptIRI = IRI.create(new File("ontologies/testConcept.owl"));
		IRI tboxIRI = IRI.create(new File("ontologies/testB3TBox.owl"));
		
		OWLOntology concept = null;
		OWLOntology tbox = null;
		try {
			concept = man.loadOntology(conceptIRI);
			tbox = man.loadOntology(tboxIRI);
		} catch (OWLOntologyCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Get concept and ontology K
		Pair<OWNAxiom, HashSet<OWNAxiom>> pairCon_K = Interpreter.read(concept, tbox);
		
		// Create and initialize tableau
		Tableau tableau = new Tableau(pairCon_K.getSecond(), false);
		tableau.init(pairCon_K.getFirst());
				
		// Loop (show status, choose operations, execute operation)
		boolean endProgram = false; // true when 'exit program' option selected
		boolean finished = false; // true when the tableau expansion finished
		boolean isSatisfiable = false; // true if the tableau results satisfiable
		while (!endProgram && !finished) {
			showStatus(tableau);
			// Returns a list so the option can easily be selected
			List<Pair<Node, Operation>> ops = showOperations(tableau);
			int option = readIntFromStdin("\nWhich option do you select? ");
			if (option == END_PROGRAM)
				endProgram = true;
			else {
				// option starts from 1, so necessary to subtract 1 to get it from the list
				Pair<Node, Operation> pNode_Op = ops.get(option-1);
				// Blocking automatically applied
				tableau.apply(pNode_Op.getFirst(), pNode_Op.getSecond());
				
				// Backtracking automatically applied
				if (finished = tableau.isFinished()) {
					isSatisfiable = tableau.isSatisfiable();
				}
			}
		}
		
		if (finished) {
			showStatus(tableau);
			System.out.println("This ontology is " +
					(isSatisfiable ? "" : "not ") + "satisfiable");
		}
	}
	
	/**
	 * Prints the tableau current status
	 * Includes ontology, L(n) for every n, if it's blocked, 
	 * and its relations with other nodes
	 * @param tableau
	 */
	private static void showStatus(Tableau tableau) {
		System.out.println();
		System.out.println("K : " + tableau.getOntology().toString());
		System.out.println();
		tableau.iterativePreorder(tableau.getFirstNode(), "printNodeStatus");
		System.out.println();
	}

	/**
	 * Print possible options and return them in list format
	 * Options start from 1. Last option is END PROGRAM
	 * @param tableau
	 * @return
	 */
	private static List<Pair<Node, Operation>> showOperations(Tableau tableau) {
		List<Pair<Node, Operation>> ops = new ArrayList<Pair<Node, Operation>>();
		Map<Node, HashSet<Operation>> operations = tableau.getOperations();
		System.out.println();
		int option = 1;
		for (Node n : operations.keySet()) {
			System.out.println(n);
			for (Operation op : operations.get(n)) {
				ops.add(new Pair<Node, Operation>(n, op));
				System.out.println("\t" + option++ + ". " + op.fullString(n, tableau.checkNextCreatedNode()));
			}
		}
		System.out.println(END_PROGRAM + ". Exit program");
		System.out.println();
		return ops;
	}
	
	private static int readIntFromStdin(String msg) {
		int number = 0;
        try {
            java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            System.out.print(msg);
            number = Integer.parseInt(in.readLine());
        } catch (Exception e) {System.err.println("\n"+e.toString());}
        return number;
	}
}
