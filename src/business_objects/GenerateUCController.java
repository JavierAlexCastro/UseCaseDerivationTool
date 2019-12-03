package business_objects;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class GenerateUCController {
	
	MachineLearningModel mlm;
	UseCase usecase;
	private static GenerateUCController generate_controller; //singleton pattern
	
	GenerateUCController(){
		mlm = new MachineLearningModel(); //creator pattern
	}
	
	//Singleton Pattern
	public static GenerateUCController getInstance() {
		if(generate_controller == null) {
			generate_controller = new GenerateUCController();
		}
		return generate_controller;
	}
	
	public String labelRequirements(String filename, String alg){
		String msg;
		try {
			mlm.makePrediction(filename, alg);
			
			usecase = new UseCase(); //creator pattern
			FileInputStream fstream = new FileInputStream("src/outputs/USECASES_"+filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String line;
			while ((line = br.readLine()) != null) { //while there are lines in the file
			  usecase.extractUC(line); //extract actor, verb, and object for that use case
			}
			fstream.close();
			usecase.printUseCases();
			
			msg = " Usecase generated successfully! Check: src/outputs/";
		} catch (Exception e) {
			msg = " There was a problem generating Use Cases";
			e.printStackTrace();
		}
		return msg;
	}

}
