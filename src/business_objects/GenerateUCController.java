package business_objects;

public class GenerateUCController {
	
	MachineLearningModel mlm;
	
	GenerateUCController(){
		mlm = new MachineLearningModel();
	}
	
	public String labelRequirements(String filename){
		String msg;
		try {
			mlm.makePrediction(filename);
			msg = " Usecase generated successfully! Check: src/outputs/";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			msg = " There was a problem generating Use Cases";
			e.printStackTrace();
		}
		return msg;
	}

}
