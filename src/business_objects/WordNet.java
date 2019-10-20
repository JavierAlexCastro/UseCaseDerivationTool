package business_objects;

import java.util.List;
import java.io.FileInputStream;
import java.util.ArrayList;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.data.Pointer;
import net.didion.jwnl.data.PointerType;
import net.didion.jwnl.data.Synset;
import net.didion.jwnl.data.Word;
import net.didion.jwnl.dictionary.Dictionary;


public class WordNet {

	/*constructor*/
	public WordNet() {
		
	}
	
	public String[] getTypes(String[] triple) {
		String stype = "";
		String otype = "";
		String vcat = "";
		String vprocess = "";
		String subject = triple[0];
		String verb = triple[1];
		String object = triple[2];
		
		// initialize JWNL (this must be done before JWNL can be used)
		try {
			JWNL.initialize(new FileInputStream("src/resources/file_properties.xml"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//if verb does not already end with -ion, add -ion
		if(!verb.endsWith("ion")) { verb = verb+"ion"; }
		
		//check if verb is a valid word
		if(isValidWord(verb)) {
			vprocess = "Y";
			System.out.println("Word: " + verb + " is Valid");
		}else {
			vprocess = "N";
			System.out.println("Word: " + verb + " is NOT Valid");
		}
		
		//get stype and otype
		stype = getWordType(subject);
		otype = getWordType(object);
		
		//get vcat
		//The V-cat needs to check with WordNet to see if the verb is one of the synonyms to one of the words on the list
		
		String[] types = {stype, otype, vcat, vprocess};
		return types;
	}
	
	private String getWordType(String word){
		String type = "";
		try {
			IndexWord iword = Dictionary.getInstance().lookupIndexWord(POS.NOUN, word);
			if(iword!=null) {
				type = iword.getSenses()[0].getLexFileName().split("\\.")[1];
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return type;
	}
	
	private boolean isValidWord(String word) {
		boolean isWord = false;
		try {
			IndexWord iword = Dictionary.getInstance().lookupIndexWord(POS.VERB, word); //attempt to look up word as verb
			if( iword != null ) { //if return value is not null then it is a word
				isWord = true; 
			}else{ //attempt to look up word as noun
				iword = Dictionary.getInstance().lookupIndexWord(POS.NOUN, word);
				if( iword != null ) { //if return value is not null then it is a word
					isWord = true; 
				}
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return isWord;
	}
	
	/**
	* @return All synsets for the given word and POS category.
	*/
	private Synset[] synsetsOf(String token, POS postag) {
		try {
			IndexWord iword = Dictionary.getInstance().lookupIndexWord(postag, token);
			if( iword != null ) {
	    		Synset[] synsets = iword.getSenses();
	    		return synsets;
	    	}
		} catch( Exception ex ) { 
			ex.printStackTrace(); 
		}
		return null;
	}
	
	private List<String> synonymOf(String token) {
	    POS pos = POS.VERB;	    
	    // Get the synsets.
	    Synset[] synsets = synsetsOf(token.substring(2), pos);
	    List<String> synonyms = new ArrayList<String>();
	    System.out.println(synsets.toString());
	    /*
	    if( synsets != null ) {
	      for( Synset synset : synsets ) {
	        List<String> syms = _wordnet.wordsInSynset(synset);
//	        System.out.println("  token " + token + " synset " + synset + ": " + syms);
	        for( String sym : syms ) {
	          String strtoken = header + sym;
	          if( !synonyms.contains(strtoken) )
	            synonyms.add(strtoken);
	        }
	      }
	    }*/
	    return synonyms;
	}
	
	public static void main(String[] args) {
	      WordNet wn = new WordNet();
	      String[] triple = {"user", "retrieve", "ATM"};
	      wn.getTypes(triple);
	}
	
}
