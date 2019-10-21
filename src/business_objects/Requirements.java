package business_objects;
import java.util.ArrayList;
import java.io.*;

public class Requirements {
	private ArrayList<String> sentences;
	
	/*Constructor*/
	public Requirements(){
		
	}
	
	public ArrayList<String> getRequirements(String filename) throws Exception {
		//open requirements file and populate `sentences`
		BufferedReader br = new BufferedReader(new FileReader(filename)); 
		String st; 
		ArrayList<String> requirement = new ArrayList<String>();
		while ((st = br.readLine()) != null) 
			//			    System.out.println(st); 
			requirement.add(st);
		//System.out.println(requirement);
		br.close();
		return requirement;
	}
	
	public ArrayList<String> getSentences() {
		return sentences;
	}
	
	public static void main(String[] args) {
	      Requirements reqs = new Requirements();
	      String filename = "src/resources/requirements.txt";
	      try{
	    	  reqs.getRequirements(filename);
	      }catch(Exception ex){
	    	  ex.printStackTrace();
	      }
	}
	
}
