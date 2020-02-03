package business_objects;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import weka.core.Instances;
import weka.classifiers.AbstractClassifier;
import weka.classifiers.lazy.IBk;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.rules.ZeroR;
import weka.classifiers.trees.DecisionStump;
import weka.classifiers.trees.REPTree;
import weka.core.converters.CSVLoader;
import weka.classifiers.meta.AutoWEKAClassifier;


public class MachineLearningModel {
	 private double accuracy;
	 private AbstractClassifier classifier = null;
	 
	 //private IBk classifier = null;
	 String[] tokenized_req;
	 String req;
	 
	 /*constructor*/
	 public MachineLearningModel() {
		 
	 }
	 
	 //Chain of Responsibility to handle exception somewhere else
	 public void makePrediction(String filename, String alg) throws Exception{
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
		 Instances train = null;
		 try{
			 train = new Instances(
				                 new BufferedReader(
				                   new FileReader("src/resources/labelledFile_true_false_version.arff")));
		 }catch(Exception e){
			 System.out.println("Error loading training data (src/resources/labelledFile_true_false_version.arff)");
			 e.printStackTrace();
		 }

		 
		 unlabeled.setClassIndex(unlabeled.numAttributes()-1); // set class attribute to "Label"
		 Instances labeled = new Instances(unlabeled); // create copy to label
		 
		 //build classifier
		 //Strategy pattern
		 switch(alg) {
		 case "IBk":classifier = new IBk();break;
		 case "DecisionStump":classifier = new DecisionStump();break;
		 case "Decision Table":classifier = new DecisionTable();break;
		 case "REPTree":classifier = new REPTree();break;
		 case "ZeroR":classifier = new ZeroR();break;
		 case "AutoWEKA":
			 classifier = new AutoWEKAClassifier();
		     ((AutoWEKAClassifier) classifier).setTimeLimit(1);
		     break;
		 default:
			 classifier = new AutoWEKAClassifier();
		     ((AutoWEKAClassifier) classifier).setTimeLimit(1);
		     break;
		 }
		 train.setClassIndex(train.numAttributes() - 1); //sets class index to 'label' attribute
		 
		//setting strategy
		 Context context = new Context();
		 context.setStrategy(classifier);
		 
		//performing steps of algorithm
		 labeled = context.performSteps(train, fname, unlabeled, labeled);
		 
		 // save labeled data - can be removed. Only used for debugging.
		 /*BufferedWriter arff_writer = new BufferedWriter(
		                           new FileWriter("src/outputs/LABELED_" +fname+".arff"));
		 arff_writer.write(labeled.toString());
		 arff_writer.newLine();
		 arff_writer.flush();
		 arff_writer.close();*/
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
