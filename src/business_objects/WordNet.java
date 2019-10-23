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
		
		//determines if verb is a process
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
		
		//get vcat (determine if verb is transitive)
		vcat = findIfTransitive(verb);
		
		String[] types = {stype, otype, vcat, vprocess};
		return types;
	}
	
	private String findIfProcess(String verb) {
		String vprocess = "0";
		String result;
		String[] sp_result;
		String temp_verb;
		String[] verb_words = verb.split(" ");
		
		if(verb_words.length>=2) {
			temp_verb = verb_words[1];
			//System.out.println(temp_verb);
		}else {
			temp_verb = verb;
		}
		
		try {
			result = getDerived(temp_verb);
			sp_result = result.replace(",", "").split(" ");
			for(String token : sp_result) {
				if(token.compareTo(" ")!=0 || !token.isEmpty()) {
					if(token.startsWith(temp_verb.substring(0, 3)) && token.endsWith("ion")) {
							//System.out.println(token);
							vprocess = "1";
					}
				}
			}
		}catch(IOException ioex){
			System.out.println("Error executing WordNet Command");
			ioex.printStackTrace();
		}catch(Exception ex){
			System.out.println("Error executing WordNet Command");
			ex.printStackTrace();
		}
		return vprocess;
	}
	
	private String findIfTransitive(String verb) {
		String vcat = "0";
		String temp_verb;
		boolean result;
		String[] verb_words = verb.split(" ");
		
		if(verb_words.length>=2) {
			temp_verb = verb_words[1];
			//System.out.println(temp_verb);
		}else {
			temp_verb = verb;
		}
		
		try{
			result = getFrames(temp_verb);
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
	
	private String getDerived(String word) throws IOException {
		String line = null;
		String result = "";
		List<String> command = new ArrayList<String>();
		command.add("D:\\Program Files/WordNet/2.1/bin/wn.exe");
		command.add(word);
		command.add("-deriv");
		
		ProcessBuilder pb = new ProcessBuilder(command);
		Process p = pb.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while((line = br.readLine()) != null) {
			result+=line;
			//System.out.println(line);
		}
		
		return result;
	}
	
	private boolean getFrames(String word) throws IOException {
		String line = null;
		String[] tokens;
		boolean size_check = true;
		List<String> command = new ArrayList<String>();
		command.add("D:\\Program Files/WordNet/2.1/bin/wn.exe");
		command.add(word);
		command.add("-framv");
		
		ProcessBuilder pb = new ProcessBuilder(command);
		Process p = pb.start();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
		while((line = br.readLine()) != null && size_check) {
			if(!line.isEmpty()) {
				if(line.contains("*>")) { 
					if(line.contains("----ing")) { tokens = line.split("----ing"); }
					else { tokens = line.split("----s"); }
					if(tokens.length!=2) {
						size_check = false;
					}
					//System.out.println(line);
				}
			}
		}
		return size_check;
	}	
	
	private String getWordType(String word) {
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
	
	public static void main(String[] args) throws IOException {
	      WordNet wn = new WordNet();
	      String[] triple = {"user", "construct", "diagram"};
	      wn.getTypes(triple);
	}
	
}
