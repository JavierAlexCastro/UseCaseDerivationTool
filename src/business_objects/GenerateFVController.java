package business_objects;

import java.util.ArrayList;
import java.util.Iterator;

import business_objects.Requirements;
import business_objects.OpenIE;
import business_objects.StanfordNLP;
import business_objects.WordNet;
import business_objects.FeatureVector;

public class GenerateFVController {

	private static GenerateFVController fv_controller; //singleton pattern
	
	public GenerateFVController(){
		
	}
	
	//singleton pattern
	public static GenerateFVController getInstance() {
		if(fv_controller == null) {
			fv_controller = new GenerateFVController();
			
		}
		return fv_controller;
	}
	
	public String generateFV(String filename){
		String msg = ""; //returning message
		//composite pattern
		ArrayList<String> reqs = new ArrayList<String>(); //list of requirements
		ArrayList<String[]> triples = new ArrayList<String[]>(); //list of triples
		String[] tags; //POS tags
		String[] types;
		int req_no = 0;
		
		Requirements r = new Requirements(); //creator pattern
		OpenIE oi = new OpenIE(); //creator pattern
		StanfordNLP nlp = new StanfordNLP(); //creator pattern
		WordNet wn = new WordNet(); //creator pattern
		FeatureVector fv = null; //creator pattern
		
		try{
			reqs = r.getRequirements(filename); //get requirements as a list from a file - expert pattern
		}catch(Exception ex){
			ex.printStackTrace();
			msg = " Error getting the requirements from the file";
			return msg;
		}
		
		try{
			Iterator<String> reqs_iterator = reqs.iterator(); //iterator pattern
			while (reqs_iterator.hasNext()) { //iterator pattern
				String requirement = reqs_iterator.next();
				System.out.println("Requirement #" + req_no + ": " + requirement);
				triples = oi.getTriple(requirement); //get list of triples for that requirement from openIE - expert pattern
				Iterator<String[]> triple_iterator = triples.iterator(); //iterator pattern
				while (triple_iterator.hasNext()) {
					String[] triple = triple_iterator.next();
					tags = nlp.getTypes(triple); //get the tags from stanfordNLP - expert pattern
					types = wn.getTypes(triple); //get the types from wordNet - expert pattern
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
