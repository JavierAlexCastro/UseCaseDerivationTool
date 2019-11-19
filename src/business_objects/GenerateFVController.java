package business_objects;

import java.util.ArrayList;

import business_objects.Requirements;
import business_objects.OpenIE;
import business_objects.StanfordNLP;
import business_objects.WordNet;
import business_objects.FeatureVector;

public class GenerateFVController {

	public GenerateFVController(){
		
	}
	
	public String generateFV(String filename){
		String msg = ""; //returning message
		ArrayList<String> reqs = new ArrayList<String>(); //list of requirements
		ArrayList<String[]> triples = new ArrayList<String[]>(); //list of triples
		String[] tags; //POS tags
		String[] types;
		int req_no = 0;
		
		Requirements r = new Requirements();
		OpenIE oi = new OpenIE();
		StanfordNLP nlp = new StanfordNLP();
		WordNet wn = new WordNet();
		FeatureVector fv = null;
		
		try{
			reqs = r.getRequirements(filename); //get requirements as a list from a file
		}catch(Exception ex){
			ex.printStackTrace();
			msg = " Error getting the requirements from the file";
			return msg;
		}
		
		try{
			for(String requirement : reqs){ //for each requirement
				System.out.println("Requirement #" + req_no + ": " + requirement);
				triples = oi.getTriple(requirement); //get list of triples for that requirement from openIE
				for(String[] triple : triples){ //for each triple in the list
					tags = nlp.getTypes(triple); //get the tags from stanfordNLP
					types = wn.getTypes(triple); //get the types from wordNet
					if(fv==null){ //if haven't created an object yet
						fv = new FeatureVector();
					}
					fv.addRow(req_no, requirement,triple, tags, types);
				}
				req_no+=1;
			}
			fv.writeFV(filename); //write fv to csv file
			msg = " The Feature Vector was generated successfully";
		}catch(Exception ex){
			msg = " There was an error generating the Feature Vector.";
			ex.printStackTrace();
		}
		return msg;
	}
	
	public static void main(String[] args) {
		/*String msg;
	    GenerateFVController fv_controller = new GenerateFVController();
	    msg = fv_controller.generateFV("src/resources/requirements.txt");
	    System.out.println(msg);*/
	}
	
}
