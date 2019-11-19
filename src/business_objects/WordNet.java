package business_objects;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import net.didion.jwnl.JWNL;
import net.didion.jwnl.data.IndexWord;
import net.didion.jwnl.data.POS;
import net.didion.jwnl.dictionary.Dictionary;


public class WordNet {

	/*constructor*/
	public WordNet() {
		
	}
	
	public String[] getTypes(String[] triple) throws IOException {
		String stype = "";
		String otype = "";
		String vcat = "";
		String vprocess = "";
		String subject = triple[0];
		String verb = triple[1];
		String object = triple[2];
		
		vprocess = findIfProcess(verb);
		
		// initialize JWNL (this must be done before JWNL can be used)
		try {
			JWNL.initialize(new FileInputStream("src/resources/file_properties.xml"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		//get stype and otype
		stype = getWordType(subject);
		otype = getWordType(object);
		
		vcat = findIfTransitive(verb);
		
		String[] types = {stype, otype, vcat, vprocess};
		return types;
	}
	
	//determines if verb is a process
	private String findIfProcess(String verb) {
		String vprocess = "0";
		String result;
		String[] sp_result;
		String temp_verb; //if verb has more than one word, this is the chosen verb to look for
		String[] verb_words = verb.split(" ");
		
		if(verb_words.length>=2) { //if triple has more than one word for verb
			temp_verb = verb_words[verb_words.length - 1]; //choose last word 
		}else {
			temp_verb = verb; //if triple has only one word for verb
		}
		
		try {
			result = getDerived(temp_verb); //execute wordnet command and returns terminal output as string
			sp_result = result.replace(",", "").split(" "); //remove commas and split based on spaces to get every word
			for(String token : sp_result) { //for each word
				if(token.compareTo(" ")!=0 || !token.isEmpty()) { //check if token is not empty
					if(token.length()>=3 && temp_verb.length()>=3){ //if token and verb are 3 or more characters long
						if(token.startsWith(temp_verb.substring(0, 3)) && token.endsWith("ion")) { //if token is a variation of the verb ending in -ion
							vprocess = "1";
						}
					}else{ //if token or verb are too short (2 characters or less)
						if(token.endsWith("ion")) { //if it SOMEHOW ends with -ion *confused programmer noises*
							vprocess = "1";
						}
					}
					
				}
			}
		}catch(IOException ioex){
			System.out.println("Error executing WordNet Command. Word: "+verb);
			ioex.printStackTrace();
		}catch(Exception ex){
			System.out.println("Error executing WordNet Command. Word: "+verb);
			ex.printStackTrace();
		}
		return vprocess;
	}
	
	//determines if verb is transitive
	private String findIfTransitive(String verb) {
		String vcat = "0";
		String temp_verb;
		boolean result;
		String[] verb_words = verb.split(" "); //all words in the verb
		
		if(verb_words.length>=2) { //if more than 1 word in a verb
			temp_verb = verb_words[verb_words.length - 1]; //use the last word
		}else {
			temp_verb = verb;
		}
		
		try{
			result = getFrames(temp_verb); //check if verb needs an object to make sense (AKA is not transitive)
			if(result){ vcat = "1"; }
		}catch(IOException ioex){
			System.out.println("Error executing WordNet Command");
			ioex.printStackTrace();
		}catch(Exception ex){
			System.out.println("Error executing WordNet Command");
			ex.printStackTrace();
		}
		return vcat;
	}
	
	//helper function for findIfProcess(). Looks for instances where verb+ion exist.
	private String getDerived(String word) throws IOException {
		String line = null;
		String result = "";
		List<String> command = new ArrayList<String>(); //command to execute as if on terminal
		command.add("D:\\Program Files/WordNet/2.1/bin/wn.exe");
		command.add(word);
		command.add("-deriv");
		
		ProcessBuilder pb = new ProcessBuilder(command);
		Process p = pb.start(); //execute command
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); //result from command
		while((line = br.readLine()) != null) { //add each line in the result to its String representation
			result+=line;
		}
		
		return result;
	}
	
	//helper function for findIfTransitive(). Finds if verb needs a subject and object to make sense. (transitive)
	private boolean getFrames(String word) throws IOException {
		String line = null;
		String[] tokens;
		boolean size_check = true;
		List<String> command = new ArrayList<String>(); //command to execute as if on terminal
		command.add("D:\\Program Files/WordNet/2.1/bin/wn.exe");
		command.add(word);
		command.add("-framv");
		
		ProcessBuilder pb = new ProcessBuilder(command);
		Process p = pb.start(); //execute command
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); //command output
		while((line = br.readLine()) != null && size_check) { //for each line in the output and while
			if(!line.isEmpty()) {
				if(line.contains("*>")) { //line with relevant information for this context
					if(line.contains("----ing")) { tokens = line.split("----ing"); } //verb can be presented as "----ing"
					else { tokens = line.split("----s"); } //or as "----s". Split will result in only subject or subject and object.
					if(tokens.length!=2) { //if there is a subject AND an object
						size_check = false; //verb is not transitive, since it needs an object to make sense
					}
				}
			}
		}
		return size_check;
	}	
	
	//return word type as determined by WordNet. Assumes word is a NOUN
	private String getWordType(String word) {
		String type = "";		
		try {
			IndexWord iword = Dictionary.getInstance().lookupIndexWord(POS.NOUN, word);
			if(iword!=null) { //if word exists in dictionary
				type = iword.getSenses()[0].getLexFileName().split("\\.")[1];
			}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return type;
	}
	
	public static void main(String[] args) throws IOException {
	      /*WordNet wn = new WordNet();
	      String[] triple = {"user", "construct", "diagram"};
	      wn.getTypes(triple);*/
	}
	
}
