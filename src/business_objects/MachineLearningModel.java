package business_objects;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import weka.core.Instances;
import weka.classifiers.lazy.IBk;
import weka.core.converters.CSVLoader;


public class MachineLearningModel {
	 private double accuracy;
	 private IBk classifier = null;
	 String[] tokenized_req;
	 String req;
	 
	 /*constructor*/
	 public MachineLearningModel() {
		 
	 }
	 
	 public void makePrediction(String filename) throws Exception{
		 String fname = filename.split("\\.")[0]; //file name without file extension
		 
		 // load CSV
		 CSVLoader loader = new CSVLoader();
		 loader.setSource(new File("src/outputs/FV_" + fname + ".csv"));
		 Instances data = loader.getDataSet();

		 //save as ARFF
		 BufferedWriter writer = new BufferedWriter(new FileWriter("src/outputs/ARFF_" + fname + ".arff"));
		 writer.write(data.toString());
		 writer.flush();
		 writer.close(); 
		 
		 // load unlabeled data
		 Instances unlabeled = new Instances(
		                         new BufferedReader(
		                           new FileReader("src/outputs/ARFF_" + fname + ".arff")));
		 //load model training data
		 Instances train = new Instances(
			                 new BufferedReader(
			                   new FileReader("src/resources/feature_vector.arff")));

		 
		 unlabeled.setClassIndex(unlabeled.numAttributes()-1); // set class attribute to "Label"
		 Instances labeled = new Instances(unlabeled); // create copy to label
		 
		 //build classifier
		 classifier = new IBk();
		 train.setClassIndex(train.numAttributes() - 1);
		 classifier.buildClassifier(train);
		 
		 PrintWriter uc_writer = new PrintWriter("src/outputs/USECASES_"+fname+".txt", "UTF-8"); //writer for labeled instances
		 double clsLabel; //predicted value

		 //label instances
		 for (int i = 0; i < unlabeled.numInstances(); i++) { //for each row in feature vector
		   clsLabel = classifier.classifyInstance(unlabeled.instance(i));
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
		 
		 // save labeled data - can be removed. Only used for debugging
		 BufferedWriter arff_writer = new BufferedWriter(
		                           new FileWriter("src/outputs/LABELED_" +fname+".arff"));
		 arff_writer.write(labeled.toString());
		 arff_writer.newLine();
		 arff_writer.flush();
		 arff_writer.close();
	 }
	 
	 public void setAccuracy(double accuracy) { 
		 this.accuracy=accuracy; 
	 }
	 
	 public double getAccuracy() {
		 return accuracy;
	 }
	 
	 public static void main(String[] args) {
		 /*MachineLearningModel mlm = new MachineLearningModel();
		 try {
			 mlm.makePrediction("Assignment1.txt");
		 } catch (Exception e) {
			 // TODO Auto-generated catch block
			 e.printStackTrace();
		 }*/
	   }

}
