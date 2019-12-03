package business_objects;

import java.io.PrintWriter;

import weka.classifiers.AbstractClassifier;
import weka.core.Instances;

public class Context {
	AbstractClassifier classifier;
	 String[] tokenized_req;
	 String req;

	public void setStrategy(AbstractClassifier classifier) {
		this.classifier = classifier;
		
	}

	//implements Strategy Pattern
	public Instances performSteps(Instances train, String fname, Instances unlabeled, Instances labeled) throws Exception {
		System.out.println("Training the Classifier. Please wait...");
		classifier.buildClassifier(train);
		System.out.println("Making a prediction. Please wait...");
		PrintWriter uc_writer = new PrintWriter("src/outputs/USECASES_"+fname+".txt", "UTF-8"); //writer for labeled instances
		double clsLabel; //predicted value

		//label instances
		for (int i = 0; i < unlabeled.numInstances(); i++) { //for each row in feature vector
			clsLabel = classifier.classifyInstance(unlabeled.instance(i));
			//System.out.println(clsLabel); //for test purposes - prints decision for each row
			if(clsLabel>=0.5){ //if 50% or more confidence, then a 1
				clsLabel = 1;
				tokenized_req = unlabeled.get(i).toString().split(","); //split feature vector row into attributes
				req = "Subject: "+tokenized_req[0]+" - Verb: "+tokenized_req[4]+" - Object: " + tokenized_req[8]; //create use case based on subject, verb and object
				uc_writer.println(req); //write use case to file
			}else{ //else a 0
				clsLabel = 0;
			}
			labeled.instance(i).setClassValue(clsLabel); //update label for that row
		}
		uc_writer.close();
		return labeled;
	}

}
