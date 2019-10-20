package business_objects;

import java.util.ArrayList;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.simple.*;

public class OpenIE {
	
	/*constructor*/
	public OpenIE() {
		
	}
	
	public ArrayList<String[]> getTriple(String requirement) {
		ArrayList<String[]> triples = new ArrayList<String[]>();
		String[] single_triple = {"", "", ""}; //triple in array form
	    Document doc = new Document(requirement); // Create a CoreNLP document
	    // Iterate over the sentences in the document
	    for (Sentence sent : doc.sentences()) {
	    	// Iterate over the triples in the sentence
	    	for (RelationTriple temp_triple : sent.openieTriples()) {
	    		single_triple = tripleToArray(temp_triple);
	    		triples.add(single_triple);
	    	}
	    	printTriples(triples);  		
	    }
	    return triples;
	}
	
	private void printTriples(ArrayList<String[]> array_triples) {
		for (int i = 0; i<array_triples.size(); i++) {
			System.out.print("Triple #" + i + ": ");
			System.out.print(array_triples.get(i)[0] + " ");
			System.out.print(array_triples.get(i)[1] + " ");
			System.out.print(array_triples.get(i)[2] + "\n");
		}
	}
	
	private String[] tripleToArray(RelationTriple relation_triple) {
		String[] array_triple = {"", "", ""};
		array_triple[0] = relation_triple.subjectLemmaGloss();
		array_triple[1] = relation_triple.relationLemmaGloss();
		array_triple[2] = relation_triple.objectLemmaGloss();
		return array_triple;
	}
	
	public static void main(String[] args) {
	      OpenIE oie = new OpenIE();
	      oie.getTriple("The system shall allow the user to retrieve money from the ATM.");
	}

}
