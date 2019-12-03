package business_objects;

import java.util.ArrayList;
import java.util.Iterator;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.simple.*;

public class OpenIE {
	
	/*constructor*/
	public OpenIE() {
		
	}
	
	//Chain of Responsibility to handle exception somewhere else	
	public ArrayList<String[]> getTriple(String requirement) throws Exception {
		ArrayList<String[]> triples = new ArrayList<String[]>(); //list of triples for requirement
		String[] single_triple = {"", "", ""}; //triple in array form - single triple
	    Document doc = new Document(requirement); // Create a CoreNLP document
	    String[] tokens;
	    // Iterate over the sentences in the document
	    Iterator<Sentence> sent_iterator = doc.sentences().iterator(); //iterator
	    while(sent_iterator.hasNext()) {
	    	Sentence sent = sent_iterator.next();
	    	Iterator<RelationTriple> triple_iterator = sent.openieTriples().iterator(); //iterator
	    	while(triple_iterator.hasNext()){
	    		RelationTriple temp_triple = triple_iterator.next();
	    		single_triple = tripleToArray(temp_triple); //sentence can have multiple triples, this is placeholder for one of them
	    		if(single_triple[0].contains(" ")){ //if subject part of triple contains a space (more than 1 word)
	    			tokens = single_triple[0].split(" "); //choose only one word as subject, otherwise arff will enclose in ''
	    			single_triple[0] = tokens[tokens.length-1];
	    		}
	    		if(single_triple[1].contains(" ")){ //same for verb
	    			tokens = single_triple[1].split(" ");
	    			single_triple[1] = tokens[tokens.length-1];
	    		}
	    		if(single_triple[2].contains(" ")){ //same for object
	    			tokens = single_triple[2].split(" ");
	    			single_triple[2] = tokens[tokens.length-1];
	    		}
	    		triples.add(single_triple); //add single triple to triples list
	    	}
	    	printTriples(triples);  		
	    }
	    /*for (Sentence sent : doc.sentences()) {
	    	// Iterate over the triples in the sentence
	    	for (RelationTriple temp_triple : sent.openieTriples()) {
	    		single_triple = tripleToArray(temp_triple); //sentence can have multiple triples, this is placeholder for one of them
	    		if(single_triple[0].contains(" ")){ //if subject part of triple contains a space (more than 1 word)
	    			tokens = single_triple[0].split(" "); //choose only one word as subject, otherwise arff will enclose in ''
	    			single_triple[0] = tokens[tokens.length-1];
	    		}
	    		if(single_triple[1].contains(" ")){ //same for verb
	    			tokens = single_triple[1].split(" ");
	    			single_triple[1] = tokens[tokens.length-1];
	    		}
	    		if(single_triple[2].contains(" ")){ //same for object
	    			tokens = single_triple[2].split(" ");
	    			single_triple[2] = tokens[tokens.length-1];
	    		}
	    		triples.add(single_triple); //add single triple to triples list
	    	}
	    	printTriples(triples);  		
	    }*/
	    return triples;
	}
	
	//prints all triples derived from requirement
	private void printTriples(ArrayList<String[]> array_triples) {
		for (int i = 0; i<array_triples.size(); i++) {
			System.out.print("Triple #" + i + ": ");
			System.out.print(array_triples.get(i)[0] + " | ");
			System.out.print(array_triples.get(i)[1] + " | ");
			System.out.print(array_triples.get(i)[2] + "\n");
		}
	}
	
	//converts a triple as returned by OpenIE to a string array representation
	private String[] tripleToArray(RelationTriple relation_triple) {
		String[] array_triple = {"", "", ""};
		array_triple[0] = relation_triple.subjectLemmaGloss();
		array_triple[1] = relation_triple.relationLemmaGloss();
		array_triple[2] = relation_triple.objectLemmaGloss();
		return array_triple;
	}
	
	public static void main(String[] args) {
	     /* OpenIE oie = new OpenIE();
	      oie.getTriple("The system shall allow the user to retrieve money from the ATM.");*/
	}

}
