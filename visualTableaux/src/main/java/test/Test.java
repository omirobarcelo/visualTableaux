package test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

import uk.ac.manchester.cs.owl.owlapi.OWLClassExpressionImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectComplementOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectIntersectionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectSomeValuesFromImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLObjectUnionOfImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLSubClassOfAxiomImpl;

import ver1.*;
import ver1.Operation.OPERATOR;
import ownapi.*;
import ownapi.OWNAxiom.AXIOM_TYPE;

public class Test {

	public static void main(String[] args) {
		ArrayList<Node> list1 = new ArrayList<Node>();
		list1.add(new Node("a"));
		list1.add(new Node("b"));
		ArrayList<Node> list2 = new ArrayList<Node>();
		list2.add(new Node("a"));
		list2.add(new Node("b"));
		System.out.println(list1.equals(list2));
		
		TreeNode n = new TreeNode(new Node("n"));
		n.addChild(new Node("ñ"));
		TreeNode o = new TreeNode(new Node("o"));
		o.addChild(new Node("p"));
		o.addChild(new Node("q"));
		TreeNode firstNode = new TreeNode(new Node("m"));
		firstNode.addChild(n);
		firstNode.addChild(o);
		
		System.out.println(TreeNode.getTreeNode(firstNode, new Node("ñ")).getData());
		
		TreeNode n2 = new TreeNode(new Node("n"));
		n2.addChild(new Node("ñ"));
		TreeNode o2 = new TreeNode(new Node("o"));
		o2.addChild(new Node("p"));
		o2.addChild(new Node("q"));
		TreeNode firstNode2 = new TreeNode(new Node("m"));
		firstNode2.addChild(n2);
		firstNode2.addChild(o2);
		
		System.out.println(firstNode.equals(firstNode2));
		
		Stack<TreeNode> s = new Stack<TreeNode>();
		s.push(firstNode2);
		while (!s.isEmpty()) {
			TreeNode tn = s.pop();
			//visit(node)
			System.out.println(tn.getData().getId() + " : " + "[L]");
			if (!tn.getChildren().isEmpty()) {
				for (TreeNode succ : tn.getChildren()) {
					System.out.println(tn.getData().getId() + "--" + "[R]" + "--" + succ.getData().getId());
					s.push(succ);
				}
			}
		}
		
//		HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> L = new HashMap<Pair<Node, Node>, HashSet<OWNLiteral>>();
//		L.put(new Pair<Node, Node>(new Node("x"), new Node("y")), new HashSet<OWNLiteral>());
//		L.get(new Pair<Node, Node>(new Node("x"), new Node("y"))).add(new OWNLiteral("test#A"));
//		L.get(new Pair<Node, Node>(new Node("x"), new Node("y"))).add(new OWNLiteral("test#B"));
//		System.out.println(L.get(new Pair<Node, Node>(new Node("x"), new Node("y"))));
//		
//		byte nodeCode = 109;
//		Node n1 = new Node(Character.toString((char)nodeCode++));
//		System.out.println(n1);
//		Node n2 = new Node(Character.toString((char)nodeCode++));
//		System.out.println(n2);
//		
//		Node x = new Node("x");
//		Node y = new Node("y");
//		Node z = new Node("z");
//		Pair<Node, Node> p_xy = new Pair<Node, Node>(x,y);
//		Pair<Node, Node> p_xz = new Pair<Node, Node>(x,z);
//		HashMap<Node, HashSet<OWNAxiom>> Ln = new HashMap<Node, HashSet<OWNAxiom>>();
//		Ln.put(x, new HashSet<OWNAxiom>());
//		Ln.get(x).add(new OWNExistential(
//				new OWNLiteral("test#R"), 
//				new OWNUnion(new OWNLiteral("test#B"), new OWNLiteral("test#C"))
//				)
//		);
////		Ln.get(x).add(new OWNLiteral("test#A"));
////		Ln.get(x).add(new OWNUnion(new OWNLiteral("test#B"), new OWNLiteral("test#C")));
//		HashMap<Pair<Node, Node>, HashSet<OWNLiteral>> Lr = new HashMap<Pair<Node, Node>, HashSet<OWNLiteral>>();
//		Lr.put(p_xy, new HashSet<OWNLiteral>());
//		Lr.get(p_xy).add(new OWNLiteral("test#S"));
//		Ln.put(y, new HashSet<OWNAxiom>());
//		Ln.get(y).add(new OWNLiteral("test#A"));
//		Ln.get(y).add(new OWNUnion(new OWNLiteral("test#B"), new OWNLiteral("test#C")));
//		Lr.put(p_xz, new HashSet<OWNLiteral>());
//		Lr.get(p_xz).add(new OWNLiteral("test#S"));
//		Ln.put(z, new HashSet<OWNAxiom>());
//		Ln.get(z).add(new OWNLiteral("test#A"));
////		Ln.get(z).add(new OWNUnion(new OWNLiteral("test#B"), new OWNLiteral("test#C")));
//		
//		for (Node n : Ln.keySet()) {
//			System.out.println(n + " : " + Ln.get(n));
//		}
//		if (!Lr.isEmpty())
//			for (Pair<Node, Node> p : Lr.keySet())
//				System.out.println(p + " : " + Lr.get(p));
//		
//		for (OWNAxiom axiom : Ln.get(x)) {
//			OWNAxiomOperationVisitor visitor = new OWNAxiomOperationVisitor(x, Ln, Lr);
//			axiom.accept(visitor);
//			System.out.println(axiom + " : " + visitor.getOperations());
//		}
		
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		IRI conceptIRI = IRI.create(new File("ontologies/testConcept.owl"));
//		IRI tboxIRI = IRI.create(new File("ontologies/testTBox.owl"));
//		
//		OWLOntology oConcept = null;
//		OWLOntology oTbox = null;
//		try {
//			oConcept = man.loadOntology(conceptIRI);
//			oTbox = man.loadOntology(tboxIRI);
//		} catch (OWLOntologyCreationException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		OWNAxiom concept = null;
//		HashSet<OWNAxiom> K = new HashSet<OWNAxiom>();
//		
//		for (OWLAxiom axiom : oConcept.getAxioms()) {
//			if (axiom.isOfType(AxiomType.SUBCLASS_OF)) {
//				// Cast to SubClassOf to get the superclass, which is of ClassExpression
//				OWLSubClassOfAxiomImpl subclass = (OWLSubClassOfAxiomImpl)axiom.getNNF();
//				OWLClassExpressionCastVisitor visitor = new OWLClassExpressionCastVisitor();
//				(subclass.getSuperClass()).accept(visitor);
//				concept = visitor.getAxiom();
//			}
//		}
//		K.add(concept);
//		
//		for (OWLAxiom axiom : oTbox.getAxioms()) {
//			if (axiom.isOfType(AxiomType.SUBCLASS_OF)) {
//				// Cast to SubClassOf to get the superclass, which is of ClassExpression
//				OWLSubClassOfAxiomImpl subclass = (OWLSubClassOfAxiomImpl)axiom.getNNF();
//				OWLClassExpressionCastVisitor visitor = new OWLClassExpressionCastVisitor();
//				(subclass.getSuperClass()).accept(visitor);
//				K.add(visitor.getAxiom());
//			}
//		}
//		
//		System.out.println(concept);
//		System.out.println(K);
//		
//		for (OWNAxiom axiom : K) {
//			OWNAxiomOperationVisitor visitor = new OWNAxiomOperationVisitor();
//			axiom.accept(visitor);
//		}
		
//		OWNAxiom other = new OWNLiteral("test#A");
//		OWNComplement comp = new OWNComplement(new OWNLiteral("test#A"));
//		System.out.println(other.equals(comp.getOperand()));
//		
//		OWNUnion un1 = new OWNUnion(new OWNLiteral("test#A"), new OWNLiteral("test#B"));
//		OWNUnion un2 = new OWNUnion(new OWNLiteral("test#A"), new OWNLiteral("test#B"));
//		System.out.println(un1.equals(un2));
//		
//		OWNIntersection in1 = new OWNIntersection(new OWNLiteral("test#A"), new OWNLiteral("test#B"));
//		OWNIntersection in2 = new OWNIntersection(new OWNLiteral("test#A"), new OWNLiteral("test#B"));
//		System.out.println(in1.equals(in2));
//		
//		OWNExistential ex1 = new OWNExistential(new OWNLiteral("test#R"), new OWNUnion(new OWNLiteral("test#A"), new OWNLiteral("test#B")));
//		OWNExistential ex2 = new OWNExistential(new OWNLiteral("test#R"), new OWNUnion(new OWNLiteral("test#A"), new OWNLiteral("test#B")));
//		System.out.println(ex1.equals(ex2));
//		
//		OWNUniversal ux1 = new OWNUniversal(new OWNLiteral("test#R"), new OWNIntersection(new OWNLiteral("test#A"), new OWNLiteral("test#B")));
//		OWNUniversal ux2 = new OWNUniversal(new OWNLiteral("test#R"), new OWNIntersection(new OWNLiteral("test#A"), new OWNLiteral("test#B")));
//		System.out.println(ux1.equals(ux2));
		
//		HashSet<OWNAxiom> L = new HashSet<OWNAxiom>();
//		L.add(new OWNLiteral("test#A"));
//		L.add(new OWNLiteral("test#B"));
//		L.add(new OWNUnion(new OWNLiteral("test#A"), new OWNLiteral("test#B")));
//		L.add(new OWNComplement(new OWNLiteral("test#A")));
//		L.add(new OWNExistential(new OWNLiteral("test#R"), new OWNLiteral("test#B")));
//		System.out.println(L);
//		HashSet<Operation> operations = new HashSet<Operation>();
//		for (OWNAxiom axiom : L) {
//			OWNAxiomOperationVisitor visitor = new OWNAxiomOperationVisitor(L);
//			axiom.accept(visitor);
//			operations.addAll(visitor.getOperations());
//		}
//		
//		System.out.println(operations);
		
//		Node x = new Node("x");
//		Node n = new Node("x");
//		System.out.println(n.equals(x));
		
//		HashSet<Operation> operations = new HashSet<Operation>();
//		OWNLiteral lit = new OWNLiteral("test#A");
//		OWNComplement comp = new OWNComplement(lit);
//		Operation opA = new Operation(OPERATOR.BOTTOM, lit);
//		operations.add(opA);
//		Operation opB = new Operation(OPERATOR.BOTTOM, lit);
//		operations.add(opB);
//
//		if (opB.equals(opA))
//			System.out.println("Equals");
//		System.out.println(operations.size());
//		
//		OWNAxiom ax = lit;
//		if (ax.equals(comp.getOperand()))
//			System.out.println("here");
		
		
//		OWNLiteral litA = new OWNLiteral("test#A");
//		OWNLiteral litB = new OWNLiteral("test#B");
//		OWNLiteral litR = new OWNLiteral("test#R");
//		OWNLiteral litS = new OWNLiteral("test#S");
//		
//		OWNComplement cA = new OWNComplement(litA);
//		
//		OWNUnion union1 = new OWNUnion(cA, litB);
//		OWNIntersection inter1 = new OWNIntersection(litB, cA);
//		
//		OWNExistential exists1 = new OWNExistential(litR, union1);
//		OWNUniversal univers1 = new OWNUniversal(litS, exists1);
//		
//		System.out.println(cA);
//		System.out.println(union1);
//		System.out.println(inter1);
//		System.out.println(exists1);
//		System.out.println(univers1);
		
//		OWNLiteral litA = new OWNLiteral("test#A");
//		OWNLiteral litB = new OWNLiteral("test#B");
//		OWNLiteral litR = new OWNLiteral("test#R");
//		OWNUnion un = new OWNUnion(litA, litB);
//		OWNExistential ex = new OWNExistential(litR, litB);
//		HashSet<OWNAxiom> set = new HashSet();
//		set.add(litA);
//		set.add(litB);
//		set.add(un);
//		set.add(ex);
//		for (OWNAxiom ax : set) {
//			if (ax.isLiteral()) {
//				OWNLiteral lit = (OWNLiteral)ax;
//				System.out.println(lit.getId());
//			} else if (ax.isOfType(AXIOM_TYPE.EXISTENTIAL)) {
//				OWNExistential exr = (OWNExistential)ax;
//				System.out.println(exr.getRelation().getId());
//			}
//		}
		
//		OWNLiteral lit = new OWNLiteral("a", "test#a");
//		OWNAxiom ax = (OWNAxiom)lit;
//		System.out.println(ax.isLiteral());
//		OWNUnion un = new OWNUnion(null, null);
//		OWNAxiom ax = (OWNAxiom)un;
//		System.out.println(ax.isOfType(AXIOM_TYPE.INTERSECTION));
		
//		Node first = new Node("a", "test#a");
//		Node secondA = new Node("b1", "test#b1");
//		Node secondB = new Node("b2", "test#b2");
//		Node third = new Node("c", "test#c");
//		
//		first.addSuccessor(secondA);
//		//secondA.setPredecessor(first);
//		first.addSuccessor(secondB);
//		//secondB.setPredecessor(first);
//		secondA.addSuccessor(third);
//		//third.setPredecessor(secondA);
//		
//		System.out.println(first);
//		System.out.println(secondA);
//		System.out.println(secondB);
//		System.out.println(third);
//		System.out.println();
//		
//		Node c = first.clone();
//		c.setId("clone");
//		System.out.println(c);
//		for (Node n : c.getSuccessors())
//			n.setId("changed");
//		System.out.println(c);
//		System.out.println(first);
//		//Node secondC = new Node("b3", "test#b3");
//		//c.addSuccessor(secondC);
//		//System.out.println(first);
//		//System.out.println(c);
//		//System.out.println(secondC);
		
//		OWLOntologyManager man = OWLManager.createOWLOntologyManager();
//		//IRI ontologyIRI = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");
//		IRI ontologyIRI = IRI.create(new File("ontologies/testTBox.owl"));
//		
//		try {
//			OWLOntology ontology = man.loadOntology(ontologyIRI);
//			System.out.println(ontologyIRI.toString());
//			System.out.println(ontology.getLogicalAxiomCount() + "\n");
//			
//			Set<OWLAxiom> axioms = ontology.getAxioms();
//			for (OWLAxiom axiom : axioms) {
////				System.out.println(axiom);
////				System.out.println(axiom.getNNF());
////				System.out.println(axiom.getAxiomType());
////				if (axiom.isOfType(AxiomType.DECLARATION)) {
////					OWLDeclarationAxiomImpl declaration = (OWLDeclarationAxiomImpl)axiom;
////					System.out.println(declaration.getIndividualsInSignature());
////					System.out.println(declaration.getEntity());
////					System.out.println(declaration.getSignature());
////				}
//				if (axiom.isOfType(AxiomType.SUBCLASS_OF)) {
//					System.out.println(axiom.getNNF());
//					OWLSubClassOfAxiomImpl subclass = (OWLSubClassOfAxiomImpl)axiom;
//					System.out.println(subclass.getSuperClass());
//					System.out.println((subclass.getSuperClass()).getClass());
//					System.out.println((OWLClassExpressionImpl)subclass.getSuperClass());
//					
//					if ((subclass.getSuperClass()).getClass() == OWLObjectIntersectionOfImpl.class) {
//						OWLObjectIntersectionOfImpl intersection = (OWLObjectIntersectionOfImpl)subclass.getSuperClass();
//						System.out.println(intersection);
//						
//						System.out.println(intersection.getOperands());
//						for (OWLClassExpression operand : intersection.getOperands()) {
//							System.out.println(operand.isClassExpressionLiteral());
//							
//							if (operand.isClassExpressionLiteral()) {
//								String lit = operand.toString();
//								System.out.println(lit.substring(1, lit.length()-1));
//							}
//						}
//					}
//					
//					if ((subclass.getSuperClass()).getClass() == OWLObjectUnionOfImpl.class) {
//						OWLObjectUnionOfImpl union = (OWLObjectUnionOfImpl)subclass.getSuperClass();
//						System.out.println(union);
//						
//						System.out.println(union.getOperands());
//						for (OWLClassExpression operand : union.getOperands()) {
//							System.out.println(operand.isClassExpressionLiteral());
//							
//							if (!operand.isClassExpressionLiteral()) {
//								System.out.println(operand.getClass());
//								OWLClassExpressionImpl cexpZ = (OWLClassExpressionImpl)operand;
//								System.out.println(cexpZ);
//								System.out.println(cexpZ.isClassExpressionLiteral());
//								System.out.println(cexpZ.getClass());
//								
//								OWLObjectSomeValuesFromImpl existential = (OWLObjectSomeValuesFromImpl)operand;
//								System.out.println(existential.isClassExpressionLiteral());
//								System.out.println("filler-" + existential.getFiller());
//								System.out.println("filler-" + existential.getFiller().isClassExpressionLiteral());
//								System.out.println("property-" + existential.getProperty());
//							}
//						}
//					}
//					
//					if (subclass.getSuperClass().getClass() == OWLObjectComplementOfImpl.class) {
//						OWLObjectComplementOfImpl complement = (OWLObjectComplementOfImpl)subclass.getSuperClass();
//						System.out.println(complement.isClassExpressionLiteral());
//						System.out.println(complement.getComplementNNF());
//					}
//					
//					System.out.println("------");
//				}
//			}
//			
//		} catch (OWLOntologyCreationException e) {
//			e.printStackTrace();
//		}
	}

}
