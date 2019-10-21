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
		String msg = "";
		ArrayList<String> reqs = new ArrayList<String>();
		ArrayList<String[]> triples = new ArrayList<String[]>();
		String[] tags;
		String[] types;
		int req_no = 0;
		
		Requirements r = new Requirements();
		OpenIE oi = new OpenIE();
		StanfordNLP nlp = new StanfordNLP();
		WordNet wn = new WordNet();
		FeatureVector fv = null;
		
		try{
			reqs = r.getRequirements(filename);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		try{
			for(String requirement : reqs){
				System.out.println("Requirement #" + req_no + ": " + requirement);
				triples = oi.getTriple(requirement);
				for(String[] triple : triples){
					tags = nlp.getTypes(triple);
					types = wn.getTypes(triple);
					if(fv==null){
						fv = new FeatureVector();
					}
					fv.addRow(req_no, requirement,triple, tags, types);
					fv.writeFV();
				}
				req_no+=1;
			}
			msg = "The Feature Vector was generated successfully";
		}catch(Exception ex){
			msg = "There was an error generating the Feature Vector.";
			ex.printStackTrace();
		}
		return msg;
	}
	
	public static void main(String[] args) {
		String msg;
	    GenerateFVController fv_controller = new GenerateFVController();
	    msg = fv_controller.generateFV("src/resources/requirements.txt");
	    System.out.println(msg);
	}
	
}
