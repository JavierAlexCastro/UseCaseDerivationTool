package business_objects;

import java.util.ArrayList;

public class UseCase {
	private ArrayList<String> actors;
	private ArrayList<String> verbs;
	private ArrayList<String> objects;
	
	/*constructor*/
	public UseCase() {
		actors = new ArrayList<>();
		verbs = new ArrayList<>();
		objects = new ArrayList<>();
	}
	
	public void extractUC(String usecase) throws Exception{
		String[] line_tokens = usecase.split("-"); //split into subject, verb, object
		
		actors.add(line_tokens[0].replace(" ","").split(":")[1]);
		verbs.add(line_tokens[1].replace(" ","").split(":")[1]);
		objects.add(line_tokens[2].replace(" ","").split(":")[1]);
	}
	
	public void printUseCases() throws Exception{
		System.out.println("================================");
		for(int i=0; i<actors.size() && i<verbs.size() && i<objects.size(); i++){
			System.out.println("Use Case #: " + (i+1));
			System.out.println("Actor: " + actors.get(i));
			System.out.println("Verb: " + verbs.get(i));
			System.out.println("Object: " + objects.get(i));
			System.out.println("================================");
		}
	}
	
}
